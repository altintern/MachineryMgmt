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
import { Category, CategoryRequest } from '@/services/categoryService';

interface CategoryFormProps {
  category?: Category;
  onSubmit: (category: CategoryRequest) => void;
  onCancel: () => void;
}

const CategoryForm: React.FC<CategoryFormProps> = ({ category, onSubmit, onCancel }) => {
  const [formData, setFormData] = React.useState<CategoryRequest>({
    name: category?.name || '',
    description: category?.description || '',
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
          {category ? 'Edit Category Details' : 'New Category Details'}
        </Typography>
        <StyledGrid>
          <GridItem>
            <TextField
              fullWidth
              required
              name="name"
              label="Category Name"
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
                {category ? 'Update' : 'Create'} Category
              </Button>
            </Box>
          </GridItem>
        </StyledGrid>
      </Paper>
    </Box>
  );
};

export default CategoryForm;
