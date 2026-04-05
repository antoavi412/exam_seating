import axios from 'axios';

const API_BASE_URL = 'http://localhost:8080/api';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Students API
export const studentAPI = {
  getAll: () => api.get('/students'),
  getById: (id) => api.get(`/students/${id}`),
  create: (data) => api.post('/students', data),
  update: (id, data) => api.put(`/students/${id}`, data),
  delete: (id) => api.delete(`/students/${id}`),
  getByDepartment: (deptId) => api.get(`/students/department/${deptId}`),
};

// Halls API
export const hallAPI = {
  getAll: () => api.get('/halls'),
  getActive: () => api.get('/halls/active'),
  getById: (id) => api.get(`/halls/${id}`),
  create: (data) => api.post('/halls', data),
  update: (id, data) => api.put(`/halls/${id}`, data),
  delete: (id) => api.delete(`/halls/${id}`),
};

// Exams API
export const examAPI = {
  getAll: () => api.get('/exams'),
  getById: (id) => api.get(`/exams/${id}`),
  create: (data) => api.post('/exams', data),
  update: (id, data) => api.put(`/exams/${id}`, data),
  delete: (id) => api.delete(`/exams/${id}`),
  allocateSeating: (id) => api.post(`/exams/${id}/allocate-seating`),
  allocateInvigilators: (id) => api.post(`/exams/${id}/allocate-invigilators`),
  getSeating: (id) => api.get(`/exams/${id}/seating`),
};

// Dashboard API
export const dashboardAPI = {
  getStats: () => api.get('/dashboard/stats'),
};

export default api;
