<!--
 * @Author: your name
 * @Date: 2021-01-26 16:06:25
 * @LastEditTime: 2021-02-09 18:21:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /doctor_admin/src/components/tags.vue
-->
<template>
  <div class="search-tags">
    <div>
      <div class="tags_value">
        <p v-for="(tags_item,tem_index) in tags_item" :key="tem_index">
          {{tags_item.company_name}}
          <i class="el-icon-close" @click="tagsClose(tags_item,tem_index)"></i>
        </p>
      </div>
      <div class="d-flex">
        <el-input v-model="keywords" style="width:300px" placeholder="请输入关键字"></el-input>
      </div>
      <div class="tag_list mt-10px">
        <div class="d-flex flex-wrap">
          <p v-for="(item,index) in tempTags" :key="index" class="ml-10px" @click="tagsTick(item)">
            <span :class="[tags_item.some(data => data.invitation_code == item.invitation_code) ? 'isSelect': '']">{{item.company_name}}</span>
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
    }
  },
  data() {
    return {
      keywords: '',
      tempTags: []
    }
  },
  watch: {
    tags_item(val) {
      this.tags_item = val
    },
    tags(val) {
      this.tags = val
    },
    keywords(val) {
      if (val) {
        this.tempTags = this.tempTags.filter(item => item.company_name.includes(val))
      } else {
        this.tempTags = JSON.parse(JSON.stringify(this.tags))
      }
    }
  },
  components: {},
  computed: {},
  methods: {
    // 删除指定企业
    tagsClose(item, index) {
      this.tags_item.splice(index, 1)
      this.$emit('tasList', this.tags_item)
    },
    tagsTick(item) {
      let obj = {
        company_name: item.company_name,
        invitation_code: item.invitation_code
      }
      let index = this.tags_item.findIndex(data => data.invitation_code == item.invitation_code)
      if (index > -1) {
        this.tags_item.splice(index, 1)
      } else {
        this.tags_item.push(obj)
      }
      this.$emit('tasList', this.tags_item)
    }
  },
  mounted() {},
  created() {
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
      font-size: 12px;
    }
  }
  .tag_list {
    padding: 10px;
    background: #f5f5f5;
    max-height: 300px;
    overflow: auto;
    margin-top: 15px;
    height: 127px;
    p {
      cursor: pointer;
      font-size: 12px;
    }
    .isSelect {
      color: #2a79df;
    }
  }
}
</style>