import React from 'react';
import { 
  TextField, 
  Button, 
  Box, 
  Paper, 
  Typography 
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { DesignationDto, DesignationRequestDto } from '@/services/designationService';

const StyledGrid = styled('div')(({ theme }) => ({
  display: 'grid',
  gap: theme.spacing(3),
}));

const GridItem = styled('div')({
  width: '100%',
});

interface DesignationFormProps {
  designation?: DesignationDto;
  onSubmit: (designation: DesignationRequestDto) => void;
  onCancel: () => void;
}

const DesignationForm: React.FC<DesignationFormProps> = ({ designation, onSubmit, onCancel }) => {
  const [formData, setFormData] = React.useState<DesignationRequestDto>({
    name: designation?.name || '',
    description: designation?.description || '',
  });

  // Handle text inputs
  const handleInputChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    console.log(`Input change - ${name}: ${value}`);
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    console.log('Submitting designation data:', formData);
    onSubmit(formData);
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
      <Paper elevation={0} sx={{ p: 3, mb: 3 }}>
        <Box sx={{ mb: 3 }}>
          <Typography component="div" variant="h6">
            {designation ? 'Edit Designation' : 'New Designation'}
          </Typography>
        </Box>
        <StyledGrid>
          <GridItem>
            <TextField
              fullWidth
              required
              name="name"
              label="Designation Name"
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
                {designation ? 'Update' : 'Create'} Designation
              </Button>
            </Box>
          </GridItem>
        </StyledGrid>
      </Paper>
    </Box>
  );
};

export default DesignationForm;
