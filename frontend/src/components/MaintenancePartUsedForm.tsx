import { useState, useEffect } from 'react';
import { useQuery } from 'react-query';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { MaintenancePartUsed, MaintenancePartUsedRequest } from '@/services/maintenancePartUsedService';
import maintenanceService from '@/services/maintenanceService';
import { itemService } from '@/services/itemService';

interface MaintenancePartUsedFormProps {
  partUsed?: MaintenancePartUsed;
  onSubmit: (data: MaintenancePartUsedRequest) => void;
  onCancel: () => void;
}

export default function MaintenancePartUsedForm({ partUsed, onSubmit, onCancel }: MaintenancePartUsedFormProps) {
  type FormData = {
    maintenanceLogId: string;
    itemId: string;
    quantity: string;
  };

  const [formData, setFormData] = useState<FormData>({
    maintenanceLogId: partUsed?.maintenanceLog?.id?.toString() || '',
    itemId: partUsed?.item?.id?.toString() || '',
    quantity: partUsed?.quantity?.toString() || '',
  });

  const { data: maintenanceData } = useQuery(['maintenanceLogs'], () => maintenanceService.getAllMaintenanceLogs());
  const maintenanceLogs = maintenanceData?.data || [];

  // itemService.getAllItems() returns Item[] directly
  const { data: items = [] } = useQuery(['items'], () => itemService.getAllItems());

  const handleChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSelectChange = (name: string, value: string) => {
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({
      maintenanceLogId: Number(formData.maintenanceLogId),
      itemId: Number(formData.itemId),
      quantity: Number(formData.quantity)
    });
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="space-y-2">
        <Label htmlFor="maintenanceLogId">Maintenance Log</Label>
        <Select
          value={formData.maintenanceLogId.toString()}
          onValueChange={(value) => handleSelectChange('maintenanceLogId', value)}
        >
          <SelectTrigger>
            <SelectValue placeholder="Select Maintenance Log" />
          </SelectTrigger>
          <SelectContent>
            {maintenanceLogs.map((log: any) => (
              <SelectItem key={log.id} value={log.id.toString()}>
                {log.equipment?.name || 'Unknown Equipment'} - {new Date(log.date).toLocaleDateString()} (ID: {log.id})
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>

      <div className="space-y-2">
        <Label htmlFor="itemId">Item</Label>
        <Select
          value={formData.itemId.toString()}
          onValueChange={(value) => handleSelectChange('itemId', value)}
        >
          <SelectTrigger>
            <SelectValue placeholder="Select Item" />
          </SelectTrigger>
          <SelectContent>
            {items.map((item: any) => (
              <SelectItem key={item.id} value={item.id.toString()}>
                {item.code} - {item.description}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>

      <div className="space-y-2">
        <Label htmlFor="quantity">Quantity</Label>
        <Input
          id="quantity"
          name="quantity"
          type="number"
          step="0.01"
          value={formData.quantity}
          onChange={handleChange}
          required
        />
      </div>

      <div className="flex justify-end space-x-2 pt-4">
        <Button type="button" variant="outline" onClick={onCancel}>
          Cancel
        </Button>
        <Button type="submit">
          {partUsed ? 'Update' : 'Create'} Part Used
        </Button>
      </div>
    </form>
  );
}
