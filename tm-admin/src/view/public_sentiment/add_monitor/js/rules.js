/*
 * @Author: your name
 * @Date: 2021-04-16 18:58:22
 * @LastEditTime: 2021-04-22 19:37:41
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/public_sentiment/add_monitor/js/ruels.js
 */
export default {
  // 添加频道校验规则
  channel: {
    type: 'string',
    required: true,
    message: '请添加监测频道'
  },
  // 添加重点栏目校验规则
  programRules: {
    programName: {
      type: 'string',
      required: true,
      message: '请选择正确的频道'
    },
    program: {
      type: 'string',
      required: true,
      message: '请输入正确的栏目'
    },
    programDate: {
      type: 'array',
      required: true,
      message: '请选择监测时间段'
    }
  },
  // 添加监测品牌列表校验规则
  addMointorBrand: {
    name: {
      type: 'string',
      required: true,
      message: '请输入公司名称'
    },
    brand: {
      type: 'string',
      required: true,
      message: '请输入品牌名称'
    }
  },
  // 添加监测人物列表校验规则
  addMointorPersonage: {
    name: {
      type: 'string',
      required: true,
      message: '请输入公司名称'
    },
    personage: {
      type: 'string',
      required: true,
      message: '请输入品牌名称'
    }
  }
}
