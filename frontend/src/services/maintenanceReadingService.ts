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
  greaseUsed: number; // Changed from gearUsed to match API spec
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
  greaseUsed: number; // Changed from gearUsed to match API spec
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
    try {
      console.log(`Deleting maintenance reading with ID: ${id}`);
      // Try using the same pattern as the parts-used endpoint
      console.log(`DELETE request to: ${API_URL}/maintenance/reading/${id}`);
      // First try the standard DELETE request
      try {
        const response = await axios.delete(`${API_URL}/maintenance/reading/${id}`);
        console.log('Delete response:', response.data);
        return response.data;
      } catch (deleteError) {
        // If DELETE fails, try using a POST request with a /delete suffix
        console.log('DELETE failed, trying POST to /delete endpoint');
        const response = await axios.post(`${API_URL}/maintenance/reading/delete/${id}`);
        console.log('Delete response (POST method):', response.data);
        return response.data;
      }
    } catch (error) {
      console.error('Error in deleteMaintenanceReading:', error);
      throw error;
    }
  },
};

export default maintenanceReadingService;
