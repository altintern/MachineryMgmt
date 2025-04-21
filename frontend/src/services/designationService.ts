import axios from 'axios';
import { API_URL } from '@/config';

export interface DesignationDto {
  id: number;
  name: string;
  description: string;
}

export interface DesignationRequestDto {
  name: string;
  description: string;
}

class DesignationService {
  async getAllDesignations(): Promise<DesignationDto[]> {
    const response = await axios.get(`${API_URL}/v1/designations`);
    return response.data?.data || [];
  }

  async getDesignationById(id: number): Promise<DesignationDto> {
    const response = await axios.get(`${API_URL}/v1/designations/${id}`);
    return response.data?.data;
  }

  async createDesignation(designation: DesignationRequestDto): Promise<void> {
    await axios.post(`${API_URL}/v1/designations`, designation);
  }

  async updateDesignation(id: number, designation: DesignationRequestDto): Promise<void> {
    await axios.put(`${API_URL}/v1/designations/${id}`, designation);
  }

  async deleteDesignation(id: number): Promise<void> {
    await axios.delete(`${API_URL}/v1/designations/${id}`);
  }
}

export const designationService = new DesignationService();
