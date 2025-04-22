'use client';

import React from 'react';
import { Dialog, DialogTitle, DialogContent, Chip } from '@mui/material';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import { IncidentReport, incidentService } from '@/services/incidentService';
import IncidentForm from '@/components/IncidentForm';
import DataTable from '@/components/DataTable';
import { toast } from 'sonner';

export default function IncidentsPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedIncident, setSelectedIncident] = React.useState<IncidentReport | undefined>();
  const queryClient = useQueryClient();

  // Fetch all incidents
  const { data: incidents = [] } = useQuery(['incidents'], incidentService.getAllIncidents);
  
  // Process incidents to add index for serial number generation
  const processedIncidents = React.useMemo(() => {
    return incidents.map((incident, index) => ({
      ...incident,
      _index: index
    }));
  }, [incidents]);

  const createMutation = useMutation(
    (data: any) => incidentService.createIncident(data),
    {
      onSuccess: (data) => {
        queryClient.invalidateQueries(['incidents']);
        handleClose();
        toast.success('Success', {
          description: 'Incident report created successfully',
          position: 'top-right',
        });
      },
      onError: (error: any) => {
        toast.error('Error', {
          description: error?.response?.data?.message || 'Failed to create incident report',
          position: 'top-right',
        });
      }
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: any }) =>
      incidentService.updateIncident(id, data),
    {
      onSuccess: (data) => {
        queryClient.invalidateQueries(['incidents']);
        handleClose();
        toast.success('Success', {
          description: 'Incident report updated successfully',
          position: 'top-right',
        });
      },
      onError: (error: any) => {
        toast.error('Error', {
          description: error?.response?.data?.message || 'Failed to update incident report',
          position: 'top-right',
        });
      }
    }
  );

  const deleteMutation = useMutation(
    (id: number) => incidentService.deleteIncident(id),
    {
      onSuccess: (data) => {
        queryClient.invalidateQueries(['incidents']);
        toast.success('Success', {
          description: 'Incident report deleted successfully',
          position: 'top-right',
        });
      },
      onError: (error: any) => {
        toast.error('Error', {
          description: error?.response?.data?.message || 'Failed to delete incident report',
          position: 'top-right',
        });
      }
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
    { key: 'serial', label: 'S. No.' },
    { key: 'equipment.name', label: 'Equipment' },
    { key: 'project.name', label: 'Project' },
    { key: 'incidentDate', label: 'Incident Date' },
    { key: 'incidentType', label: 'Type' },
    { key: 'actionTaken', label: 'Action Taken' },
    { key: 'status', label: 'Status' },
    { key: 'estimatedCompletionDate', label: 'Est. Completion' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    // Handle serial number
    if (column === 'serial') {
      return item._index + 1;
    }
    
    // Handle date fields
    if (column === 'incidentDate' || column === 'estimatedCompletionDate' || column === 'closeDate') {
      return item[column] ? new Date(item[column]).toLocaleDateString() : '-';
    }
    
    // Handle incident type with more user-friendly display
    if (column === 'incidentType') {
      const typeMap: Record<string, string> = {
        'TYPE1': 'Type 1',
        'TYPE2': 'Type 2',
        'TYPE3': 'Type 3'
      };
      return typeMap[item.incidentType] || item.incidentType;
    }
    
    // Handle status field with color-coded chip
    if (column === 'status') {
      return (
        <Chip
          label={item.status}
          color={getStatusColor(item.status) as any}
          size="small"
        />
      );
    }
    
    // Handle action taken (truncate if too long)
    if (column === 'actionTaken') {
      const maxLength = 30;
      if (item.actionTaken && item.actionTaken.length > maxLength) {
        return `${item.actionTaken.substring(0, maxLength)}...`;
      }
      return item.actionTaken || '-';
    }
    
    // Handle nested properties
    if (column === 'equipment.name' && item.equipment) {
      return item.equipment.name;
    }
    
    if (column === 'project.name' && item.project) {
      return item.project.name;
    }
    
    // Return the value or a dash if it doesn't exist
    return item[column] || '-';
  };

  return (
    <div className="p-6">
      <DataTable
        title="Incident Reports"
        columns={columns}
        data={processedIncidents}
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
        <DialogTitle sx={{ p: 3 }}>{selectedIncident ? 'Edit Incident' : 'Add Incident'}</DialogTitle>
        <DialogContent sx={{ p: 3 }}>
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
