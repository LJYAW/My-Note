/*
 * @Author: zzx
 * @Date: 2020-07-08 14:29:33
 * @LastEditTime: 2021-07-16 18:10:34
 * @FilePath: /zhijian_web/vue.config.js
 */
const path = require('path')
const BundleAnalyzerPlugin = require('webpack-bundle-analyzer').BundleAnalyzerPlugin
// 引入等比适配插件
// const px2rem = require('postcss-px2rem')
// const postcss = px2rem({
//   // 基准大小 baseSize，需要和rem.js中相同
//   remUnit: 16
// })

function resolve(dir) {
  return path.join(__dirname, dir)
}
const Timestamp = new Date().getTime()

const cdn = {
  css: ['//at.alicdn.com/t/font_1147406_w53g8v4v62g.css'],
  js: ['//at.alicdn.com/t/font_2090945_k67y4clx3nl.js']
}

module.exports = {
  // 部署文件使用的路径
  publicPath: '/',

  runtimeCompiler: true, // 是否使用包含运行时编译器的 Vue 构建版本

  productionSourceMap: true, // 生产环境的 source map

  chainWebpack: config => {
    // 修改文件引入自定义路径
    config.resolve.alias.set('@', resolve('src')).set('@img', resolve('src/assets/img'))
    config.resolve.extensions.add('.js', '.json', '.vue', '.scss')

    // 配置CDN
    config.plugin('html').tap(args => {
      args[0].cdn = cdn
      return args
      // return [ /* 传递给 html-webpack-plugin's 构造函数的新参数 */ ]
    })

    // 给js和css配置版本号
    config.output.filename('js/[name].' + Timestamp + '.js').end()
    config.output.chunkFilename('js/[name].' + Timestamp + '.js').end()
  },

  // 修改webpack的配置
  configureWebpack: {
    // 警告 webpack 的性能提示
    performance: {
      hints: 'warning',
      // 入口起点的最大体积
      maxEntrypointSize: 50000000,
      // 生成文件的最大体积
      maxAssetSize: 30000000,
      // 只给出 js 文件的性能提示
      assetFilter(assetFilename) {
        return assetFilename.endsWith('.js')
      }
    }
    // plugins: [new BundleAnalyzerPlugin()]
  },

  // 关闭 eslint 语法检查
  lintOnSave: false,
  // 配置基本大小

  css: {
    // 是否使用css分离插件 ExtractTextPlugin
    extract: false,

    loaderOptions: {
      // postcss: {
      //   plugins: [postcss]
      // },
      sass: {
        // @是src的别名
        prependData: `
                @import "@/assets/scss/variable.scss";
              `
      }
    }
  }
}
