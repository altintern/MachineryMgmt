// File: src/app/assignments/page.tsx
'use client';

import React from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { EmployeeAssignmentDto, EmployeeAssignmentRequestDto, employeeAssignmentService } from '@/services/employeeAssignmentService';
import DataTable from '@/components/DataTable';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import toast from 'react-hot-toast';
import EmployeeAssignmentForm from '@/components/EmployeeAssignmentForm';

export default function AssignmentsPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedAssignment, setSelectedAssignment] = React.useState<EmployeeAssignmentDto | undefined>();
  const queryClient = useQueryClient();

  // Fetch employee assignments with error handling
  const { data: assignments = [], isLoading, isError } = useQuery(
    ['employeeAssignments'], 
    employeeAssignmentService.getAllEmployeeAssignments,
    {
      onError: (error) => {
        console.error('Failed to fetch employee assignments:', error);
        toast.error('Failed to load employee assignments. Please try again later.');
      }
    }
  );

  // Create employee assignment mutation
  const createMutation = useMutation(
    (data: EmployeeAssignmentRequestDto) => employeeAssignmentService.createEmployeeAssignment(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['employeeAssignments']);
        handleClose();
        toast.success('Employee assignment created successfully!');
      },
      onError: (error) => {
        console.error('Failed to create employee assignment:', error);
        toast.error('Failed to create employee assignment. Please try again.');
      }
    }
  );

  // Update employee assignment mutation
  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: EmployeeAssignmentRequestDto }) =>
      employeeAssignmentService.updateEmployeeAssignment(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['employeeAssignments']);
        handleClose();
        toast.success('Employee assignment updated successfully!');
      },
      onError: (error) => {
        console.error('Failed to update employee assignment:', error);
        toast.error('Failed to update employee assignment. Please try again.');
      }
    }
  );

  // Delete employee assignment mutation
  const deleteMutation = useMutation(
    (id: number) => employeeAssignmentService.deleteEmployeeAssignment(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['employeeAssignments']);
        toast.success('Employee assignment deleted successfully!');
      },
      onError: (error) => {
        console.error('Failed to delete employee assignment:', error);
        toast.error('Failed to delete employee assignment. Please try again.');
      }
    }
  );

  const handleOpen = (assignment?: EmployeeAssignmentDto) => {
    setSelectedAssignment(assignment);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedAssignment(undefined);
    setOpen(false);
  };

  const handleSubmit = (formData: EmployeeAssignmentRequestDto) => {
    if (selectedAssignment) {
      updateMutation.mutate({ id: selectedAssignment.id, data: formData });
    } else {
      createMutation.mutate(formData);
    }
  };

  const handleDelete = (id: number) => {
    if (confirm('Are you sure you want to delete this assignment?')) {
      deleteMutation.mutate(id);
    }
  };

  const columns = [
    { key: 'employee.name', label: 'Employee' },
    { key: 'project.name', label: 'Project' },
    { key: 'joiningDate', label: 'Joining Date' },
    { key: 'equipment.name', label: 'Equipment' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'joiningDate') {
      return new Date(item.joiningDate).toLocaleDateString();
    }
    
    if (column === 'employee.name' && item.employee) {
      return item.employee.name;
    }
    
    if (column === 'project.name' && item.project) {
      return item.project.name;
    }
    
    if (column === 'equipment.name' && item.equipment) {
      return item.equipment.name;
    }
    
    return null;
  };

  if (isLoading) {
    return <div className="p-6">Loading employee assignments...</div>;
  }

  if (isError) {
    return <div className="p-6 text-red-500">Error loading employee assignments. Please try again later.</div>;
  }

  return (
    <div className="p-6">
      <DataTable
        title="Employee Assignments"
        columns={columns}
        data={Array.isArray(assignments) ? assignments : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="sm:max-w-[700px]">
          <DialogHeader>
            <DialogTitle>{selectedAssignment ? 'Edit Assignment' : 'New Assignment'}</DialogTitle>
          </DialogHeader>
          <EmployeeAssignmentForm
            assignment={selectedAssignment}
            onSubmit={handleSubmit}
            onCancel={handleClose}
          />
        </DialogContent>
      </Dialog>
    </div>
  );
}