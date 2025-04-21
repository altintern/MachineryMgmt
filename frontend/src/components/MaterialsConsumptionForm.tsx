import React, { useEffect } from 'react';
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
import { useQuery } from 'react-query';
import projectService from '@/services/projectService';
import equipmentService from '@/services/equipmentService';
import itemService from '@/services/itemService';
import type { MaterialsConsumptionTransaction, MaterialsConsumptionTransactionRequest } from '@/services/materialsConsumptionService';

interface MaterialsConsumptionFormProps {
  transaction?: MaterialsConsumptionTransaction;
  onSubmit: (data: MaterialsConsumptionTransactionRequest) => Promise<void>;
  onCancel: () => void;
}

export default function MaterialsConsumptionForm({
  transaction,
  onSubmit,
  onCancel,
}: MaterialsConsumptionFormProps) {
  const [formData, setFormData] = React.useState<MaterialsConsumptionTransactionRequest>({
    projectId: transaction?.project?.id || 0,
    equipmentId: transaction?.equipment?.id || 0,
    itemId: transaction?.item?.id || 0,
    issueDate: transaction?.issueDate || new Date().toISOString().split('T')[0],
    quantity: transaction?.quantity || 0,
    costPerUnit: transaction?.costPerUnit || 0,
    totalCost: transaction?.totalCost || 0,
    remarks: transaction?.remarks || '',
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

  const handleChange = (field: keyof MaterialsConsumptionTransactionRequest, value: any) => {
    setFormData(prev => {
      const newData = {
        ...prev,
        [field]: value,
      };

      // Auto-calculate total cost when quantity or cost per unit changes
      if (field === 'quantity' || field === 'costPerUnit') {
        newData.totalCost = Number(newData.quantity) * Number(newData.costPerUnit);
      }

      return newData;
    });
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.projectId || !formData.equipmentId || !formData.itemId || !formData.quantity || !formData.costPerUnit) {
      toast.error('Please fill in all required fields');
      return;
    }

    try {
      await onSubmit(formData);
    } catch (error) {
      console.error('Form submission error:', error);
      toast.error('Failed to save transaction');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="projectId">Project *</Label>
          <Select
            value={formData.projectId.toString()}
            onValueChange={(value) => handleChange('projectId', parseInt(value))}
          >
            <SelectTrigger>
              <SelectValue placeholder="Select project" />
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
          <Label htmlFor="equipmentId">Equipment *</Label>
          <Select
            value={formData.equipmentId.toString()}
            onValueChange={(value) => handleChange('equipmentId', parseInt(value))}
          >
            <SelectTrigger>
              <SelectValue placeholder="Select equipment" />
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
          <Label htmlFor="itemId">Item *</Label>
          <Select
            value={formData.itemId.toString()}
            onValueChange={(value) => handleChange('itemId', parseInt(value))}
          >
            <SelectTrigger>
              <SelectValue placeholder="Select item" />
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
          <Label htmlFor="issueDate">Issue Date *</Label>
          <Input
            id="issueDate"
            type="date"
            value={formData.issueDate}
            onChange={(e) => handleChange('issueDate', e.target.value)}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="quantity">Quantity *</Label>
          <Input
            id="quantity"
            type="number"
            min="1"
            value={formData.quantity}
            onChange={(e) => handleChange('quantity', parseInt(e.target.value))}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="costPerUnit">Cost Per Unit *</Label>
          <Input
            id="costPerUnit"
            type="number"
            step="0.01"
            min="0"
            value={formData.costPerUnit}
            onChange={(e) => handleChange('costPerUnit', parseFloat(e.target.value))}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="totalCost">Total Cost</Label>
          <Input
            id="totalCost"
            type="number"
            step="0.01"
            value={formData.totalCost}
            readOnly
          />
        </div>

        <div className="space-y-2 col-span-2">
          <Label htmlFor="remarks">Remarks</Label>
          <Input
            id="remarks"
            value={formData.remarks}
            onChange={(e) => handleChange('remarks', e.target.value)}
            placeholder="Enter any additional notes"
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
