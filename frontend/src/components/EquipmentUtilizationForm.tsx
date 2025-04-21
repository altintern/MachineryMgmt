import React from 'react';
import { TextField, Button, Box, MenuItem, Paper, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import { useQuery } from 'react-query';
import { equipmentService } from '@/services/equipmentService';
import { projectService } from '@/services/projectService';
import { EquipmentUtilization, EquipmentUtilizationRequest } from '@/services/equipmentUtilizationService';

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

interface EquipmentUtilizationFormProps {
  utilization?: EquipmentUtilization;
  onSubmit: (utilization: EquipmentUtilizationRequest) => void;
  onCancel: () => void;
}

const EquipmentUtilizationForm: React.FC<EquipmentUtilizationFormProps> = ({ utilization, onSubmit, onCancel }) => {
  const [formData, setFormData] = React.useState<EquipmentUtilizationRequest>({
    equipmentId: utilization?.equipment?.id || 0,
    projectId: utilization?.project?.id || 0,
    startingHoursKms: utilization?.startingHoursKms || 0,
    closingHoursKms: utilization?.closingHoursKms || 0,
    availabilityHours: utilization?.availabilityHours || 0,
    dieselConsumedLtrs: utilization?.dieselConsumedLtrs || 0,
    avgFuelConsumption: utilization?.avgFuelConsumption || 0,
    utilizationPercentage: utilization?.utilizationPercentage || 0,
    month: utilization?.month || new Date().getMonth() + 1,
    year: utilization?.year || new Date().getFullYear(),
    remarks: utilization?.remarks || '',
  });

  const { data: equipment = [] } = useQuery(['equipment'], () => equipmentService.getAllEquipment());
  const { data: projects = [] } = useQuery(['projects'], () => projectService.getAllProjects());

  const equipmentList = Array.isArray(equipment) ? equipment : [];
  const projectList = Array.isArray(projects) ? projects : [];

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: ['equipmentId', 'projectId', 'month', 'year'].includes(name) ? parseInt(value) : parseFloat(value),
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
      <Paper elevation={0} sx={{ p: 3, mb: 3 }}>
        <Typography variant="h6" sx={{ mb: 3 }}>
          {utilization ? 'Edit Equipment Utilization' : 'New Equipment Utilization'}
        </Typography>
        <StyledGrid>
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
              fullWidth
              required
              type="number"
              name="startingHoursKms"
              label="Starting Hours/Kms"
              value={formData.startingHoursKms}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
            <TextField
              fullWidth
              required
              type="number"
              name="closingHoursKms"
              label="Closing Hours/Kms"
              value={formData.closingHoursKms}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </div>
          <div className="row-fields">
            <TextField
              fullWidth
              required
              type="number"
              name="availabilityHours"
              label="Availability Hours"
              value={formData.availabilityHours}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
            <TextField
              fullWidth
              required
              type="number"
              name="dieselConsumedLtrs"
              label="Diesel Consumed (Ltrs)"
              value={formData.dieselConsumedLtrs}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </div>
          <div className="row-fields">
            <TextField
              fullWidth
              required
              type="number"
              name="avgFuelConsumption"
              label="Avg Fuel Consumption"
              value={formData.avgFuelConsumption}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
            <TextField
              fullWidth
              required
              type="number"
              name="utilizationPercentage"
              label="Utilization %"
              value={formData.utilizationPercentage}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </div>
          <div className="row-fields">
            <TextField
              fullWidth
              required
              type="number"
              name="month"
              label="Month"
              value={formData.month}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
            <TextField
              fullWidth
              required
              type="number"
              name="year"
              label="Year"
              value={formData.year}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </div>
          <GridItem>
            <TextField
              fullWidth
              multiline
              rows={4}
              name="remarks"
              label="Remarks"
              value={formData.remarks}
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
              {utilization ? 'Update' : 'Create'}
            </Button>
          </Box>
        </StyledGrid>
      </Paper>
    </Box>
  );
};

export default EquipmentUtilizationForm;
