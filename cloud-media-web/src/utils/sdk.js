/*
 * @Author: your name
 * @Date: 2021-07-23 17:34:13
 * @LastEditTime: 2021-09-15 14:33:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /vite-project/src/util/baiduSDK.js
 */

import async from 'async'
import * as imageConversion from 'image-conversion'
const PART_SIZE = 5 * 1024 * 1024 // 指定分块大小
const sdk = require('@baiducloud/sdk/dist/baidubce-sdk.bundle.min.js')
class BaiduSDK {
  constructor(params) {
    this.params = params
    this.tasks = []
    this.sdk = sdk
    this.bucket = params.bucket
    this.key = this.getkey(params.acl_path)
    this._progress = 0
    this.uploadId = null
    // 初始化client
    this.initClient()
    // 监听progress进度条
    this.watchProgress()
  }
  // 初始化client
  initClient() {
    var bosConfig = {
      credentials: {
        ak: this.params.ak, // STS服务器下发的临时ak
        sk: this.params.sk // STS服务器下发的临时sk
      },
      sessionToken: this.params.session_token, // STS服务器下发的sessionToken
      endpoint: this.params.endpoint
    }
    this.client = new this.sdk.BosClient(bosConfig)
  }
  // 文件根据PART_SIZE分块
  getTasks(file, uploadId, bucketName, key) {
    let leftSize = file.size
    let offset = 0
    let partNumber = 1
    const tasks = []
    while (leftSize > 0) {
      const partSize = Math.min(leftSize, PART_SIZE)
      tasks.push({
        file: file,
        uploadId: uploadId,
        bucketName: bucketName,
        key: key,
        partNumber: partNumber,
        partSize: partSize,
        start: offset,
        stop: offset + partSize - 1
      })

      leftSize -= partSize
      offset += partSize
      partNumber += 1
    }
    return tasks
  }
  // 根据分块上传
  uploadPartFile(state, client) {
    return (task, callback) => {
      this.uploadId = task.uploadId
      const blob = task.file.slice(task.start, task.stop + 1)
      client
        .uploadPartFromBlob(
          task.bucketName,
          task.key,
          task.uploadId,
          task.partNumber,
          task.partSize,
          blob
        )
        .then(res => {
          ++state.loaded
          this.progress = (state.loaded / state.total) * 100
          callback(null, res)
        })
        .catch(err => {
          callback(err)
        })
    }
  }
  // 分块上传
  async startPartUpload(file, cb) {
    const self = this
    var blob
    const imgTypeArr = [
      'image/png',
      'image/jpg',
      'image/gif',
      'image/jpeg',
      'image/bmp',
      'image/svg+xml'
    ]
    // 图片压缩后上传 其他类型直接上传
    if (imgTypeArr.includes(file.type)) {
      await imageConversion
        .compress(file, {
          quality: 0.7,
          type: 'image/png'
        })
        .then(res => {
          blob = new window.File([res], file.name, { type: file.type })
        })
    } else {
      blob = file
    }

    // 拼接上文件扩展名
    const index = file.name.lastIndexOf('.')
    const suffix = file.name.substring(index + 1)
    this.key += suffix

    let uploadId
    this.client
      .initiateMultipartUpload(this.bucket, this.key, this.options)
      .then(response => {
        uploadId = response.body.uploadId // 开始上传，获取服务器生成的uploadId
        const deferred = this.sdk.Q.defer()
        const tasks = this.getTasks(blob, uploadId, this.bucket, this.key)
        self.tasks = tasks
        const THREADS = 5 // 同时上传的分块数量
        const state = {
          lengthComputable: true,
          loaded: 0,
          total: tasks.length,
          THREADS: THREADS
        }
        // 为了管理分块上传，使用了async（https://github.com/caolan/async）库来进行异步处理
        async.mapLimit(
          tasks,
          THREADS,
          this.uploadPartFile(state, this.client),
          function(err, results) {
            if (err) {
              deferred.reject(err)
            } else {
              deferred.resolve(results)
            }
          }
        )
        return deferred.promise
      })
      .then(allResponse => {
        const partList = []
        allResponse.forEach(function(response, index) {
          // 生成分块清单
          partList.push({
            partNumber: index + 1,
            eTag: response.http_headers.etag
          })
        })
        return this.client.completeMultipartUpload(
          this.bucket,
          this.key,
          uploadId,
          partList
        )
      })
      .then(res => {
        // 上传完成
        cb(res, this.params)
      })
      .catch(function(err) {
        // 上传失败，添加您的代码
        console.log(err)
      })
  }

  // 简单上传
  putObjectFromBlob(file, cb) {
    const index = file.name.lastIndexOf('.')
    const suffix = file.name.substring(index + 1)
    this.key += suffix
    this.getProgress()
    const blob = file
    this.client
      .putObjectFromBlob(this.bucket, this.key, blob)
      .then(function(res) {
        // 上传完成，添加您的代码
        cb(res)
      })
      .catch(function(err) {
        // 上传失败，添加您的代码
        console.error(err)
      })
  }
  // 中断上传
  abortUpload() {
    console.log(this.bucket, this.key, this.uploadId)
    this.client.abortMultipartUpload(this.bucket, this.key, this.uploadId)
  }
  // 获取进度
  getProgress(cb) {
    this.client.on('progress', evt => {
      // 监听上传进度
      if (evt.lengthComputable) {
        var percentage = (evt.loaded / evt.total) * 100
        this.progress = percentage
        typeof cb === 'function' && cb(Math.floor(percentage))
      }
    })
  }

  // 获取分段上传进度

  getLimitProgress(cb) {
    const percentageList = []

    this.client.on('progress', (evt, harder) => {
      const partNumber = harder.args.params.partNumber
      const index = percentageList.findIndex(
        item => item.partNumber === partNumber
      )

      if (index < 0) {
        percentageList.push({
          partNumber: partNumber,
          loaded: evt.loaded
        })
      } else {
        percentageList[index].loaded = evt.loaded
      }

      let totalLoaded = 0
      percentageList.forEach((item) => {
        totalLoaded += item.loaded
      })

      typeof cb === 'function' && cb(Math.floor(totalLoaded))
    })
  }

  // 生成key----Start
  getkey(acl_path) {
    return `${acl_path}${this.currentDate()}/${this.randomString()}.` // 保存到bos时的key，您可更改，默认以文件名作为key
  }
  currentDate() {
    const myDate = new Date()
    const year = myDate.getFullYear() // 获取完整的年份(4位)
    const month = (myDate.getMonth() + 1).toString().padStart(2, '0') // 获取当前月份(0-11,0代表1月)
    const day = myDate
      .getDate()
      .toString()
      .padStart(2, '0') // 获取当前日(1-31)
    return `${year}-${month}-${day}`
  }
  randomString(e) {
    e = e || 32
    var t = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'
    var a = t.length
    var n = ''
    for (let i = 0; i < e; i++) n += t.charAt(Math.floor(Math.random() * a))
    return n
  }
  // 生成key----End

  // 监听progress返回给实例
  watchProgress() {
    Object.defineProperty(this, 'progress', {
      get: value => {
        return value
      },
      set: value => {
        this._progress = value
      }
    })
  }
}

export default BaiduSDK
