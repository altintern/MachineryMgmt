'use client';

import React from 'react';
import { Equipment, EquipmentRequest } from '@/services/equipmentService';
import { useQuery } from 'react-query';
import { categoryService } from '@/services/categoryService';
import { modelService } from '@/services/modelService';
import { projectService } from '@/services/projectService';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';


interface EquipmentFormProps {
  equipment?: Equipment;
  onSubmit: (equipment: EquipmentRequest) => void;
  onCancel: () => void;
}

const EquipmentForm: React.FC<EquipmentFormProps> = ({ equipment, onSubmit, onCancel }) => {
  const [formData, setFormData] = React.useState<EquipmentRequest>({
    name: equipment?.name || '',
    categoryId: equipment?.category?.id || 0,
    modelId: equipment?.model?.id || 0,
    assetCode: equipment?.assetCode || '',
    yearOfManufacture: equipment?.yearOfManufacture || new Date().getFullYear(),
    projectId: equipment?.project?.id || 0,
  });

  const { data: categories = [] } = useQuery(['categories'], () => categoryService.getAllCategories());
  const { data: models = [] } = useQuery(['models'], () => modelService.getAllModels());
  const { data: projects = [] } = useQuery(['projects'], () => projectService.getAllProjects());

  const categoryList = Array.isArray(categories) ? categories : [];
  const modelList = Array.isArray(models) ? models : [];
  const projectList = Array.isArray(projects) ? projects : [];

  const handleChange = (name: string, value: string | number) => {
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-6">
      <div className="space-y-4">
        <div className="grid gap-4">
          <div className="space-y-2">
            <Label htmlFor="name">Equipment Name</Label>
            <Input
              id="name"
              name="name"
              value={formData.name}
              onChange={(e) => handleChange('name', e.target.value)}
              required
            />
          </div>

          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
              <Label htmlFor="assetCode">Asset Code</Label>
              <Input
                id="assetCode"
                name="assetCode"
                value={formData.assetCode}
                onChange={(e) => handleChange('assetCode', e.target.value)}
                required
              />
            </div>
            <div className="space-y-2">
              <Label htmlFor="yearOfManufacture">Year of Manufacture</Label>
              <Input
                id="yearOfManufacture"
                name="yearOfManufacture"
                type="number"
                value={formData.yearOfManufacture}
                onChange={(e) => handleChange('yearOfManufacture', parseInt(e.target.value))}
                required
              />
            </div>
          </div>

          <div className="space-y-2">
  <Label htmlFor="categoryId">Category</Label>
  <select
    id="categoryId"
    name="categoryId"
    className="w-full border rounded px-3 py-2"
    value={formData.categoryId}
    onChange={e => handleChange('categoryId', parseInt(e.target.value))}
    required
  >
    <option value="">Select Category</option>
    {categoryList.map((category: { id: number; name: string }) => (
      <option key={category.id} value={category.id}>
        {category.name}
      </option>
    ))}
  </select>
</div>

          <div className="grid grid-cols-2 gap-4">
            <div className="space-y-2">
  <Label htmlFor="modelId">Model</Label>
  <select
    id="modelId"
    name="modelId"
    className="w-full border rounded px-3 py-2"
    value={formData.modelId}
    onChange={e => handleChange('modelId', parseInt(e.target.value))}
    required
  >
    <option value="">Select Model</option>
    {modelList.map((model: { id: number; name: string }) => (
      <option key={model.id} value={model.id}>
        {model.name}
      </option>
    ))}
  </select>
</div>

            <div className="space-y-2">
  <Label htmlFor="projectId">Project</Label>
  <select
    id="projectId"
    name="projectId"
    className="w-full border rounded px-3 py-2"
    value={formData.projectId}
    onChange={e => handleChange('projectId', parseInt(e.target.value))}
    required
  >
    <option value="">Select Project</option>
    {projectList.map((project: { id: number; name: string }) => (
      <option key={project.id} value={project.id}>
        {project.name}
      </option>
    ))}
  </select>
</div>
          </div>
        </div>
      </div>

      <div className="flex justify-end space-x-4">
        <Button variant="outline" onClick={onCancel}>
          Cancel
        </Button>
        <Button type="submit">
          {equipment ? 'Update' : 'Create'} Equipment
        </Button>
      </div>
    </form>
  );
};

export default EquipmentForm;
