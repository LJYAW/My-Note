/*
 * @Author: zzx
 * @Date: 2020-09-25 17:02:40
 * @LastEditTime: 2020-09-25 17:03:19
 * @FilePath: /video_edit/src/utils/guid.js
 */
export default function guid() {
  return Number(
    Math.random()
      .toString()
      .substr(3, 3) + Date.now()
  ).toString(36)
}
