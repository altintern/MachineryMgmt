import axios from 'axios';

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1';

export interface MaintenanceLogSummary {
  id: number;
}

export interface MaintenanceReading {
  id: number;
  airPressure: number;
  engineOil: number;
  engineTemperature: number;
  gearOil: number;
  gearUsed: number;
  hsdUsed: number;
  hydraulicOil: number;
  hydraulicTemperature: number;
  oilPressure: number;
  maintenanceLog: MaintenanceLogSummary;
}

export interface MaintenanceReadingRequest {
  maintenanceLogId: number;
  airPressure: number;
  engineOil: number;
  engineTemperature: number;
  gearOil: number;
  gearUsed: number;
  hsdUsed: number;
  hydraulicOil: number;
  hydraulicTemperature: number;
  oilPressure: number;
}

const maintenanceReadingService = {
  getAllMaintenanceReadings: async () => {
    const response = await axios.get(`${API_URL}/maintenance/reading`);
    return response.data;
  },

  getMaintenanceReadingById: async (id: number) => {
    const response = await axios.get(`${API_URL}/maintenance/reading/${id}`);
    return response.data;
  },

  createMaintenanceReading: async (data: MaintenanceReadingRequest) => {
    const response = await axios.post(`${API_URL}/maintenance/reading`, data);
    return response.data;
  },

  updateMaintenanceReading: async (id: number, data: MaintenanceReadingRequest) => {
    const response = await axios.put(`${API_URL}/maintenance/reading/${id}`, data);
    return response.data;
  },

  deleteMaintenanceReading: async (id: number) => {
    const response = await axios.delete(`${API_URL}/maintenance/reading/${id}`);
    return response.data;
  },
};

export default maintenanceReadingService;
