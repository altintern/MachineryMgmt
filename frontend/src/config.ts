const config = {
  API_URL: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api'
};

export const { API_URL } = config;
export default config;
