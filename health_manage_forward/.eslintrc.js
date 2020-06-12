module.exports = {
  root: true,
  env: {
    browser: true,
    node: true,
    es6: true
  },
  parserOptions: {
    parser: 'babel-eslint'
  },
  extends: [
    '@nuxtjs',
    'plugin:nuxt/recommended'
  ],
  // add your custom rules here
  rules: {
    'semi': 0,
    'space-before-function-paren': 0,
    'no-console': 'off',
    "quotes": 0,
    "vue/html-closing-bracket-newline": 0,
    "vue/html-self-closing": ["error", {
      "html": {
        "void": "any",
        "normal": "any",
        "component": "any"
      },
      "svg": "any",
      "math": "always"
    }],
    "vue/singleline-html-element-content-newline": 0,
    'vue/max-attributes-per-line': 0,
    'arrow-parens': 'off',
    "standard/computed-property-even-spacing": ["error", "never"],
    "no-extend-native": ["error", { "exceptions": ["Date"] }]
  }
}
