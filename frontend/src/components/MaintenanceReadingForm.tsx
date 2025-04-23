import { useState, useEffect } from 'react';
import { useQuery } from 'react-query';
import { Button } from '@/components/ui/button';
import { Input } from '@/components/ui/input';
import { Label } from '@/components/ui/label';
import { Select, SelectContent, SelectItem, SelectTrigger, SelectValue } from '@/components/ui/select';
import { MaintenanceReading, MaintenanceReadingRequest } from '@/services/maintenanceReadingService';
import maintenanceService from '@/services/maintenanceService';

interface MaintenanceReadingFormProps {
  reading?: MaintenanceReading;
  onSubmit: (data: MaintenanceReadingRequest) => void;
  onCancel: () => void;
}

export default function MaintenanceReadingForm({ reading, onSubmit, onCancel }: MaintenanceReadingFormProps) {
  type FormData = {
    maintenanceLogId: string;
    airPressure: string;
    engineOil: string;
    engineTemperature: string;
    gearOil: string;
    greaseUsed: string;
    hsdUsed: string;
    hydraulicOil: string;
    hydraulicTemperature: string;
    oilPressure: string;
  };

  const [formData, setFormData] = useState<FormData>({
    maintenanceLogId: reading?.maintenanceLog?.id?.toString() || '',
    airPressure: reading?.airPressure?.toString() || '',
    engineOil: reading?.engineOil?.toString() || '',
    engineTemperature: reading?.engineTemperature?.toString() || '',
    gearOil: reading?.gearOil?.toString() || '',
    greaseUsed: reading?.greaseUsed?.toString() || '',
    hsdUsed: reading?.hsdUsed?.toString() || '',
    hydraulicOil: reading?.hydraulicOil?.toString() || '',
    hydraulicTemperature: reading?.hydraulicTemperature?.toString() || '',
    oilPressure: reading?.oilPressure?.toString() || '',
  });

  const { data: maintenanceData } = useQuery(['maintenanceLogs'], () => maintenanceService.getAllMaintenanceLogs());
  const maintenanceLogs = maintenanceData?.data || [];

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
      airPressure: Number(formData.airPressure),
      engineOil: Number(formData.engineOil),
      engineTemperature: Number(formData.engineTemperature),
      gearOil: Number(formData.gearOil),
      greaseUsed: Number(formData.greaseUsed),
      hsdUsed: Number(formData.hsdUsed),
      hydraulicOil: Number(formData.hydraulicOil),
      hydraulicTemperature: Number(formData.hydraulicTemperature),
      oilPressure: Number(formData.oilPressure)
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
                {log.equipment?.name} - {new Date(log.date).toLocaleDateString()}
              </SelectItem>
            ))}
          </SelectContent>
        </Select>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="airPressure">Air Pressure</Label>
          <Input
            id="airPressure"
            name="airPressure"
            type="number"
            value={formData.airPressure}
            onChange={handleChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="engineOil">Engine Oil</Label>
          <Input
            id="engineOil"
            name="engineOil"
            type="number"
            value={formData.engineOil}
            onChange={handleChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="engineTemperature">Engine Temperature</Label>
          <Input
            id="engineTemperature"
            name="engineTemperature"
            type="number"
            value={formData.engineTemperature}
            onChange={handleChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="gearOil">Gear Oil</Label>
          <Input
            id="gearOil"
            name="gearOil"
            type="number"
            value={formData.gearOil}
            onChange={handleChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="greaseUsed">Grease Used</Label>
          <Input
            id="greaseUsed"
            name="greaseUsed"
            type="number"
            value={formData.greaseUsed}
            onChange={handleChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="hsdUsed">HSD Used</Label>
          <Input
            id="hsdUsed"
            name="hsdUsed"
            type="number"
            value={formData.hsdUsed}
            onChange={handleChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="hydraulicOil">Hydraulic Oil</Label>
          <Input
            id="hydraulicOil"
            name="hydraulicOil"
            type="number"
            value={formData.hydraulicOil}
            onChange={handleChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="hydraulicTemperature">Hydraulic Temperature</Label>
          <Input
            id="hydraulicTemperature"
            name="hydraulicTemperature"
            type="number"
            value={formData.hydraulicTemperature}
            onChange={handleChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="oilPressure">Oil Pressure</Label>
          <Input
            id="oilPressure"
            name="oilPressure"
            type="number"
            value={formData.oilPressure}
            onChange={handleChange}
            required
          />
        </div>
      </div>

      <div className="flex justify-end space-x-2 pt-4">
        <Button type="button" variant="outline" onClick={onCancel}>
          Cancel
        </Button>
        <Button type="submit">
          {reading ? 'Update' : 'Create'} Reading
        </Button>
      </div>
    </form>
  );
}
