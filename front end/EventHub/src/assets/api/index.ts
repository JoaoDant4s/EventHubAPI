import axios from 'axios';

const baseURL = 'https://localhost';

export const MOCK = false;

export const axiosAPI = axios.create({
  baseURL,
});
