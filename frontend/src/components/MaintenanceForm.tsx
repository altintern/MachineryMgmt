import { useState, useEffect } from 'react';
import { useQuery } from 'react-query';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Textarea } from '@/components/ui/textarea';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { MaintenanceLog, MaintenanceLogRequest } from '@/services/maintenanceService';
import { equipmentService } from '@/services/equipmentService';
import { format } from 'date-fns';

interface MaintenanceFormProps {
  maintenance?: MaintenanceLog;
  onSubmit: (data: MaintenanceLogRequest) => void;
  onCancel: () => void;
}

export default function MaintenanceForm({ maintenance, onSubmit, onCancel }: MaintenanceFormProps) {
  const [formData, setFormData] = useState<MaintenanceLogRequest>({
    equipmentId: maintenance?.equipment?.id || 0,
    date: maintenance?.date || format(new Date(), 'yyyy-MM-dd'),
    serviceDate: maintenance?.serviceDate || format(new Date(), 'yyyy-MM-dd'),
    breakdownSynopsis: maintenance?.breakdownSynopsis || '',
    feedback: maintenance?.feedback || '',
    balanceForService: maintenance?.balanceForService || 0,
    closeReading: maintenance?.closeReading || 0,
    serviceHours: maintenance?.serviceHours || 0,
    startReading: maintenance?.startReading || 0,
    maintenanceSignature: maintenance?.maintenanceSignature || '',
    operatorSignature: maintenance?.operatorSignature || '',
    maintenanceName: maintenance?.maintenanceName || '',
    purposeActivities: maintenance?.purposeActivities || '',
    remarks: maintenance?.remarks || '',
    typeOfMaintenance: maintenance?.typeOfMaintenance || 'PREVENTIVE',
  });

  const { data: equipmentData } = useQuery(['equipment'], () => equipmentService.getAllEquipment());
  const equipment = equipmentData?.data || [];

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  const handleNumberChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: Number(value) }));
  };

  const handleSelectChange = (name: string, value: string) => {
    if (name === 'equipmentId') {
      setFormData((prev) => ({ ...prev, [name]: Number(value) }));
    } else {
      setFormData((prev) => ({ ...prev, [name]: value }));
    }
  };

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit(formData);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="equipmentId">Equipment</Label>
          <Select
            value={formData.equipmentId.toString()}
            onValueChange={(value) => handleSelectChange('equipmentId', value)}
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
          <Label htmlFor="typeOfMaintenance">Type of Maintenance</Label>
          <Select
            value={formData.typeOfMaintenance}
            onValueChange={(value) => handleSelectChange('typeOfMaintenance', value)}
          >
            <SelectTrigger>
              <SelectValue placeholder="Select Type" />
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="PREVENTIVE">Preventive</SelectItem>
              <SelectItem value="CORRECTIVE">Corrective</SelectItem>
              <SelectItem value="BREAKDOWN">Breakdown</SelectItem>
            </SelectContent>
          </Select>
        </div>

        <div className="space-y-2">
          <Label htmlFor="date">Date</Label>
          <Input
            id="date"
            name="date"
            type="date"
            value={formData.date}
            onChange={handleChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="serviceDate">Service Date</Label>
          <Input
            id="serviceDate"
            name="serviceDate"
            type="date"
            value={formData.serviceDate}
            onChange={handleChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="startReading">Start Reading</Label>
          <Input
            id="startReading"
            name="startReading"
            type="number"
            value={formData.startReading}
            onChange={handleNumberChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="closeReading">Close Reading</Label>
          <Input
            id="closeReading"
            name="closeReading"
            type="number"
            value={formData.closeReading}
            onChange={handleNumberChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="serviceHours">Service Hours</Label>
          <Input
            id="serviceHours"
            name="serviceHours"
            type="number"
            value={formData.serviceHours}
            onChange={handleNumberChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="balanceForService">Balance for Service</Label>
          <Input
            id="balanceForService"
            name="balanceForService"
            type="number"
            value={formData.balanceForService}
            onChange={handleNumberChange}
            required
          />
        </div>
      </div>

      <div className="space-y-2">
        <Label htmlFor="breakdownSynopsis">Breakdown Synopsis</Label>
        <Textarea
          id="breakdownSynopsis"
          name="breakdownSynopsis"
          value={formData.breakdownSynopsis}
          onChange={handleChange}
          rows={3}
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="purposeActivities">Purpose/Activities</Label>
        <Textarea
          id="purposeActivities"
          name="purposeActivities"
          value={formData.purposeActivities}
          onChange={handleChange}
          rows={3}
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="feedback">Feedback</Label>
        <Textarea
          id="feedback"
          name="feedback"
          value={formData.feedback}
          onChange={handleChange}
          rows={3}
        />
      </div>

      <div className="space-y-2">
        <Label htmlFor="remarks">Remarks</Label>
        <Textarea
          id="remarks"
          name="remarks"
          value={formData.remarks}
          onChange={handleChange}
          rows={3}
        />
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="maintenanceName">Maintenance Name</Label>
          <Input
            id="maintenanceName"
            name="maintenanceName"
            value={formData.maintenanceName}
            onChange={handleChange}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="maintenanceSignature">Maintenance Signature</Label>
          <Input
            id="maintenanceSignature"
            name="maintenanceSignature"
            value={formData.maintenanceSignature}
            onChange={handleChange}
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="operatorSignature">Operator Signature</Label>
          <Input
            id="operatorSignature"
            name="operatorSignature"
            value={formData.operatorSignature}
            onChange={handleChange}
          />
        </div>
      </div>

      <div className="flex justify-end space-x-2 pt-4">
        <Button type="button" variant="outline" onClick={onCancel}>
          Cancel
        </Button>
        <Button type="submit">
          {maintenance ? 'Update' : 'Create'} Maintenance Log
        </Button>
      </div>
    </form>
  );
}
