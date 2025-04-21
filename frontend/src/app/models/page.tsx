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
import { Model, ModelRequest, modelService } from '@/services/modelService';
import ModelForm from '@/components/ModelForm';
import { Tooltip } from '@/components/ui/tooltip';
import { TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip';
import { toast } from 'sonner';

export default function ModelsPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedModel, setSelectedModel] = React.useState<Model | null>(null);
  const queryClient = useQueryClient();

  // Fetch models
  const { data: modelsData, isLoading } = useQuery('models', modelService.getAllModels);
  const models = modelsData?.data || [];

  // Mutations with improved toast notifications
  const createMutation = useMutation(modelService.createModel, {
    onSuccess: (data) => {
      queryClient.invalidateQueries('models');
      handleClose();
      toast.success('Success', {
        description: 'Model created successfully',
        position: 'top-right',
      });
    },
    onError: (error: any) => {
      toast.error('Error', {
        description: error?.response?.data?.message || 'Failed to create model',
        position: 'top-right',
      });
    }
  });

  const updateMutation = useMutation(
    (data: { id: number; model: ModelRequest }) => modelService.updateModel(data.id, data.model),
    {
      onSuccess: (data) => {
        queryClient.invalidateQueries('models');
        handleClose();
        toast.success('Success', {
          description: 'Model updated successfully',
          position: 'top-right',
        });
      },
      onError: (error: any) => {
        toast.error('Error', {
          description: error?.response?.data?.message || 'Failed to update model',
          position: 'top-right',
        });
      }
    }
  );

  const deleteMutation = useMutation(modelService.deleteModel, {
    onSuccess: (data) => {
      queryClient.invalidateQueries('models');
      toast.success('Success', {
        description: 'Model deleted successfully',
        position: 'top-right',
      });
    },
    onError: (error: any) => {
      toast.error('Error', {
        description: error?.response?.data?.message || 'Failed to delete model',
        position: 'top-right',
      });
    }
  });

  const handleOpen = (model?: Model) => {
    setSelectedModel(model || null);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedModel(null);
    setOpen(false);
  };

  const handleSubmit = (modelData: ModelRequest) => {
    if (selectedModel?.id) {
      updateMutation.mutate({ id: selectedModel.id, model: modelData });
    } else {
      createMutation.mutate(modelData);
    }
  };

  const handleDelete = (id: number) => {
    if (confirm('Are you sure you want to delete this model?')) {
      deleteMutation.mutate(id);
    }
  };

  const columns = [
    { key: 'name', label: 'Name' },
    { key: 'make.name', label: 'Make' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'name') {
      return item.name; // Explicitly return the name
    }
    if (column === 'make.name' && item.make) {
      return item.make.name;
    }
    // For debugging
    console.log('Rendering cell for column:', column, 'item:', item);
    return undefined; // Return undefined to use default rendering
  };

  if (isLoading) {
    return <div className="p-6">Loading...</div>;
  }

  return (
    <div className="p-6">
      <DataTable
        title="Models"
        columns={columns}
        data={models}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="sm:max-w-[500px]">
          <DialogHeader>
            <DialogTitle>
              {selectedModel ? 'Edit Model' : 'Create Model'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <ModelForm
              model={selectedModel || undefined}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
