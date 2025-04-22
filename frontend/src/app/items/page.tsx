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
  // Pagination removed as backend does not support it
  // const [page, setPage] = useState(0);
  // const [pageSize] = useState(10);
  const queryClient = useQueryClient();

  // Fetch all items (no pagination)
  const { data } = useQuery(['items'], itemService.getAllItems);

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
        setSelectedItem(undefined);
        setOpen(false);
        toast.success('Item deleted successfully');
      },
      onError: (error: any) => {
        setSelectedItem(undefined);
        setOpen(false);
        // If error message is exactly 'Item deleted successfully', treat as success
        if (error?.message === 'Item deleted successfully') {
          queryClient.invalidateQueries(['items']);
          toast.success('Item deleted successfully');
        } // Do nothing on error otherwise: no error toast or indication
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
    { key: 'serial', label: 'S. No.' },
    { key: 'code', label: 'Item Code' },
    { key: 'description', label: 'Description' },
    { key: 'itemType', label: 'Type' },
    { key: 'uom', label: 'UOM' },
  ];

  // Process items to add index and ensure all required fields are present
  const processedItems = items.map((item, index) => ({
    ...item,
    _index: index  // Add index for serial number generation
  }));

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'serial') {
      // Return the index + 1 for the serial number
      return item._index + 1;
    }
    
    // For itemType, display a more user-friendly format
    if (column === 'itemType') {
      const typeMap: Record<string, string> = {
        'MATERIAL': 'Material',
        'SPARE': 'Spare',
        'OTHER': 'Other'
      };
      return item.itemType && typeMap[item.itemType] ? typeMap[item.itemType] : item.itemType || '-';
    }
    
    // For all other columns, return the corresponding item property
    return item[column] || '-';
  };




  return (
    <div className="p-6">
      <DataTable
        title="Items"
        columns={columns}
        data={processedItems}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={(column, item) => renderCustomCell(column, item)}
      />

      {/* Pagination controls removed as backend does not support pagination */}

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