<!--
 * @Author: your name
 * @Date: 2021-08-05 14:51:56
 * @LastEditTime: 2021-09-24 10:57:36
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/directory-manage/components/fileLIst.vue
-->
<template>
  <div class="file-list">
    <div v-show="markShow" class="mask-box" :class="markShow?'ActiveZindex':''" @click="fileCliak()" />
    <ul class="file-ul">
      <li v-for="(item,index) in tempFileData" :key="index" :class="tabIndex===index?'activeClass':''" @click.self="tabClick(item,index)">
        <svg-icon icon-class="catalogue" class="icon-file" @click="tabClick(item,index)" />
        <div class="directory-box">
          <div class="add-directory">
            <el-popover
              :key="item.id"
              placement="bottom"
              trigger="hover"
              popper-class="popover-box"
            >
              <ul class="popover-list">
                <li
                  v-for="(k,i) in fileList"
                  :key="i"
                  :class="[popoverIndex===i?k.label==='删除目录'?'detelteClass':'activeClass':'']"
                  @click.stop="popoverClick(item,index,k,i)"
                >
                  {{ k.label }}
                </li>
              </ul>
              <i slot="reference" class="el-icon-more icon-morre" />
            </el-popover>
          </div>
          <div class="message-box">
            <el-input v-if="item.show" v-model="item.Filename" class="file-input" />
            <div v-else>
              <div class="file-name" @click="tabClick(item,index)">{{ item.names }}</div>
              <div class="file-size" @click="tabClick(item,index)">{{ item.videocounts }}个视频</div>
            </div>
          </div>
        </div>
      </li>
    </ul>
    <slot name="page" />
    <!-- 弹窗 -->
    <BaseDialog
      :title="dialogTit"
      :show.sync="dialogVisible"
      :width="width"
      class="dialog"
    >
      <compontents
        :is="dialogCompomtentsName"
        :id="id"
        @showDialog="showDialog"
        @close="close"
      />
    </BaseDialog>
  </div>
</template>

<script>
import Dateils from '../model/dateils'
import PersonAdministration from '../model/personAdministration'
import MoveVideo from '../model/moveVideo'
export default {
  components: {
    Dateils, PersonAdministration, MoveVideo
  },
  props: {
    fileData: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      markShow: false,
      width: '',
      dialogTit: '',
      tabIndex: '',
      popoverIndex: '',
      dialogCompomtentsName: '',
      dialogVisible: false,
      id: '',
      tempFileData: [],
      fileList: [
        { label: '重命名' },
        // { label: '详细信息' },
        { label: '移动视频' },
        // { label: '成员管理' },
        { label: '删除目录' }
      ]
    }
  },
  computed: {
  },
  watch: {
    // 监听数据是否存在
    fileData: {
      handler(newName, oldName) {
        if (newName) {
          this.getTempFileData(newName)
        }
      },
      immediate: true
    }
  },
  created() {
  },
  mounted() {
    this.onkeydownClick()
  },
  destroyed() {
    // 移除监听回车事件
    var _this = this
    document.removeEventListener('keydown', _this.onkeydownClick)
  },
  methods: {
    // 回车重命名
    onkeydownClick() {
      const _this = this
      document.onkeydown = function(e) {
        var keycode = document.all ? event.keyCode : e.which
        if (keycode === 13) {
          if (_this.markShow) {
            _this.setFileData()
          }
        }
      }
    },
    // 元素最外层点击空白隐藏元素
    fileCliak() {
      this.setFileData()
    },
    // 设置重命名信息
    setFileData() {
      const num = JSON.parse(JSON.stringify(this.tabIndex)).toString()
      const data = this.tempFileData[this.tabIndex]
      if (num && data.show && this.checkFun(data)) {
        if (data.Filename !== data.names) {
          this.renameData(data)
        }
        this.tabIndex = ''
        this.markShow = false
        data.show = false
      }
    },
    // 判断条件
    checkFun(item) {
      if (!item.Filename) {
        this.$message({
          message: '请填写目录名称',
          type: 'warning'
        })
        return false
      }
      return true
    },
    // 转换数据
    getTempFileData(data) {
      const arr = JSON.parse(JSON.stringify(data))
      arr.forEach(item => {
        item.show = false
        item.Filename = ''
      })
      this.tempFileData = arr
    },
    // 展示dialog
    showDialog(width, title, name, id) {
      this.width = width + 'px'
      this.dialogTit = title
      this.dialogCompomtentsName = name
      this.dialogVisible = true
      this.id = id
    },
    // 关闭
    close(val) {
      this.id = ''
      this.dialogVisible = false
    },
    // 重命名
    rename(item, index) {
      item.show = true
      this.tabIndex = index
      this.markShow = true
      item.Filename = item.names
    },
    renameData(item) {
      const params = {
        names: item.Filename
      }
      this.$put(`/dirs/${item.id}`, params).then(response => {
        const { err, res } = response
        if (err) {
          this.$message({
            message: err.msg,
            type: 'error'
          })
        } else {
          this.$message({
            message: '修改成功',
            type: 'success'
          })
          this.$bus.$emit('resetData')
        }
      })
    },
    // 删除目录
    deleteData(item) {
      this.$confirm('是否删除?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.deleteDirect(item)
      }).catch(() => {
      })
    },
    async deleteDirect(item) {
      const { err, res } = await this.$deleteRun(`/dirs/${item.id}`)
      if (err) {
        this.$message({
          message: err.msg,
          type: 'error'
        })
        return
      }
      this.$message.success('删除成功')
      this.$bus.$emit('resetData')
    },
    // 点击目录
    tabClick(item, index) {
      console.log(item)
      this.$router.push({
        path: '/manage-center/videos-manage',
        query: {
          id: item.id,
          names: item.names
        }
      })

      this.tabIndex = index
    },
    // 点击popover弹框
    popoverClick(item, index, k, i) {
      this.popoverIndex = i
      this.switchData(k, item, index)
    },
    // switch数据
    switchData(k, item, index) {
      switch (k.label) {
        case '重命名':
          this.rename(item, index)
          break
        // case '详细信息':
        //   this.showDialog(294, '云媒资项目文件', 'Dateils', item.id)
        //   break
        case '移动视频':
          this.showDialog(294, '移动视频', 'MoveVideo', item.id)
          break
        case '成员管理':
          this.showDialog(558, '成员管理', 'PersonAdministration', item.id)
          break
        default:
          this.deleteData(item)
          break
      }
    }
  }
}
</script>

<style scoped lang="scss">

</style>
