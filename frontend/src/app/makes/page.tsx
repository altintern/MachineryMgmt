'use client';

import React from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import DataTable from '@/components/DataTable';
import { Make, makeService } from '@/services/makeService';
import MakeForm from '@/components/MakeForm';

export default function MakesPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedMake, setSelectedMake] = React.useState<Make | null>(null);
  const queryClient = useQueryClient();

  // Fetch makes
  const { data: makesData, isLoading } = useQuery('makes', makeService.getAllMakes);
  const makes = makesData?.data || [];

  // Mutations
  const createMutation = useMutation(makeService.createMake, {
    onSuccess: () => {
      queryClient.invalidateQueries('makes');
      handleClose();
    },
  });

  const updateMutation = useMutation(
    (data: { id: number; make: Make }) => makeService.updateMake(data.id, data.make),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('makes');
        handleClose();
      },
    }
  );

  const deleteMutation = useMutation(makeService.deleteMake, {
    onSuccess: () => {
      queryClient.invalidateQueries('makes');
    },
  });

  const handleOpen = (make?: Make) => {
    setSelectedMake(make || null);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedMake(null);
    setOpen(false);
  };

  const handleSubmit = (make: Make) => {
    if (selectedMake?.id) {
      updateMutation.mutate({ id: selectedMake.id, make });
    } else {
      createMutation.mutate(make);
    }
  };

  const handleDelete = (id: number) => {
    if (confirm('Are you sure you want to delete this make?')) {
      deleteMutation.mutate(id);
    }
  };

  const columns = [
    { key: 'id', label: 'ID' },
    { key: 'name', label: 'Name' },
  ];

  if (isLoading) {
    return <div className="p-6">Loading...</div>;
  }

  return (
    <div className="p-6">
      <DataTable
        title="Makes"
        columns={columns}
        data={makes}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="sm:max-w-[500px]">
          <DialogHeader>
            <DialogTitle>
              {selectedMake ? 'Edit Make' : 'Create Make'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <MakeForm
              make={selectedMake || undefined}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
