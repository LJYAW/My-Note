<!--
 * @Author: your name
 * @Date: 2021-03-03 14:04:36
 * @LastEditTime: 2021-08-03 10:15:59
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /production_web/src/views/EPG/model/tableList.vue
-->
<template>
  <el-table :data="[propData]" :class="[propData.type==='非广告'?'zp_bg':'ad_bg']">

    <el-table-column prop="type" label="类型" width="120">
      <template slot-scope="scope">
        <span v-if="scope.row.curStatus">({{ scope.row.curStatus }})</span>{{ scope.row.type }}
      </template>
    </el-table-column>

    <el-table-column prop="type" label="时间">
      <template slot-scope="scope">
        {{ getDateTime(scope.row.start_time) }}-{{ getDateTime(scope.row.end_time) }}
      </template>
    </el-table-column>

    <el-table-column prop="title" label="标题">
      <template slot-scope="scope">
        <el-tooltip v-if="scope.row.type=='广告'" class="item" effect="dark" :content="scope.row.title" placement="top-start">
          <base-btn type="text">
            {{ scope.row.title }}
          </base-btn>
        </el-tooltip>
      </template>
    </el-table-column>

    <el-table-column label="净时长" width="90">
      <template slot-scope="scope">
        {{ getTime(scope.row.duration).split('.')[0] }}
      </template>
    </el-table-column>

    <el-table-column label="分类">
      <template slot-scope="scope">
        {{ scope.row.tags }}
      </template>
    </el-table-column>

    <el-table-column label="更新时间" prop="updated_at" />
  </el-table>
</template>

<script>
import { timesToHMS } from '../../js/times'
import { parseTime } from '@/utils/index.js'
export default {
  components: {

  },
  props: {
    propData: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {

    }
  },
  computed: {

  },
  watch: {

  },
  created() {

  },
  mounted() {

  },
  methods: {
    getTime(val) {
      return timesToHMS(val)
    },
    getDateTime(val) {
      return parseTime(val)
    }
  }
}
</script>

<style scoped lang="scss">
.zp_bg{
  ::v-deep td{
    background: rgba(241,240,255,1);
    &:first-child{
      color: #7975FF;
    }
  }
}
.ad_bg{
  ::v-deep td{
    background:rgba(255,240,223,1);
    &:first-child{
      color: #FF000C;
    }
  }
}
.el-table{
  ::v-deep .el-table__header-wrapper{
    display: none;
  }
  ::v-deep .cell{
    min-height: 30px;
    max-height: 30px;
    line-height: 30px;
  }
  &:first-child{
    ::v-deep .el-table__header-wrapper{
      display: block;
    }
  }
}
</style>
