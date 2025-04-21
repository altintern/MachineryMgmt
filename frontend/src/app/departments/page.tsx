'use client';

import React from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { DepartmentDto, DepartmentRequestDto, departmentService } from '@/services/departmentService';
import DataTable from '@/components/DataTable';
import toast from 'react-hot-toast';
import DepartmentForm from '@/components/DepartmentForm';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';

export default function DepartmentsPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedDepartment, setSelectedDepartment] = React.useState<DepartmentDto | undefined>();
  const queryClient = useQueryClient();

  // Fetch departments with error handling
  const { data: departments = [], isLoading, isError } = useQuery(
    ['departments'], 
    departmentService.getAllDepartments,
    {
      onError: (error) => {
        console.error('Failed to fetch departments:', error);
        toast.error('Failed to load departments. Please try again later.');
      }
    }
  );

  // Create department mutation
  const createMutation = useMutation(
    (data: DepartmentRequestDto) => departmentService.createDepartment(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['departments']);
        handleClose();
        toast.success('Department created successfully!');
      },
      onError: (error) => {
        console.error('Failed to create department:', error);
        toast.error('Failed to create department. Please try again.');
      }
    }
  );

  // Update department mutation
  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: DepartmentRequestDto }) =>
      departmentService.updateDepartment(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['departments']);
        handleClose();
        toast.success('Department updated successfully!');
      },
      onError: (error) => {
        console.error('Failed to update department:', error);
        toast.error('Failed to update department. Please try again.');
      }
    }
  );

  // Delete department mutation
  const deleteMutation = useMutation(
    (id: number) => departmentService.deleteDepartment(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['departments']);
        toast.success('Department deleted successfully!');
      },
      onError: (error) => {
        console.error('Failed to delete department:', error);
        toast.error('Failed to delete department. Please try again.');
      }
    }
  );

  const handleOpen = (department?: DepartmentDto) => {
    setSelectedDepartment(department);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedDepartment(undefined);
    setOpen(false);
  };

  const handleSubmit = (formData: DepartmentRequestDto) => {
    if (selectedDepartment) {
      updateMutation.mutate({ id: selectedDepartment.id, data: formData });
    } else {
      createMutation.mutate(formData);
    }
  };

  const handleDelete = (id: number) => {
    if (confirm('Are you sure you want to delete this department?')) {
      deleteMutation.mutate(id);
    }
  };

  const columns = [
    { key: 'name', label: 'Name' },
    { key: 'description', label: 'Description' },
  ];

  if (isLoading) {
    return <div className="p-6">Loading departments...</div>;
  }

  if (isError) {
    return <div className="p-6 text-red-500">Error loading departments. Please try again later.</div>;
  }

  return (
    <div className="p-6">
      <DataTable
        title="Departments"
        columns={columns}
        data={Array.isArray(departments) ? departments : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="sm:max-w-[500px]">
          <DialogHeader>
            <DialogTitle>
              {selectedDepartment ? 'Edit Department' : 'New Department'}
            </DialogTitle>
          </DialogHeader>
          <DepartmentForm
            department={selectedDepartment}
            onSubmit={handleSubmit}
            onCancel={handleClose}
          />
        </DialogContent>
      </Dialog>
    </div>
  );
}
