<!--
 * @Author: your name
 * @Date: 2021-07-06 11:39:57
 * @LastEditTime: 2021-07-15 18:19:39
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/components/base/Tags.vue
-->
<template>
  <div class="tags-lists">
    <div
      v-for="(item,index) in tempTagsList"
      :key="index"
      class="item-list"
      :class="{'activeClass':item.falg}"
      @click="tagsClick(item,index)"
    >
      {{ item.channelsname }}
      <div v-show="item.falg" class="el-icon-success icon-box" />
    </div>
  </div>
</template>

<script>
export default {
  name: 'BaseTags',
  components: {

  },
  props: {
    channelData: {
      type: Array,
      default: () => {
        return []
      }
    },
    tags: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      tempTagsList: []
    }
  },
  computed: {
  },
  watch: {

  },
  created() {
    this.forData()
  },
  mounted() {
  },
  methods: {
    forData() {
      const arr = JSON.parse(JSON.stringify(this.channelData))
      arr.forEach(item => {
        item.falg = !!(this.tags.length > 0 && this.tags.some(k => k.id === item.id))
      })
      this.tempTagsList = arr
    },
    tagsClick(item, index) {
      item.falg = !item.falg
    }
  }
}
</script>

<style scoped lang="scss">
.tags-lists {
  display: flex;
  flex-wrap: wrap;
  height: 320px;
  overflow: auto;

  .activeClass {
    border: 2px solid #5878e9 !important;
  }

  .item-list {
    position: relative;
    width: auto;
    height: 40px;
    text-align: center;
    line-height: 40px;
    background: #eaf1fe;
    border-radius: 4px;
    margin-right: 20px;
    font-size: 14px;
    font-weight: 400;
    color: #000;
    cursor: pointer;
    padding: 0 10px;
    box-sizing: border-box;
    margin-top: 10px;
    border: 2px solid transparent;

    &:last-child {
      margin-right: 0;
    }

    .icon-box {
      position: absolute;
      top: -5px;
      right: -5px;
      color: rgba(86, 118, 232, 1);
      font-size: 22px;
    }
  }
}
</style>
