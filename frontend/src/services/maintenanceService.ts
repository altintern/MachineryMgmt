import axios from 'axios';

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1';

export interface Equipment {
  id: number;
  name: string;
}

export interface MaintenanceLog {
  id: number;
  date: string;
  serviceDate: string;
  breakdownSynopsis: string;
  feedback: string;
  balanceForService: number;
  closeReading: number;
  serviceHours: number;
  startReading: number;
  maintenanceSignature: string;
  operatorSignature: string;
  maintenanceName: string;
  purposeActivities: string;
  remarks: string;
  typeOfMaintenance: string;
  equipment: Equipment;
}

export interface MaintenanceLogRequest {
  equipmentId: number;
  date: string;
  serviceDate: string;
  breakdownSynopsis: string;
  feedback: string;
  balanceForService: number;
  closeReading: number;
  serviceHours: number;
  startReading: number;
  maintenanceSignature: string;
  operatorSignature: string;
  maintenanceName: string;
  purposeActivities: string;
  remarks: string;
  typeOfMaintenance: string;
}

const maintenanceService = {
  getAllMaintenanceLogs: async () => {
    const response = await axios.get(`${API_URL}/maintenance`);
    return response.data;
  },

  getMaintenanceLogById: async (id: number) => {
    const response = await axios.get(`${API_URL}/maintenance/${id}`);
    return response.data;
  },

  createMaintenanceLog: async (data: MaintenanceLogRequest) => {
    const response = await axios.post(`${API_URL}/maintenance`, data);
    return response.data;
  },

  updateMaintenanceLog: async (id: number, data: MaintenanceLogRequest) => {
    const response = await axios.put(`${API_URL}/maintenance/${id}`, data);
    return response.data;
  },

  deleteMaintenanceLog: async (id: number) => {
    const response = await axios.delete(`${API_URL}/maintenance/${id}`);
    return response.data;
  },
};

export default maintenanceService;
