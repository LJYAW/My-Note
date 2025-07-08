<template>
  <div class="d-flex tag-wrap align-items-center flex-wrap">

    <div class="d-flex">
      <el-input type="text"
                @keyup.enter.native="addtags()"
                style="width:100px"
                maxlength="10"
                v-model.trim="tag_name" />

      <a class="add-btn mx-10px"
         @click="addtags()">添加</a>
      <span class="word-break-all">
        {{list.length}}/{{MAX_LENGTH}}
      </span>

    </div>

    <ul class="tag-list d-flex ml-20px">
      <li v-for="(item,index) in list"
          class="mr-8px tags py-2px px-3px"
          :key="index">
        <span class="">{{item}}</span>
        <i @click="deletTag(index)"
           class="iconfont iconclose fz-12px"></i>
      </li>
    </ul>

  </div>
</template>

<script>
export default {
  props: {
    tag_list: {
      type: Array,
      default() {
        return []
      }
    },
    MAX_LENGTH: {
      type: Number,
      default() {
        return 5
      }
    }
  },
  data() {
    return {
      list: [],
      tag_name: ''
    }
  },
  components: {},

  computed: {},

  watch: {},

  methods: {
    setList() {
      this.list = this.list.concat(this.tag_list)
    },
    addtags() {
      console.log(this.list.length >= this.MAX_LENGTH)

      if (this.list.length >= this.MAX_LENGTH) {
        this.$alertMsg(`最多只能添加${this.MAX_LENGTH}个标签`)
        return
      }

      if (this.tag_name) {
        this.list.push(this.tag_name)
        this.tag_name = ''
        this.$emit('getTagList', this.list)
      }
    },
    deletTag(index) {
      this.list.splice(index, 1)
      this.$emit('getTagList', this.list)
    }
  },

  created() {
    this.setList()
  },

  mounted() {}
}
</script>

<style scoped lang="scss">
.tag-wrap {
  .tag-list {
    .tags {
      background: rgba(240, 240, 240, 1);
      border-radius: 1px;
      color: #333333;
      cursor: pointer;
      height: 20px;
      display: flex;
      align-items: center;
      .iconfont {
        color: #cdcdcd;
      }
      &:hover {
        color: $c;
        .iconfont {
          color: $c;
        }
      }
    }
  }
}
</style>
