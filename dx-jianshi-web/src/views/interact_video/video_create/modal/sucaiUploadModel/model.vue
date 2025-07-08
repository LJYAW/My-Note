<!--
 * @Author: wxh
 * @Date: 2020-09-28 11:23:24
 * @LastEditTime: 2020-12-01 11:19:06
 * @FilePath: /zhijian_web/src/views/interact_video/video_create/modal/sucaiUploadModel/model.vue
-->
<template>
  <div>
    <model :id="'sucaiEdit'" ref="sucaiEdit" @close="close">
      <div slot="title">编辑素材</div>
      <div slot="body" class="sucaiEdit_body">
        <div
          :class="['box mr-10px', { active: my_image_index == index }]"
          @click="addItem(item, index, 2)"
          v-for="(item, index) in img_video_list"
          :key="index">
          <div v-if="item.type == '上传中'" style="margin-top: 37px" class="px-10px">
            <!-- <p>上传中...</p> -->
            <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
          </div>

          <div v-else class="w-100 h-100">
            <img class :src="item.resource_url" v-if="item.type == 'image'" />
            <video
              v-if="item.type == 'video'"
              controls="controls"
              style="width: 100%; height: 100%"
              :src="item.resource_url"></video>

            <label :class="['el-upload-list__item-status-label']">
              <i class="iconfont iconqueding d-block"></i>
            </label>

            <a class="delete-icon" @click="deleteRsource(item, index)">
              <i class="iconfont fc-white iconshanchuicon hove-c"></i>
            </a>
          </div>
        </div>
      </div>
      <div slot="foot" class="sucaiEdit_foot d-flex">
        <upload
          button_name="上传本地素材"
          file_type="image/png, image/jpeg, image/gif, image/jpg, video/mp4"
          class="ml-10px mr-24px"
          @getFileUrl="getFileUrl" />
        <el-button type="primary" size="medium" round @click="determine_btn()">确定</el-button>
      </div>
    </model>
  </div>
</template>

<script>
// import cBtn from './custom_btn'
export default {
  name: 'sucaiEdit',
  props: [],
  data() {
    return {
      page: 1,
      limit: 6,
      complete: 0, // 文件上传进度
      my_image_index: null,
      img_video_list: [],
      select_item: {}
    }
  },
  components: {
    // cBtn,
  },

  computed: {},

  watch: {},

  methods: {
    // 获取用户本地素材列表
    getMyFileList() {
      this.$get('/intelligent-creation/user-resource-list', { params: { page: this.page, limit: this.limit } }).then(res => {
        if (res.data.code === '0000') {
          this.img_video_list = this.img_video_list.concat(res.data.data.list)
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    // 删除本地素材
    deleteRsource(item) {
      this.$deleteRun('/intelligent-creation/delete-user-resource', { data: { id: item.id } }).then(res => {
        if (res.data.code == '0000') {
          this.$alertMsg('删除成功')
          this.page = 1
          this.img_video_list = []
          this.getMyFileList()
        } else {
          this.$alertMsg(res.data.msg)
        }
      })
    },
    getFileUrl(url, file) {
      var fileType = file.type

      var type = fileType.split('/')[0]

      var obj = {
        type: type,
        url: url
      }
      // this.img_video_list.unshift(obj)
      this.uploadMyFile(obj, file)
    },
    uploadMyFile(obj, file) {
      this.complete = 0
      var formData = new FormData()
      formData.append('type', obj.type)
      formData.append('resource', file)
      formData.append('duration', '123')

      this.img_video_list.unshift({
        type: '上传中'
      })

      this.$axios
        .post('/intelligent-creation/upload-user-resource', formData, {
          onUploadProgress: progressEvent => {
            this.complete = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          }
        })
        .then(res => {
          if (res.data.code === '0000') {
            this.img_video_list = []
            this.page = 1
            this.getMyFileList()
          } else {
            this.$alertMsg('上传失败，请重新上传')
          }
        })
        .catch(err => {
          this.$alertMsg('上传失败，请重新上传')
          console.error(err)
        })
    },
    addItem(item, index, type) {
      console.log('addItem -> item', item)
      if (item.imgError || item.type == '上传中') {
        return
      }

      this.my_image_index = null
      this.ai_image_index = null

      // 选择用户素材
      if (type == 2) {
        this.my_image_index = index
        this.select_item = this.img_video_list[index]
      }
    },
    close() {
      this.$emit('Modalclose', 'modalName')
    },
    determine_btn() {
      if (this.select_item.type) {
        this.$emit('addItem', this.select_item)
      }
      this.close()
    }
  },

  created() {
    this.getMyFileList()
  },

  mounted() {}
}
</script>

<style scoped lang="scss">
@import '../../css/sucaiEditModal.scss';
</style>
