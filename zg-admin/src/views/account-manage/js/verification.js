/*
 * @Author: zll
 * @Date: 2021-01-12 00:14:15
 * @LastEditTime: 2021-03-18 16:10:09
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/customer/js/verification.js
 */
export default {
  username: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    {
      pattern: /^1[3456789]\d{9}$/,
      message: '目前只支持中国大陆的手机号码',
      trigger: 'blur'
    }
  ],
  nickname: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  work: [{ required: true, message: '请选择业务', trigger: 'blur' }]
}
