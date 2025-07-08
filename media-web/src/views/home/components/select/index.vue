<!--
 * @Author: your name
 * @Date: 2021-07-27 17:13:05
 * @LastEditTime: 2021-10-14 11:00:27
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/select/index.vue
-->
<template>
  <div class="screen-bar">
    <div class="select-bar">
      <Select :option="option1" :default-value="defaultDir" @onChange="changeProject" />
      <Select :option="option3" :default-value="defaultVca" @onChange="changeAnalysis" />
    </div>

    <div class="tab-bar">
      <TabBar
        v-for="(str,index) in tabList"
        :key="index"
        :val="str"
        :class="tabType === str.type?'active':''"
        @click.native="tabChange(str, index)"
      />
    </div>

    <div v-if="count>0" class="share">
      <Share />
    </div>
    <div class="videoSum">共有{{ count }}个内容</div>
  </div>

</template>

<script>
import Select from '@/components/VideoSelect/selectItem.vue'
import TabBar from './tabItem.vue'
import Share from './shareBtn.vue'

export default {
  components: {
    Select,
    TabBar,
    Share
  },
  props: {
    count: {
      type: Number,
      default: 1
    }
  },
  data() {
    return {
      option3: [
        { name: '全部状态', id: 0 },
        { name: '已分析', id: 1 },
        { name: '未分析', id: 2 },
        { name: '分析中', id: 3 }
      ],
      tabList: [
        // { name: '综合搜索', type: 1 },
        // { name: '物体搜索', type: 3 },
        // { name: '人物搜索', type: 4 }
      ],
      tabType: '',
      sum: 0,
      defaultDir: { name: '全部目录', id: 0 },
      defaultVca: { name: '全部状态', id: 0 },
      selectData: {}
    }
  },
  computed: {
    option1() {
      const arr = [{ name: '全部目录', id: 0 }]
      this.$store.state.user.dirInfo.forEach(item => {
        const obj = {}
        obj.name = item.dir_name
        obj.id = item.dir_id
        arr.push(obj)
      })
      return arr
    }
  },
  watch: {

  },
  created() {
    this.$bus.$on('params', (e) => {
      this.initVal(e)
    })
  },
  mounted() {

  },
  methods: {
    initVal(e) {
      const { selectData, searchType, tabList } = e
      this.tabList = tabList
      this.tabType = searchType
      this.defaultDir = selectData.dir
      this.defaultVca = selectData.vca_status
    },
    changeProject(val) {
      this.$emit('onChange', { dir: val })
    },
    changeAnalysis(val) {
      this.$emit('onChange', { vca_status: val })
    },
    tabChange(obj, index) {
      this.tabType = obj.type
      this.$emit('onSearchType', obj.type)
    }
  }
}
</script>

<style scoped lang="scss">
.screen-bar {
  display: flex;
  align-items: center;
  margin: 20px 0;
  position: relative;
}

.select-bar {
  display: flex;
  justify-content: flex-start;
  width: 240px;
  max-width: 240px;
  // overflow: hidden;
  border-radius: 4px;

  ::v-deep.select-item {
    position: relative;
    z-index: 1;

    &::after {
      content: "";
      position: absolute;
      right: 0;
      top: 8px;
      height: 15px;
      width: 1px;
      background: #d8d8d8;
    }

    &:last-child {

      &::after {
        display: none;
      }
    }

  }
}

.tab-bar {
  display: none;
  justify-content: flex-start;
  border-radius: 4px;
  overflow: hidden;
  margin-left: 30px;

  .active {
    font-weight: 600;
    color: #5675e8
  }

  ::v-deep.tab-item {
    position: relative;
    z-index: 1;

    &::after {
      content: "";
      position: absolute;
      right: 0;
      top: 8px;
      height: 15px;
      width: 1px;
      background: #d8d8d8;
    }

    &:last-child {

      &::after {
        display: none;
      }
    }

  }
}

.share {
  margin-left: 20px;
  border-radius: 4px;
  overflow: hidden;
}

.videoSum {
  font-size: 12px;
  color: #bababa;
  position: absolute;
  right: 0;
}
</style>
