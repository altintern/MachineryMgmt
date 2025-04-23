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
  type FormData = {
    equipmentId: string;
    date: string;
    serviceDate: string;
    breakdownSynopsis: string;
    feedback: string;
    balanceForService: string;
    closeReading: string;
    serviceHours: string;
    startReading: string;
    maintenanceSignature: string;
    operatorSignature: string;
    operatorName: string;
    purposeActivities: string;
    remarks: string;
    typeOfMaintenance: string;
  };

  const [formData, setFormData] = useState<FormData>({
    equipmentId: maintenance?.equipment?.id?.toString() || '',
    date: maintenance?.date || format(new Date(), 'yyyy-MM-dd'),
    serviceDate: maintenance?.serviceDate || format(new Date(), 'yyyy-MM-dd'),
    breakdownSynopsis: maintenance?.breakdownSynopsis || '',
    feedback: maintenance?.feedback || '',
    balanceForService: maintenance?.balanceForService?.toString() || '',
    closeReading: maintenance?.closeReading?.toString() || '',
    serviceHours: maintenance?.serviceHours?.toString() || '',
    startReading: maintenance?.startReading?.toString() || '',
    maintenanceSignature: maintenance?.maintenanceSignature || '',
    operatorSignature: maintenance?.operatorSignature || '',
    operatorName: maintenance?.operatorName || '',
    purposeActivities: maintenance?.purposeActivities || '',
    remarks: maintenance?.remarks || '',
    typeOfMaintenance: maintenance?.typeOfMaintenance || 'PREVENTIVE',
  });

  const { data: equipment = [] } = useQuery(['equipment'], () => equipmentService.getAllEquipment());

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };




  const handleSelectChange = (name: string, value: string) => {
    setFormData((prev) => ({ ...prev, [name]: value }));
  };


  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    onSubmit({
      ...formData,
      equipmentId: Number(formData.equipmentId),
      balanceForService: Number(formData.balanceForService),
      closeReading: Number(formData.closeReading),
      serviceHours: Number(formData.serviceHours),
      startReading: Number(formData.startReading),
    });
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
            onChange={handleChange}
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
            onChange={handleChange}
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
            onChange={handleChange}
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
            onChange={handleChange}
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
          <Label htmlFor="operatorName">Operator Name</Label>
          <Input
            id="operatorName"
            name="operatorName"
            value={formData.operatorName}
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
