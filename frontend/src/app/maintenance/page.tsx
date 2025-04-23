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
import MaintenanceForm from '@/components/MaintenanceForm';
import maintenanceService, { MaintenanceLog, MaintenanceLogRequest } from '@/services/maintenanceService';
import { toast } from 'sonner';
import Link from 'next/link';

export default function MaintenancePage() {
  const [open, setOpen] = useState(false);
  const [selectedMaintenance, setSelectedMaintenance] = useState<MaintenanceLog | undefined>();
  const queryClient = useQueryClient();

  const { data: maintenanceData } = useQuery(['maintenanceLogs'], () =>
    maintenanceService.getAllMaintenanceLogs()
  );

  // Make sure we have the correct data structure
  const maintenanceLogs = maintenanceData?.data || [];

  const createMutation = useMutation(
    (data: MaintenanceLogRequest) => maintenanceService.createMaintenanceLog(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['maintenanceLogs']);
        setOpen(false);
        toast.success('Maintenance log created successfully');
      },
      onError: () => {
        toast.error('Failed to create maintenance log');
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: MaintenanceLogRequest }) => maintenanceService.updateMaintenanceLog(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['maintenanceLogs']);
        setOpen(false);
        toast.success('Maintenance log updated successfully');
      },
      onError: () => {
        toast.error('Failed to update maintenance log');
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => maintenanceService.deleteMaintenanceLog(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['maintenanceLogs']);
        toast.success('Maintenance log deleted successfully');
      },
      onError: () => {
        toast.error('Failed to delete maintenance log');
      },
    }
  );

  const handleOpen = (maintenance?: MaintenanceLog) => {
    setSelectedMaintenance(maintenance);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedMaintenance(undefined);
    setOpen(false);
  };

  const handleSubmit = async (data: MaintenanceLogRequest) => {
    if (selectedMaintenance) {
      await updateMutation.mutateAsync({ id: selectedMaintenance.id, data });
    } else {
      await createMutation.mutateAsync(data);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this maintenance log?')) {
      await deleteMutation.mutateAsync(id);
    }
  };

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  };

  const columns = [
    { key: 'date', label: 'Date' },
    { key: 'equipment.name', label: 'Equipment' },
    { key: 'typeOfMaintenance', label: 'Type' },
    { key: 'serviceDate', label: 'Service Date' },
    { key: 'startReading', label: 'Start Reading' },
    { key: 'closeReading', label: 'Close Reading' },
    { key: 'serviceHours', label: 'Service Hours' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'date' || column === 'serviceDate') {
      return formatDate(item[column]);
    }
    if (column === 'equipment.name') {
      return item.equipment?.name || '';
    }
    if (column === 'typeOfMaintenance') {
      return item.typeOfMaintenance
        ? item.typeOfMaintenance.charAt(0).toUpperCase() + item.typeOfMaintenance.slice(1).toLowerCase()
        : '';
    }
    if (column === 'startReading' || column === 'closeReading' || column === 'serviceHours') {
      return typeof item[column] === 'number' ? item[column] : '';
    }
    return null;
  };

  return (
    <div className="p-4">
      <div className="flex gap-4 mb-4">
        <Button variant="outline" className="bg-background">
          Maintenance Logs
        </Button>
        <Button variant="outline" asChild>
          <Link href="/maintenance/readings">Readings</Link>
        </Button>
        <Button variant="outline" asChild>
          <Link href="/maintenance/parts-used">Parts Used</Link>
        </Button>
      </div>
      
      <DataTable
        title="Maintenance Logs"
        columns={columns}
        data={Array.isArray(maintenanceLogs) ? maintenanceLogs : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="max-w-4xl max-h-[90vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle>
              {selectedMaintenance ? 'Edit Maintenance Log' : 'Add Maintenance Log'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <MaintenanceForm
              maintenance={selectedMaintenance}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
