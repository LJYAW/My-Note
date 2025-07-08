/*
 * @Author: your name
 * @Date: 2021-04-12 17:29:22
 * @LastEditTime: 2021-04-19 15:54:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/utils/validate.js
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

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(str) {
  const valid_map = ['admin', 'editor']
  return valid_map.indexOf(str.trim()) >= 0
}

import Schema from 'async-validator'
import { Message } from 'element-ui'
/**
 * @param {Object} rules
 * @param {Object} value
 * @param {Function} cb
 * @returns {Boolean}
 */
export function validatorFn(rules, value, cb = () => {}) {
  // console.log("🚀 ~ file: validate.js ~ line 39 ~ validatorFn ~ value", value);
  // console.log("🚀 ~ file: validate.js ~ line 39 ~ validatorFn ~ rules", rules);
  // console.log(cb);

  const validator = new Schema(rules)
  validator
    .validate(value)
    .then(() => {
      cb()
    })
    .catch(({ errors, fields }) => {
      // console.log("🚀 ~ file: validate.js ~ line 44 ~ validatorFn ~ errors", errors);
      Message({
        message: errors[0].message,
        type: 'error',
        duration: 5 * 1000
      })
    })
}
