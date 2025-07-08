import { Promise } from 'core-js'

export function canvasImgSrc(id) {
  function dataURItoBlob(base64Data) {
    var byteString
    if (base64Data.split(',')[0].indexOf('base64') >= 0) { byteString = atob(base64Data.split(',')[1]) } else byteString = unescape(base64Data.split(',')[1])
    var mimeString = base64Data
      .split(',')[0]
      .split(':')[1]
      .split(';')[0]
    var ia = new Uint8Array(byteString.length)
    for (var i = 0; i < byteString.length; i++) {
      ia[i] = byteString.charCodeAt(i)
    }
    return new Blob([ia], {
      type: mimeString
    })
  }
  const canvas = document.createElement('canvas')

  const dom = document.getElementById(id)

  if (dom.nodeName === 'VIDEO') {
    canvas.width = dom.videoWidth * 1
    canvas.height = dom.videoHeight * 1
  } else {
    canvas.width = dom.clientWidth
    canvas.height = dom.clientHeight
  }

  canvas.getContext('2d').drawImage(dom, 0, 0, canvas.width, canvas.height)
  let dataUrl = ''
  try {
    dataUrl = canvas.toDataURL('image/png')
  } catch (error) {
    // alert('截取封面失败请手动上传\n' + error)
    dataUrl = ''
  }

  return dataUrl
}

// 将base64转换成file对象
export function dataURLtoFile(dataurl, filename = 'file') {
  const arr = dataurl.split(',')
  const mime = arr[0].match(/:(.*?);/)[1]
  const suffix = mime.split('/')[1]
  const bstr = atob(arr[1])
  let n = bstr.length
  const u8arr = new Uint8Array(n)
  while (n--) {
    u8arr[n] = bstr.charCodeAt(n)
  }
  return new File([u8arr], `${filename}.${suffix}`, { type: mime })
}
export function imgToBase64(url) {
  return new Promise((resolve, reject) => {
    let canvas = document.createElement('canvas')
    const ctx = canvas.getContext('2d')
    const img = new Image()
    img.src = url

    img.crossOrigin = 'Anonymous'
    img.onload = function() {
      canvas.height = img.height
      canvas.width = img.width
      ctx.drawImage(img, 0, 0)
      var dataURL = canvas.toDataURL('image/png')
      resolve(dataURL)
      canvas = null
    }
  }).then()
}

export function cacheExternalImage(url) {
  var img = new Image()
  var src = url
  var cvs = document.createElement('canvas')
  var ctx = cvs.getContext('2d')
  img.crossOrigin = 'Anonymous'
  img.onload = function() {
    // ctx.drawImage( img, 0, 0 );
  }
  img.src = src
  if (img.complete || img.complete === undefined) {
    img.src =
      'data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw=='
    img.src = src
  }
  return img
}

// 高斯模糊
export function gaussBlur(ctx, left, top, width, height) {
  var imgData = ctx.getImageData(left, top, width, height)

  var pixes = imgData.data
  // var width = imgData.width
  // var height = imgData.height
  var gaussMatrix = []
  var gaussSum = 0
  var x
  var y
  var r
  var g
  var b
  var a
  var i
  var j
  var k
  var len

  var radius = 10
  var sigma = 5

  a = 1 / (Math.sqrt(2 * Math.PI) * sigma)
  b = -1 / (2 * sigma * sigma)
  // 生成高斯矩阵
  for (i = 0, x = -radius; x <= radius; x++, i++) {
    g = a * Math.exp(b * x * x)
    gaussMatrix[i] = g
    gaussSum += g
  }

  // 归一化, 保证高斯矩阵的值在[0,1]之间
  for (i = 0, len = gaussMatrix.length; i < len; i++) {
    gaussMatrix[i] /= gaussSum
  }
  // x 方向一维高斯运算
  for (y = 0; y < height; y++) {
    for (x = 0; x < width; x++) {
      r = g = b = a = 0
      gaussSum = 0
      for (j = -radius; j <= radius; j++) {
        k = x + j
        if (k >= 0 && k < width) {
          // 确保 k 没超出 x 的范围
          // r,g,b,a 四个一组
          i = (y * width + k) * 4
          r += pixes[i] * gaussMatrix[j + radius]
          g += pixes[i + 1] * gaussMatrix[j + radius]
          b += pixes[i + 2] * gaussMatrix[j + radius]
          // a += pixes[i + 3] * gaussMatrix[j];
          gaussSum += gaussMatrix[j + radius]
        }
      }
      i = (y * width + x) * 4
      // 除以 gaussSum 是为了消除处于边缘的像素, 高斯运算不足的问题
      // console.log(gaussSum)
      pixes[i] = r / gaussSum
      pixes[i + 1] = g / gaussSum
      pixes[i + 2] = b / gaussSum
      // pixes[i + 3] = a ;
    }
  }
  // y 方向一维高斯运算
  for (x = 0; x < width; x++) {
    for (y = 0; y < height; y++) {
      r = g = b = a = 0
      gaussSum = 0
      for (j = -radius; j <= radius; j++) {
        k = y + j
        if (k >= 0 && k < height) {
          // 确保 k 没超出 y 的范围
          i = (k * width + x) * 4
          r += pixes[i] * gaussMatrix[j + radius]
          g += pixes[i + 1] * gaussMatrix[j + radius]
          b += pixes[i + 2] * gaussMatrix[j + radius]
          // a += pixes[i + 3] * gaussMatrix[j];
          gaussSum += gaussMatrix[j + radius]
        }
      }
      i = (y * width + x) * 4
      pixes[i] = r / gaussSum
      pixes[i + 1] = g / gaussSum
      pixes[i + 2] = b / gaussSum
    }
  }
  ctx.putImageData(imgData, left, top)
}

export function mosaic(ctx, left, top, width, height) {
  console.log('马赛克')
  var oImg = ctx.getImageData(left, top, width, height)

  var w = oImg.width
  var h = oImg.height
  // 马赛克的程度，数字越大越模糊
  var num = 8
  // 等分画布
  var stepW = w / num
  var stepH = h / num

  // 这里是循环画布的像素点
  for (var i = 0; i < stepH; i++) {
    for (var j = 0; j < stepW; j++) {
      // 获取一个小方格 第一格的颜色 最好的效果是 求平均值
      var color = getXY(oImg, j * num, i * num)
      // 这里是循环小方格的像素点，
      for (var k = 0; k < num; k++) {
        for (var l = 0; l < num; l++) {
          // 设置小方格的颜色
          setXY(oImg, j * num + l, i * num + k, color)
        }
      }
    }
  }
  ctx.putImageData(oImg, left, top)
}

function getXY(obj, x, y) {
  var w = obj.width
  var h = obj.height
  var d = obj.data

  var color = []
  color[0] = obj.data[4 * (y * w + x)]
  color[1] = obj.data[4 * (y * w + x) + 1]
  color[2] = obj.data[4 * (y * w + x) + 2]
  color[3] = obj.data[4 * (y * w + x) + 3]
  return color
}

function setXY(obj, x, y, color) {
  var w = obj.width
  var h = obj.height
  var d = obj.data
  obj.data[4 * (y * w + x)] = color[0]
  obj.data[4 * (y * w + x) + 1] = color[1]
  obj.data[4 * (y * w + x) + 2] = color[2]
  obj.data[4 * (y * w + x) + 3] = color[3]
}

function getAverageColor(imageData, opt) {
  var options = {
    calculateAlpha:
      opt && typeof opt.calculateAlpha === 'boolean' ? opt.calculateAlpha : true
  }
  var sum = { r: 0, g: 0, b: 0 }
  var data = imageData.data ? imageData.data : imageData
  var length = data.length
  var totalPixel = length / 4
  var opacity = 1

  for (var i = 0; i < length; i += 4) {
    if (options.calculateAlpha) {
      opacity = data[i + 3] / 255
    }
    sum.r += data[i] * opacity
    sum.g += data[i + 1] * opacity
    sum.b += data[i + 2] * opacity
  }
  return {
    r: Math.round(sum.r / totalPixel),
    g: Math.round(sum.g / totalPixel),
    b: Math.round(sum.b / totalPixel)
  }
}
