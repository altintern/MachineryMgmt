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
import StockStatementForm from '@/components/StockStatementForm';
import stockStatementService, { StockStatement, StockStatementRequest } from '@/services/stockStatementService';
import { toast } from 'sonner';

export default function StockStatementPage() {
  const [open, setOpen] = useState(false);
  const [selectedStatement, setSelectedStatement] = useState<StockStatement | undefined>();
  const queryClient = useQueryClient();

  const { data: statements = [] } = useQuery(['stockStatements'], () =>
    stockStatementService.getAllStockStatements()
  );

  const createMutation = useMutation(
    (data: StockStatementRequest) => stockStatementService.createStockStatement(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['stockStatements']);
        setOpen(false);
        toast.success('Stock statement created successfully');
      },
      onError: () => {
        toast.error('Failed to create stock statement');
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: StockStatementRequest }) => stockStatementService.updateStockStatement(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['stockStatements']);
        setOpen(false);
        toast.success('Stock statement updated successfully');
      },
      onError: () => {
        toast.error('Failed to update stock statement');
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => stockStatementService.deleteStockStatement(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['stockStatements']);
        toast.success('Stock statement deleted successfully');
      },
      onError: () => {
        toast.error('Failed to delete stock statement');
      },
    }
  );

  const handleOpen = (statement?: StockStatement) => {
    setSelectedStatement(statement);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedStatement(undefined);
    setOpen(false);
  };

  const handleSubmit = async (data: StockStatementRequest) => {
    if (selectedStatement) {
      await updateMutation.mutateAsync({ id: selectedStatement.id, data });
    } else {
      await createMutation.mutateAsync(data);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this statement?')) {
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

  const getMonthName = (month: number) => {
    return new Date(2000, month - 1, 1).toLocaleString('default', { month: 'long' });
  };

  const columns = [
    { key: 'monthYear', label: 'Month/Year' },
    { key: 'project.name', label: 'Project' },
    { key: 'equipment.name', label: 'Equipment' },
    { key: 'item.code', label: 'Item Code' },
    { key: 'balance', label: 'Balance' },
    { key: 'landedValue', label: 'Landed Value' },
    { key: 'landedRate', label: 'Landed Rate' },
    { key: 'lastIssueOn', label: 'Last Issue' },
    { key: 'lastReceiptOn', label: 'Last Receipt' },
    // Add more columns here if you want to show more details
    // { key: 'id', label: 'ID' },
  ];

  const renderCustomCell = (column: string, item: any) => {
    if (column === 'monthYear') {
      return `${getMonthName(item.month)} ${item.year}`;
    }
    if (column === 'project.name') {
      return item.project?.name || '';
    }
    if (column === 'equipment.name') {
      return item.equipment?.name || '';
    }
    if (column === 'item.code') {
      return item.item?.code || '';
    }
    if (column === 'balance') {
      return typeof item.balance === 'number' ? item.balance : '';
    }
    if (column === 'landedValue') {
      return formatCurrency(item.landedValue);
    }
    if (column === 'landedRate') {
      return formatCurrency(item.landedRate);
    }
    if (column === 'lastIssueOn') {
      return formatDate(item.lastIssueOn);
    }
    if (column === 'lastReceiptOn') {
      return formatDate(item.lastReceiptOn);
    }
    return null;
  };

  return (
    <div className="p-4">
      <DataTable
        title="Stock Statements"
        columns={columns}
        data={Array.isArray(statements) ? statements : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="max-w-3xl max-h-[90vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle>
              {selectedStatement ? 'Edit Stock Statement' : 'Add Stock Statement'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <StockStatementForm
              statement={selectedStatement}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}
