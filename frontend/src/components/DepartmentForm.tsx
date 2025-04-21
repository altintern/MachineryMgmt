import React from 'react';
import { 
  TextField, 
  Button, 
  Box, 
  Paper, 
  Typography 
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { DepartmentDto, DepartmentRequestDto } from '@/services/departmentService';

const StyledGrid = styled('div')(({ theme }) => ({
  display: 'grid',
  gap: theme.spacing(3),
}));

const GridItem = styled('div')({
  width: '100%',
});

interface DepartmentFormProps {
  department?: DepartmentDto;
  onSubmit: (department: DepartmentRequestDto) => void;
  onCancel: () => void;
}

const DepartmentForm: React.FC<DepartmentFormProps> = ({ department, onSubmit, onCancel }) => {
  const [formData, setFormData] = React.useState<DepartmentRequestDto>({
    name: department?.name || '',
    description: department?.description || '',
  });

  // Handle text inputs
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    console.log(`Input change - ${name}: ${value}`);
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Submitting department data:', formData);
    onSubmit(formData);
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
      <Paper elevation={0} sx={{ p: 3, mb: 3 }}>
        <Box sx={{ mb: 3 }}>
          <Typography component="div" variant="h6">
            {department ? 'Edit Department' : 'New Department'}
          </Typography>
        </Box>
        <StyledGrid>
          <GridItem>
            <TextField
              fullWidth
              required
              name="name"
              label="Department Name"
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
                {department ? 'Update' : 'Create'} Department
              </Button>
            </Box>
          </GridItem>
        </StyledGrid>
      </Paper>
    </Box>
  );
};

export default DepartmentForm;
