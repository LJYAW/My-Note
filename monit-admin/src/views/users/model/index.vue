<!--
 * @Author: your name
 * @Date: 2021-07-06 14:29:15
 * @LastEditTime: 2021-07-15 18:09:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/users/model/index.vue
-->
<template>
  <div class="tags-model">
    <p class="title">选择频道</p>
    <base-tags v-if="channelData.length>0" ref="tagsList" :channel-data="channelData" :tags="tags" />
    <div class="btn-wrap">
      <base-btn class="btn" @click="back()">返回</base-btn>
      <base-btn class="btn" @click="save">确认</base-btn>
    </div>
  </div>
</template>

<script>
import mixin from '../js/minx'
export default {
  components: {
  },
  mixins: [mixin],
  props: {
    tags: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
    }
  },

  watch: {

  },
  created() {
  },
  mounted() {
  },
  methods: {
    save() {
      const list = this.$refs.tagsList.tempTagsList
      const arr = this.forData(list)
      this.$emit('save', arr)
    },
    forData(data) {
      return data.filter(item => item.falg)
    },
    back() {
      this.$emit('save', '')
    }
  }
}
</script>

<style scoped lang="scss">
.tags-model {
  width: 100%;
  height: 570px;

  .title {
    font-size: 48px;
    font-weight: 600;
    text-align: center;
    color: #000;
    margin-bottom: 50px;
  }

  .btn-wrap {
    text-align: center;
    margin-top: 95px;

    .btn {
      width: 150px;
      height: 40px;
      background: #9aacf1;
      border-radius: 4px;
      border: none;
      margin-right: 30px;
    }
  }
}
</style>
