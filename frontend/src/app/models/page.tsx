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

export default function ModelsPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedModel, setSelectedModel] = React.useState<Model | null>(null);
  const queryClient = useQueryClient();

  // Fetch models
  const { data: modelsData, isLoading } = useQuery('models', modelService.getAllModels);
  const models = modelsData?.data || [];

  // Mutations
  const createMutation = useMutation(modelService.createModel, {
    onSuccess: () => {
      queryClient.invalidateQueries('models');
      handleClose();
    },
  });

  const updateMutation = useMutation(
    (data: { id: number; model: ModelRequest }) => modelService.updateModel(data.id, data.model),
    {
      onSuccess: () => {
        queryClient.invalidateQueries('models');
        handleClose();
      },
    }
  );

  const deleteMutation = useMutation(modelService.deleteModel, {
    onSuccess: () => {
      queryClient.invalidateQueries('models');
    },
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
    { key: 'description', label: 'Description' },
    { key: 'make.name', label: 'Make' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'description') {
      return (
        <TooltipProvider>
          <Tooltip>
            <TooltipTrigger asChild>
              <div className="max-w-[300px] overflow-hidden text-ellipsis whitespace-nowrap">
                {item.description}
              </div>
            </TooltipTrigger>
            <TooltipContent>
              <p>{item.description}</p>
            </TooltipContent>
          </Tooltip>
        </TooltipProvider>
      );
    }
    return null;
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
