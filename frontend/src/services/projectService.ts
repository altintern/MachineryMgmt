import axios from 'axios';
import { API_URL } from '@/config';

export interface Project {
  id?: number;
  name: string;
  description: string;
  location: string;
  startDate: string;
  endDate: string;
  status: string;
}

export const projectService = {
  getAllProjects: async () => {
    try {
      const response = await axios.get(`${API_URL}/v1/projects`);
      return response.data?.data || [];
    } catch (error) {
      console.error('Error fetching projects:', error);
      return [];
    }
  },

  getProjectById: async (id: number) => {
    try {
      const response = await axios.get(`${API_URL}/v1/projects/${id}`);
      return response.data?.data;
    } catch (error) {
      console.error(`Error fetching project with id ${id}:`, error);
      throw error;
    }
  },

  createProject: async (project: Project) => {
    try {
      const response = await axios.post(`${API_URL}/v1/projects`, project);
      return response.data?.data;
    } catch (error) {
      console.error('Error creating project:', error);
      throw error;
    }
  },

  updateProject: async (id: number, project: Project) => {
    try {
      const response = await axios.put(`${API_URL}/v1/projects/${id}`, project);
      return response.data?.data;
    } catch (error) {
      console.error(`Error updating project with id ${id}:`, error);
      throw error;
    }
  },

  deleteProject: async (id: number) => {
    try {
      const response = await axios.delete(`${API_URL}/v1/projects/${id}`);
      return response.data?.data;
    } catch (error) {
      console.error(`Error deleting project with id ${id}:`, error);
      throw error;
    }
  }
};
