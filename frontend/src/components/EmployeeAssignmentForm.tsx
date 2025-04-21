'use client';

import React from 'react';
import { TextField, Button, Box, Paper, Typography } from '@mui/material';
import { styled } from '@mui/material/styles';
import { useQuery } from 'react-query';
import { employeeService } from '@/services/employeeService';
import { projectService } from '@/services/projectService';
import { equipmentService } from '@/services/equipmentService';
import { EmployeeAssignmentDto, EmployeeAssignmentRequestDto } from '@/services/employeeAssignmentService';

const StyledGrid = styled('div')(({ theme }) => ({
  display: 'grid',
  gap: theme.spacing(3),
}));

const GridItem = styled('div')({
  width: '100%',
});

interface EmployeeAssignmentFormProps {
  assignment?: EmployeeAssignmentDto;
  onSubmit: (data: EmployeeAssignmentRequestDto) => void;
  onCancel: () => void;
}

export default function EmployeeAssignmentForm({
  assignment,
  onSubmit,
  onCancel,
}: EmployeeAssignmentFormProps) {
  const [formData, setFormData] = React.useState<EmployeeAssignmentRequestDto>({
    employeeId: assignment?.employee?.id || 0,
    projectId: assignment?.project?.id || 0,
    joiningDate: assignment?.joiningDate || new Date().toISOString().split('T')[0],
    equipmentId: assignment?.equipment?.id || 0,
  });

  // Fetch employees
  const { data: employees = [], isLoading: isLoadingEmployees } = useQuery(
    ['employees'], 
    () => employeeService.getAllEmployees(),
    {
      onError: (error) => {
        console.error('Failed to fetch employees:', error);
      }
    }
  );

  // Fetch projects
  const { data: projects = [], isLoading: isLoadingProjects } = useQuery(
    ['projects'], 
    () => projectService.getAllProjects(),
    {
      onError: (error) => {
        console.error('Failed to fetch projects:', error);
      }
    }
  );

  // Fetch equipment
  const { data: equipment = [], isLoading: isLoadingEquipment } = useQuery(
    ['equipment'], 
    () => equipmentService.getAllEquipment(),
    {
      onError: (error) => {
        console.error('Failed to fetch equipment:', error);
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
  const handleSelectChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [name]: parseInt(value, 10),
    }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    // Validate form data
    if (formData.employeeId === 0) {
      alert('Please select an employee');
      return;
    }
    
    if (formData.projectId === 0) {
      alert('Please select a project');
      return;
    }
    
    if (!formData.joiningDate) {
      alert('Joining date is required');
      return;
    }
    
    if (formData.equipmentId === 0) {
      alert('Please select equipment');
      return;
    }
    
    onSubmit(formData);
  };

  if (isLoadingEmployees || isLoadingProjects || isLoadingEquipment) {
    return <div>Loading...</div>;
  }

  return (
    <Box component="form" onSubmit={handleSubmit} sx={{ mt: 2 }}>
      <Paper elevation={0} sx={{ p: 3, mb: 3 }}>
        <Box sx={{ mb: 3 }}>
          <Typography component="div" variant="h6">
            {assignment ? 'Edit Assignment' : 'New Assignment'}
          </Typography>
        </Box>
        <StyledGrid>
          <GridItem>
            <label htmlFor="employeeId" className="block mb-2 text-sm font-medium">
              Employee
            </label>
            <select
              id="employeeId"
              name="employeeId"
              className="bg-white border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
              value={formData.employeeId || ''}
              onChange={(e) => setFormData(prev => ({ ...prev, employeeId: parseInt(e.target.value, 10) }))}
              required
            >
              <option value="" disabled>Select Employee</option>
              {employees.map((employee) => (
                <option key={employee.id} value={employee.id}>
                  {employee.name}
                </option>
              ))}
            </select>
          </GridItem>

          <GridItem>
            <label htmlFor="projectId" className="block mb-2 text-sm font-medium">
              Project
            </label>
            <select
              id="projectId"
              name="projectId"
              className="bg-white border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
              value={formData.projectId || ''}
              onChange={(e) => setFormData(prev => ({ ...prev, projectId: parseInt(e.target.value, 10) }))}
              required
            >
              <option value="" disabled>Select Project</option>
              {projects.map((project) => (
                <option key={project.id} value={project.id}>
                  {project.name}
                </option>
              ))}
            </select>
          </GridItem>

          <GridItem>
            <label htmlFor="joiningDate" className="block mb-2 text-sm font-medium">
              Joining Date
            </label>
            <input
              id="joiningDate"
              type="date"
              name="joiningDate"
              className="bg-white border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
              value={formData.joiningDate}
              onChange={(e) => setFormData(prev => ({ ...prev, joiningDate: e.target.value }))}
              required
            />
          </GridItem>

          <GridItem>
            <label htmlFor="equipmentId" className="block mb-2 text-sm font-medium">
              Equipment
            </label>
            <select
              id="equipmentId"
              name="equipmentId"
              className="bg-white border border-gray-300 text-gray-900 text-sm rounded-lg focus:ring-blue-500 focus:border-blue-500 block w-full p-2.5"
              value={formData.equipmentId || ''}
              onChange={(e) => setFormData(prev => ({ ...prev, equipmentId: parseInt(e.target.value, 10) }))}
              required
            >
              <option value="" disabled>Select Equipment</option>
              {equipment.map((item) => (
                <option key={item.id} value={item.id}>
                  {item.name}
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
              {assignment ? 'Update' : 'Create'}
            </Button>
          </Box>
        </StyledGrid>
      </Paper>
    </Box>
  );
}
