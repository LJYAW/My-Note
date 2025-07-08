/*
 * @Author: your name
 * @Date: 2021-10-27 11:37:26
 * @LastEditTime: 2021-10-27 11:47:23
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/classify-manage/js/rule.js
 */
const rules = {
  name: [
    {
      required: true,
      message: '请输入分类名称',
      trigger: 'blur'
    }
  ],
  intro: [
    {
      required: true,
      message: '请输入分类简介',
      trigger: 'change'
    }
  ],
  small_img: [
    {
      required: true,
      message: '请上传分类主形象图',
      trigger: 'blur'
    }
  ],
  main_img: [
    {
      required: true,
      message: '请上传分类主图',
      trigger: 'blur'
    }
  ],
  banner_img: [
    {
      required: true,
      message: '请上传分类banner图',
      trigger: 'blur'
    }
  ],
  des: [
    {
      required: true,
      message: '请输入分类描述',
      trigger: 'blur'
    }
  ]
}
export default rules
