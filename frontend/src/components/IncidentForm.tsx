import React from 'react';
import { TextField, Button, Box, MenuItem, Paper, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import { useQuery } from 'react-query';
import { equipmentService } from '@/services/equipmentService';
import { projectService } from '@/services/projectService';
import { IncidentReport, IncidentReportRequest, IncidentType, IncidentStatus } from '@/services/incidentService';

const StyledGrid = styled('div')(({ theme }) => ({
  display: 'grid',
  gap: theme.spacing(3),
  '& .row-fields': {
    display: 'grid',
    gridTemplateColumns: 'repeat(2, 1fr)',
    gap: theme.spacing(3),
  }
}));

const GridItem = styled('div')({
  width: '100%',
});

interface IncidentFormProps {
  incident?: IncidentReport;
  onSubmit: (incident: IncidentReportRequest) => void;
  onCancel: () => void;
}

const incidentTypes: IncidentType[] = ['TYPE1', 'TYPE2', 'TYPE3'];
const incidentStatuses: IncidentStatus[] = ['OPEN', 'IN_PROGRESS', 'CLOSED', 'RESOLVED'];

const IncidentForm: React.FC<IncidentFormProps> = ({ incident, onSubmit, onCancel }) => {
  const [formData, setFormData] = React.useState<IncidentReportRequest>({
    incidentDate: incident?.incidentDate || new Date().toISOString().split('T')[0],
    closeDate: incident?.closeDate || '',
    estimatedCompletionDate: incident?.estimatedCompletionDate || '',
    incidentType: incident?.incidentType || 'TYPE1',
    equipmentId: incident?.equipment?.id || 0,
    projectId: incident?.project?.id || 0,
    actionTaken: incident?.actionTaken || '',
    incidentDetails: incident?.incidentDetails || '',
    status: incident?.status || 'OPEN',
  });

  const { data: equipment = [] } = useQuery(['equipment'], () => equipmentService.getAllEquipment());
  const { data: projects = [] } = useQuery(['projects'], () => projectService.getAllProjects());

  const equipmentList = Array.isArray(equipment) ? equipment : [];
  const projectList = Array.isArray(projects) ? projects : [];

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    let newValue: string | number = value;
    
    // Convert to numbers for these fields
    if (['equipmentId', 'projectId'].includes(name)) {
      newValue = value === '' ? '' : parseInt(value, 10);
    }
    
    setFormData((prev) => ({
      ...prev,
      [name]: newValue,
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
      <Paper elevation={0} sx={{ p: 3, mb: 3 }}>
        <Box sx={{ mb: 3 }}>
          <Typography component="div" variant="h6">
            {incident ? 'Edit Incident Report' : 'New Incident Report'}
          </Typography>
        </Box>
        <StyledGrid>
          <div className="row-fields">
            <TextField
              fullWidth
              required
              type="date"
              name="incidentDate"
              label="Incident Date"
              value={formData.incidentDate}
              onChange={handleChange}
              variant="outlined"
              InputLabelProps={{ shrink: true }}
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
            <div style={{ width: '100%' }}>
              <label htmlFor="incidentType" style={{ display: 'block', marginBottom: 4 }}>Incident Type *</label>
              <select
                id="incidentType"
                name="incidentType"
                value={formData.incidentType}
                onChange={(e) => handleChange(e as any)}
                required
                style={{ width: '100%', padding: 10, borderRadius: 4, border: '1px solid #ccc', background: 'white' }}
              >
                {incidentTypes.map((type) => (
                  <option key={type} value={type}>{type}</option>
                ))}
              </select>
            </div>
          </div>
          <div className="row-fields">
            <TextField
              fullWidth
              required
              type="date"
              name="estimatedCompletionDate"
              label="Estimated Completion Date"
              value={formData.estimatedCompletionDate}
              onChange={handleChange}
              variant="outlined"
              InputLabelProps={{ shrink: true }}
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
            {/* Close Date field removed as requested */}
          </div>
          <div className="row-fields">
            <div style={{ width: '100%' }}>
              <label htmlFor="equipmentId" style={{ display: 'block', marginBottom: 4 }}>Equipment *</label>
              <select
                id="equipmentId"
                name="equipmentId"
                value={formData.equipmentId}
                onChange={(e) => handleChange(e as any)}
                required
                style={{ width: '100%', padding: 10, borderRadius: 4, border: '1px solid #ccc', background: (!equipmentList.length ? '#f5f5f5' : 'white') }}
                disabled={!equipmentList.length}
              >
                <option value="">Select Equipment</option>
                {equipmentList.map((item) => (
                  <option key={item.id} value={item.id}>{item.name}</option>
                ))}
              </select>
            </div>
            <div style={{ width: '100%' }}>
              <label htmlFor="projectId" style={{ display: 'block', marginBottom: 4 }}>Project *</label>
              <select
                id="projectId"
                name="projectId"
                value={formData.projectId}
                onChange={(e) => handleChange(e as any)}
                required
                style={{ width: '100%', padding: 10, borderRadius: 4, border: '1px solid #ccc', background: (!projectList.length ? '#f5f5f5' : 'white') }}
                disabled={!projectList.length}
              >
                <option value="">Select Project</option>
                {projectList.map((project) => (
                  <option key={project.id} value={project.id}>{project.name}</option>
                ))}
              </select>
            </div>
          </div>
          <div className="row-fields">
            <div style={{ width: '100%' }}>
              <label htmlFor="status" style={{ display: 'block', marginBottom: 4 }}>Status *</label>
              <select
                id="status"
                name="status"
                value={formData.status}
                onChange={(e) => handleChange(e as any)}
                required
                style={{ width: '100%', padding: 10, borderRadius: 4, border: '1px solid #ccc', background: 'white' }}
              >
                {incidentStatuses.map((status) => (
                  <option key={status} value={status}>{status}</option>
                ))}
              </select>
            </div>
          </div>
          <GridItem>
            <TextField
              fullWidth
              required
              multiline
              rows={3}
              name="actionTaken"
              label="Action Taken"
              value={formData.actionTaken}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </GridItem>
          <GridItem>
            <TextField
              fullWidth
              multiline
              rows={3}
              name="incidentDetails"
              label="Incident Details"
              value={formData.incidentDetails}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </GridItem>

          <Box sx={{ display: 'flex', gap: 2, justifyContent: 'flex-end' }}>
            <Button variant="outlined" onClick={onCancel} size="large">
              Cancel
            </Button>
            <Button type="submit" variant="contained" color="primary" size="large">
              {incident ? 'Update' : 'Create'}
            </Button>
          </Box>
        </StyledGrid>
      </Paper>
    </Box>
  );
};

export default IncidentForm;
