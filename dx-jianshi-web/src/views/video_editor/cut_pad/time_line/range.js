var body_width_total_ms = 0 // 屏幕宽度 对应的 总毫秒数
var base_block_times = 0

var rangeChange = function rangeChange(range, callback) {
  if (!range) {
    range = 0
  }

  switch (parseInt(range)) {
    case 0:
      body_width_total_ms = 840000
      base_block_times = 24000
      break
    case 1:
      body_width_total_ms = 420000
      base_block_times = 12000
      break
    case 2:
      body_width_total_ms = 180000
      base_block_times = 30000
      break
    case 3:
      body_width_total_ms = 120000
      base_block_times = 30000
      break
    case 4:
      body_width_total_ms = 60000
      base_block_times = 4800
      break
    case 5:
      body_width_total_ms = 30000
      base_block_times = 4500
      break
    case 6:
      body_width_total_ms = 15000
      base_block_times = 2400
      break
  }

  if (callback && typeof callback === 'function') {
    callback()
  }

  return {
    body_width_total_ms: body_width_total_ms,
    base_block_times: base_block_times
  }
}

export default rangeChange
