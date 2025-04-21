import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1';

export interface EquipmentUtilization {
  id?: number;
  startingHoursKms: number;
  closingHoursKms: number;
  availabilityHours: number;
  dieselConsumedLtrs: number;
  avgFuelConsumption: number;
  utilizationPercentage: number;
  month: number;
  year: number;
  remarks: string;
  equipment: {
    id: number;
    name: string;
  };
  project: {
    id: number;
    name: string;
  };
}

export interface EquipmentUtilizationRequest {
  equipmentId: number;
  projectId: number;
  startingHoursKms: number;
  closingHoursKms: number;
  availabilityHours: number;
  dieselConsumedLtrs: number;
  avgFuelConsumption: number;
  utilizationPercentage: number;
  month: number;
  year: number;
  remarks: string;
}

export const equipmentUtilizationService = {
  getAllUtilizations: async () => {
    try {
      const response = await axios.get(`${API_URL}/equipment/utilization`);
      return response.data?.data || [];
    } catch (error) {
      console.error('Error fetching utilizations:', error);
      return [];
    }
  },

  getUtilizationById: async (id: number) => {
    try {
      const response = await axios.get(`${API_URL}/equipment/utilization/${id}`);
      return response.data?.data || null;
    } catch (error) {
      console.error('Error fetching utilization:', error);
      return null;
    }
  },

  createUtilization: async (utilization: EquipmentUtilizationRequest) => {
    try {
      const response = await axios.post(`${API_URL}/equipment/utilization`, utilization);
      return response.data;
    } catch (error) {
      console.error('Error creating utilization:', error);
      throw error;
    }
  },

  updateUtilization: async (id: number, utilization: EquipmentUtilizationRequest) => {
    try {
      const response = await axios.put(`${API_URL}/equipment/utilization/${id}`, utilization);
      return response.data;
    } catch (error) {
      console.error('Error updating utilization:', error);
      throw error;
    }
  },

  deleteUtilization: async (id: number) => {
    try {
      const response = await axios.delete(`${API_URL}/equipment/utilization/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error deleting utilization:', error);
      throw error;
    }
  }
};
