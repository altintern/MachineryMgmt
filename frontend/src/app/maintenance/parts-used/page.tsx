'use client';

import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import DataTable from '@/components/DataTable';
import MaintenancePartUsedForm from '@/components/MaintenancePartUsedForm';
import maintenancePartUsedService, { MaintenancePartUsed, MaintenancePartUsedRequest } from '@/services/maintenancePartUsedService';
import { toast } from 'sonner';
import Link from 'next/link';

export default function MaintenancePartsUsedPage() {
  const [open, setOpen] = useState(false);
  const [selectedPartUsed, setSelectedPartUsed] = useState<MaintenancePartUsed | undefined>();
  const queryClient = useQueryClient();

  const { data: partsUsedData } = useQuery(['maintenancePartsUsed'], () =>
    maintenancePartUsedService.getAllMaintenancePartUsed()
  );

  // Make sure we have the correct data structure
  const partsUsed = partsUsedData?.data || [];

  const createMutation = useMutation(
    (data: MaintenancePartUsedRequest) => maintenancePartUsedService.createMaintenancePartUsed(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['maintenancePartsUsed']);
        setOpen(false);
        toast.success('Part used record created successfully');
      },
      onError: () => {
        toast.error('Failed to create part used record');
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: MaintenancePartUsedRequest }) => maintenancePartUsedService.updateMaintenancePartUsed(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['maintenancePartsUsed']);
        setOpen(false);
        toast.success('Part used record updated successfully');
      },
      onError: () => {
        toast.error('Failed to update part used record');
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => maintenancePartUsedService.deleteMaintenancePartUsed(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['maintenancePartsUsed']);
        toast.success('Part used record deleted successfully');
      },
      onError: () => {
        toast.error('Failed to delete part used record');
      },
    }
  );

  const handleOpen = (partUsed?: MaintenancePartUsed) => {
    setSelectedPartUsed(partUsed);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedPartUsed(undefined);
    setOpen(false);
  };

  const handleSubmit = async (data: MaintenancePartUsedRequest) => {
    if (selectedPartUsed) {
      await updateMutation.mutateAsync({ id: selectedPartUsed.id, data });
    } else {
      await createMutation.mutateAsync(data);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this part used record?')) {
      await deleteMutation.mutateAsync(id);
    }
  };

  const formatNumber = (num: number) => {
    return new Intl.NumberFormat('en-IN', {
      minimumFractionDigits: 0,
      maximumFractionDigits: 2,
    }).format(num);
  };

  const columns = [
    { key: 'maintenancelog.id', label: 'Maintenance Log ID' },
    { key: 'item.name', label: 'Item' },
    { key: 'quantity', label: 'Quantity' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'quantity') {
      return formatNumber(item.quantity);
    }
    return null;
  };

  return (
    <div className="p-4">
      <div className="flex gap-4 mb-4">
        <Button variant="outline" asChild>
          <Link href="/maintenance">Maintenance Logs</Link>
        </Button>
        <Button variant="outline" asChild>
          <Link href="/maintenance/readings">Readings</Link>
        </Button>
        <Button variant="outline" className="bg-background">
          Parts Used
        </Button>
      </div>
      
      <DataTable
        title="Parts Used"
        columns={columns}
        data={Array.isArray(partsUsed) ? partsUsed : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="max-w-3xl max-h-[90vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle>
              {selectedPartUsed ? 'Edit Part Used' : 'Add Part Used'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <MaintenancePartUsedForm
              partUsed={selectedPartUsed}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
