import axios from 'axios';

// Backend'in ana adresi
const API_BASE_URL = 'http://localhost:8080/api';

// Axios nesnesi oluştur
const api = axios.create({
    baseURL: API_BASE_URL,
});

// Her istekten önce çalışır (Interceptor)
// Eğer LocalStorage'da token varsa, otomatik olarak Header'a ekler.
api.interceptors.request.use(
    (config) => {
        const token = localStorage.getItem('token');
        if (token) {
            config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

export default api;