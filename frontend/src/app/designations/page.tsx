'use client';

import React from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { DesignationDto, DesignationRequestDto, designationService } from '@/services/designationService';
import DataTable from '@/components/DataTable';
import toast from 'react-hot-toast';
import DesignationForm from '@/components/DesignationForm';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';

export default function DesignationsPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedDesignation, setSelectedDesignation] = React.useState<DesignationDto | undefined>();
  const queryClient = useQueryClient();

  // Fetch designations with error handling
  const { data: designations = [], isLoading, isError } = useQuery(
    ['designations'], 
    designationService.getAllDesignations,
    {
      onError: (error) => {
        console.error('Failed to fetch designations:', error);
        toast.error('Failed to load designations. Please try again later.');
      }
    }
  );

  // Create designation mutation
  const createMutation = useMutation(
    (data: DesignationRequestDto) => designationService.createDesignation(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['designations']);
        handleClose();
        toast.success('Designation created successfully!');
      },
      onError: (error) => {
        console.error('Failed to create designation:', error);
        toast.error('Failed to create designation. Please try again.');
      }
    }
  );

  // Update designation mutation
  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: DesignationRequestDto }) =>
      designationService.updateDesignation(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['designations']);
        handleClose();
        toast.success('Designation updated successfully!');
      },
      onError: (error) => {
        console.error('Failed to update designation:', error);
        toast.error('Failed to update designation. Please try again.');
      }
    }
  );

  // Delete designation mutation
  const deleteMutation = useMutation(
    (id: number) => designationService.deleteDesignation(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['designations']);
        toast.success('Designation deleted successfully!');
      },
      onError: (error) => {
        console.error('Failed to delete designation:', error);
        toast.error('Failed to delete designation. Please try again.');
      }
    }
  );

  const handleOpen = (designation?: DesignationDto) => {
    setSelectedDesignation(designation);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedDesignation(undefined);
    setOpen(false);
  };

  const handleSubmit = (formData: DesignationRequestDto) => {
    if (selectedDesignation) {
      updateMutation.mutate({ id: selectedDesignation.id, data: formData });
    } else {
      createMutation.mutate(formData);
    }
  };

  const handleDelete = (id: number) => {
    if (confirm('Are you sure you want to delete this designation?')) {
      deleteMutation.mutate(id);
    }
  };

  const columns = [
    { key: 'name', label: 'Name' },
    { key: 'description', label: 'Description' },
  ];

  if (isLoading) {
    return <div className="p-6">Loading designations...</div>;
  }

  if (isError) {
    return <div className="p-6 text-red-500">Error loading designations. Please try again later.</div>;
  }

  return (
    <div className="p-6">
      <DataTable
        title="Designations"
        columns={columns}
        data={Array.isArray(designations) ? designations : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="sm:max-w-[500px]">
          <DialogHeader>
            <DialogTitle>
              {selectedDesignation ? 'Edit Designation' : 'New Designation'}
            </DialogTitle>
          </DialogHeader>
          <DesignationForm
            designation={selectedDesignation}
            onSubmit={handleSubmit}
            onCancel={handleClose}
          />
        </DialogContent>
      </Dialog>
    </div>
  );
}
