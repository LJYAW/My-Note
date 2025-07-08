/*
 * @Author: your name
 * @Date: 2021-02-22 19:58:59
 * @LastEditTime: 2021-06-08 19:36:44
 * @LastEditors: your name
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin-template/src/utils/validate.js
 */
/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}
