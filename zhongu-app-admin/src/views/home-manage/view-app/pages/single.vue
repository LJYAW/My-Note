<!--
 * @Author: your name
 * @Date: 2021-10-20 18:05:22
 * @LastEditTime: 2021-11-21 19:13:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/view-app-edit/pages/single.vue
-->
<template>
  <div class="all-single-wrap">
    <!-- <transition-group name="el-fade-in-linear"> -->
    <div
      v-for="(data, index) in singleData"
      :key="index"
      :class="['single product hander-select',{'active': (activeIndex === index && activeComName==='Single')}]"
      @click="singleHandel(index)"
    >

      <svg-icon
        v-if="activeIndex === index && activeComName==='Single' && singleData.length > 1"
        icon-class="delete"
        class="del-icon"
        @click="deleteSingleData(index)"
      />
      <Vtitle :title="data.calssData.name" :sub-title="data.calssData.intro" />

      <div v-if="data.banner.length !== 0" class="swiper-wrap card">
        <div class="swiper">
          <swiper ref="mySwiper" :options="swiperOptions">
            <swiper-slide
              v-for="(item,j) in data.banner"
              :key="j"
            >
              <BaseImage :src="item.bannerSrc" />
            </swiper-slide>
            <div slot="pagination" class="swiper-pagination" />
          </swiper>
        </div>
      </div>

      <div :class="`sm-card  w-${data.comType}`">
        <BuyCard
          v-for="(item, j) in data.commodity"
          :key="j"
          class="card"
          :base-data="item.group"
        />
      </div>
    </div>
    <!-- </transition-group> -->

  </div>
</template>

<script>
import 'swiper/dist/css/swiper.css'
import { swiper, swiperSlide } from 'vue-awesome-swiper'
import { mapState } from 'vuex'
import BuyCard from '../../components/buy-card.vue'

export default {
  name: 'Single',
  components: {
    BuyCard,
    swiper,
    swiperSlide,
    Vtitle: () => import('../../components/v-title.vue')
  },
  props: {},
  data() {
    return {
      swiperOptions: {
        pagination: {
          el: '.swiper-pagination', // 与slot="pagination"处 class 一致
          clickable: true // 轮播按钮支持点击
        }
      },
      activeIndex: null
    }
  },
  computed: {
    ...mapState('bdapp', ['appData', 'activeComName', 'upCom']),
    singleData() {
      return this.appData.Single
    }
  },
  watch: {
    upCom() {
      this.$forceUpdate()
    }
  },
  created() {},
  mounted() {},
  methods: {
    deleteSingleData(index) {
      this.$store.commit('bdapp/DEL_SINGLE', index)
      this.activeIndex = 0
      this.singleHandel()
      this.$bus.$emit('delSingle')
    },
    singleHandel(index) {
      this.activeIndex = index
      this.$store.commit('bdapp/SET_SINGLE_INDEX', index)
    }
  }
}
</script>

<style scoped lang="scss">
.swiper {
  width: 100%;
  height: 150px;
}
.single {
  margin-bottom: 20px;

  &.hander-select {
    position: relative;

    .del-icon {
      position: absolute;
      right: -14px;
      top: -14px;
      z-index: 11;
      font-size: 18px;
      cursor: pointer;
    }

    &.active {
      &::after {
        opacity: 1;
      }
    }

    &::after {
      display: block;
      content: " ";
      width: calc(100% + 12px);
      height: calc(100% + 12px);
      position: absolute;
      top: -6px;
      left: -6px;
      border: 1px solid #5675e8;
      border-radius: 4px;
      opacity: 0;
      transition: all 0.3s;
    }
  }

  &:hover {
    &::after {
      opacity: 1;
    }
  }
}

.swiper-wrap {
  margin-bottom: 15px;
}
.sm-card {
    display: grid;
    grid-gap: 10px 9px;

  &.w-4 {
    grid-template-columns: repeat(4, 1fr);
    .card {
      min-height: 124px;
      max-height: 124px;
    }
  }
  &.w-3 {
    grid-template-columns: repeat(3, 1fr);
    .card {
      min-height: 154px;
      max-height: 154px;
    }
  }
  &.w-2 {
    grid-template-columns: repeat(2, 1fr);
    .card {
      min-height: 216px;
      max-height: 216px;
    }
  }
}
</style>
