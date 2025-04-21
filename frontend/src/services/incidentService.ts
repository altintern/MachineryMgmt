import axios from 'axios';
import { API_URL } from '@/config';

export type IncidentType = 'TYPE1' | 'TYPE2' | 'TYPE3';
export type IncidentStatus = 'OPEN' | 'IN_PROGRESS' | 'CLOSED' | 'RESOLVED';

export interface IncidentReport {
  id?: number;
  incidentDate: string;
  estimatedCompletionDate: string;
  incidentType: IncidentType;
  actionTaken: string;
  status: IncidentStatus;
  equipment: {
    id: number;
    name: string;
  };
  project: {
    id: number;
    name: string;
  };
}

export interface IncidentReportRequest {
  incidentDate: string;
  estimatedCompletionDate: string;
  incidentType: IncidentType;
  equipmentId: number;
  projectId: number;
  actionTaken: string;
  status: IncidentStatus;
}

class IncidentService {
  async getAllIncidents(): Promise<IncidentReport[]> {
    const response = await axios.get(`${API_URL}/v1/incidents`);
    return response.data?.data || [];
  }

  async getIncidentById(id: number): Promise<IncidentReport> {
    const response = await axios.get(`${API_URL}/v1/incidents/${id}`);
    return response.data?.data;
  }

  async createIncident(incident: IncidentReportRequest): Promise<void> {
    await axios.post(`${API_URL}/v1/incidents`, incident);
  }

  async updateIncident(id: number, incident: IncidentReportRequest): Promise<void> {
    await axios.put(`${API_URL}/v1/incidents/${id}`, incident);
  }

  async deleteIncident(id: number): Promise<void> {
    await axios.delete(`${API_URL}/v1/incidents/${id}`);
  }
}

export const incidentService = new IncidentService();
