import { api } from '@/lib/api';

export interface MastAnchorageDetails {
  id: number;
  anchorageAtSite: number;
  anchorageFixedAtSite: number;
  anchorageIdleAtSite: number;
  mastAvailableAtSite: number;
  mastFixedAtSite: number;
  mastIdleAtSite: number;
  totalAnchorageRequirement: number;
  totalMastRequirement: number;
  location: string;
  presentBuildingHeight: string;
  presentHeightOfHoist: string;
  remarks: string;
  totalBuildingHeight: string;
  status: string;
  project: {
    id: number;
    name: string;
  };
  equipment: {
    id: number;
    name: string;
  };
}

export interface MastAnchorageRequest {
  projectId: number;
  equipmentId: number;
  anchorageAtSite: number;
  anchorageFixedAtSite: number;
  anchorageIdleAtSite: number;
  mastAvailableAtSite: number;
  mastFixedAtSite: number;
  mastIdleAtSite: number;
  totalAnchorageRequirement: number;
  totalMastRequirement: number;
  location: string;
  presentBuildingHeight: string;
  presentHeightOfHoist: string;
  remarks: string;
  totalBuildingHeight: string;
  status: string;
}

const mastAnchorageService = {
  getAllMastAnchorage: async () => {
    const response = await api.get('/v1/mastanchorage');
    return response.data.data;
  },

  getMastAnchorageById: async (id: number) => {
    const response = await api.get(`/v1/mastanchorage/${id}`);
    return response.data.data;
  },

  createMastAnchorage: async (data: MastAnchorageRequest) => {
    const response = await api.post('/v1/mastanchorage', data);
    return response.data;
  },

  updateMastAnchorage: async (id: number, data: MastAnchorageRequest) => {
    const response = await api.put(`/v1/mastanchorage/${id}`, data);
    return response.data;
  },

  deleteMastAnchorage: async (id: number) => {
    const response = await api.delete(`/v1/mastanchorage/${id}`);
    return response.data;
  },
};

export default mastAnchorageService;
