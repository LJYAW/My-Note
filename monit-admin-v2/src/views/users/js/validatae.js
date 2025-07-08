/*
 * @Author: your name
 * @Date: 2021-07-14 15:14:24
 * @LastEditTime: 2021-07-14 15:50:20
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/users/js/validatae.js
 */
export default {
  company: [
    { required: true, message: '请输入公司名称', trigger: 'blur' },
    { min: 12, message: '最低为12个字符', trigger: 'blur' }
  ],
  names: [
    { required: true, message: '请输入账号信息', trigger: 'blur' },
    { min: 8, message: '最低8个字符', trigger: 'blur' }
  ],
  create_time: [{ required: true, message: '请选择开设时间', trigger: 'change' }],
  endtime: [{ required: true, message: '请选择有效时间', trigger: 'change' }],
  saleuser: [{ required: true, message: '请输入销售人员', trigger: 'blur' }],
  handsflag: [{ required: true, message: '请选择是否加入人工判断', trigger: 'change' }],
  status: [{ required: true, message: '请选择用户状态', trigger: 'change' }],
  type: [{ required: true, message: '请选择是否开启管理员权限', trigger: 'change' }],
  channelsuuids: [{ required: true, message: '请开通频道', trigger: 'blur' }],
  emails: [{ required: true, message: '请输入邮箱', trigger: 'change' }],
  mobile: [
    { required: true, message: '请输入手机号', trigger: 'blur' },
    {
      pattern: /^1[3456789]\d{9}$/,
      message: '目前只支持中国大陆的手机号码',
      trigger: 'blur'
    }
  ],
  passwd: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, message: '最低为6个字符', trigger: 'blur' }
  ]
}
