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
import EquipmentUtilizationForm from '@/components/EquipmentUtilizationForm';
import { EquipmentUtilization, equipmentUtilizationService } from '@/services/equipmentUtilizationService';
import { toast } from 'sonner';

export default function EquipmentUtilizationPage() {
  const [open, setOpen] = useState(false);
  const [selectedUtilization, setSelectedUtilization] = useState<EquipmentUtilization | undefined>();
  const queryClient = useQueryClient();

  const { data: utilizations = [] } = useQuery(['utilizations'], () =>
    equipmentUtilizationService.getAllUtilizations()
  );

  const createMutation = useMutation(
    (data: any) => equipmentUtilizationService.createUtilization(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['utilizations']);
        setOpen(false);
        toast.success('Equipment utilization created successfully');
      },
      onError: () => {
        toast.error('Failed to create equipment utilization');
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: any }) =>
      equipmentUtilizationService.updateUtilization(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['utilizations']);
        setOpen(false);
        toast.success('Equipment utilization updated successfully');
      },
      onError: () => {
        toast.error('Failed to update equipment utilization');
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => equipmentUtilizationService.deleteUtilization(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['utilizations']);
        toast.success('Equipment utilization deleted successfully');
      },
      onError: () => {
        toast.error('Failed to delete equipment utilization');
      },
    }
  );

  const handleOpen = (utilization?: EquipmentUtilization) => {
    setSelectedUtilization(utilization);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedUtilization(undefined);
    setOpen(false);
  };

  const handleSubmit = (data: any) => {
    if (selectedUtilization) {
      updateMutation.mutate({ id: selectedUtilization.id!, data });
    } else {
      createMutation.mutate(data);
    }
  };

  const handleDelete = (id: number) => {
    if (window.confirm('Are you sure you want to delete this utilization record?')) {
      deleteMutation.mutate(id);
    }
  };

  const columns = [
    { key: 'equipment.name', label: 'Equipment' },
    { key: 'project.name', label: 'Project' },
    { key: 'monthYear', label: 'Month/Year' },
    { key: 'startingHoursKms', label: 'Starting Hours' },
    { key: 'targetHoursKms', label: 'Target Hours' },
    { key: 'closingHoursKms', label: 'Closing Hours' },
    { key: 'utilizationPercentage', label: 'Utilization %' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'monthYear') {
      return `${item.month}/${item.year}`;
    }
    if (column === 'utilizationPercentage') {
      return `${item.utilizationPercentage}%`;
    }
    return null;
  };

  return (
    <div className="p-4">
      <DataTable
        title="Equipment Utilization"
        columns={columns}
        data={Array.isArray(utilizations) ? utilizations : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="max-w-3xl max-h-[90vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle>
              {selectedUtilization ? 'Edit Utilization' : 'Add Utilization'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <EquipmentUtilizationForm
              utilization={selectedUtilization}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
