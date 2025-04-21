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
  const [formData, setFormData] = React.useState<ItemRequest>({
    name: item?.name || '',
    description: item?.description || '',
    unit: item?.unit || '',
    unitPrice: item?.unitPrice || 0,
  });

  const handleChange = (field: keyof ItemRequest, value: string | number) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!formData.name || !formData.unit) {
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
          <Label htmlFor="name">Name *</Label>
          <Input
            id="name"
            value={formData.name}
            onChange={(e) => handleChange('name', e.target.value)}
            placeholder="Enter item name"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="unit">Unit *</Label>
          <Input
            id="unit"
            value={formData.unit}
            onChange={(e) => handleChange('unit', e.target.value)}
            placeholder="e.g., pcs, kg, m"
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="unitPrice">Unit Price</Label>
          <Input
            id="unitPrice"
            type="number"
            step="0.01"
            min="0"
            value={formData.unitPrice}
            onChange={(e) => handleChange('unitPrice', parseFloat(e.target.value))}
            placeholder="Enter unit price"
          />
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
