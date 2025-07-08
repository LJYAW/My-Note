<!--
 * @Author: zzx
 * @Date: 2020-10-27 19:22:24
 * @LastEditTime: 2020-11-02 15:44:31
 * @FilePath: /zhijian_web/src/views/intellect_create/modal/modal_m/src/ai_img_list.vue
-->
<template>
  <div class="ai-img-list-wrap">
    <div class="d-flex search-nav">
      <ul class="mini-nav d-flex justify-content-start" v-if="!status_loading">
        <li
          v-for="(link,j) in search_tab_img"
          :key="j"
          :class="[{'active' : j == search_tab_index}]"
          @click="searchImgTab(link,j)">
          <a v-if="search_img_list[j] && search_img_list[j].length > 0">{{link.name}}</a>
        </li>
      </ul>
      <div class="text-loading" v-if="!is_done">
        <img src="@/assets/images/text_loading.svg" width="50" height="35" />
      </div>
    </div>
    <scroll
      v-if="is_done"
      class="wrapper search-list img-list"
      ref="scroll"
      style=""
      @scrollDown="scrollDown">
      <div class="content">
        <div class="text-center w-100 py-100px" v-if="!search_img_list[search_tab_index]">
          <img src="@/assets/images/is_empty.png" style="264" height="205" />
          <p class="fz-12px fc-999 mt-25px">未找到您所搜索的内容，换个词试试？</p>
        </div>
        <el-row :gutter="20" style="margin-left: 10px">
          <el-col
            :class="['video-box',{ 'isactive': select_img_index == v}]"
            v-for="(img,v) in search_img_list[search_tab_index]"
            :key="v"
            :span="5"
            style="padding: 0;"
            @click.native="addSearchItem(img.image_url,v)">
            <div class="card-img">
              <img :src="img.image_url" class="w-100 h-100" />
              <label class="el-upload-list__item-status-label">
                <i class="iconfont iconqueding"></i>
              </label>
            </div>
          </el-col>
        </el-row>

        <div v-if="search_img_list[search_tab_index] && is_done">
          <div
            class="text-center w-100 py-100px"
            v-if="search_img_list[search_tab_index].length < 1">
            <img src="@/assets/images/is_empty.png" style="264" height="205" />
            <p class="fz-12px fc-999 mt-25px">未找到您所搜索的内容，换个词试试？</p>
          </div>
        </div>
      </div>
    </scroll>
    <div class="text-center py-20px" v-if="is_done">
      <el-button
        round
        size="mini"
        type="primary"
        style="width: 100px"
        @click="submitAddImg">完成</el-button>
    </div>
  </div>
</template>

<script>
import { searchImgAi } from '../js/search_img_ai'
import { EventBus } from '../js/eventBus'
export default {
  name: 'AiImgList',
  props: {},
  inject: ['modalM'],
  mixins: [searchImgAi],
  data() {
    return {
      status_loading: false,
      search_tab_index: -1, // 选中tab的索引
      search_tab_img: [
        {
          name: '人脸图片',
          key: 'face-query'
        },
        {
          name: '物体图片',
          key: 'object-query'
        },
        {
          name: '相似图片',
          key: 'query'
        }
      ],
      select_img_index: -1, //选中图片索引
      select_item: null
    }
  },
  computed: {
    search_img_list() {
      return this.modalM['search_img_list']
    },
    is_done() {
      if (this.modalM['is_done']) {
        this.setTabIndex()
      }
      return this.modalM['is_done']
    }
  },
  watch: {},
  methods: {
    searchImgTab(link, index) {
      this.search_tab_index = index
      this.$refs.scroll.scrollTop()
    },
    scrollDown() {},
    addSearchItem(url, index) {
      this.select_img_index = index
      this.select_item = {
        resource_url: url,
        is_ai_image: true,
        type: 'image'
      }
    },
    submitAddImg() {
      if (this.select_item) this.modalM.submit(this.select_item)
    },
    setTabIndex() {
      let arr = this.search_tab_img
      for (let i = 0; i < this.search_tab_img.length; i++) {
        const type = arr[i].key
        const name = arr[i].name

        for (let i = 0; i < this.search_img_list.length; i++) {
          let arr = this.search_img_list[i]
          if (arr.length > 0) {
            this.search_tab_index = i
            break
          }
        }
      }
    }
  },
  components: {},
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
.ai-img-list-wrap {
  height: 0;
  flex: 1;
  display: flex;
  flex-direction: column;
}
.search-nav {
  position: relative;
  width: fit-content;
  .mini-nav {
    border: none;
    margin-bottom: 10px;

    li {
      margin: 0;

      &.active {
        a {
          border-bottom: 2px solid $c;
        }
      }

      a {
        margin-right: 10px;
        padding: 0 2px;
        padding-bottom: 10px;
        border-bottom: 2px solid transparent;
      }
    }
  }
  .text-loading {
    position: absolute;
    right: -45px;
    top: -10px;
  }
}
.search-list {
  margin: 0 -15px;
  max-height: 600px;
  overflow: hidden;
  overflow-y: auto;

  &.img-list {
    .card-img {
      width: 100%;
      height: 100%;
      position: relative;
      overflow: hidden;

      .el-upload-list__item-status-label {
        position: absolute;
        right: -17px;
        top: -7px;
        width: 46px;
        height: 26px;
        line-height: 33px;
        background: #c51a1a;
        text-align: center;
        transform: rotate(45deg);
        box-shadow: 0 1px 1px #ccc;
        color: white;

        i {
          display: block;
          transform: rotate(-45deg);
          font-size: 12px;
        }
      }

      &:hover {
        .el-upload-list__item-status-label {
          display: block;
        }
      }
    }
  }

  .video-box {
    border: 2px solid transparent;
    position: relative;
    width: 180px;
    height: 100px;
    background-color: #e5e5e5;
    border-radius: 3px;
    margin: 9px 6px;

    &:hover {
      border: 2px solid $c;
    }

    &.isactive {
      border: 2px solid $c;

      .el-upload-list__item-status-label {
        display: block;
      }
    }

    #videoWrap {
      height: 100%;
      width: 100%;
    }
  }
}
</style>
