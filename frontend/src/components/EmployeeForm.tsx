'use client';

import React from 'react';
import { TextField, Button, Box, Paper, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import { useQuery } from 'react-query';
import { departmentService } from '@/services/departmentService';
import { designationService } from '@/services/designationService';
import { EmployeeDto, EmployeeRequestDto } from '@/services/employeeService';

const StyledGrid = styled('div')(({ theme }) => ({
  display: 'grid',
  gap: theme.spacing(3),
}));

const GridItem = styled('div')({
  width: '100%',
});

interface EmployeeFormProps {
  employee?: EmployeeDto;
  onSubmit: (data: EmployeeRequestDto) => void;
  onCancel: () => void;
}

export default function EmployeeForm({
  employee,
  onSubmit,
  onCancel,
}: EmployeeFormProps) {
  const [formData, setFormData] = React.useState<EmployeeRequestDto>({
    name: employee?.name || '',
    remarks: employee?.remarks || '',
    departmentId: employee?.department?.id || 0,
    designationId: employee?.designation?.id || 0,
  });

  // Fetch departments
  const { data: departments = [], isLoading: isLoadingDepartments } = useQuery(
    ['departments'], 
    () => departmentService.getAllDepartments(),
    {
      onError: (error) => {
        console.error('Failed to fetch departments:', error);
      }
    }
  );

  // Fetch designations
  const { data: designations = [], isLoading: isLoadingDesignations } = useQuery(
    ['designations'], 
    () => designationService.getAllDesignations(),
    {
      onError: (error) => {
        console.error('Failed to fetch designations:', error);
      }
    }
  );

  // Handle text input changes
  const handleTextChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: value,
    }));
  };

  // Handle select input changes
  const handleSelectChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: parseInt(value, 10),
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    // Validate form data
    if (!formData.name.trim()) {
      alert('Name is required');
      return;
    }
    
    if (formData.departmentId === 0) {
      alert('Please select a department');
      return;
    }
    
    if (formData.designationId === 0) {
      alert('Please select a designation');
      return;
    }
    
    onSubmit(formData);
  };

  if (isLoadingDepartments || isLoadingDesignations) {
    return <div>Loading...</div>;
  }

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
      <Paper elevation={0} sx={{ p: 3, mb: 3 }}>
        <Box sx={{ mb: 3 }}>
          <Typography component="div" variant="h6">
            {employee ? 'Edit Employee' : 'New Employee'}
          </Typography>
        </Box>
        <StyledGrid>
          <GridItem>
            <TextField
              fullWidth
              required
              name="name"
              label="Name"
              value={formData.name}
              onChange={handleTextChange}
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
              name="remarks"
              label="Remarks"
              value={formData.remarks}
              onChange={handleTextChange}
              variant="outlined"
              sx={{ '& .MuiOutlinedInput-root': { backgroundColor: 'white' } }}
            />
          </GridItem>

          <GridItem>
            <label htmlFor="departmentId" className="block mb-2 text-sm font-medium">
              Department
            </label>
            <select
              id="departmentId"
              name="departmentId"
              className="bg-white border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
              value={formData.departmentId || ''}
              onChange={handleSelectChange}
              required
            >
              <option value="" disabled>Select Department</option>
              {departments.map((department) => (
                <option key={department.id} value={department.id}>
                  {department.name}
                </option>
              ))}
            </select>
          </GridItem>

          <GridItem>
            <label htmlFor="designationId" className="block mb-2 text-sm font-medium">
              Designation
            </label>
            <select
              id="designationId"
              name="designationId"
              className="bg-white border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
              value={formData.designationId || ''}
              onChange={handleSelectChange}
              required
            >
              <option value="" disabled>Select Designation</option>
              {designations.map((designation) => (
                <option key={designation.id} value={designation.id}>
                  {designation.name}
                </option>
              ))}
            </select>
          </GridItem>

          <Box sx={{ display: 'flex', gap: 2, justifyContent: 'flex-end' }}>
            <Button variant="outlined" onClick={onCancel} size="large">
              Cancel
            </Button>
            <Button
              variant="contained"
              type="submit"
              size="large"
              sx={{
                backgroundColor: 'primary.dark',
                '&:hover': {
                  backgroundColor: 'primary.main',
                },
              }}
            >
              {employee ? 'Update' : 'Create'}
            </Button>
          </Box>
        </StyledGrid>
      </Paper>
    </Box>
  );
}
