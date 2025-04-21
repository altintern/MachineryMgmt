'use client';

import React from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { EmployeeDto, EmployeeRequestDto, employeeService } from '@/services/employeeService';
import EmployeeForm from '@/components/EmployeeForm';
import DataTable from '@/components/DataTable';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import toast from 'react-hot-toast';

export default function EmployeesPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedEmployee, setSelectedEmployee] = React.useState<EmployeeDto | undefined>();
  const queryClient = useQueryClient();

  // Fetch employees with error handling
  const { data: employees = [], isLoading, isError } = useQuery(
    ['employees'], 
    employeeService.getAllEmployees,
    {
      onError: (error) => {
        console.error('Failed to fetch employees:', error);
        toast.error('Failed to load employees. Please try again later.');
      }
    }
  );

  // Create employee mutation
  const createMutation = useMutation(
    (data: EmployeeRequestDto) => employeeService.createEmployee(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['employees']);
        handleClose();
        toast.success('Employee created successfully!');
      },
      onError: (error) => {
        console.error('Failed to create employee:', error);
        toast.error('Failed to create employee. Please try again.');
      }
    }
  );

  // Update employee mutation
  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: EmployeeRequestDto }) =>
      employeeService.updateEmployee(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['employees']);
        handleClose();
        toast.success('Employee updated successfully!');
      },
      onError: (error) => {
        console.error('Failed to update employee:', error);
        toast.error('Failed to update employee. Please try again.');
      }
    }
  );

  // Delete employee mutation
  const deleteMutation = useMutation(
    (id: number) => employeeService.deleteEmployee(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['employees']);
        toast.success('Employee deleted successfully!');
      },
      onError: (error) => {
        console.error('Failed to delete employee:', error);
        toast.error('Failed to delete employee. Please try again.');
      }
    }
  );

  const handleOpen = (employee?: EmployeeDto) => {
    setSelectedEmployee(employee);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedEmployee(undefined);
    setOpen(false);
  };

  const handleSubmit = (formData: EmployeeRequestDto) => {
    if (selectedEmployee) {
      updateMutation.mutate({ id: selectedEmployee.id, data: formData });
    } else {
      createMutation.mutate(formData);
    }
  };

  const handleDelete = (id: number) => {
    if (confirm('Are you sure you want to delete this employee?')) {
      deleteMutation.mutate(id);
    }
  };

  const columns = [
    { key: 'name', label: 'Name' },
    { key: 'department.name', label: 'Department' },
    { key: 'department.description', label: 'Department Description' },
    { key: 'designation.name', label: 'Designation' },
    { key: 'designation.description', label: 'Designation Description' },
    { key: 'remarks', label: 'Remarks' },
  ];

  if (isLoading) {
    return <div className="p-6">Loading employees...</div>;
  }

  if (isError) {
    return <div className="p-6 text-red-500">Error loading employees. Please try again later.</div>;
  }

  return (
    <div className="p-6">
      <DataTable
        title="Employees"
        columns={columns}
        data={Array.isArray(employees) ? employees : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="sm:max-w-[700px]">
          <DialogHeader>
            <DialogTitle>{selectedEmployee ? 'Edit Employee' : 'Add Employee'}</DialogTitle>
          </DialogHeader>
          <EmployeeForm
            employee={selectedEmployee}
            onSubmit={handleSubmit}
            onCancel={handleClose}
          />
        </DialogContent>
      </Dialog>
    </div>
  );
}
