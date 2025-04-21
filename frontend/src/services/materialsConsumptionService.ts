import { api } from '@/lib/api';

export interface MaterialsConsumptionTransaction {
  id: number;
  issueDate: string;
  quantity: number;
  costPerUnit: number;
  totalCost: number;
  remarks: string;
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

export interface MaterialsConsumptionTransactionRequest {
  projectId: number;
  issueDate: string;
  equipmentId: number;
  itemId: number;
  quantity: number;
  costPerUnit: number;
  totalCost: number;
  remarks?: string;
}

export interface MaterialsConsumptionTransactionResponse {
  data: MaterialsConsumptionTransaction;
  success: boolean;
  message: string;
}

export interface MaterialsConsumptionTransactionListResponse {
  data: MaterialsConsumptionTransaction[];
  success: boolean;
  message: string;
}

const materialsConsumptionService = {
  getAllTransactions: async (page = 0, size = 10) => {
    const response = await api.get(`/v1/materials-consumption-transactions?page=${page}&size=${size}`);
    return response.data;
  },

  getTransactionById: async (id: number) => {
    const response = await api.get(`/v1/materials-consumption-transactions/${id}`);
    return response.data;
  },

  createTransaction: async (data: MaterialsConsumptionTransactionRequest) => {
    const response = await api.post('/v1/materials-consumption-transactions', data);
    return response.data;
  },

  updateTransaction: async (id: number, data: MaterialsConsumptionTransactionRequest) => {
    const response = await api.put(`/v1/materials-consumption-transactions/${id}`, data);
    return response.data;
  },

  deleteTransaction: async (id: number) => {
    const response = await api.delete(`/v1/materials-consumption-transactions/${id}`);
    return response.data;
  },
};

export default materialsConsumptionService;
