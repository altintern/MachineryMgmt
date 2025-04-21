import React from 'react';
import { 
  TextField, 
  Button, 
  Box, 
  MenuItem, 
  Paper, 
  Typography 
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { Project } from '@/services/projectService';

const StyledGrid = styled('div')(({ theme }) => ({
  display: 'grid',
  gap: theme.spacing(3),
  '& .date-fields': {
    display: 'grid',
    gridTemplateColumns: 'repeat(2, 1fr)',
    gap: theme.spacing(3),
  }
}));

const GridItem = styled('div')({
  width: '100%',
});

interface ProjectFormProps {
  project?: Project;
  onSubmit: (project: Project) => void;
  onCancel: () => void;
}

const ProjectForm: React.FC<ProjectFormProps> = ({ project, onSubmit, onCancel }) => {
  const [formData, setFormData] = React.useState<Project>({
    name: project?.name || '',
    description: project?.description || '',
    location: project?.location || '',
    startDate: project?.startDate || '',
    endDate: project?.endDate || '',
    status: project?.status || 'PENDING'
  });

  // Debug log to check initial status value
  React.useEffect(() => {
    console.log('Initial form data:', formData);
  }, []);

  // Handle text and date inputs
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    console.log(`Input change - ${name}: ${value}`);
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  // Handle direct status change
  const handleStatusChange = (status: string) => {
    console.log(`Direct status change: ${status}`);
    setFormData(prev => ({ ...prev, status }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Submitting form data:', formData);
    onSubmit(formData);
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
      <Paper elevation={0} sx={{ p: 3, mb: 3 }}>
        <Box sx={{ mb: 3 }}>
          <Typography component="div" variant="h6">
            {project ? 'Edit Project Details' : 'New Project Details'}
          </Typography>
        </Box>
        <StyledGrid>
          <GridItem>
            <TextField
              fullWidth
              required
              name="name"
              label="Project Name"
              value={formData.name}
              onChange={handleInputChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </GridItem>
          <GridItem>
            <TextField
              fullWidth
              required
              multiline
              rows={3}
              name="description"
              label="Description"
              value={formData.description}
              onChange={handleInputChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </GridItem>
          <GridItem>
            <TextField
              fullWidth
              required
              name="location"
              label="Location"
              value={formData.location}
              onChange={handleInputChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </GridItem>
          <div className="date-fields">
            <TextField
              fullWidth
              required
              type="date"
              name="startDate"
              label="Start Date"
              value={formData.startDate}
              onChange={handleInputChange}
              variant="outlined"
              InputLabelProps={{ shrink: true }}
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
            <TextField
              fullWidth
              required
              type="date"
              name="endDate"
              label="End Date"
              value={formData.endDate}
              onChange={handleInputChange}
              variant="outlined"
              InputLabelProps={{ shrink: true }}
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </div>
          <GridItem>
            <Box sx={{ mb: 1 }}>
              <Typography variant="body2" color="text.secondary">
                Status *
              </Typography>
              <select
                name="status"
                value={formData.status}
                onChange={(e) => {
                  console.log(`Status changed to: ${e.target.value}`);
                  setFormData(prev => ({ ...prev, status: e.target.value }));
                }}
                style={{
                  width: '100%',
                  padding: '14px',
                  border: '1px solid #ccc',
                  borderRadius: '4px',
                  backgroundColor: 'white',
                  fontSize: '16px'
                }}
                required
              >
                <option value="PENDING">Pending</option>
                <option value="IN_PROGRESS">In Progress</option>
                <option value="COMPLETED">Completed</option>
              </select>
            </Box>
          </GridItem>
          <GridItem>
            <Box sx={{ display: 'flex', gap: 2, justifyContent: 'flex-end', mt: 2 }}>
              <Button 
                variant="outlined" 
                onClick={onCancel}
                size="large"
              >
                Cancel
              </Button>
              <Button 
                type="submit" 
                variant="contained" 
                color="primary"
                size="large"
              >
                {project ? 'Update' : 'Create'} Project
              </Button>
            </Box>
          </GridItem>
        </StyledGrid>
      </Paper>
    </Box>
  );
};

export default ProjectForm;
