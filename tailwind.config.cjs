module.exports = {
  future: {
    removeDeprecatedGapUtilities: true,
    purgeLayersByDefault: true
  },
  theme: {
    screens: {
      'l': {'max': '1280px'},
      'm': {'max': '1024px'},
      's': {'max': '768px'},
      'xs': {'max': '600px'}
    },
    extend: {
      colors: {
        'waveyBrown': '#98473E',
        'waveyGreen': '#37C9B7',
        'waveyYellow': '#F2F79E'
      },
      fontFamily: {
        'glacial': ['Glacial Indifference', 'sans-serif'],
        'glacial--bold': ['Glacial Indifference Bold', 'sans-serif']
      }
    }
  },
  plugins: [],
  content : ['./svelte-client/**/*.svelte'],
}