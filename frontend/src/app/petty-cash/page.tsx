'use client';

import { useState } from 'react';
import { useQuery, useMutation, useQueryClient } from 'react-query';
import {
  Dialog,
  DialogContent,
  DialogHeader,
  DialogTitle,
} from '@/components/ui/dialog';
import DataTable from '@/components/DataTable';
import PettyCashForm from '@/components/PettyCashForm';
import pettyCashService, { PettyCashTransaction, PettyCashTransactionRequest } from '@/services/pettyCashService';
import { toast } from 'sonner';

export default function PettyCashPage() {
  const [open, setOpen] = useState(false);
  const [selectedTransaction, setSelectedTransaction] = useState<PettyCashTransaction | undefined>();
  const queryClient = useQueryClient();

  const { data: pettyCashData } = useQuery(['pettyCash'], () =>
    pettyCashService.getAllPettyCash()
  );

  // Make sure we have the correct data structure
  const transactions = pettyCashData?.data || [];

  const createMutation = useMutation(
    (data: PettyCashTransactionRequest) => pettyCashService.createPettyCash(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['pettyCash']);
        setOpen(false);
        toast.success('Petty cash transaction created successfully');
      },
      onError: () => {
        toast.error('Failed to create petty cash transaction');
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: PettyCashTransactionRequest }) => pettyCashService.updatePettyCash(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['pettyCash']);
        setOpen(false);
        toast.success('Petty cash transaction updated successfully');
      },
      onError: () => {
        toast.error('Failed to update petty cash transaction');
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => pettyCashService.deletePettyCash(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['pettyCash']);
        toast.success('Petty cash transaction deleted successfully');
      },
      onError: () => {
        toast.error('Failed to delete petty cash transaction');
      },
    }
  );

  const handleOpen = (transaction?: PettyCashTransaction) => {
    setSelectedTransaction(transaction);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedTransaction(undefined);
    setOpen(false);
  };

  const handleSubmit = async (data: PettyCashTransactionRequest) => {
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

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric',
    });
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
    { key: 'reportDate', label: 'Date' },
    { key: 'project.name', label: 'Project' },
    { key: 'equipment.name', label: 'Equipment' },
    { key: 'item.name', label: 'Item' },
    { key: 'amountSpent', label: 'Amount' },
    { key: 'quantity', label: 'Quantity' },
    { key: 'cumulativeAmountSpent', label: 'Cumulative' },
    { key: 'purposeJustification', label: 'Purpose' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'reportDate') {
      return formatDate(item.reportDate);
    }
    if (column === 'amountSpent') {
      return formatCurrency(item.amountSpent);
    }
    if (column === 'cumulativeAmountSpent') {
      return formatCurrency(item.cumulativeAmountSpent);
    }
    return null;
  };

  return (
    <div className="p-4">
      <DataTable
        title="Petty Cash Transactions"
        columns={columns}
        data={Array.isArray(transactions) ? transactions : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="max-w-3xl max-h-[90vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle>
              {selectedTransaction ? 'Edit Transaction' : 'Add Transaction'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <PettyCashForm
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
