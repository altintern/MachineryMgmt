import axios from 'axios';
import { API_URL } from '@/config';

export interface DepartmentDto {
  id: number;
  name: string;
  description: string;
}

export interface DesignationDto {
  id: number;
  name: string;
  description: string;
}

export interface EmployeeDto {
  id: number;
  name: string;
  remarks: string;
  department: {
    id: number;
    name: string;
    description: string;
  };
  designation: {
    id: number;
    name: string;
    description: string;
  };
}

export interface EmployeeRequestDto {
  name: string;
  remarks: string;
  departmentId: number;
  designationId: number;
}

export interface EmployeeSummaryDto {
  id: number;
  name: string;
}

class EmployeeService {
  async getAllEmployees(): Promise<EmployeeDto[]> {
    try {
      const response = await axios.get(`${API_URL}/v1/employees`);
      return response.data?.data || [];
    } catch (error) {
      console.error('Error fetching employees:', error);
      return [];
    }
  }

  async getEmployeeById(id: number): Promise<EmployeeDto> {
    try {
      const response = await axios.get(`${API_URL}/v1/employee/${id}`);
      return response.data?.data;
    } catch (error) {
      console.error(`Error fetching employee with id ${id}:`, error);
      throw error;
    }
  }

  async createEmployee(employee: EmployeeRequestDto): Promise<void> {
    try {
      await axios.post(`${API_URL}/v1/employees`, employee);
    } catch (error) {
      console.error('Error creating employee:', error);
      throw error;
    }
  }

  async updateEmployee(id: number, employee: EmployeeRequestDto): Promise<void> {
    try {
      await axios.put(`${API_URL}/v1/employee/${id}`, employee);
    } catch (error) {
      console.error(`Error updating employee with id ${id}:`, error);
      throw error;
    }
  }

  async deleteEmployee(id: number): Promise<void> {
    try {
      await axios.delete(`${API_URL}/v1/employee/${id}`);
    } catch (error) {
      console.error(`Error deleting employee with id ${id}:`, error);
      throw error;
    }
  }
}

export const employeeService = new EmployeeService();
