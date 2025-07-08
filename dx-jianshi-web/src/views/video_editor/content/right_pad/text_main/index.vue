<template>
  <div class="text-main-wrap"
       @click.stop.prevent>
    <div class="text-list"
         v-show="active_data.type !== 'text'">
      <div class="item">
        文字
        <i class="iconfont iconsolid_add"
           @click="addTrackList()"></i>
      </div>
    </div>

    <textEditor v-show="active_data.type == 'text'"></textEditor>
  </div>
</template>

<script>
import textEditor from './text_editor'
export default {
  props: [],
  data() {
    return {
      is_action: false,
      text_list: []
    }
  },
  components: { textEditor },

  computed: {
    active_data() {
      return this.$store.state.videoM.active_data
    }
  },

  watch: {},

  methods: {
    addTrackList() {
      var text_list = this.$store.state.videoM.track_text_list
      var progrees_x = this.$store.state.videoM.progrees_x

      console.log('添加文字')

      text_list.push({
        left: progrees_x,
        width: 150,
        height: 30,
        content: '双击文本编辑',
        style: this.$store.state.videoM.track_text_style,
        position: {
          position: 'absolute',
          top: 150 + 'px',
          left: 190 + 'px'
        }
      })
      // this.$store.commit('setTrackTextList', text_list)
    }
  },

  created() { },

  mounted() { }
}
</script>

<style scoped lang="scss">
.text-main-wrap {
  .text-list {
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
}
</style>
