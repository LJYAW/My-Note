<!--
 * @Author: your name
 * @Date: 2021-07-26 10:44:45
 * @LastEditTime: 2021-10-22 17:31:14
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /jianshi-v2/src/views/intellectCreate/components/videoMaterial/components/materialList/materialImg/index.vue
-->
<template>
  <div ref="materialImg" class="material-img-wrap">
    <transition-group name="list" tag="div">
      <div v-for="(item, index) in edtor_img_list" :key="`img${index}`" class="material-img">
        <div v-if="item.resource_url">
          <base-image v-if="item.type == 'image'" :src="item.resource_url" @error="setImgError(arguments,index)" />
          <VideoCard v-if="item.type == 'video'" :controls="true" :cover-url="item.cover_url" :video-url="item.resource_url" />
          <div class="edit-material-label" @click="editMaterial(item, index)">
            <p class="btn">点击更换视频素材</p>
            <p v-if="item.type==='video'" class="duration">{{ item.video_duration|timesToHMS }}</p>
          </div>
        </div>
        <div v-else class="svg-wrap" @click="editMaterial(item, index)">
          <svg-icon icon-class="jianshi-video" />
          <p>请点击选择素材</p>
        </div>
        <svg-icon
          v-if="img_list.length != text_list.length ||
            (img_list.length == text_list.length && index != 0)"
          icon-class="delete-material"
          class="del-btn"
          @click.native="deleteMetarial(item,index)"
        />
      </div>
    </transition-group>
    <base-dialog :show.sync="dialogVisible" width="1146px" :show-close="false">
      <editMaterialContent @submit="submit" />
    </base-dialog>
  </div>
</template>

<script>
import { mapState } from 'vuex'
import { setRedetDate } from '../../materialList/js/reset-edti'
import * as duration from '../js/set-duration'
import * as edit from '../../materialList/js/edit-pad'
import editMaterialContent from '../../modals/materialModal/index.vue'
import VideoCard from '@/app/jianshi-video/components/VideoCard'
export default {
  components: {
    editMaterialContent,
    VideoCard
  },
  props: {

  },
  data() {
    return {
      edtor_img_list: [{ type: '', duration: 0 }], // 素材列表
      src_list: [],
      dialogVisible: false,
      edtor_img_index: -1
    }
  },
  computed: {
    ...mapState('jianshi', ['img_list', 'materialData', 'text_list', 'subTitle_list', 'srcData'])
  },
  watch: {
    // 重新编辑回显数据
    img_list: {
      handler(val, oldVal) {
        if (val && val.length) this.init(val)
      },
      immediate: true
    },
    // 重新编辑回显数据
    materialData: {
      handler(val, oldVal) {
        if (this.$route.query.reset_edti_id) {
          this.$store.commit('jianshi/SET_IMG_LIST', setRedetDate(val).edtor_img_list)
        }
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    init(val) {
      this.edtor_img_list = JSON.parse(JSON.stringify(val))
    },
    // 编辑素材
    editMaterial(item, index) {
      // if (this.text_list.length === this.img_list.length && item.duration < 3000) {
      //   this.$message({
      //     type: 'warning',
      //     message: '时长必须大于3秒'
      //   })
      //   return
      // }
      this.dialogVisible = true
      this.edtor_img_index = index
      this.getKeyword()
    },
    // 获取弹框关键词
    getKeyword() {
      let text_str = ''
      if (this.edtor_img_index > -1) {
        if (this.text_list.length < 2) {
          text_str = this.img_list[this.edtor_img_index].text_str
        } else {
          text_str = this.text_list[this.edtor_img_index].text
        }
      }
      let arr = []
      if (this.srcData && this.srcData.lexer) {
        arr = this.srcData.lexer.filter(text => {
          return text_str.includes(text.item)
        })
      }
      this.$store.commit('jianshi/SET_KEYWORD', arr)
    },
    close() {
      this.dialogVisible = false
    },
    // 弹窗完成
    submit(obj) {
      if (obj) {
        console.log('🚀 ~ file: index.vue ~ line 137 ~ submit ~ obj', obj)
        const img_list = JSON.parse(JSON.stringify(this.img_list))
        let data = {}; const duration = this.edtor_img_list[this.edtor_img_index].duration
        // 如果是本地素材的话
        if (obj.source === '本地素材') {
          const { file_key, file_url, source, cover_url } = obj
          data = {
            duration,
            video_duration: obj.duration || 0,
            file_key,
            cover_url,
            resource_url: file_url,
            source,
            type: obj.type
          }
        } else if (obj.source === '原文素材') {
        // 视频
          data = {
            resource_url: obj.proxy_url,
            duration,
            source: '原文素材',
            type: obj.type
          }
          // const { id, end_ms, resource_url, start_ms, media_type, uuid, video_hd_str, video_origin, decorateList, videoSize } = obj
          // data = { id, duration, end_ms, resource_url, start_ms, media_type, uuid, video_hd_str, video_origin, decorateList, videoSize }
        }
        img_list[this.edtor_img_index] = data
        this.$store.commit('jianshi/SET_IMG_LIST', img_list)
      }
      this.close()
    },
    // 删除素材
    deleteMetarial(obj, index) {
      if (!obj.duration) {
        this.removeMaterial(index)
        return
      }
      // 链接获取
      if (this.text_list.length === 1) {
        const img_list = JSON.parse(JSON.stringify(this.img_list))
        const text = this.text_list[0].text
        img_list.splice(index, 1)
        img_list.forEach((item, index) => {
          const text_str = edit.getText(text, img_list.length, index)
          item.duration = duration.getMs(text_str)
        })
        this.$store.commit('jianshi/SET_IMG_LIST', img_list)
        return
      }
      const msg = '删除段落后图片与文本将同时删除，是否需要将文本合并至上一段落（合并后视频素材需要重新编辑）'
      this.$confirm(msg, '提示', {
        distinguishCancelAndClose: true,
        showCancelButton: true,
        confirmButtonText: '直接删除',
        cancelButtonText: '合并'
      })
        .then(() => {
          // 直接删除
          this.removeMaterial(index)
        })
        .catch(action => {
          // 合并(区分取消和关闭)
          if (action === 'close') return
          // 合并文本素材
          const text_list = JSON.parse(JSON.stringify(this.text_list))
          const text_str = text_list[index - 1].text + text_list[index].text
          text_list[index - 1].text = text_str
          text_list.splice(index, 1)
          this.$store.commit('jianshi/SET_TEXT_LIST', text_list)
          // 合并图片素材
          const img_list = JSON.parse(JSON.stringify(this.img_list))
          img_list[index - 1].duration = duration.getMs(text_str)
          img_list.splice(index, 1)
          this.$store.commit('jianshi/SET_IMG_LIST', img_list)
          // 删除子标题
          const subTitle_list = JSON.parse(JSON.stringify(this.subTitle_list))
          subTitle_list.splice(index, 1)
          this.$store.commit('jianshi/SET_SUBTITLEDATA', subTitle_list)
        })
    },
    // 移除素材
    removeMaterial(index) {
      // 删除图片素材
      const img_list = JSON.parse(JSON.stringify(this.img_list))
      img_list.splice(index, 1)
      this.$store.commit('jianshi/SET_IMG_LIST', img_list)
      // 删除文本素材
      const text_list = JSON.parse(JSON.stringify(this.text_list))
      text_list.splice(index, 1)
      this.$store.commit('jianshi/SET_TEXT_LIST', text_list)
      // 删除子标题
      const subTitle_list = JSON.parse(JSON.stringify(this.subTitle_list))
      subTitle_list.splice(index, 1)
      this.$store.commit('jianshi/SET_SUBTITLEDATA', subTitle_list)
    },
    setImgError(data, index) {
      console.log('🚀 ~ file: index.vue ~ line 227 ~ setImgError ~ index', index)
      const img_list = JSON.parse(JSON.stringify(this.img_list))
      this.$set(img_list[index], 'imgError', true)
      this.$store.commit('jianshi/SET_IMG_LIST', img_list)
    }
  }
}
</script>

<style scoped lang="scss">
.material-img-wrap {
  width: 342px;
  margin-left: 20px;

  .material-img {
    width: 342px;
    height: 192px;
    margin-bottom: 20px;
    background-color: #f7f8f9;
    background-size: 60px 50px;
    background-position: center;
    position: relative;
    border-radius: 4px;

    .el-image {
      position: absolute;
      width: 100%;
      left: 50%;
      transform: translate(-50%);
    }

    .edit-material-label {
      display: flex;
      justify-content: space-between;
      height: 20px;
      background: rgba(0, 0, 0, .4);
      font-size: 12px;
      line-height: 12px;
      padding: 4px 10px;
      color: #fff;
      position: absolute;
      bottom: 0;
      width: 100%;
      z-index: 9;
      transition: all .3s;
      border-radius: 0 0 4px 4px;

      .btn {
        cursor: pointer;
      }

      .duration {
        margin-right: 20px;
      }
    }

    .del-btn {
      position: absolute;
      right: -6px;
      top: -6px;
      cursor: pointer;
      color: #fd5352;
    }

    .svg-wrap {
      position: absolute;
      width: 100%;
      height: 100%;
      text-align: center;
      cursor: pointer;

      svg {
        width: 50px;
        height: 40px;
        margin-top: 49px;
      }

      p {
        font-size: 12px;
        line-height: 12px;
        font-weight: 400;
        color: #bababa;
        margin-top: 10px;
      }
    }
  }
}

::v-deep .el-dialog {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  margin-top: 0!important;

  .el-dialog__header {
    padding: 0;
  }

  .el-dialog__body {
    padding: 30px;
  }
}
</style>
