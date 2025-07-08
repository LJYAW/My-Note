/*
 * @Author: zzx
 * @Date: 2020-08-05 15:24:55
 * @LastEditTime: 2021-02-24 15:01:50
 * @FilePath: /video_edit/src/components/time_line/src/range.js
 */
var base_block_times = 0
var block_width = 80

var rangeChange = function rangeChange(range = 4, callback) {
  switch (parseInt(range)) {
    case 0:
      // body_width_total_ms = 840000
      base_block_times = 60000
      block_width = 80
      break
    case 1:
      // body_width_total_ms = 420000
      base_block_times = 50000
      block_width = 90
      break
    case 2:
      // body_width_total_ms = 180000
      base_block_times = 40000
      block_width = 100
      break
    case 3:
      // body_width_total_ms = 1200000
      base_block_times = 30000
      block_width = 110
      break
    case 4:
      // body_width_total_ms = 60000
      base_block_times = 20000
      block_width = 120
      break
    case 5:
      // body_width_total_ms = 30000
      base_block_times = 10000
      block_width = 130
      break
    case 6:
      // body_width_total_ms = 15000
      base_block_times = 5000
      block_width = 80
      break
    case 7:
      // 一个小时的宽度 特殊定一小时
      base_block_times = 60000
      block_width = 80
      break
  }

  if (callback && typeof callback === 'function') {
    callback()
  }

  return {
    BLOCK_TOTAL_MS: base_block_times,
    BLOCK_WIDTH: block_width
  }
}

export default rangeChange
