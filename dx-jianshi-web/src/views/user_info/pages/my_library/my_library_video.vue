<!--
 * @Author: your name
 * @Date: 2020-11-17 15:47:29
 * @LastEditTime: 2020-12-10 16:24:47
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/user_info/pages/my_library/my_library_video.vue
-->
<template>
  <div class="content">
    <loading v-if="status_loading" />
    <div v-else>
      <el-button round class='upload-btn mb-24px' @click.native='uploadBtn'>+上传素材</el-button>
      <cardList :list='list' v-if='list.length'>
        <template slot='progress'>
          <el-progress :text-inside="true" :stroke-width="13" :percentage="complete"></el-progress>
        </template>
        <template #img="slotProps">
          <img :src="slotProps.item.cover_pic"
            :alt="slotProps.item.title">
          <span>{{slotProps.item.duration_str}}</span>
          <div class="icon-wrap d-flex justify-content-between w-100">
            <a @click.stop="deleteItem(slotProps.item)"
              class="align-self-end ml-auto">
              <i class="iconfont icondelect fc-white"></i>
            </a>
          </div>
          <div class='w-100 h-100 bofang d-flex justify-content-center align-items-center' @click="handerClick(slotProps.item)">
            <vsvg icon='iconbofanganniu' class='iconfont fz-40px' />
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
    <showVideoM @Modalclose="Modalclose"
      v-if="modaName =='showVideoM'"
      :video_details="video_details"
      :video_options="video_options" />
    <updateTitle @Modalclose="Modalclose"
      v-if="modaName =='updateTitle'" :updateItem='update_item' />
  </div>
</template>

<script>
import showVideoM from '@/components/show_video_m'
import cardList from '@/components/card_list/index'
import uploadM from '../../components/upload_m'
import updateTitle from '../../components/update_title'
import { common } from '../../js/common'
export default {
  props: ['activeId'],
  mixins: [common],
  data() {
    return {
      modaName: '',
      video_options: {},
      video_details: {},
      update_item: {}
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
      if (this.activeId == 'video') {
        this.getSearch()
      } else {
        this.getVideoOptions()
      }
    },
    handerClick(item) {
      this.video_options = {
        width: '800',
        height: '450',
        poster: item.cover_pic,
        autoplay: false,
        src: item.resource_url
      }
      this.video_details = item
      this.modaName = 'showVideoM'
      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showVideoM')
      })
    },
    Modalclose() {
      this.video_options.src = ''
      this.video_details = {}
      this.modaName = ''
    },
    uploadMyFile(obj) {
      this.complete = 0
      var formData = new FormData()
      formData.append('type', obj.type)
      formData.append('title', obj.title)
      formData.append('resource', obj.file)
      if (this.activeId == 'video_begin' || this.activeId == 'video_end') {
        formData.append('tag', this.activeId)
      }
      formData.append('duration', obj.duration || '123')
      this.list.unshift({
        type: '上传中'
      })
      this.uploadResource(formData)
    },
    uploadBtn() {
      this.$layer.iframe({
        content: {
          content: uploadM,
          parent: this,
          data: {
            fileType: 'video/mp4'
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
    }
  },
  components: { cardList, showVideoM, updateTitle },
  created() {
    this.getData()
  },
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
