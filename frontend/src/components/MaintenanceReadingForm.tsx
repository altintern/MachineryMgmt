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
  const [formData, setFormData] = useState<MaintenanceReadingRequest>({
    maintenanceLogId: reading?.maintenanceLog?.id || 0,
    airPressure: reading?.airPressure || 0,
    engineOil: reading?.engineOil || 0,
    engineTemperature: reading?.engineTemperature || 0,
    gearOil: reading?.gearOil || 0,
    gearUsed: reading?.gearUsed || 0,
    hsdUsed: reading?.hsdUsed || 0,
    hydraulicOil: reading?.hydraulicOil || 0,
    hydraulicTemperature: reading?.hydraulicTemperature || 0,
    oilPressure: reading?.oilPressure || 0,
  });

  const { data: maintenanceData } = useQuery(['maintenanceLogs'], () => maintenanceService.getAllMaintenanceLogs());
  const maintenanceLogs = maintenanceData?.data || [];

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

      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        <div className="space-y-2">
          <Label htmlFor="airPressure">Air Pressure</Label>
          <Input
            id="airPressure"
            name="airPressure"
            type="number"
            value={formData.airPressure}
            onChange={handleNumberChange}
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
            onChange={handleNumberChange}
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
            onChange={handleNumberChange}
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
            onChange={handleNumberChange}
            required
          />
        </div>

        <div className="space-y-2">
          <Label htmlFor="gearUsed">Gear Used</Label>
          <Input
            id="gearUsed"
            name="gearUsed"
            type="number"
            value={formData.gearUsed}
            onChange={handleNumberChange}
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
            onChange={handleNumberChange}
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
            onChange={handleNumberChange}
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
            onChange={handleNumberChange}
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
            onChange={handleNumberChange}
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
