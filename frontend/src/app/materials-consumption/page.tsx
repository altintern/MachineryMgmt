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
import MaterialsConsumptionForm from '@/components/MaterialsConsumptionForm';
import materialsConsumptionService, { MaterialsConsumptionTransaction, MaterialsConsumptionTransactionRequest } from '@/services/materialsConsumptionService';
import { toast } from 'sonner';

export default function MaterialsConsumptionPage() {
  const [open, setOpen] = useState(false);
  const [selectedTransaction, setSelectedTransaction] = useState<MaterialsConsumptionTransaction | undefined>();
  const [page, setPage] = useState(0);
  const [pageSize] = useState(10);
  const queryClient = useQueryClient();

  const { data } = useQuery(['materials-consumption', page], () =>
    materialsConsumptionService.getAllTransactions(page, pageSize)
  );

  const transactions = data?.data || [];

  const createMutation = useMutation(
    (data: MaterialsConsumptionTransactionRequest) => materialsConsumptionService.createTransaction(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['materials-consumption']);
        setOpen(false);
        toast.success('Transaction created successfully');
      },
      onError: (error: any) => {
        console.error('Create error:', error);
        toast.error(error?.response?.data?.message || 'Failed to create transaction');
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: MaterialsConsumptionTransactionRequest }) => materialsConsumptionService.updateTransaction(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['materials-consumption']);
        setOpen(false);
        toast.success('Transaction updated successfully');
      },
      onError: (error: any) => {
        console.error('Update error:', error);
        toast.error(error?.response?.data?.message || 'Failed to update transaction');
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => materialsConsumptionService.deleteTransaction(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['materials-consumption']);
        toast.success('Transaction deleted successfully');
      },
      onError: (error: any) => {
        console.error('Delete error:', error);
        toast.error(error?.response?.data?.message || 'Failed to delete transaction');
      },
    }
  );

  const handleOpen = (transaction?: MaterialsConsumptionTransaction) => {
    setSelectedTransaction(transaction);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedTransaction(undefined);
    setOpen(false);
  };

  const handleSubmit = async (data: MaterialsConsumptionTransactionRequest) => {
    if (selectedTransaction) {
      await updateMutation.mutateAsync({ id: selectedTransaction.id, data });
    } else {
      await createMutation.mutateAsync(data);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this transaction?')) {
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

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-IN', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
  };

  const columns = [
    { key: 'issueDate', label: 'Issue Date' },
    { key: 'project.name', label: 'Project' },
    { key: 'equipment.name', label: 'Equipment' },
    { key: 'item.code', label: 'Item Code' },
    // { key: 'item.description', label: 'Description' }, // Uncomment if you want description
    { key: 'quantity', label: 'Quantity' },
    { key: 'costPerUnit', label: 'Cost/Unit' },
    { key: 'totalCost', label: 'Total Cost' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'issueDate') {
      return formatDate(item.issueDate);
    }
    if (column === 'costPerUnit') {
      return formatCurrency(item.costPerUnit);
    }
    if (column === 'totalCost') {
      return formatCurrency(item.totalCost);
    }
    return null;
  };
  return (
    <div className="p-4">
      <DataTable
        title="Materials Consumption"
        columns={columns}
        data={transactions}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={(column, item) => {
          if (column === 'project.name') {
            return item.project?.name || '';
          }
          if (column === 'equipment.name') {
            return item.equipment?.name || '';
          }
          if (column === 'item.code') {
            return item.item?.code || '';
          }
          // Uncomment if you want to show description
          // if (column === 'item.description') {
          //   return item.item?.description || '';
          // }
          if (column === 'costPerUnit' || column === 'totalCost') {
            return formatCurrency(item[column]);
          }
          if (column === 'issueDate') {
            return formatDate(item.issueDate);
          }
          if (column === 'quantity') {
            return typeof item.quantity === 'number' ? item.quantity : '';
          }
          return null;
        }}
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
          disabled={!data?.data || data.data.length < pageSize}
        >
          Next
        </Button>
      </div>

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="max-w-3xl max-h-[90vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle>
              {selectedTransaction ? 'Edit Transaction' : 'Add Transaction'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <MaterialsConsumptionForm
              transaction={selectedTransaction}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
