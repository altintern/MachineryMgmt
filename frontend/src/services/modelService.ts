import axios from 'axios';
import { Make } from './makeService';

const API_URL = 'http://localhost:8080/api/v1';

// Interface matching ModelDto from API spec
export interface Model {
  id?: number;
  name: string;
  make?: Make;
}

// Interface matching ModelSummaryDto from API spec
export interface ModelSummary {
  id: number;
  name: string;
}

// Interface matching ModelRequestDto from API spec
export interface ModelRequest {
  name: string;
  makeId: number;
}

export const modelService = {
  // Get all models - matches getAllModels operation
  getAllModels: async () => {
    try {
      const response = await axios.get(`${API_URL}/models`);
      console.log('Models API response:', response.data);
      console.log('Models data array:', response.data?.data);
      
      if (response.data?.data) {
        // Log each model to verify structure
        response.data.data.forEach((model: any, index: number) => {
          console.log(`Model ${index}:`, model);
          console.log(`Model ${index} name:`, model.name);
          console.log(`Model ${index} make:`, model.make);
        });
      }
      
      return response.data?.data || [];
    } catch (error) {
      console.error('Error fetching models:', error);
      return { data: [] };
    }
  },

  // Get model by ID - matches getModelById operation
  getModelById: async (id: number) => {
    try {
      const response = await axios.get(`${API_URL}/models/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error fetching model with ID ${id}:`, error);
      throw error;
    }
  },

  // Create new model - matches createModel operation
  createModel: async (model: ModelRequest) => {
    try {
      console.log('Creating model with data:', model);
      const response = await axios.post(`${API_URL}/models`, model);
      return response.data;
    } catch (error) {
      console.error('Error creating model:', error);
      throw error;
    }
  },

  // Update model - matches updateModel operation
  updateModel: async (id: number, model: ModelRequest) => {
    try {
      console.log(`Updating model ${id} with data:`, model);
      const response = await axios.put(`${API_URL}/models/${id}`, model);
      return response.data;
    } catch (error) {
      console.error(`Error updating model with ID ${id}:`, error);
      throw error;
    }
  },

  // Delete model - matches deleteModel operation
  deleteModel: async (id: number) => {
    try {
      console.log(`Deleting model with ID ${id}`);
      const response = await axios.delete(`${API_URL}/models/${id}`);
      return response.data;
    } catch (error) {
      console.error(`Error deleting model with ID ${id}:`, error);
      throw error;
    }
  }
};
