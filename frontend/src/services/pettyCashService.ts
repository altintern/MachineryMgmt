import { api } from '@/lib/api';

export interface PettyCashTransaction {
  id: number;
  reportDate: string;
  remarks: string;
  amountSpent: number;
  quantity: number;
  rate: number; // Added to match backend requirement
  cumulativeTotalAmount: number;
  purposeJustification: string;
  project: {
    id: number;
    name: string;
  };
  equipment: {
    id: number;
    name: string;
  };
  item: {
    id: number;
    code: string;
    // name?: string; // Uncomment if backend ever returns name
  };
}

export interface PettyCashTransactionRequest {
  projectId: number;
  equipmentId: number;
  itemId: number;
  reportDate: string;
  remarks: string;
  amountSpent: number;
  quantity: number;
  rate: number; // Added to match backend requirement
  cumulativeTotalAmount: number;
  purposeJustification: string;
}

const pettyCashService = {
  getAllPettyCash: async () => {
    try {
      const response = await api.get('/v1/pettycash');
      console.log('Raw API response:', response.data);
      // The API returns { data: [...transactions] } inside response.data
      return response.data.data || [];
    } catch (error) {
      console.error('Error fetching petty cash data:', error);
      return [];
    }
  },

  getPettyCashById: async (id: number) => {
    const response = await api.get(`/v1/pettycash/${id}`);
    return response.data.data;
  },

  createPettyCash: async (data: PettyCashTransactionRequest) => {
    const response = await api.post('/v1/pettycash', data);
    return response.data;
  },

  updatePettyCash: async (id: number, data: PettyCashTransactionRequest) => {
    const response = await api.put(`/v1/pettycash/${id}`, data);
    return response.data;
  },

  deletePettyCash: async (id: number) => {
    const response = await api.delete(`/v1/pettycash/${id}`);
    return response.data;
  },
};

export default pettyCashService;
