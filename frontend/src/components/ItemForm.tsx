import React from 'react';
import { Label } from '@/components/ui/label';
import { Input } from '@/components/ui/input';
import { Button } from '@/components/ui/button';
import { toast } from 'sonner';
import type { Item, ItemRequest } from '@/services/itemService';

interface ItemFormProps {
  item?: Item;
  onSubmit: (data: ItemRequest) => Promise<void>;
  onCancel: () => void;
}

export default function ItemForm({ item, onSubmit, onCancel }: ItemFormProps) {
  const [formData, setFormData] = React.useState<ItemRequest & {id?: number}>({
    id: item?.id,
    code: item?.code || '',
    description: item?.description || '',
    itemType: item?.itemType || 'MATERIAL',
    uom: item?.uom || '',
  });

  // Reset formData (including id) whenever the item prop changes (including after delete)
  React.useEffect(() => {
    if (item) {
      setFormData({
        id: item.id,
        code: item.code || '',
        description: item.description || '',
        itemType: item.itemType || 'MATERIAL',
        uom: item.uom || '',
      });
    } else {
      setFormData({
        id: undefined,
        code: '',
        description: '',
        itemType: 'MATERIAL',
        uom: '',
      });
    }
  }, [item]);

  const handleChange = (field: keyof ItemRequest, value: string | number) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.code || !formData.description || !formData.uom) {
      toast.error('Please fill in all required fields');
      return;
    }

    try {
      await onSubmit(formData);
    } catch (error) {
      console.error('Form submission error:', error);
      toast.error('Failed to save item');
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="code">Code *</Label>
          <Input
            id="code"
            value={formData.code}
            onChange={(e) => handleChange('code', e.target.value)}
            placeholder="Enter item code"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="uom">Unit of Measure *</Label>
          <Input
            id="uom"
            value={formData.uom}
            onChange={(e) => handleChange('uom', e.target.value)}
            placeholder="e.g., pcs, kg, m"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="itemType">Item Type *</Label>
          <select
            id="itemType"
            className="w-full p-2 border rounded"
            value={formData.itemType}
            onChange={(e) => handleChange('itemType', e.target.value as 'MATERIAL' | 'SPARE' | 'OTHER')}
            required
          >
            <option value="MATERIAL">Material</option>
            <option value="SPARE">Spare</option>
            <option value="OTHER">Other</option>
          </select>
        </div>

        <div className="space-y-2 col-span-2">
          <Label htmlFor="description">Description</Label>
          <Input
            id="description"
            value={formData.description}
            onChange={(e) => handleChange('description', e.target.value)}
            placeholder="Enter item description"
          />
        </div>
      </div>

      <div className="flex justify-end space-x-2 sticky bottom-0 pt-4 bg-background border-t">
        <Button variant="outline" onClick={onCancel} type="button">
          Cancel
        </Button>
        <Button type="submit" className="bg-blue-600 hover:bg-blue-700">
          {item ? 'Update' : 'Create'}
        </Button>
      </div>
    </form>
  );
}