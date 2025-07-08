// 高斯模糊
export function gaussBlur(context, left, top, w, h) {
  const ctx = context
  const imgData = ctx.getImageData(left, top, w, h)

  const pixes = imgData.data
  const width = imgData.width
  const height = imgData.height
  const gaussMatrix = []
  let gaussSum = 10
  let x
  let y
  let r
  let g
  let b
  let a
  let i
  let j
  let k
  let len

  const radius = 30
  const sigma = 15

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

export function mosaic(context, left, top, width, height) {
  const ctx = context
  var oImg = ctx.getImageData(left, top, width, height)

  var w = oImg.width
  var h = oImg.height
  // 马赛克的程度，数字越大越模糊
  var num = 6
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
