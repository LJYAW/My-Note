<!--
 * @Author: zzx
 * @Date: 2020-10-16 16:51:55
 * @LastEditTime: 2021-07-14 18:53:27
 * @FilePath: /zhijian_web/src/views/active_video/create_video/edit_pad/index.vue
-->
<template>
  <div class="edit-pad container">
    <div class="edit-header">
      <p>视频片段</p>
      <p>互动编辑</p>
    </div>
    <transition-group name="list" tag="div">
      <div class="edit-wrap list-item" v-for="(item, index) in dataList" :key="'list' + index">
        <div class="index">{{ letter[index] }}:</div>

        <div class="right-pad">
          <div class="icontianjiashipinsucaiicon">
            <vsvg icon="icontianjiashipinsucaiicon" style="width: 48px; height: 57px"></vsvg>
            <p>请添加视频</p>
          </div>
          <!-- 删除 -->
          <vsvg v-if="dataList.length > 1" icon="iconshanchu" class="iconshanchu" @click.native="deletaMaterial(index)"></vsvg>

          <!-- 素材 -->
          <div class="material">
            <videoCut
              :key="item.material.material_id"
              v-if="item.material && item.material.type == 'video'"
              :video_options="{ width: '338', height: '189', src: item.material.resource_url }"
            />
            <img v-if="item.material && item.material.type == 'image'" :key="item.resource_url" :src="item.material.resource_url" />
          </div>

          <!-- 编辑素材 -->
          <div class="add-btn" @click="editPalette(index)">
            <vsvg icon="iconbianjisucaiicon" style="width: 12px; height: 12px"></vsvg>
            <span class="btn-name">编辑素材</span>
          </div>
        </div>

        <div class="left-pad">
          <img class="not-content" src="@/assets/images/active_video/trans.png" width="180" height="130" v-if="!item.btn_effect.btn_list" />

          <div class="d-flex btn_pad flex-wrap" v-else-if="item.btn_effect">
            <div v-for="(btnitem, btnindex) in item.btn_effect.btn_list" :key="btnindex" class="d-flex active-btn">
              <img :src="btnitem.selectBtn.image_url" alt="" class="btnimg" />
              <div class="fragmentText" v-if="btnitem.eventFn == 'jump'">跳转视频片段 至 {{ btnitem.eventParams }}</div>
              <div class="fragmentText" v-if="btnitem.eventFn == 'link'">跳转链接 {{ btnitem.eventParams }}</div>
              <div class="fragmentText" v-if="btnitem.eventFn == 'phone'">拨打电话 {{ btnitem.eventParams }}</div>
            </div>
          </div>

          <!-- 编辑互动效果 -->
          <div class="edit-btn" @click="activeVideo(index)" v-if="!!item.material.type">
            <vsvg icon="iconbianjihudongxiaoguoicon" style="width: 15px; height: 15px"></vsvg>
            <span>编辑互动效果</span>
          </div>
        </div>
      </div>
    </transition-group>

    <!-- 添加片段 -->
    <div class="add-btn-right" @click="addMaterial">添加片段</div>
  </div>
</template>

<script>
import letter from '../js/en_letter'
import myMaterialList from '../my_material_list/index'
import videoModel from '..//video_btn_modal/index'

export default {
  props: {},
  data() {
    return {
      letter: letter,
      dataList: [
        {
          btn_effect: {}, // 按钮效果
          material: {} // 素材
        }
      ],
      active_index: null,
      select_item: {}
    }
  },
  computed: {},
  watch: {
    dataList: {
      handler(item) {
        localStorage.setItem('active_video_data_list', JSON.stringify(this.dataList))
      },
      deep: true
    }
  },
  methods: {
    // 添加素材
    addMaterial() {
      this.dataList.push({
        btn_effect: {},
        material: {}
      })
    },
    // 删除素材
    deletaMaterial(index) {
      if (this.dataList.length < 2) return
      this.dataList.splice(index, 1)
    },
    // 编辑素材
    editPalette(index) {
      this.active_index = index
      this.$layer.iframe({
        content: {
          content: myMaterialList,
          parent: this,
          data: {
            index: index
          }
        },
        area: ['800px', '648px'],
        title: '添加素材',
        maxmin: true,
        shade: true, //是否显示遮罩
        shade: false,
        shadeClose: true,
        cancel: () => {
          // 关闭弹窗事件
        }
      })
    },
    // 编辑互动视频
    activeVideo(index) {
      this.active_index = index

      this.$layer.iframe({
        content: {
          content: videoModel,
          parent: this,
          data: {
            data: this.dataList[index],
            DATA_LENTH: this.dataList.length,
            DATA_INDEX: index
          }
        },
        area: ['815px', '855px'],
        title: '编辑互动效果',
        maxmin: true,
        shade: false,
        shadeClose: false,
        cancel: () => {}
      })
    },
    getLocalActiveVideoList(data) {
      let btn_effect = {
        btn_list: data.btnList,
        radio_event: data.radio_event,
        play_auto_btn: data.play_auto_btn
      }
      this.$set(this.dataList[this.active_index], 'btn_effect', btn_effect)
    },
    getMaterialTime(data) {
      let index = data.index
      this.$set(this.dataList[index].material, 'start_ms', data.startMs)
      this.$set(this.dataList[index].material, 'end_ms', data.endMs)
    },
    setVideoDetails(data) {
      this.$set(this.dataList[this.active_index], 'material', data)
    }
  },
  components: {},
  created() {},
  mounted() {
    this.$bus.$on('active_video_list', this.getLocalActiveVideoList)
    this.$bus.$on('set_material_time', this.getMaterialTime)
    this.$bus.$on('setVideoDetails', this.setVideoDetails)
    localStorage.removeItem('active_video_data_list')
  }
}
</script>

<style scoped lang="scss">
@import './index.scss';
</style>
