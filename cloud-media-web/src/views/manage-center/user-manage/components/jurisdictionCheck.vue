<!--
 * @Author: your name
 * @Date: 2021-08-04 16:29:55
 * @LastEditTime: 2021-08-18 19:18:00
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/user-manage/components/jurisdictionCheck.vue
-->
<template>
  <div class="jurisdiction-check">
    <div class="table-list">
      <div class="top">
        <div class="titel">{{ title }}</div>
        <el-switch
          v-if="title==='后台管理系统'"
          v-model="switchBtn"
          active-color="#13ce66"
        />
      </div>
      <div class="check-list">
        <el-checkbox-group v-model="TempcheckList">
          <el-checkbox
            v-for="(item) in checkData"
            :key="item.val"
            :label="item.label"
            :checked="item.label===defaultVal"
            :disabled="!switchBtn || item.label===defaultVal"
            @change="checkChange($event,item)"
          />
        </el-checkbox-group>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    // 默认选中值
    defaultVal: {
      type: String,
      default: ''
    },
    title: {
      type: String,
      default: ''
    },
    // 回显数据
    checkList: {
      type: Array,
      default: () => {
        return []
      }
    },
    // 初始化展示数据
    checkData: {
      type: Array,
      default: () => {
        return []
      }
    }
  },
  data() {
    return {
      switchBtn: true,
      TempcheckList: []
    }
  },
  computed: {
  },
  watch: {
    switchBtn: {
      handler(val) {
        this.switchBtn = val
        this.setSwitch(val)
      },
      immediate: true
    },
    checkList: {
      handler(val) {
        if (val.length > 0) {
          this.TempcheckList = JSON.parse(JSON.stringify(val))
        }
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    checkChange(ev, item) {
      if (item.label === '视频工具箱') {
        if (ev) {
          this.$emit('resetData', true)
        } else {
          this.$emit('resetData', false)
        }
      }
    },
    setSwitch(val) {
      if (!val) {
        this.TempcheckList = []
      }
    }
  }
}
</script>

<style scoped lang="scss">
.jurisdiction-check {
  width: 100%;
  background: #fff;

  .table-list {

    .top {
      width: 100%;
      height: 30px;
      line-height: 30px;
      background: #f8f9fa;
      display: flex;
      justify-content: space-between;
      align-items: center;
      padding: 0 10px;

      .titel {
        font-size: 12px;
        font-weight: 600;
        color: #404040;
      }
    }

    .check-list {
      margin-top: 10px;
      padding: 0 10px;
    }
  }
}
</style>
