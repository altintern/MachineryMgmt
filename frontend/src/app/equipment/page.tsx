'use client';

import React from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { Equipment, equipmentService } from '@/services/equipmentService';
import DataTable from '@/components/DataTable';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import EquipmentForm from '@/components/EquipmentForm';

export default function EquipmentPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedEquipment, setSelectedEquipment] = React.useState<Equipment | undefined>();
  const queryClient = useQueryClient();

  const { data: equipment = [] } = useQuery(['equipment'], () =>
    equipmentService.getAllEquipment()
  );

  const createMutation = useMutation(
    (data: any) => equipmentService.createEquipment(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['equipment']);
        handleClose();
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: any }) =>
      equipmentService.updateEquipment(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['equipment']);
        handleClose();
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => equipmentService.deleteEquipment(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['equipment']);
      },
    }
  );

  const handleOpen = (equipment?: Equipment) => {
    setSelectedEquipment(equipment);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedEquipment(undefined);
    setOpen(false);
  };

  const handleSubmit = (data: any) => {
    if (selectedEquipment) {
      updateMutation.mutate({ id: selectedEquipment.id, data });
    } else {
      createMutation.mutate(data);
    }
  };

  const handleDelete = (id: number) => {
    if (confirm('Are you sure you want to delete this equipment?')) {
      deleteMutation.mutate(id);
    }
  };

  const columns = [
    { key: 'name', label: 'Name' },
    { key: 'assetCode', label: 'Asset Code' },
    { key: 'yearOfManufacture', label: 'Year' },
    { key: 'category.name', label: 'Category' },
    { key: 'model.name', label: 'Model' },
    { key: 'project.name', label: 'Project' },
  ];

  return (
    <div className="p-4">
      <DataTable
        title="Equipment"
        columns={columns}
        data={Array.isArray(equipment) ? equipment : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent>
          <DialogHeader>
            <DialogTitle>
              {selectedEquipment ? 'Edit Equipment' : 'Add Equipment'}
            </DialogTitle>
          </DialogHeader>
          <EquipmentForm
            equipment={selectedEquipment}
            onSubmit={handleSubmit}
            onCancel={handleClose}
          />
        </DialogContent>
      </Dialog>
    </div>
  );
}
