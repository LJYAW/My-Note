<!--
 * @Author: your name
 * @Date: 2021-02-23 14:46:28
 * @LastEditTime: 2021-04-26 11:04:35
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/model/ExamineModel.vue
-->
<template>
  <base-dialog
    :show="show"
    :title="dialogsObj.title"
    :width="dialogsObj.width"
    :center="dialogsObj.center"
    @close="close"
  >
    <component
      :is="item.componentName||'tableList'"
      v-for="(item,index) in approvedData"
      :key="index"
      :ref="item.componentName||'tableList'"
      :prop-data="approvedData[index]"
      @close="close"
      @submit="submit"
    />
  </base-dialog>
</template>

<script>
import serCatGory from '../js/set-catgory'
import TableList from '../tableList.vue'
import ApprovedDetail from '../ApprovedDetail.vue'
export default {
  name: 'Test',
  components: {
    TableList,
    ApprovedDetail
  },
  props: {
    modelData: {
      type: Array,
      default: () => {
        return []
      }
    },
    currentIndex: {
      type: Number,
      default: () => {
        return 0
      }
    }
  },
  data() {
    return {
      show: false,
      dialogsObj: {
        width: '80%',
        title: '审核修改',
        center: true
      },
      approvedData: []

    }
  },
  computed: {

  },
  watch: {
    currentIndexl: {
      handler() {
        this.setApprovedData()
      },
      immediate: true
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    open() {
      this.show = true
    },
    close() {
      this.show = false
      this.$emit('close')
    },
    submit() {
      this.$emit('submit')
    },
    setApprovedData() {
      this.approvedData = serCatGory(JSON.parse(JSON.stringify(this.modelData)), this.currentIndex)
    }
  }
}
</script>

<style scoped lang="scss">
.videoPaly{
    ::v-deep video{
        width: 100%;
    }
}
.d-flex{
    display: flex;
}
.flex-1{
    flex: 1;
}
.text-center{
    text-align: center;
}
.mt-10px{
    margin-top: 10px;
}
.pl-10px{
  padding-left: 10px;
}
.el-dialog__body{
    table{
        width: 100%;
    }
    .tableHead{
      padding: 10px 0 ;
      p{
        padding-left:10px;
      }
    }
}
</style>
