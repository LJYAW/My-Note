<template>
  <div class="image-track-list-wrap">
    <div class="image-track-list">
      <ul class="my-image-list">
        <li v-for="(item,index) in imag_list"
            :class="['item',{'is-hover':imag_index == index}]"
            @mouseenter="focus(index)"
            @mouseleave="imag_index = -1"
            :key="index">

          <img :src="item.src"
               v-if="item.src">
          <div v-else>
            {{item.img_name}}
          </div>

          <i class="iconfont iconsolid_add"
             @click="addImage(item)"></i>
        </li>
      </ul>
    </div>
  </div>
</template>

<script>
export default {
  props: [],
  data() {
    return {
      imag_index: -1,
      imag_list: [
        {
          img_name: '马赛克',
          type_id: 0,
          src: ''
        },
        {
          img_name: '高斯模糊',
          type_id: 1,
          src: ''
        }
      ]
    }
  },
  components: {},

  computed: {},

  watch: {},

  methods: {
    addVideo() {

    },
    focus(index) {
      this.imag_index = index
    },
    addImage(item) {
      var list = this.$store.state.videoM.image_track_list
      var progrees_x = this.$store.state.videoM.progrees_x

      list.push({
        type: 'image',
        type_name: item.img_name,
        type_id: item.type_id,
        left: progrees_x,
        width: 150,
        src: item.src,
        position: {
          position: 'absolute',
          top: 150 + 'px',
          left: 190 + 'px'
        },
        style: {
          width: 100 + 'px',
          height: 100 + 'px'
        }
      })

      this.$store.commit('setImageTrackList', list)
    }
  },

  created() {
  },

  mounted() { }
}
</script>

<style scoped lang="scss">
.my-image-list {
  margin: 10px;
  .item {
    margin: 2px;
    text-align: center;
    width: 129px;
    height: 85px;
    line-height: 85px;
    border-radius: 3px;
    font-size: 13px;
    color: #fff;
    background: #26272b;
    float: left;
    position: relative;
    border: 2px solid #31323a;
    overflow: hidden;
    cursor: pointer;

    .iconfont {
      font-weight: 400;
      font-size: 16px;
      position: absolute;
      width: 20px;
      height: 20px;
      line-height: 20px;
      cursor: pointer;
      top: 3px;
      right: 0.1875rem;
      opacity: 0;
      transition: all 0.3s;
    }
    &:hover {
      border: 2px solid $c;
      .iconfont {
        opacity: 1;
      }
    }
  }
}
</style>
