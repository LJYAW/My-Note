/*
 * @Author: your name
 * @Date: 2021-06-03 11:12:55
 * @LastEditTime: 2021-06-03 15:55:31
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin-template/babel.config.js
 */
// module.exports = {
//   presets: [
//     // https://github.com/vuejs/vue-cli/tree/master/packages/@vue/babel-preset-app
//     '@vue/cli-plugin-babel/preset'
//   ],
//   'env': {
//     'development': {
//       // babel-plugin-dynamic-import-node plugin only does one thing by converting all import() to require().
//       // This plugin can significantly increase the speed of hot updates, when you have a large number of pages.
//       // https://panjiachen.github.io/vue-element-admin-site/guide/advanced/lazy-loading.html
//       'plugins': ['dynamic-import-node']
//     }
//   }
// }
const plugins = [
  [
    'component',
    {
      libraryName: 'element-ui',
      styleLibraryName: 'theme-chalk'
    }
  ]
]
if (process.env.NODE_ENV === 'production') {
  plugins.push('transform-remove-console')
}
module.exports = {
  presets: ['@vue/cli-plugin-babel/preset'],
  plugins: plugins
}
