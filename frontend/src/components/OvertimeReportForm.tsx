import React from 'react';
import { useQuery } from 'react-query';
import { Label } from '@/components/ui/label';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { toast } from 'sonner';
import {
  Select,
  SelectContent,
  SelectItem,
  SelectTrigger,
  SelectValue,
} from '@/components/ui/select';
import { employeeService } from '@/services/employeeService';
import type { OvertimeReport, OvertimeReportRequest } from '@/services/overtimeReportService';

interface OvertimeReportFormProps {
  overtimeReport?: OvertimeReport;
  onSubmit: (data: OvertimeReportRequest) => Promise<void>;
  onCancel: () => void;
}

export default function OvertimeReportForm({ overtimeReport, onSubmit, onCancel }: OvertimeReportFormProps) {
  const [formData, setFormData] = React.useState<OvertimeReportRequest>({
    employeeId: overtimeReport?.employee?.id || 0,
    date: overtimeReport?.date || new Date().toISOString().split('T')[0],
    presentDays: overtimeReport?.presentDays || 0,
    otHours: overtimeReport?.otHours || 0,
    remarks: overtimeReport?.remarks || '',
  });

  const { data: employees = [] } = useQuery(['employees'], () =>
    employeeService.getAllEmployees()
  );

  const handleChange = (field: keyof OvertimeReportRequest, value: string | number) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    // Basic validation
    if (!formData.employeeId || !formData.date) {
      toast.error('Please fill in all required fields');
      return;
    }

    try {
      await onSubmit(formData);
    } catch (error) {
      console.error('Form submission error:', error);
      toast.error('Failed to save overtime report');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="employeeId">Employee</Label>
          <Select
            value={formData.employeeId.toString()}
            onValueChange={(value) => handleChange('employeeId', parseInt(value))}
          >
            <SelectTrigger>
              <SelectValue placeholder="Select Employee" />
            </SelectTrigger>
            <SelectContent>
              {employees.map((employee: any) => (
                <SelectItem key={employee.id} value={employee.id.toString()}>
                  {employee.name}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="date">Date</Label>
          <Input
            id="date"
            type="date"
            value={formData.date}
            onChange={(e) => handleChange('date', e.target.value)}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="presentDays">Present Days</Label>
          <Input
            id="presentDays"
            type="number"
            min="0"
            max="31"
            value={formData.presentDays}
            onChange={(e) => handleChange('presentDays', parseInt(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="otHours">Overtime Hours</Label>
          <Input
            id="otHours"
            type="number"
            step="0.5"
            min="0"
            value={formData.otHours}
            onChange={(e) => handleChange('otHours', parseFloat(e.target.value))}
          />
        </div>

        <div className="space-y-2 col-span-2">
          <Label htmlFor="remarks">Remarks</Label>
          <Input
            id="remarks"
            value={formData.remarks}
            onChange={(e) => handleChange('remarks', e.target.value)}
          />
        </div>
      </div>

      <div className="flex justify-end space-x-2 sticky bottom-0 pt-4 bg-background border-t">
        <Button variant="outline" onClick={onCancel} type="button">
          Cancel
        </Button>
        <Button type="submit" className="bg-blue-600 hover:bg-blue-700">
          {overtimeReport ? 'Update' : 'Create'}
        </Button>
      </div>
    </form>
  );
}
