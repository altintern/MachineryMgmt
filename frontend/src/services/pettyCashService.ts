import { api } from '@/lib/api';

export interface PettyCashTransaction {
  id: number;
  reportDate: string;
  remarks: string;
  amountSpent: number;
  quantity: number;
  cumulativeAmountSpent: number;
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
    name: string;
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
  cumulativeAmountSpent: number;
  purposeJustification: string;
}

const pettyCashService = {
  getAllPettyCash: async () => {
    const response = await api.get('/v1/pettycash');
    return response.data.data;
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
