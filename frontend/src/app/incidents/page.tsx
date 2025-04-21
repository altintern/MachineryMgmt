'use client';

import React from 'react';
import { Dialog, Chip } from '@mui/material';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { IncidentReport, incidentService } from '@/services/incidentService';
import IncidentForm from '@/components/IncidentForm';
import DataTable from '@/components/DataTable';
import { DialogContent, DialogHeader, DialogTitle } from '@/components/ui/dialog';

export default function IncidentsPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedIncident, setSelectedIncident] = React.useState<IncidentReport | undefined>();
  const queryClient = useQueryClient();

  const { data: incidents = [] } = useQuery(['incidents'], () =>
    incidentService.getAllIncidents()
  );

  const createMutation = useMutation(
    (data: any) => incidentService.createIncident(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['incidents']);
        handleClose();
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: any }) =>
      incidentService.updateIncident(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['incidents']);
        handleClose();
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => incidentService.deleteIncident(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['incidents']);
      },
    }
  );

  const handleOpen = (incident?: IncidentReport) => {
    setSelectedIncident(incident);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedIncident(undefined);
    setOpen(false);
  };

  const handleSubmit = (data: any) => {
    if (selectedIncident) {
      updateMutation.mutate({ id: selectedIncident.id!, data });
    } else {
      createMutation.mutate(data);
    }
  };

  const handleDelete = (id: number) => {
    if (confirm('Are you sure you want to delete this incident report?')) {
      deleteMutation.mutate(id);
    }
  };

  const getStatusColor = (status: string) => {
    switch (status) {
      case 'OPEN':
        return 'error';
      case 'IN_PROGRESS':
        return 'warning';
      case 'CLOSED':
        return 'success';
      case 'RESOLVED':
        return 'info';
      default:
        return 'default';
    }
  };

  const columns = [
    { key: 'equipment.name', label: 'Equipment' },
    { key: 'project.name', label: 'Project' },
    { key: 'incidentDate', label: 'Incident Date' },
    { key: 'incidentType', label: 'Type' },
    { key: 'status', label: 'Status' },
    { key: 'estimatedCompletionDate', label: 'Est. Completion' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'incidentDate' || column === 'estimatedCompletionDate') {
      return item[column] ? new Date(item[column]).toLocaleDateString() : '-';
    }
    
    if (column === 'status') {
      return (
        <Chip
          label={item.status}
          color={getStatusColor(item.status) as any}
          size="small"
        />
      );
    }
    
    return null;
  };

  return (
    <div className="p-6">
      <DataTable
        title="Incident Reports"
        columns={columns}
        data={Array.isArray(incidents) ? incidents : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog
        open={open}
        onClose={handleClose}
        maxWidth="md"
        fullWidth
      >
        <DialogHeader>
          <DialogTitle>{selectedIncident ? 'Edit Incident' : 'Add Incident'}</DialogTitle>
        </DialogHeader>
        <DialogContent>
          <IncidentForm
            incident={selectedIncident}
            onSubmit={handleSubmit}
            onCancel={handleClose}
          />
        </DialogContent>
      </Dialog>
    </div>
  );
}
