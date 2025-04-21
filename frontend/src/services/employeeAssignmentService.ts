import axios from 'axios';
import { API_URL } from '@/config';
import { EmployeeSummaryDto } from './employeeService';

export interface ProjectSummaryDto {
  id: number;
  name: string;
}

export interface EquipmentSummaryDto {
  id: number;
  name: string;
}

export interface EmployeeAssignmentDto {
  id: number;
  joiningDate: string;
  employee: EmployeeSummaryDto;
  project: ProjectSummaryDto;
  equipment: EquipmentSummaryDto;
}

export interface EmployeeAssignmentRequestDto {
  employeeId: number;
  projectId: number;
  joiningDate: string;
  equipmentId: number;
}

class EmployeeAssignmentService {
  async getAllEmployeeAssignments(): Promise<EmployeeAssignmentDto[]> {
    try {
      const response = await axios.get(`${API_URL}/v1/employees/assignment`);
      return response.data?.data || [];
    } catch (error) {
      console.error('Error fetching employee assignments:', error);
      return [];
    }
  }

  async getEmployeeAssignmentById(id: number): Promise<EmployeeAssignmentDto> {
    try {
      const response = await axios.get(`${API_URL}/v1/employees/assignment/${id}`);
      return response.data?.data;
    } catch (error) {
      console.error(`Error fetching employee assignment with id ${id}:`, error);
      throw error;
    }
  }

  async createEmployeeAssignment(assignment: EmployeeAssignmentRequestDto): Promise<void> {
    try {
      await axios.post(`${API_URL}/v1/employees/assignment`, assignment);
    } catch (error) {
      console.error('Error creating employee assignment:', error);
      throw error;
    }
  }

  async updateEmployeeAssignment(id: number, assignment: EmployeeAssignmentRequestDto): Promise<void> {
    try {
      await axios.put(`${API_URL}/v1/employees/assignment/${id}`, assignment);
    } catch (error) {
      console.error(`Error updating employee assignment with id ${id}:`, error);
      throw error;
    }
  }

  async deleteEmployeeAssignment(id: number): Promise<void> {
    try {
      await axios.delete(`${API_URL}/v1/employees/assignment/${id}`);
    } catch (error) {
      console.error(`Error deleting employee assignment with id ${id}:`, error);
      throw error;
    }
  }
}

export const employeeAssignmentService = new EmployeeAssignmentService();
