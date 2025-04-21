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
  const [formData, setFormData] = useState<MaintenancePartUsedRequest>({
    maintenanceLogId: partUsed?.maintenancelog?.id || 0,
    itemId: partUsed?.item?.id || 0,
    quantity: partUsed?.quantity || 0,
  });

  const { data: maintenanceData } = useQuery(['maintenanceLogs'], () => maintenanceService.getAllMaintenanceLogs());
  const maintenanceLogs = maintenanceData?.data || [];

  const { data: itemsData } = useQuery(['items'], () => itemService.getAllItems());
  const items = itemsData?.data || [];

  const handleNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: Number(value) }));
  };

  const handleSelectChange = (name: string, value: string) => {
    setFormData((prev) => ({ ...prev, [name]: Number(value) }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
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
                {log.equipment?.name} - {new Date(log.date).toLocaleDateString()}
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
                {item.name}
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
          onChange={handleNumberChange}
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
