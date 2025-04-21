import React from 'react';
import { TextField, Button, Box, Paper, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';

const StyledGrid = styled('div')(({ theme }) => ({
  display: 'grid',
  gap: theme.spacing(3),
}));

const GridItem = styled('div')({
  width: '100%',
});
import { Make } from '@/services/makeService';

interface MakeFormProps {
  make?: Make;
  onSubmit: (make: Make) => void;
  onCancel: () => void;
}

const MakeForm: React.FC<MakeFormProps> = ({ make, onSubmit, onCancel }) => {
  const [formData, setFormData] = React.useState<Make>({
    name: make?.name || '',
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
      <Paper elevation={0} sx={{ p: 3, mb: 3 }}>
        <Typography variant="h6" sx={{ mb: 3 }}>
          {make ? 'Edit Make Details' : 'New Make Details'}
        </Typography>
        <StyledGrid>
          <GridItem>
            <TextField
              fullWidth
              required
              name="name"
              label="Make Name"
              value={formData.name}
              onChange={handleChange}
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
                {make ? 'Update' : 'Create'} Make
              </Button>
            </Box>
          </GridItem>
        </StyledGrid>
      </Paper>
    </Box>
  );
};

export default MakeForm;
