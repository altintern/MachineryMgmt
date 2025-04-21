import axios from 'axios';
import { API_URL } from '@/config';

export interface DepartmentDto {
  id: number;
  name: string;
  description: string;
}

export interface DepartmentRequestDto {
  name: string;
  description: string;
}

class DepartmentService {
  async getAllDepartments(): Promise<DepartmentDto[]> {
    const response = await axios.get(`${API_URL}/v1/departments`);
    return response.data?.data || [];
  }

  async getDepartmentById(id: number): Promise<DepartmentDto> {
    const response = await axios.get(`${API_URL}/v1/departments/${id}`);
    return response.data?.data;
  }

  async createDepartment(department: DepartmentRequestDto): Promise<void> {
    await axios.post(`${API_URL}/v1/departments`, department);
  }

  async updateDepartment(id: number, department: DepartmentRequestDto): Promise<void> {
    await axios.put(`${API_URL}/v1/departments/${id}`, department);
  }

  async deleteDepartment(id: number): Promise<void> {
    await axios.delete(`${API_URL}/v1/departments/${id}`);
  }
}

export const departmentService = new DepartmentService();
