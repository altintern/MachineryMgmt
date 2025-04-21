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
import { Category, CategoryRequest, categoryService } from '@/services/categoryService';
import CategoryForm from '@/components/CategoryForm';
import { Tooltip } from '@/components/ui/tooltip';
import { TooltipContent, TooltipProvider, TooltipTrigger } from '@/components/ui/tooltip';

export default function CategoriesPage() {
  const [open, setOpen] = React.useState(false);
  const [selectedCategory, setSelectedCategory] = React.useState<Category | null>(null);
  const queryClient = useQueryClient();

  // Fetch categories
  const { data: categoriesData, isLoading } = useQuery(['categories'], categoryService.getAllCategories);
  const categories = categoriesData?.data || [];

  // Mutations
  const createMutation = useMutation(categoryService.createCategory, {
    onSuccess: () => {
      queryClient.invalidateQueries(['categories']);
      handleClose();
    },
  });

  const updateMutation = useMutation(
    (data: { id: number; category: CategoryRequest }) => categoryService.updateCategory(data.id, data.category),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['categories']);
        handleClose();
      },
    }
  );

  const deleteMutation = useMutation(categoryService.deleteCategory, {
    onSuccess: () => {
      queryClient.invalidateQueries(['categories']);
    },
  });

  const handleOpen = (category?: Category) => {
    setSelectedCategory(category || null);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedCategory(null);
    setOpen(false);
  };

  const handleSubmit = (categoryData: CategoryRequest) => {
    if (selectedCategory?.id) {
      updateMutation.mutate({ id: selectedCategory.id, category: categoryData });
    } else {
      createMutation.mutate(categoryData);
    }
  };

  const handleDelete = (id: number) => {
    if (confirm('Are you sure you want to delete this category?')) {
      deleteMutation.mutate(id);
    }
  };

  const columns = [
    { key: 'name', label: 'Name' },
    { key: 'description', label: 'Description' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'description') {
      return (
        <TooltipProvider>
          <Tooltip>
            <TooltipTrigger asChild>
              <div className="max-w-[400px] overflow-hidden text-ellipsis whitespace-nowrap">
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
        title="Equipment Categories"
        columns={columns}
        data={categories}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="sm:max-w-[500px]">
          <DialogHeader>
            <DialogTitle>
              {selectedCategory ? 'Edit Category' : 'Create Category'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <CategoryForm
              category={selectedCategory || undefined}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
