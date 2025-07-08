/*
 * @Author: zzx
 * @Date: 2020-09-18 11:11:25
 * @LastEditTime: 2020-09-18 15:16:21
 * @FilePath: /video_edit/src/components/vue-draggable-resizable/utils/fns.js
 */
export function isFunction(func) {
  return typeof func === 'function' || Object.prototype.toString.call(func) === '[object Function]'
}

export function snapToGrid(grid, pendingX, pendingY, scale = 1) {
  const x = Math.round(pendingX / scale / grid[0]) * grid[0]
  const y = Math.round(pendingY / scale / grid[1]) * grid[1]

  return [x, y]
}

export function getSize(el) {
  const rect = el.getBoundingClientRect()

  return [parseInt(rect.width), parseInt(rect.height)]
}

export function computeWidth(parentWidth, left, right) {
  return parentWidth - left - right
}

export function computeHeight(parentHeight, top, bottom) {
  return parentHeight - top - bottom
}

export function restrictToBounds(value, min, max) {
  if (min !== null && value < min) {
    return min
  }

  if (max !== null && max < value) {
    return max
  }

  return value
}
