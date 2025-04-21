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
import { projectService } from '@/services/projectService';
import { equipmentService } from '@/services/equipmentService';
import { itemService } from '@/services/itemService';
import type { StockStatement, StockStatementRequest } from '@/services/stockStatementService';

interface StockStatementFormProps {
  statement?: StockStatement;
  onSubmit: (data: StockStatementRequest) => Promise<void>;
  onCancel: () => void;
}

export default function StockStatementForm({ statement, onSubmit, onCancel }: StockStatementFormProps) {
  const [formData, setFormData] = React.useState<StockStatementRequest>({
    itemId: statement?.item?.id || 0,
    projectId: statement?.project?.id || 0,
    equipmentId: statement?.equipment?.id || 0,
    lastIssueOn: statement?.lastIssueOn || '',
    lastReceiptOn: statement?.lastReceiptOn || '',
    month: statement?.month || new Date().getMonth() + 1,
    year: statement?.year || new Date().getFullYear(),
    balance: statement?.balance || 0,
    landedValue: statement?.landedValue || 0,
    landedRate: statement?.landedRate || 0,
  });

  const { data: projects = [] } = useQuery(['projects'], () =>
    projectService.getAllProjects()
  );

  const { data: equipment = [] } = useQuery(['equipment'], () =>
    equipmentService.getAllEquipment()
  );

  const { data: items = [] } = useQuery(['items'], () =>
    itemService.getAllItems()
  );

  const handleChange = (field: keyof StockStatementRequest, value: string | number) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.itemId || !formData.projectId || !formData.equipmentId) {
      toast.error('Please fill in all required fields');
      return;
    }

    try {
      await onSubmit(formData);
    } catch (error) {
      console.error('Form submission error:', error);
      toast.error('Failed to save stock statement');
    }
  };

  const months = [
    { value: 1, label: 'January' },
    { value: 2, label: 'February' },
    { value: 3, label: 'March' },
    { value: 4, label: 'April' },
    { value: 5, label: 'May' },
    { value: 6, label: 'June' },
    { value: 7, label: 'July' },
    { value: 8, label: 'August' },
    { value: 9, label: 'September' },
    { value: 10, label: 'October' },
    { value: 11, label: 'November' },
    { value: 12, label: 'December' },
  ];

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="projectId">Project</Label>
          <Select
            value={formData.projectId.toString()}
            onValueChange={(value) => handleChange('projectId', parseInt(value))}
          >
            <SelectTrigger>
              <SelectValue placeholder="Select Project" />
            </SelectTrigger>
            <SelectContent>
              {projects.map((project: any) => (
                <SelectItem key={project.id} value={project.id.toString()}>
                  {project.name}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="equipmentId">Equipment</Label>
          <Select
            value={formData.equipmentId.toString()}
            onValueChange={(value) => handleChange('equipmentId', parseInt(value))}
          >
            <SelectTrigger>
              <SelectValue placeholder="Select Equipment" />
            </SelectTrigger>
            <SelectContent>
              {equipment.map((eq: any) => (
                <SelectItem key={eq.id} value={eq.id.toString()}>
                  {eq.name}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="itemId">Item</Label>
          <Select
            value={formData.itemId.toString()}
            onValueChange={(value) => handleChange('itemId', parseInt(value))}
          >
            <SelectTrigger>
              <SelectValue placeholder="Select Item" />
            </SelectTrigger>
            <SelectContent>
              {items.map((item: any) => (
                <SelectItem key={item.id} value={item.id.toString()}>
                  {item.name}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="month">Month</Label>
          <Select
            value={formData.month.toString()}
            onValueChange={(value) => handleChange('month', parseInt(value))}
          >
            <SelectTrigger>
              <SelectValue placeholder="Select Month" />
            </SelectTrigger>
            <SelectContent>
              {months.map((month) => (
                <SelectItem key={month.value} value={month.value.toString()}>
                  {month.label}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="year">Year</Label>
          <Input
            id="year"
            type="number"
            min="2000"
            max="2100"
            value={formData.year}
            onChange={(e) => handleChange('year', parseInt(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="lastIssueOn">Last Issue Date</Label>
          <Input
            id="lastIssueOn"
            type="date"
            value={formData.lastIssueOn}
            onChange={(e) => handleChange('lastIssueOn', e.target.value)}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="lastReceiptOn">Last Receipt Date</Label>
          <Input
            id="lastReceiptOn"
            type="date"
            value={formData.lastReceiptOn}
            onChange={(e) => handleChange('lastReceiptOn', e.target.value)}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="balance">Balance</Label>
          <Input
            id="balance"
            type="number"
            step="0.01"
            value={formData.balance}
            onChange={(e) => handleChange('balance', parseFloat(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="landedValue">Landed Value</Label>
          <Input
            id="landedValue"
            type="number"
            step="0.01"
            value={formData.landedValue}
            onChange={(e) => handleChange('landedValue', parseFloat(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="landedRate">Landed Rate</Label>
          <Input
            id="landedRate"
            type="number"
            step="0.01"
            value={formData.landedRate}
            onChange={(e) => handleChange('landedRate', parseFloat(e.target.value))}
          />
        </div>
      </div>

      <div className="flex justify-end space-x-2 sticky bottom-0 pt-4 bg-background border-t">
        <Button variant="outline" onClick={onCancel} type="button">
          Cancel
        </Button>
        <Button type="submit" className="bg-blue-600 hover:bg-blue-700">
          {statement ? 'Update' : 'Create'}
        </Button>
      </div>
    </form>
  );
}
