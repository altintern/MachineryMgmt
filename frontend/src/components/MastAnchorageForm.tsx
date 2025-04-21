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
import type { MastAnchorageDetails, MastAnchorageRequest } from '@/services/mastAnchorageService';

interface MastAnchorageFormProps {
  mastAnchorage?: MastAnchorageDetails;
  onSubmit: (data: MastAnchorageRequest) => void;
  onCancel: () => void;
}

export default function MastAnchorageForm({ mastAnchorage, onSubmit, onCancel }: MastAnchorageFormProps) {
  const [formData, setFormData] = React.useState<MastAnchorageRequest>({
    projectId: mastAnchorage?.project?.id || 0,
    equipmentId: mastAnchorage?.equipment?.id || 0,
    anchorageAtSite: mastAnchorage?.anchorageAtSite || 0,
    anchorageFixedAtSite: mastAnchorage?.anchorageFixedAtSite || 0,
    anchorageIdleAtSite: mastAnchorage?.anchorageIdleAtSite || 0,
    mastAvailableAtSite: mastAnchorage?.mastAvailableAtSite || 0,
    mastFixedAtSite: mastAnchorage?.mastFixedAtSite || 0,
    mastIdleAtSite: mastAnchorage?.mastIdleAtSite || 0,
    totalAnchorageRequirement: mastAnchorage?.totalAnchorageRequirement || 0,
    totalMastRequirement: mastAnchorage?.totalMastRequirement || 0,
    location: mastAnchorage?.location || '',
    presentBuildingHeight: mastAnchorage?.presentBuildingHeight || '',
    presentHeightOfHoist: mastAnchorage?.presentHeightOfHoist || '',
    remarks: mastAnchorage?.remarks || '',
    totalBuildingHeight: mastAnchorage?.totalBuildingHeight || '',
    status: mastAnchorage?.status || '',
  });

  const { data: projects = [] } = useQuery(['projects'], () =>
    projectService.getAllProjects()
  );

  const { data: equipment = [] } = useQuery(['equipment'], () =>
    equipmentService.getAllEquipment()
  );

  const handleChange = (field: keyof MastAnchorageRequest, value: string | number) => {
    setFormData(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    // Basic validation
    if (!formData.projectId || !formData.equipmentId || !formData.location) {
      toast.error('Please fill in all required fields');
      return;
    }

    try {
      await onSubmit(formData);
    } catch (error) {
      console.error('Form submission error:', error);
      toast.error('Failed to save mast anchorage');
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
              {equipment.map((item: any) => (
                <SelectItem key={item.id} value={item.id.toString()}>
                  {item.name}
                </SelectItem>
              ))}
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="location">Location</Label>
          <Input
            id="location"
            value={formData.location}
            onChange={(e) => handleChange('location', e.target.value)}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="status">Status</Label>
          <Input
            id="status"
            value={formData.status}
            onChange={(e) => handleChange('status', e.target.value)}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="anchorageAtSite">Anchorage at Site</Label>
          <Input
            id="anchorageAtSite"
            type="number"
            value={formData.anchorageAtSite}
            onChange={(e) => handleChange('anchorageAtSite', parseInt(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="anchorageFixedAtSite">Anchorage Fixed at Site</Label>
          <Input
            id="anchorageFixedAtSite"
            type="number"
            value={formData.anchorageFixedAtSite}
            onChange={(e) => handleChange('anchorageFixedAtSite', parseInt(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="anchorageIdleAtSite">Anchorage Idle at Site</Label>
          <Input
            id="anchorageIdleAtSite"
            type="number"
            value={formData.anchorageIdleAtSite}
            onChange={(e) => handleChange('anchorageIdleAtSite', parseInt(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="mastAvailableAtSite">Mast Available at Site</Label>
          <Input
            id="mastAvailableAtSite"
            type="number"
            value={formData.mastAvailableAtSite}
            onChange={(e) => handleChange('mastAvailableAtSite', parseInt(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="mastFixedAtSite">Mast Fixed at Site</Label>
          <Input
            id="mastFixedAtSite"
            type="number"
            value={formData.mastFixedAtSite}
            onChange={(e) => handleChange('mastFixedAtSite', parseInt(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="mastIdleAtSite">Mast Idle at Site</Label>
          <Input
            id="mastIdleAtSite"
            type="number"
            value={formData.mastIdleAtSite}
            onChange={(e) => handleChange('mastIdleAtSite', parseInt(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="totalAnchorageRequirement">Total Anchorage Requirement</Label>
          <Input
            id="totalAnchorageRequirement"
            type="number"
            value={formData.totalAnchorageRequirement}
            onChange={(e) => handleChange('totalAnchorageRequirement', parseInt(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="totalMastRequirement">Total Mast Requirement</Label>
          <Input
            id="totalMastRequirement"
            type="number"
            value={formData.totalMastRequirement}
            onChange={(e) => handleChange('totalMastRequirement', parseInt(e.target.value))}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="presentBuildingHeight">Present Building Height</Label>
          <Input
            id="presentBuildingHeight"
            value={formData.presentBuildingHeight}
            onChange={(e) => handleChange('presentBuildingHeight', e.target.value)}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="presentHeightOfHoist">Present Height of Hoist</Label>
          <Input
            id="presentHeightOfHoist"
            value={formData.presentHeightOfHoist}
            onChange={(e) => handleChange('presentHeightOfHoist', e.target.value)}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="totalBuildingHeight">Total Building Height</Label>
          <Input
            id="totalBuildingHeight"
            value={formData.totalBuildingHeight}
            onChange={(e) => handleChange('totalBuildingHeight', e.target.value)}
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
          {mastAnchorage ? 'Update' : 'Create'}
        </Button>
      </div>
    </form>
  );
}
