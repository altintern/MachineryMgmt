'use client';

import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import { Button } from '@/components/ui/button';
import DataTable from '@/components/DataTable';
import ItemForm from '@/components/ItemForm';
import itemService, { Item, ItemRequest } from '@/services/itemService';
import { toast } from 'sonner';

export default function ItemsPage() {
  const [open, setOpen] = useState(false);
  const [selectedItem, setSelectedItem] = useState<Item | undefined>();
  const [page, setPage] = useState(0);
  const [pageSize] = useState(10);
  const queryClient = useQueryClient();

  const { data } = useQuery(['items', page], () =>
    itemService.getAllItems(page, pageSize)
  );

  const items = data || [];

  const createMutation = useMutation(
    (data: ItemRequest) => itemService.createItem(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['items']);
        setOpen(false);
        toast.success('Item created successfully');
      },
      onError: (error: any) => {
        console.error('Create error:', error);
        toast.error(error?.response?.data?.message || 'Failed to create item');
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: ItemRequest }) => itemService.updateItem(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['items']);
        setOpen(false);
        toast.success('Item updated successfully');
      },
      onError: (error: any) => {
        console.error('Update error:', error);
        toast.error(error?.response?.data?.message || 'Failed to update item');
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => itemService.deleteItem(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['items']);
        toast.success('Item deleted successfully');
      },
      onError: (error: any) => {
        console.error('Delete error:', error);
        toast.error(error?.response?.data?.message || 'Failed to delete item');
      },
    }
  );

  const handleOpen = (item?: Item) => {
    setSelectedItem(item);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedItem(undefined);
    setOpen(false);
  };

  const handleSubmit = async (data: ItemRequest) => {
    if (selectedItem) {
      await updateMutation.mutateAsync({ id: selectedItem.id, data });
    } else {
      await createMutation.mutateAsync(data);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this item?')) {
      await deleteMutation.mutateAsync(id);
    }
  };

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      minimumFractionDigits: 0,
      maximumFractionDigits: 0,
    }).format(amount);
  };

  const columns = [
    { key: 'name', label: 'Name' },
    { key: 'description', label: 'Description' },
    { key: 'unit', label: 'Unit' },
    { key: 'unitPrice', label: 'Unit Price' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'unitPrice') {
      return formatCurrency(item.unitPrice);
    }
    return null;
  };

  return (
    <div className="p-6">
      <DataTable
        title="Items"
        columns={columns}
        data={Array.isArray(items) ? items : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <div className="flex justify-center gap-2 mt-4">
        <Button
          variant="outline"
          onClick={() => setPage(p => Math.max(0, p - 1))}
          disabled={page === 0}
        >
          Previous
        </Button>
        <Button
          variant="outline"
          onClick={() => setPage(p => p + 1)}
          disabled={items.length < pageSize}
        >
          Next
        </Button>
      </div>

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="max-w-3xl max-h-[90vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle>
              {selectedItem ? 'Edit Item' : 'Add Item'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <ItemForm
              item={selectedItem}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
