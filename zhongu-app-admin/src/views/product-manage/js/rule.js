/*
 * @Author: your name
 * @Date: 2021-10-26 14:34:58
 * @LastEditTime: 2021-10-27 19:01:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/product-manage/js/rule.js
 */
export const rules = {
  name: [
    {
      required: true,
      message: '请输入产品名称',
      trigger: 'blur'
    }
  ],
  category_id: [
    {
      required: true,
      message: '请选择产品分类',
      trigger: 'change'
    }
  ],
  inventory: [
    {
      required: true,
      message: '请输入产品库存',
      trigger: 'blur'
    }
  ],
  price: [
    {
      required: true,
      message: '请输入产品价格',
      trigger: 'blur'
    }
  ],
  type: [
    {
      required: true,
      message: '请选择售卖模式',
      trigger: 'change'
    }
  ],
  on_at: [
    {
      required: true,
      message: '请选择上架日期',
      trigger: 'blur'
    }
  ],
  max_price: [
    {
      required: true,
      message: '请输入产品封顶价格',
      trigger: 'blur'
    }
  ],
  end_at: [
    {
      required: true,
      message: '请输入产品售卖时长',
      trigger: 'blur'
    }
  ],

  main_img: [
    {
      required: true,
      message: '请上传产品主图',
      trigger: 'blur'
    }
  ],
  intro: [
    {
      required: true,
      message: '请输入产品简介',
      trigger: 'blur'
    }
  ],
  des: [
    {
      required: true,
      message: '请输入产品描述',
      trigger: 'blur'
    }
  ]
}
export const blindRules = {
  name: [
    {
      required: true,
      message: '请输入盲盒单品名称',
      trigger: 'blur'
    }
  ],
  des: [
    {
      required: true,
      message: '请输入盲盒单品描述',
      trigger: 'blur'
    }
  ],
  img: [
    {
      required: true,
      message: '请上传盲盒单品图',
      trigger: 'blur'
    }
  ]
}
// export default rules
