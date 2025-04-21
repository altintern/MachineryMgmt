import { api } from '@/lib/api';

export interface OvertimeReport {
  id: number;
  date: string;
  employee: {
    id: number;
    name: string;
  };
  presentDays: number;
  otHours: number;
  remarks: string;
}

export interface OvertimeReportRequest {
  date: string;
  employeeId: number;
  presentDays: number;
  otHours: number;
  remarks: string;
}

const overtimeReportService = {
  getAllOvertimeReports: async () => {
    const response = await api.get('/v1/overtimereport');
    return response.data.data;
  },

  getOvertimeReportById: async (id: number) => {
    const response = await api.get(`/v1/overtimereport/${id}`);
    return response.data.data;
  },

  createOvertimeReport: async (data: OvertimeReportRequest) => {
    const response = await api.post('/v1/overtimereport', data);
    return response.data;
  },

  updateOvertimeReport: async (id: number, data: OvertimeReportRequest) => {
    const response = await api.put(`/v1/overtimereport/${id}`, data);
    return response.data;
  },

  deleteOvertimeReport: async (id: number) => {
    const response = await api.delete(`/v1/overtimereport/${id}`);
    return response.data;
  },
};

export default overtimeReportService;
