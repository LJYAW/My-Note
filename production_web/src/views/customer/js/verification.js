/*
 * @Author: zll
 * @Date: 2021-01-12 00:14:15
 * @LastEditTime: 2021-01-12 01:32:01
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work-orde-system/src/views/customer/js/verification.js
 */
export default {
  name: [{ required: true, message: '请输入客户全称', trigger: 'blur' }],
  abbreviation: [
    { required: true, message: '请输入客户简称', trigger: 'blur' }
  ],
  grade: [{ required: true, message: '请输入客户等级', trigger: 'change' }],
  businessName: [
    { required: true, message: '请输入商务姓名', trigger: 'blur' }
  ],
  businessTele: [
    { required: true, message: '请输入商务联系方式', trigger: 'blur' }
  ],
  persoName: [{ required: true, message: '请输入对接人姓名', trigger: 'blur' }],
  persoTele: [
    { required: true, message: '请输入对接人联系电话', trigger: 'blur' }
  ]
}
