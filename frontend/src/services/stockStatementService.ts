import { api } from '@/lib/api';

export interface StockStatement {
  id: number;
  lastIssueOn: string;
  lastReceiptOn: string;
  month: number;
  year: number;
  balance: number;
  landedValue: number;
  landedRate: number;
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

export interface StockStatementRequest {
  itemId: number;
  projectId: number;
  equipmentId: number;
  lastIssueOn: string;
  lastReceiptOn: string;
  month: number;
  year: number;
  balance: number;
  landedValue: number;
  landedRate: number;
}

const stockStatementService = {
  getAllStockStatements: async () => {
    const response = await api.get('/v1/stockstatement');
    return response.data.data;
  },

  getStockStatementById: async (id: number) => {
    const response = await api.get(`/v1/stockstatement/${id}`);
    return response.data.data;
  },

  createStockStatement: async (data: StockStatementRequest) => {
    const response = await api.post('/v1/stockstatement', data);
    return response.data;
  },

  updateStockStatement: async (id: number, data: StockStatementRequest) => {
    const response = await api.put(`/v1/stockstatement/${id}`, data);
    return response.data;
  },

  deleteStockStatement: async (id: number) => {
    const response = await api.delete(`/v1/stockstatement/${id}`);
    return response.data;
  },
};

export default stockStatementService;
