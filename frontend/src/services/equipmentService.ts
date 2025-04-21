import axios from 'axios';
import { API_URL } from '@/config';

export interface Equipment {
  id: number;
  name: string;
  assetCode: string;
  yearOfManufacture: number;
  category: {
    id: number;
    name: string;
  };

  model: {
    id: number;
    name: string;
  };
  project: {
    id: number;
    name: string;
  };
}

export interface EquipmentRequest {
  name: string;
  categoryId: number;

  modelId: number;
  assetCode: string;
  yearOfManufacture: number;
  projectId: number;
}

export const equipmentService = {
  getAllEquipment: async (): Promise<Equipment[]> => {
    try {
      const response = await axios.get(`${API_URL}/v1/equipment`);
      return response.data?.data || [];
    } catch (error) {
      console.error('Error fetching equipment:', error);
      return [];
    }
  },

  getEquipmentById: async (id: number): Promise<Equipment | null> => {
    try {
      const response = await axios.get(`${API_URL}/v1/equipment/${id}`);
      return response.data?.data || null;
    } catch (error) {
      console.error('Error fetching equipment by id:', error);
      return null;
    }
  },

  createEquipment: async (equipment: EquipmentRequest) => {
    try {
      const response = await axios.post(`${API_URL}/v1/equipment`, equipment);
      return response.data;
    } catch (error) {
      console.error('Error creating equipment:', error);
      throw error;
    }
  },

  updateEquipment: async (id: number, equipment: EquipmentRequest) => {
    try {
      const response = await axios.put(`${API_URL}/v1/equipment/${id}`, equipment);
      return response.data;
    } catch (error) {
      console.error('Error updating equipment:', error);
      throw error;
    }
  },

  deleteEquipment: async (id: number) => {
    try {
      const response = await axios.delete(`${API_URL}/v1/equipment/${id}`);
      return response.data;
    } catch (error) {
      console.error('Error deleting equipment:', error);
      throw error;
    }
  }
};
