/*
 * @Author: your name
 * @Date: 2021-08-16 11:34:31
 * @LastEditTime: 2021-08-24 11:24:12
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/user-manage/js/mini.js
 */
import rules from '../model/rules'
import JurisdictionCheck from '../components/jurisdictionCheck.vue'
export default {
  data() {
    return {
      videoMenuShow: false,
      ruleForm: {
        names: '',
        mobile: '',
        passwd: '',
        befor_menu: '',
        after_menu: ''
      },
      // 前端功能
      receptionData: [
        {
          label: '视频库',
          val: 1
        },
        {
          label: '视频工具箱',
          val: 2
        }
      ],
      // 视频工具箱
      videoToolData: [
        {
          label: '简视创作',
          val: 1
        },
        {
          label: '虚拟直播',
          val: 2
        }
      ],
      // 后台功能
      backstageData: [
        {
          label: '视频管理',
          val: 1
        },
        {
          label: '目录管理',
          val: 2
        },
        {
          label: '企业信息',
          val: 3
        },
        {
          label: '购买套餐',
          val: 4
        },
        {
          label: '订单记录',
          val: 5
        },
        {
          label: '操作记录',
          val: 6
        },
        {
          label: '用户管理',
          val: 7
        },
        {
          label: '素材中心',
          val: 8
        },
        {
          label: '账号信息',
          val: 9
        }
      ],
      rules: rules
    }
  },
  created() {},
  mounted() {},
  methods: {
    // 更新数据
    resetData(val) {
      this.videoMenuShow = val
    },
    // 设置菜单数据
    setMenuData() {
      const after_menu = this.$refs.after_menu.TempcheckList.join(',')
      const befor_menu = this.$refs.befor_menu.TempcheckList.join(',')
      const video_menu = this.videoMenuShow ? this.$refs.video_menu.TempcheckList.join(',') : ''
      this.ruleForm.after_menu = after_menu
      this.ruleForm.befor_menu = video_menu ? befor_menu + ',' + video_menu : befor_menu
    },
    // 提交数据
    postData() {
      this.setMenuData() // 设置菜单数据
      this.$post('/user/', this.ruleForm).then(response => {
        const { err, res } = response
        if (err) {
          this.$message({
            message: err.msg,
            type: 'error'
          })
        } else {
          this.$message({
            message: '创建成功',
            type: 'success'
          })
          this.$parent.$parent.$parent.getData()
          this.$emit('close')
        }
      })
    },
    // 创建
    submitForm(formnames) {
      this.$refs[formnames].validate((valid) => {
        if (valid) {
          if (this.id) {
            this.editPostData()
          } else {
            this.postData()
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
    }
  },
  components: { JurisdictionCheck }
}
