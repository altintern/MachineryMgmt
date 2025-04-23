import axios from 'axios';

const API_URL = process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api/v1';

export interface ItemSummary {
  id: number;
  code: string; // API returns code instead of name
}

export interface MaintenanceLogSummary {
  id: number;
}

export interface MaintenancePartUsed {
  id: number;
  quantity: number;
  item: ItemSummary;
  maintenanceLog: {
    id: number;
  };
}

export interface MaintenancePartUsedRequest {
  maintenanceLogId: number;
  itemId: number;
  quantity: number;
}

const maintenancePartUsedService = {
  getAllMaintenancePartUsed: async () => {
    const response = await axios.get(`${API_URL}/maintenance/partused`);
    return response.data;
  },

  getMaintenancePartUsedById: async (id: number) => {
    const response = await axios.get(`${API_URL}/maintenance/partused/${id}`);
    return response.data;
  },

  createMaintenancePartUsed: async (data: MaintenancePartUsedRequest) => {
    const response = await axios.post(`${API_URL}/maintenance/partused`, data);
    return response.data;
  },

  updateMaintenancePartUsed: async (id: number, data: MaintenancePartUsedRequest) => {
    const response = await axios.put(`${API_URL}/maintenance/partused/${id}`, data);
    return response.data;
  },

  deleteMaintenancePartUsed: async (id: number) => {
    const response = await axios.delete(`${API_URL}/maintenance/partused/${id}`);
    return response.data;
  },
};

export default maintenancePartUsedService;
