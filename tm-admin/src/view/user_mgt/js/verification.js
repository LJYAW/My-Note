/*
 * @Author: zll
 * @Date: 2021-01-12 00:14:15
 * @LastEditTime: 2021-06-04 15:31:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/customer/js/verification.js
 */
export default {
  mobile: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    {
      pattern: /^1[3456789]\d{9}$/,
      message: '目前只支持中国大陆的手机号码',
      trigger: 'blur'
    }
  ],
  names: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  passwd: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  Limits: [{ required: true, message: '请选择业务', trigger: 'blur' }]
}
