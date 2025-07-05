/** @type {import('tailwindcss').Config} */
export default {
  content: ['./index.html', './src/**/*.{js,ts,jsx,tsx}'],
  theme: {
    extend: {
      colors: {
        primary: '#2563eb',
        // 'primary-dark': '#312e81',
        'primary-dark': '#0f172a',
        'primary-dark-2': '#1e293b',
        secundary: '#3730a3',
      },
      screens: {
        xs: '480px',
      },
      fontFamily: {
        inter: ['Inter', 'sans-serif'],
      },
    },
    plugins: [],
  },
};
