import React from 'react';
import { TextField, Button, Box, Paper, Typography, FormControl, InputLabel, Select, MenuItem } from '@mui/material';
import { styled } from '@mui/material/styles';
import { Model, ModelRequest } from '@/services/modelService';
import { useQuery } from 'react-query';
import { makeService } from '@/services/makeService';

const StyledGrid = styled('div')(({ theme }) => ({
  display: 'grid',
  gap: theme.spacing(3),
}));

const GridItem = styled('div')({
  width: '100%',
});

interface ModelFormProps {
  model?: Model;
  onSubmit: (model: ModelRequest) => void;
  onCancel: () => void;
}

const ModelForm: React.FC<ModelFormProps> = ({ model, onSubmit, onCancel }) => {
  const { data: makesData } = useQuery('makes', makeService.getAllMakes);

  const [formData, setFormData] = React.useState<ModelRequest>({
    name: model?.name || '',
    description: model?.description || '',
    makeId: model?.make?.id || 0,
  });

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData(prev => ({ 
      ...prev, 
      [name]: name === 'makeId' ? parseInt(value, 10) : value 
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
          {model ? 'Edit Model Details' : 'New Model Details'}
        </Typography>
        <StyledGrid>
          <GridItem>
            <TextField
              fullWidth
              required
              name="name"
              label="Model Name"
              value={formData.name}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </GridItem>
          <GridItem>
            <TextField
              fullWidth
              required
              name="description"
              label="Description"
              value={formData.description}
              onChange={handleChange}
              multiline
              rows={3}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </GridItem>
          <GridItem>
            <TextField
              fullWidth
              required
              select
              name="makeId"
              label="Make"
              value={formData.makeId}
              onChange={handleChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            >
              <MenuItem value={0} disabled>Select a Make</MenuItem>
              {makesData?.data?.map((make: any) => (
                <MenuItem key={make.id} value={make.id}>
                  {make.name}
                </MenuItem>
              ))}
            </TextField>
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
                disabled={formData.makeId === 0}
              >
                {model ? 'Update' : 'Create'} Model
              </Button>
            </Box>
          </GridItem>
        </StyledGrid>
      </Paper>
    </Box>
  );
};

export default ModelForm;
