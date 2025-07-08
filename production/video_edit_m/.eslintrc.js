module.exports = {
  root: true,
  env: {
    node: true
  },
  extends: ['plugin:vue/essential', '@vue/standard'],
  parserOptions: {
    parser: 'babel-eslint',
    ecmaFeatures: {
      globalReturn: true,
      impliedStrict: false,
      jsx: true,
      modules: true
    }
  },
  rules: {
    'no-unused-vars': 0,
    'no-unused-expressions': 0,
    'eol-last': 0,
    'prefer-const': 0,
    camelcase: 0,
    indent: 0,
    eqeqeq: 0,
    'space-before-function-paren': 0,
    properties: 0,
    'vue/no-unused-components': [
      0,
      {
        ignoreWhenBindingPresent: true
      }
    ]
  }
}
