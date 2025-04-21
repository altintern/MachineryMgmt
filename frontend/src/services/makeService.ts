import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1';

export interface Make {
  id?: number;
  name: string;
}

export const makeService = {
  getAllMakes: async () => {
    try {
      const response = await axios.get(`${API_URL}/makes`);
      return response.data || [];
    } catch (error) {
      console.error('Error fetching makes:', error);
      return [];
    }
  },

  getMakeById: async (id: number) => {
    const response = await axios.get(`${API_URL}/makes/${id}`);
    return response.data;
  },

  createMake: async (make: Make) => {
    const response = await axios.post(`${API_URL}/makes`, make);
    return response.data;
  },

  updateMake: async (id: number, make: Make) => {
    const response = await axios.put(`${API_URL}/makes/${id}`, make);
    return response.data;
  },

  deleteMake: async (id: number) => {
    const response = await axios.delete(`${API_URL}/makes/${id}`);
    return response.data;
  }
};
