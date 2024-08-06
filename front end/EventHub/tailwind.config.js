const defaultTheme = require('tailwindcss/defaultTheme')
const colors = require('tailwindcss/colors')

/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    colors: {
      'transparency': colors.transparent,
      'slate': colors.slate,
      'danger': colors.red,
      'alert': colors.amber,
      'success': colors.green,
      'info': colors.sky,
      'bg': {
        index: '#DEECF3',
        input: '#E6E6E6',
        dashboard: '#E6E6E6',
        white: '#FFFFFF',
        bar: '#F5F5F5',
      },
      'font': {
        title: '#333333',
        subtitle: '#4D4D4D',
        input: '#4D4D4D', 
        icon: '#4D4D4D',
        navItem: '#4D4D4D',
        text: '#666666',
        placeholder: '#808080',
        white: '#FFFFFF',
      },
      'table': {
        odd: '#D9D9D9',
        even: '#CCCCCC',
      },
      'primary': '#559EF5',
      'secondary': '#55F5CE',
      'tertiary': '#F5A155',
      'white': '#FFFFFF'
    },
  },
  spacing: {
    '128': '32rem',
  },
  fontFamily: {
    Roboto: ['Roboto']
  },
  extend: {
    keyframes: {
      openModal: {
        '0%': { opacity: 0 },
        '100%': { opacity: 1 },
      },
      closeModal: {
        '0%': { opacity: 1 },
        '100%': { opacity: 0 },
      }
    },
    animation: {
      openModal: 'openModal 0.5s ease-in-out forwards',
      closeModal: 'closeModal 0.5s ease-in-out forwards',
    },
  },
  plugins: [],
}

