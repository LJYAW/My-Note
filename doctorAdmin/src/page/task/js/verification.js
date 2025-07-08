/*
 * @Author: your name
 * @Date: 2021-01-26 19:10:42
 * @LastEditTime: 2021-01-27 16:27:24
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/page/task/js/verification.js
 */
var validDate = (rule, value, callback) => {
    if (!value[0] || !value[1]) {
        callback(new Error("请选择任务时间"))
    } else {
        callback()
    }
}
export default {
    cover_pic: [
        { required: true, message: '请选择封面图', trigger: 'change' },
    ],
    banner: [
        { required: true, message: '请选择banner图', trigger: 'change' },
    ],
    // attach_file: [
    //     { required: true, message: '请选择附件', trigger: 'change' },
    // ],
    title: [
        { required: true, message: '请输入标题', trigger: 'blur' },
    ],
    type: [
        { required: true, message: '请选择任务类型', trigger: 'change' },
    ],
    date: [
        { validator: validDate, required: true, trigger: 'change' },
    ],
}