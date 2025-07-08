/*
 * @Author: zzx
 * @Date: 2020-08-05 15:24:55
 * @LastEditTime: 2021-08-12 16:00:31
 * @FilePath: /video_edit/src/components/time_line/src/range.js
 */
const rangeArr = [
  { base_block_times: 12000, block_width: 12 },
  { base_block_times: 8000, block_width: 14 },
  { base_block_times: 6000, block_width: 12 },
  { base_block_times: 2000, block_width: 14 },
  { base_block_times: 1000, block_width: 12 },
  { base_block_times: 400, block_width: 14 },
  { base_block_times: 200, block_width: 16 }
]

var range = function rangeChange(range = 4, callback) {
  return {
    BLOCK_TOTAL_MS: rangeArr[range] ? (rangeArr[range].base_block_times) : 2000,
    BLOCK_WIDTH: rangeArr[range] ? (rangeArr[range].block_width) : 12
  }
}

export { range, rangeArr }
