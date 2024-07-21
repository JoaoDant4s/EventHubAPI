const defaultTheme = require('tailwindcss/defaultTheme')
const colors = require('tailwindcss/colors')

/** @type {import('tailwindcss').Config} */
export default {
  content: [
    "./index.html",
    "./src/**/*.{js,ts,jsx,tsx}",
  ],
  theme: {
    extend: {},
    colors: {
      'transparency': colors.transparent,
      'slate': colors.slate,
      'bg': {
        index: '#DEECF3',
        input: '#E6E6E6',
        dashboard: '#E6E6E6',
        white: "#FFFFFF",
      },
      'font': {
        title: '#333333',
        icon: '#4D4D4D',
        text: '#666666',
        placeholder: '#808080'
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
  plugins: [],
}

