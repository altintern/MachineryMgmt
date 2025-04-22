import { api } from '@/lib/api';

// Matches ItemDto from OpenAPI spec
export interface Item {
  id: number;
  code: string;
  description: string;
  itemType: 'MATERIAL' | 'SPARE' | 'OTHER';
  uom: string;
}

// Matches ItemRequestDto from OpenAPI spec
export interface ItemRequest {
  code: string;
  description: string;
  itemType: 'MATERIAL' | 'SPARE' | 'OTHER';
  uom: string;
}

// Base API response structure from OpenAPI spec
export interface MachinaryMgmtBaseApiResponse {
  status?: {
    statusCode?: number;
    statusMessage?: string;
  };
}

// Response with Item data
export interface ItemResponse extends MachinaryMgmtBaseApiResponse {
  data?: Item;
}

// Response with Item array data
export interface ItemListResponse extends MachinaryMgmtBaseApiResponse {
  data?: Item[];
}

// itemService matches OpenAPI contract for /items endpoints
export const itemService = {
  // GET /v1/items - returns ItemListResponse
  getAllItems: async () => {
    try {
      // Simple request without pagination parameters for now
      const response = await api.get('/v1/items');
      console.log('getAllItems response:', response);
      const result: ItemListResponse = response.data;
      return result.data || [];
    } catch (error) {
      console.error('Error fetching items:', error);
      return [];
    }
  },

  // GET /v1/items/{id} - returns ItemResponse
  getItemById: async (id: number) => {
    const response = await api.get(`/v1/items/${id}`);
    console.log('getItemById response:', response);
    const result: ItemResponse = response.data;
    return result.data;
  },

  // POST /v1/items - body: ItemRequest, returns ItemResponse
  createItem: async (data: ItemRequest) => {
    const response = await api.post('/v1/items', data);
    console.log('createItem response:', response);
    const result: ItemResponse = response.data;
    return result.data;
  },

  // PUT /v1/items/{id} - body: ItemRequest, returns MachinaryMgmtBaseApiResponse
  updateItem: async (id: number, data: ItemRequest) => {
    const response = await api.put(`/v1/items/${id}`, data);
    console.log('updateItem response:', response);
    return response.data as MachinaryMgmtBaseApiResponse;
  },

  // DELETE /v1/items/{id} - returns MachinaryMgmtBaseApiResponse
  deleteItem: async (id: number) => {
    const response = await api.delete(`/v1/items/${id}`);
    console.log('deleteItem response:', response);
    const result: MachinaryMgmtBaseApiResponse = response.data;
    
    // Check if operation was successful based on status code or message
    if (result.status?.statusCode === 1073741824) {
      return true;
    }
    
    const msg = result.status?.statusMessage || '';
    if (msg.trim().toLowerCase() === 'item deleted successfully') {
      return true;
    }
    
    return false;
  }
};

export default itemService;