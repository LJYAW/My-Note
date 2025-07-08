<!--
 * @Author: your name
 * @Date: 2020-11-17 16:20:50
 * @LastEditTime: 2020-12-10 16:38:32
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/user_info/pages/my_library/my_library_image.vue
-->
<template>
  <div class="content">
    <loading v-if="status_loading" />
    <div v-else>
      <el-button round class='upload-btn mb-24px' @click.native='uploadBtn'>+上传素材</el-button>
      <cardList :list='list' @handerClick="handerClick" v-if='list.length'>
        <template #progress="slotProps">
          <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
        </template>
        <template #img="slotProps">
          <img :src="slotProps.item.resource_url" :alt="slotProps.item.title">
          <div class="icon-wrap d-flex justify-content-between w-100">
            <a @click.stop="deleteItem(slotProps.item)"
              class="align-self-end ml-auto">
              <i class="iconfont icondelect fc-white"></i>
            </a>
          </div>
        </template>
        <template #title="slotProps">
          <p class="title" @click="updateTitle(slotProps.item)">{{slotProps.item.title}}</p>
        </template>
        <template #details="slotProps">
          <div class='d-flex justify-content-between'>
            <p>{{slotProps.item.file_size}}M</p>
            <p>{{slotProps.item.resolution}}</p>
          </div>
        </template>
      </cardList>
      <div class='no-data' v-else>
        <img src="../../../../assets/images/user_info/no_content.png" alt="">
        <p class='text-center'>暂无内容</p>
      </div>
    </div>
    <div class="mb-50px d-flex justify-content-end" v-if='total>limit'>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="limit"
        layout="prev, pager, next, jumper"
        :total="total">
      </el-pagination>
    </div>
    <updateTitle @Modalclose="Modalclose"
      v-if="modaName =='updateTitle'" :updateItem='update_item' />
  </div>
</template>

<script>
import cardList from '@/components/card_list/index'
import { common } from '../../js/common'
import uploadM from '../../components/upload_m'
import updateTitle from '../../components/update_title'
export default {
  props: ['activeId'],
  mixins: [common],
  data() {
    return {
      modaName: ''
    }
  },
  computed: {},
  watch: {
    activeId(val) {
      this.currentPage = 1
      this.page = 1
      this.getData()
    }
  },
  methods: {
    getData() {
      if (this.activeId == 'image') {
        this.getSearch()
      } else {
        this.getVideoOptions()
      }
    },
    handerClick(item) {
      return
    },
    uploadMyFile(obj) {
      this.complete = 0
      var formData = new FormData()
      formData.append('type', obj.type)
      formData.append('title', obj.title)
      formData.append('resource', obj.file)
      if (this.activeId == 'video_logo') {
        formData.append('tag', this.activeId)
      }
      formData.append('duration', obj.duration || '123')
      this.list.unshift({
        type: '上传中'
      })
      this.uploadResource(formData)
    },
    uploadResource(formData) {
      this.$axios
        .post('/intelligent-creation/upload-user-resource', formData, {
          onUploadProgress: progressEvent => {
            this.complete = ((progressEvent.loaded / progressEvent.total) * 100) | 0
          }
        })
        .then(res => {
          if (res.data.code === '0000') {
            this.getData()
          } else {
            this.$alertMsg('上传失败，请重新上传')
          }
        })
        .catch(function(err) {
          console.error(err)
        })
    },
    uploadBtn() {
      this.$layer.iframe({
        content: {
          content: uploadM,
          parent: this,
          data: {
            fileType: 'image/png, image/jpeg, image/gif, image/jpg'
          }
        },
        area: ['800px', '550px'],
        title: '上传素材',
        maxmin: true,
        shade: true, //是否显示遮罩
        shade: false,
        shadeClose: true,
        cancel: () => {
          // 关闭弹窗事件
        }
      })
    },
    updateTitle(item) {
      this.modaName = 'updateTitle'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'updateTitle')
      })
      this.update_item = item
    },
    Modalclose() {
      this.modaName = ''
    }
  },
  components: { cardList, updateTitle },
  created() {
    this.getData()
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
