import { api } from '@/lib/api';

export interface Item {
  id: number;
  name: string;
  description: string;
  unit: string;
  unitPrice: number;
}

export interface ItemRequest {
  name: string;
  description: string;
  unit: string;
  unitPrice: number;
}

export const itemService = {
  getAllItems: async (page = 0, size = 10) => {
    const response = await api.get(`/v1/items?page=${page}&size=${size}`);
    console.log('Items response:', response.data);
    return response.data;
  },

  getItemById: async (id: number) => {
    const response = await api.get(`/v1/items/${id}`);
    return response.data;
  },

  createItem: async (data: ItemRequest) => {
    const response = await api.post('/v1/items', data);
    return response.data;
  },

  updateItem: async (id: number, data: ItemRequest) => {
    const response = await api.put(`/v1/items/${id}`, data);
    return response.data;
  },

  deleteItem: async (id: number) => {
    const response = await api.delete(`/v1/items/${id}`);
    return response.data;
  },
};

export default itemService;
