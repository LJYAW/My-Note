/*
 * @Author: your name
 * @Date: 2020-11-18 15:27:22
 * @LastEditTime: 2020-12-10 17:00:16
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/user_info/js/common.js
 */
export const common = {
  data() {
    return {
      page: 1,
      limit: 12,
      file_type: '',
      total: 0,
      currentPage: 0,
      list: [],
      status_loading: false,
      complete: 0,
    }
  },
  methods: {
    getSearch() {
      let params = {
        type: this.activeId,
        page: this.page,
        limit: this.limit,
      }
      this.status_loading = true
      this.finished_status = true
      this.$get('/intelligent-creation/user-resource-list', { params: params }).then(res => {
        if (res.data.code == '0000') {
          var data = res.data.data
          this.list = data.list
          this.total = data.total
          this.currentPage = Number(data.page)
        } else {
          this.$alertMsg(res.data.msg)
        }
        this.status_loading = false
      })
    },
    getVideoOptions() {
      let params = {
        tag: this.activeId,
        page: this.page,
        limit: this.limit,
      }
      this.status_loading = true
      this.finished_status = true
      this.$get('/intelligent-creation/user-resource-list', { params: params }).then(res => {
        if (res.data.code == '0000') {
          var data = res.data.data
          this.list = data.list[this.activeId] || []
          this.total = data.total
          this.currentPage = Number(data.page)
        } else {
          this.$alertMsg(res.data.msg)
        }
        this.status_loading = false
      })
    },
    uploadResource(formData) {
      this.$axios
        .post('/intelligent-creation/upload-user-resource', formData, {
          onUploadProgress: progressEvent => {
            this.complete = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          },
        })
        .then(res => {
          if (res.data.code === '0000') {
            this.getData()
          } else {
            this.$alertMsg('上传失败，请重新上传')
          }
        })
        .catch(function (err) {
          console.error(err)
        })
    },
    // 删除素材
    deleteItem(item) {
      this.$confirm('此操作将永久删除该文件, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning',
      })
        .then(() => {
          // 删除本地素材
          this.$deleteRun('/intelligent-creation/delete-user-resource', {
            data: { id: item.id },
          }).then(res => {
            if (res.data.code == '0000') {
              this.$alertMsg('删除成功')
              this.getData()
            } else {
              this.$alertMsg(res.data.msg)
            }
          })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除',
          })
        })
    },
    handleCurrentChange(val) {
      this.currentPage = val
      this.page = val
      this.getData()
    },
  },
}
