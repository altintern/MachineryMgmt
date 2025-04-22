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
import OvertimeReportForm from '@/components/OvertimeReportForm';
import overtimeReportService, { OvertimeReport, OvertimeReportRequest } from '@/services/overtimeReportService';
import { toast } from 'sonner';

export default function OvertimeReportPage() {
  const [open, setOpen] = useState(false);
  const [selectedReport, setSelectedReport] = useState<OvertimeReport | undefined>();
  const queryClient = useQueryClient();

  const { data } = useQuery(['overtimeReports'], () =>
    overtimeReportService.getAllOvertimeReports()
  );

  // Make sure we have the correct data structure
  const overtimeReports = data?.data || data || [];

  const createMutation = useMutation(
    (data: OvertimeReportRequest) => overtimeReportService.createOvertimeReport(data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['overtimeReports']);
        setOpen(false);
        toast.success('Overtime report created successfully');
      },
      onError: () => {
        toast.error('Failed to create overtime report');
      },
    }
  );

  const updateMutation = useMutation(
    ({ id, data }: { id: number; data: OvertimeReportRequest }) => overtimeReportService.updateOvertimeReport(id, data),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['overtimeReports']);
        setOpen(false);
        toast.success('Overtime report updated successfully');
      },
      onError: () => {
        toast.error('Failed to update overtime report');
      },
    }
  );

  const deleteMutation = useMutation(
    (id: number) => overtimeReportService.deleteOvertimeReport(id),
    {
      onSuccess: () => {
        queryClient.invalidateQueries(['overtimeReports']);
        toast.success('Overtime report deleted successfully');
      },
      onError: () => {
        toast.error('Failed to delete overtime report');
      },
    }
  );

  const handleOpen = (report?: OvertimeReport) => {
    setSelectedReport(report);
    setOpen(true);
  };

  const handleClose = () => {
    setSelectedReport(undefined);
    setOpen(false);
  };

  const handleSubmit = async (data: OvertimeReportRequest) => {
    if (selectedReport) {
      await updateMutation.mutateAsync({ id: selectedReport.id, data });
    } else {
      await createMutation.mutateAsync(data);
    }
  };

  const handleDelete = async (id: number) => {
    if (window.confirm('Are you sure you want to delete this overtime report?')) {
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

  const columns = [
    { key: 'employee.name', label: 'Employee' },
    { key: 'date', label: 'Date' },
    { key: 'presentDays', label: 'Present Days' },
    { key: 'otHours', label: 'OT Hours' },
    { key: 'remarks', label: 'Remarks' },
  ];

  const renderCustomCell = (column: string, item: any) => {
  switch (column) {
    case 'employee.name':
      return item.employee && item.employee.name ? item.employee.name : '-';
    case 'date':
      return item.date ? formatDate(item.date) : '-';
    case 'presentDays':
      return item.presentDays !== undefined && item.presentDays !== null ? item.presentDays : '-';
    case 'otHours':
      return item.otHours !== undefined && item.otHours !== null ? item.otHours : '-';
    case 'remarks':
      return item.remarks || '-';
    default:
      return null;
  }
};

  console.log("Overtime Reports Data:", overtimeReports);

  return (
    <div className="p-4">
      <DataTable
        title="Overtime Reports"
        columns={columns}
        data={Array.isArray(overtimeReports) ? overtimeReports : []}
        onAdd={() => handleOpen()}
        onEdit={handleOpen}
        onDelete={handleDelete}
        renderCustomCell={renderCustomCell}
      />

      <Dialog open={open} onOpenChange={setOpen}>
        <DialogContent className="max-w-3xl max-h-[90vh] overflow-y-auto">
          <DialogHeader>
            <DialogTitle>
              {selectedReport ? 'Edit Overtime Report' : 'Add Overtime Report'}
            </DialogTitle>
          </DialogHeader>
          <div className="mt-4">
            <OvertimeReportForm
              overtimeReport={selectedReport}
              onSubmit={handleSubmit}
              onCancel={handleClose}
            />
          </div>
        </DialogContent>
      </Dialog>
    </div>
  );
}