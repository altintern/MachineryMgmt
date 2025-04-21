'use client';

import React from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import DataTable from '@/components/DataTable';
import { Project, projectService } from '@/services/projectService';
import ProjectForm from '@/components/ProjectForm';
import { Badge } from '@/components/ui';
import { toast } from 'react-hot-toast';

const getStatusColor = (status: string) => {
  switch (status.toUpperCase()) {
    case 'COMPLETED':
      return 'bg-green-500 hover:bg-green-600';
    case 'IN_PROGRESS':
      return 'bg-yellow-500 hover:bg-yellow-600';
    case 'PENDING':
      return 'bg-gray-500 hover:bg-gray-600';
    default:
      return 'bg-gray-500 hover:bg-gray-600';
  }
};

export default function ProjectsPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedProject, setSelectedProject] = React.useState<Project | null>(null);
  const queryClient = useQueryClient();

  // Fetch projects data
  const { data: projects, isLoading, isError, error } = useQuery(
    'projects', 
    projectService.getAllProjects,
    {
      onError: (err: any) => {
        console.error('Failed to fetch projects:', err);
        toast.error('Failed to load projects. Please try again later.');
      }
    }
  );

  // Create project mutation
  const createMutation = useMutation(projectService.createProject, {
    onSuccess: () => {
      queryClient.invalidateQueries('projects');
      handleClose();
      toast.success('Project created successfully!');
    },
    onError: (err: any) => {
      console.error('Failed to create project:', err);
      toast.error('Failed to create project. Please try again.');
    }
  });

  // Update project mutation
  const updateMutation = useMutation(
    (data: { id: number; project: Project }) => projectService.updateProject(data.id, data.project),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('projects');
        handleClose();
        toast.success('Project updated successfully!');
      },
      onError: (err: any) => {
        console.error('Failed to update project:', err);
        toast.error('Failed to update project. Please try again.');
      }
    }
  );

  // Delete project mutation
  const deleteMutation = useMutation(projectService.deleteProject, {
    onSuccess: () => {
      queryClient.invalidateQueries('projects');
      toast.success('Project deleted successfully!');
    },
    onError: (err: any) => {
      console.error('Failed to delete project:', err);
      toast.error('Failed to delete project. Please try again.');
    }
  });

  const handleOpen = (project?: Project) => {
    setSelectedProject(project || null);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedProject(null);
    setOpen(false);
  };

  const handleSubmit = (project: Project) => {
    if (selectedProject?.id) {
      updateMutation.mutate({ id: selectedProject.id, project });
    } else {
      createMutation.mutate(project);
    }
  };

  const handleDelete = (id: number) => {
    if (confirm('Are you sure you want to delete this project?')) {
      deleteMutation.mutate(id);
    }
  };

  const columns = [
    { key: 'name', label: 'Name' },
    { key: 'description', label: 'Description' },
    { key: 'location', label: 'Location' },
    { key: 'startDate', label: 'Start Date' },
    { key: 'endDate', label: 'End Date' },
    { key: 'status', label: 'Status' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'startDate' || column === 'endDate') {
      return new Date(item[column]).toLocaleDateString();
    }
    if (column === 'status') {
      return (
        <Badge className={`${getStatusColor(item.status)} capitalize min-w-[100px] justify-center`}>
          {item.status}
        </Badge>
      );
    }
    return item[column];
  };

  if (isLoading) {
    return <div className="p-6">Loading projects...</div>;
  }

  if (isError) {
    return <div className="p-6 text-red-500">Error loading projects. Please try again later.</div>;
  }

  return (
    <div className="p-6">
      <DataTable
        title="Projects"
        columns={columns}
        data={projects || []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="sm:max-w-[500px]">
          <DialogHeader>
            <DialogTitle>
              {selectedProject ? 'Edit Project' : 'Create Project'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <ProjectForm
              project={selectedProject || undefined}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
