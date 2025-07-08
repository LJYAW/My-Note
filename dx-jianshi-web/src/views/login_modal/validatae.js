/*
 * @Author: zzx
 * @Date: 2020-06-11 10:54:03
 * @LastEditTime: 2020-12-05 14:33:03
 * @FilePath: /zhijian_web/src/views/login_modal/validatae.js
 */
var validatelink = (rule, value, callback) => {
  if (value === '') {
    callback(new Error('请输入链接'))
  } else {
    const reg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/
    if (!reg.test(value)) {
      callback(new Error('请输入链接'))
    } else {
      callback()
    }
  }
}
// var reg = /^\w+@[a-zA-Z0-9]{2,10}(?:\.[a-z]{2,4}){1,3}$/

export default {
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' },
    { min: 2, max: 15, message: '长度在 2 到 15 个字符', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 10, message: '长度在 6 到 10 个字符', trigger: 'blur' }
  ],
  ver_code: [{ required: true, message: '请输入验证码', trigger: 'blur' }],
  invitation_code: [{ required: true, message: ' ', trigger: 'blur' }],
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    {
      pattern: /^1[3456789]\d{9}$/,
      message: '目前只支持中国大陆的手机号码',
      trigger: 'blur'
    }
  ],
  link: [{ required: true, message: '请输入链接', trigger: 'blur' }],
  ver_code: [{ required: true, message: '请输入验证码', trigger: 'blur' }]
}
