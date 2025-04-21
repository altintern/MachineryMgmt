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
import type { PettyCashTransaction, PettyCashTransactionRequest } from '@/services/pettyCashService';

interface PettyCashFormProps {
  transaction?: PettyCashTransaction;
  onSubmit: (data: PettyCashTransactionRequest) => Promise<void>;
  onCancel: () => void;
}

export default function PettyCashForm({ transaction, onSubmit, onCancel }: PettyCashFormProps) {
  const [formData, setFormData] = React.useState<PettyCashTransactionRequest>({
    projectId: transaction?.project?.id || 0,
    equipmentId: transaction?.equipment?.id || 0,
    itemId: transaction?.item?.id || 0,
    reportDate: transaction?.reportDate || new Date().toISOString().split('T')[0],
    remarks: transaction?.remarks || '',
    amountSpent: transaction?.amountSpent || 0,
    quantity: transaction?.quantity || 0,
    cumulativeAmountSpent: transaction?.cumulativeAmountSpent || 0,
    purposeJustification: transaction?.purposeJustification || '',
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

  const handleChange = (field: keyof PettyCashTransactionRequest, value: string | number) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.projectId || !formData.equipmentId || !formData.itemId || !formData.reportDate) {
      toast.error('Please fill in all required fields');
      return;
    }

    try {
      await onSubmit(formData);
    } catch (error) {
      console.error('Form submission error:', error);
      toast.error('Failed to save petty cash transaction');
    }
  };

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
          <Label htmlFor="reportDate">Report Date</Label>
          <Input
            id="reportDate"
            type="date"
            value={formData.reportDate}
            onChange={(e) => handleChange('reportDate', e.target.value)}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="amountSpent">Amount Spent</Label>
          <Input
            id="amountSpent"
            type="number"
            step="0.01"
            value={formData.amountSpent}
            onChange={(e) => handleChange('amountSpent', parseFloat(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="quantity">Quantity</Label>
          <Input
            id="quantity"
            type="number"
            step="0.01"
            value={formData.quantity}
            onChange={(e) => handleChange('quantity', parseFloat(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="cumulativeAmountSpent">Cumulative Amount</Label>
          <Input
            id="cumulativeAmountSpent"
            type="number"
            step="0.01"
            value={formData.cumulativeAmountSpent}
            onChange={(e) => handleChange('cumulativeAmountSpent', parseFloat(e.target.value))}
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

        <div className="space-y-2 col-span-2">
          <Label htmlFor="purposeJustification">Purpose/Justification</Label>
          <Input
            id="purposeJustification"
            value={formData.purposeJustification}
            onChange={(e) => handleChange('purposeJustification', e.target.value)}
          />
        </div>
      </div>

      <div className="flex justify-end space-x-2 sticky bottom-0 pt-4 bg-background border-t">
        <Button variant="outline" onClick={onCancel} type="button">
          Cancel
        </Button>
        <Button type="submit" className="bg-blue-600 hover:bg-blue-700">
          {transaction ? 'Update' : 'Create'}
        </Button>
      </div>
    </form>
  );
}
