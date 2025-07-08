/*
 * @Author: zll
 * @Date: 2021-01-12 00:14:15
 * @LastEditTime: 2021-01-18 21:34:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/customer/js/verification.js
 */
export default {
  phone: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    {
      pattern: /^1[3456789]\d{9}$/,
      message: '目前只支持中国大陆的手机号码',
      trigger: 'blur'
    }
  ],
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  team_id: [{ required: true, message: '请选择团队名称', trigger: 'change' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
  work: [{ required: true, message: '请选择业务', trigger: 'blur' }]
  //   adminPerson: [{ required: true, message: '请选择管理员', trigger: 'blur' }]
}
