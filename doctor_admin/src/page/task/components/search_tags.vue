<!--
 * @Author: your name
 * @Date: 2021-01-26 16:06:25
 * @LastEditTime: 2021-02-10 11:49:03
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/components/tags.vue
-->
<template>
  <div class="search-tags">
    <div>
      <div class="tags_value">
        <p v-for="(tags_item, tem_index) in temp_item" :key="tem_index">
          {{ tags_item.name }}
          <i class="el-icon-close" @click="tagsClose(tags_item, tem_index)"></i>
        </p>
      </div>
      <div class="d-flex">
        <el-input
          v-model="keywords"
          style="width:300px"
          placeholder="请输入关键词"></el-input>
      </div>
      <div class="tag_list">
        <div class="d-flex flex-wrap">
          <p
            v-for="(item, index) in tempTags"
            :key="index"
            class="ml-10px item-p"
            @click="tagsTick(item)">
            <span
              :class="[
                temp_item.some((data) => data.id == item.id) ? 'isSelect' : '',
              ]">{{ item.name }}</span>
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: '',
  props: {
    tags_item: {
      type: Array,
      default: () => []
    },
    tags: {
      type: Array,
      default: () => []
    },
    type: {
      type: String,
      default: ''
    }
  },
  data() {
    return {
      temp_item: [],
      keywords: '',
      tempTags: []
    }
  },
  watch: {
    keywords(val) {
      if (val) {
        this.tempTags = this.tempTags.filter(item => item.name.includes(val))
      } else {
        this.tempTags = JSON.parse(JSON.stringify(this.tags))
      }
    },
    tags_item(val) {
      this.temp_item = val
    }
  },
  components: {},
  computed: {},
  methods: {
    // 删除指定企业
    tagsClose(item, index) {
      this.temp_item.splice(index, 1)
      this.$emit('tasList', this.temp_item)
    },
    tagsTick(item) {
      let obj = {
        name: item.name,
        id: item.id
      }
      let index = this.temp_item.findIndex(data => data.id == item.id)
      if (index > -1) {
        this.temp_item.splice(index, 1)
      } else {
        if (this.type) {
          this.temp_item = []
          this.temp_item.push(obj)
        } else {
          this.temp_item.push(obj)
        }
      }
      this.$emit('tasList', this.temp_item)
    }
  },
  mounted() {},
  created() {
    this.temp_item = JSON.parse(JSON.stringify(this.tags_item))
    this.tempTags = JSON.parse(JSON.stringify(this.tags))
  }
}
</script>

<style lang="scss" scoped>
.search-tags {
  position: relative;
  top: -8px;
  .tags_value {
    margin-top: 10px;
    display: flex;
    flex-wrap: wrap;
    i {
      &:hover {
        color: #c51b19;
      }
    }
    p {
      width: auto;
      cursor: pointer;
      height: 30px;
      margin-right: 10px;
      background: #f5f5f5;
      margin-top: 10px;
      line-height: 20px;
      padding: 5px;
      box-sizing: border-box;
      border-radius: 2px;
      color: #101010;
      font-size: 12px;
    }
  }
  .tag_list {
    width: 100%;
    height: 127px;
    padding: 10px;
    background: #f5f5f5;
    max-height: 300px;
    overflow: auto;
    margin-top: 15px;
    color: #101010;
    .item-p {
      width: auto;
      font-size: 12px;
      cursor: pointer;
    }
    .isSelect {
      color: #2a79df;
    }
  }
}
</style>
