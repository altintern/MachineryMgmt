import axios from 'axios';

const API_URL = 'http://localhost:8080/api/v1';

export interface Category {
  id?: number;
  name: string;
}

export interface CategoryRequest {
  name: string;
}

export const categoryService = {
  getAllCategories: async () => {
    try {
      const response = await axios.get(`${API_URL}/categories`);
      return response.data?.data || [];
    } catch (error) {
      console.error('Error fetching categories:', error);
      return [];
    }
  },

  getCategoryById: async (id: number) => {
    const response = await axios.get(`${API_URL}/categories/${id}`);
    return response.data;
  },

  createCategory: async (category: CategoryRequest) => {
    const response = await axios.post(`${API_URL}/categories`, category);
    return response.data;
  },

  updateCategory: async (id: number, category: CategoryRequest) => {
    const response = await axios.put(`${API_URL}/categories/${id}`, category);
    return response.data;
  },

  deleteCategory: async (id: number) => {
    const response = await axios.delete(`${API_URL}/categories/${id}`);
    return response.data;
  }
};