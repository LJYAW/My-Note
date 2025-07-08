<!--
 * @Author: your name
 * @Date: 2021-07-02 17:50:01
 * @LastEditTime: 2021-07-08 14:37:44
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/channel/pages/channelTable.vue
-->
<template>
  <div class="channel-table">
    <el-table :data="tableData" stripe border>
      <el-table-column label="频道名称" prop="channelsname" />
      <el-table-column label="频道类型">
        <template slot-scope="scope">
          {{ getTableItemType(scope.row.types) }}
        </template>
      </el-table-column>
      <el-table-column label="频道归属地" prop="region" />
      <el-table-column label="频道分级">
        <template slot-scope="scope">
          {{ getTableItemLevel(scope.row.levels) }}
        </template>
      </el-table-column>
      <el-table-column label="匹配任务数" prop="num" />
      <!-- <el-table-column label="状态">
        <template slot-scope="scope">
          <span :class="[scope.row.status=='命中任务中'&&'isHit','normal']">{{ scope.row.status }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="设置">
        <template slot-scope="scope">
          <base-btn type="text" @click="toUpdate(scope.row.id)">修改</base-btn>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
  components: {

  },
  props: {
    tableData: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    return {
    }
  },
  computed: {
    ...mapGetters(['channelLevel', 'channelType'])
  },
  watch: {

  },
  created() {
    if (!this.channelLevel.length) this.getChannelLevel()
    if (!this.channelType.length) this.getChannelType()
  },
  mounted() {

  },
  methods: {
    toUpdate(id) {
      this.$router.push({
        path: 'update-channel',
        query: { id: id }
      })
    },
    // 获取频道分级列表
    async getChannelLevel() {
      await this.$store.dispatch('channel/getChannelLevel', { page: 1, limit: 10000 })
    },
    // 获取频道类型列表
    async getChannelType() {
      await this.$store.dispatch('channel/getChannelType', { page: 1, limit: 10000 })
    },
    getTableItemLevel(val) {
      const arr = this.channelLevel.filter(item => item.id === val)
      if (arr.length) return arr[0].names
    },
    getTableItemType(val) {
      const arr = this.channelType.filter(item => item.id === val)
      if (arr.length) return arr[0].names
    }
  }
}
</script>

<style scoped lang="scss">
.channel-table {

  .el-table {

    ::v-deep .el-table__body-wrapper {
      max-height: calc(100% - 270px);
      overflow: auto;

      .normal {
        color: #f00;
      }

      .isHit {
        color: #16d049;
      }
    }
  }
}
</style>
