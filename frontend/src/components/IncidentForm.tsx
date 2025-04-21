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
    estimatedCompletionDate: incident?.estimatedCompletionDate || '',
    incidentType: incident?.incidentType || 'TYPE1',
    equipmentId: incident?.equipment?.id || 0,
    projectId: incident?.project?.id || 0,
    actionTaken: incident?.actionTaken || '',
    status: incident?.status || 'OPEN',
  });

  const { data: equipment = [] } = useQuery(['equipment'], () => equipmentService.getAllEquipment());
  const { data: projects = [] } = useQuery(['projects'], () => projectService.getAllProjects());

  const equipmentList = Array.isArray(equipment) ? equipment : [];
  const projectList = Array.isArray(projects) ? projects : [];

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: ['equipmentId', 'projectId'].includes(name) ? parseInt(value) : value,
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
            <TextField
              select
              fullWidth
              required
              name="incidentType"
              label="Incident Type"
              value={formData.incidentType}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            >
              {incidentTypes.map((type) => (
                <MenuItem key={type} value={type}>
                  {type}
                </MenuItem>
              ))}
            </TextField>
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
          </div>
          <div className="row-fields">
            <TextField
              select
              fullWidth
              required
              name="equipmentId"
              label="Equipment"
              value={formData.equipmentId}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            >
              <MenuItem value={0}>Select Equipment</MenuItem>
              {equipmentList.map((item) => (
                <MenuItem key={item.id} value={item.id}>
                  {item.name}
                </MenuItem>
              ))}
            </TextField>
            <TextField
              select
              fullWidth
              required
              name="projectId"
              label="Project"
              value={formData.projectId}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            >
              <MenuItem value={0}>Select Project</MenuItem>
              {projectList.map((project) => (
                <MenuItem key={project.id} value={project.id}>
                  {project.name}
                </MenuItem>
              ))}
            </TextField>
          </div>
          <div className="row-fields">
            <TextField
              select
              fullWidth
              required
              name="status"
              label="Status"
              value={formData.status}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            >
              {incidentStatuses.map((status) => (
                <MenuItem key={status} value={status}>
                  {status}
                </MenuItem>
              ))}
            </TextField>
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
