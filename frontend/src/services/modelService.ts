import axios from 'axios';
import { Make } from './makeService';

const API_URL = 'http://localhost:8080/api/v1';

export interface Model {
  id?: number;
  name: string;
  description: string;
  make?: Make;
}

export interface ModelRequest {
  name: string;
  description: string;
  makeId: number;
}

export const modelService = {
  getAllModels: async () => {
    try {
      const response = await axios.get(`${API_URL}/models`);
      return response.data || [];
    } catch (error) {
      console.error('Error fetching models:', error);
      return [];
    }
  },

  getModelById: async (id: number) => {
    const response = await axios.get(`${API_URL}/models/${id}`);
    return response.data;
  },

  createModel: async (model: ModelRequest) => {
    const response = await axios.post(`${API_URL}/models`, model);
    return response.data;
  },

  updateModel: async (id: number, model: ModelRequest) => {
    const response = await axios.put(`${API_URL}/models/${id}`, model);
    return response.data;
  },

  deleteModel: async (id: number) => {
    const response = await axios.delete(`${API_URL}/models/${id}`);
    return response.data;
  }
};
