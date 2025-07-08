/*
 * @Author: your names
 * @Date: 2021-08-04 16:17:21
 * @LastEditTime: 2021-08-18 15:45:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/user-manage/model/rules.js
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
  names: [{ required: true, message: '请输入操作人员', trigger: 'blur' }],
  passwd: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 8, max: 16, message: '请输入8至16位用户密码', trigger: 'blur' }
  ]
}
