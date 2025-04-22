import { api } from '@/lib/api';

// Incident types from OpenAPI spec
export type IncidentType = 'TYPE1' | 'TYPE2' | 'TYPE3';
export type IncidentStatus = 'OPEN' | 'IN_PROGRESS' | 'CLOSED' | 'RESOLVED';

// Base API response structure from OpenAPI spec
export interface MachinaryMgmtBaseApiResponse {
  status?: {
    statusCode?: number;
    statusMessage?: string;
  };
}

// Equipment and Project summary DTOs
export interface EquipmentSummary {
  id: number;
  name: string;
}

export interface ProjectSummary {
  id: number;
  name: string;
}

// Interface matching IncidentReportDto from API spec
export interface IncidentReport {
  id: number;
  incidentDate: string; // ISO date format
  closeDate?: string; // ISO date format
  estimatedCompletionDate: string; // ISO date format
  incidentType: IncidentType;
  actionTaken: string;
  incidentDetails: string;
  status: IncidentStatus;
  equipment: EquipmentSummary;
  project: ProjectSummary;
}

// Interface matching IncidentReportRequestDto from API spec
export interface IncidentReportRequest {
  incidentDate: string; // ISO date format
  closeDate?: string; // ISO date format
  estimatedCompletionDate: string; // ISO date format
  incidentType: IncidentType;
  equipmentId: number;
  projectId: number;
  actionTaken: string;
  incidentDetails: string;
  status: IncidentStatus;
}

// Response with IncidentReport data
export interface IncidentReportResponse extends MachinaryMgmtBaseApiResponse {
  data?: IncidentReport;
}

// Response with IncidentReport array data
export interface IncidentReportListResponse extends MachinaryMgmtBaseApiResponse {
  data?: IncidentReport[];
}

export const incidentService = {
  // GET /v1/incidents - returns IncidentReportListResponse
  getAllIncidents: async () => {
    try {
      const response = await api.get('/v1/incidents');
      console.log('getAllIncidents response:', response);
      const result: IncidentReportListResponse = response.data;
      return result.data || [];
    } catch (error) {
      console.error('Error fetching incidents:', error);
      return [];
    }
  },

  // GET /v1/incidents/{id} - returns IncidentReportResponse
  getIncidentById: async (id: number) => {
    try {
      const response = await api.get(`/v1/incidents/${id}`);
      console.log('getIncidentById response:', response);
      const result: IncidentReportResponse = response.data;
      return result.data;
    } catch (error) {
      console.error(`Error fetching incident with ID ${id}:`, error);
      throw error;
    }
  },

  // POST /v1/incidents - body: IncidentReportRequest, returns MachinaryMgmtBaseApiResponse
  createIncident: async (data: IncidentReportRequest) => {
    try {
      const response = await api.post('/v1/incidents', data);
      console.log('createIncident response:', response);
      return response.data as MachinaryMgmtBaseApiResponse;
    } catch (error) {
      console.error('Error creating incident:', error);
      throw error;
    }
  },

  // PUT /v1/incidents/{id} - body: IncidentReportRequest, returns MachinaryMgmtBaseApiResponse
  updateIncident: async (id: number, data: IncidentReportRequest) => {
    try {
      const response = await api.put(`/v1/incidents/${id}`, data);
      console.log('updateIncident response:', response);
      return response.data as MachinaryMgmtBaseApiResponse;
    } catch (error) {
      console.error(`Error updating incident with ID ${id}:`, error);
      throw error;
    }
  },

  // DELETE /v1/incidents/{id} - returns MachinaryMgmtBaseApiResponse
  deleteIncident: async (id: number) => {
    try {
      const response = await api.delete(`/v1/incidents/${id}`);
      console.log('deleteIncident response:', response);
      const result: MachinaryMgmtBaseApiResponse = response.data;
      
      // Check if operation was successful based on status code or message
      if (result.status?.statusCode === 1073741824) {
        return true;
      }
      
      const msg = result.status?.statusMessage || '';
      if (msg.trim().toLowerCase() === 'incident report deleted successfully') {
        return true;
      }
      
      return false;
    } catch (error) {
      console.error(`Error deleting incident with ID ${id}:`, error);
      throw error;
    }
  }
};

export default incidentService;
