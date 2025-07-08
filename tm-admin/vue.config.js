/*
 * @Author: your name
 * @Date: 2021-03-17 11:36:41
 * @LastEditTime: 2021-06-08 14:53:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /special_subject_admin_web/vue.config.js
 */
const path = require('path')
const defaultSettings = require('./src/settings.js')
const name = defaultSettings.title || 'vue Admin Template' // page title

function resolve(dir) {
  return path.join(__dirname, dir)
}

const Timestamp = new Date().getTime()
const autoprefixer = require('autoprefixer')
const pxtorem = require('postcss-pxtorem')
module.exports = {
  devServer: {
    proxy: {
      // detail: https://cli.vuejs.org/config/#devserver-proxy
      '/bd': {
        target: `http://192.168.10.98:8020/`,
        changeOrigin: true,
        pathRewrite: {
          '^/bd': ''
        }
      }
    }
  },
  // 部署文件使用的路径
  publicPath: '/',

  runtimeCompiler: true, // 是否使用包含运行时编译器的 Vue 构建版本

  productionSourceMap: false, // 生产环境的 source map

  chainWebpack: (config) => {
    // 修改文件引入自定义路径
    config.resolve.alias.set('@', resolve('src')).set('@img', resolve('src/assets/img'))
    config.resolve.extensions.add('.js', '.json', '.vue', '.scss')
  },
  // 修改webpack的配置
  configureWebpack: {
    name: name,
    // 警告 webpack 的性能提示
    performance: {
      hints: 'warning',
      // 入口起点的最大体积
      maxEntrypointSize: 50000000,
      // 生成文件的最大体积
      maxAssetSize: 30000000,
      // 只给出 js 文件的性能提示
      assetFilter: function(assetFilename) {
        return assetFilename.endsWith('.js')
      }
    },
    output: {
      // 输出重构  打包编译后的 文件名称  【模块名称.时间戳】
      filename: `static/js/[name].[hash].js`,
      chunkFilename: `static/js/[name].[hash].js`
    }
  },

  // 关闭 eslint 语法检查
  lintOnSave: false,

  css: {
    // 是否使用css分离插件 ExtractTextPlugin
    extract: false,

    loaderOptions: {
      postcss: {
        plugins: [
          autoprefixer(),
          pxtorem({
            rootValue: 192, // 设计稿宽度为1920px的
            propList: ['*'],
            exclude: (e) => {
              if (/src(\\|\/)views(\\|\/)login/.test(e)) {
                return false
              }
              return true
            }
          })
        ]
      },
      sass: {
        // @是src的别名
        prependData: `
                @import "@/styles/variables.scss";
              `
      }
    }
  }
}
