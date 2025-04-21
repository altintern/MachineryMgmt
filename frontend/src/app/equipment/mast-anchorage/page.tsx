'use client';

import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import DataTable from '@/components/DataTable';
import MastAnchorageForm from '@/components/MastAnchorageForm';
import mastAnchorageService, { MastAnchorageDetails, MastAnchorageRequest } from '@/services/mastAnchorageService';
import { toast } from 'sonner';

export default function MastAnchoragePage() {
  const [open, setOpen] = useState(false);
  const [selectedMastAnchorage, setSelectedMastAnchorage] = useState<MastAnchorageDetails | undefined>();
  const queryClient = useQueryClient();

  const { data: mastAnchorages = [] } = useQuery(['mastAnchorages'], () =>
    mastAnchorageService.getAllMastAnchorage()
  );

  const createMutation = useMutation(
    (data: MastAnchorageRequest) => mastAnchorageService.createMastAnchorage(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['mastAnchorages']);
        setOpen(false);
        toast.success('Mast anchorage created successfully');
      },
      onError: () => {
        toast.error('Failed to create mast anchorage');
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: MastAnchorageRequest }) => mastAnchorageService.updateMastAnchorage(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['mastAnchorages']);
        setOpen(false);
        toast.success('Mast anchorage updated successfully');
      },
      onError: () => {
        toast.error('Failed to update mast anchorage');
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => mastAnchorageService.deleteMastAnchorage(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['mastAnchorages']);
        toast.success('Mast anchorage deleted successfully');
      },
      onError: () => {
        toast.error('Failed to delete mast anchorage');
      },
    }
  );

  const handleOpen = (mastAnchorage?: MastAnchorageDetails) => {
    setSelectedMastAnchorage(mastAnchorage);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedMastAnchorage(undefined);
    setOpen(false);
  };

  const handleSubmit = async (data: MastAnchorageRequest) => {
    if (selectedMastAnchorage) {
      await updateMutation.mutateAsync({ id: selectedMastAnchorage.id, data });
    } else {
      await createMutation.mutateAsync(data);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this mast anchorage?')) {
      await deleteMutation.mutateAsync(id);
    }
  };

  const columns = [
    { key: 'project.name', label: 'Project' },
    { key: 'equipment.name', label: 'Equipment' },
    { key: 'location', label: 'Location' },
    { key: 'status', label: 'Status' },
    { key: 'totalAnchorageRequirement', label: 'Total Anchorage' },
    { key: 'totalMastRequirement', label: 'Total Mast' },
  ];

  return (
    <div className="p-4">
      <DataTable
        title="Mast Anchorage"
        columns={columns}
        data={Array.isArray(mastAnchorages) ? mastAnchorages : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="max-w-3xl max-h-[90vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle>
              {selectedMastAnchorage ? 'Edit Mast Anchorage' : 'Add Mast Anchorage'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <MastAnchorageForm
              mastAnchorage={selectedMastAnchorage}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
