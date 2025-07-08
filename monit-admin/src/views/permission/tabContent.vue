<!--
 * @Author: your name
 * @Date: 2021-07-06 11:02:22
 * @LastEditTime: 2021-07-15 15:56:52
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/permission/tabContent.vue
-->
<template>
  <div class="tab-content">
    <div v-for="item in persionList" :key="item.levels" class="persion-item">
      <span class="form-label">{{ item.name }}</span>
      <el-select
        v-if="permissionKey!=='channelTime'"
        v-model="item.contents"
        clearable
        filterable
        @change="((value)=>{handelChange(value,item)})"
      >
        <el-option
          v-for="option in optionList"
          :key="option.id"
          :disabled="isDisable(option.names)"
          :label="option.names"
          :value="option.names"
        />
        <div style="padding-left: .1rem" @click="showAddDialog('channelLevel')">
          <base-btn type="text">新增<i class="el-icon-circle-plus-outline" /></base-btn>
        </div>
      </el-select>
      <el-time-picker
        v-else
        v-model="item.contents"
        is-range
        value-format="HH:mm:ss"
        range-separator="至"
        start-placeholder="开始时间"
        end-placeholder="结束时间"
        placeholder="选择时间范围"
        @change="((value)=>{handelChange(value,item)})"
      />
    </div>
    <base-dialog :show.sync="dialogVisible" :show-close="false" width="60%">
      <AddTag :tag-list="optionList" @addTag="addTag" @deleteTag="deleteTag" @close="close" />
    </base-dialog>
  </div>
</template>
<script>
import AddTag from '@/components/AddTag/index.vue'
import { mapGetters } from 'vuex'
export default {
  components: {
    AddTag
  },
  props: {
    permissionKey: {
      type: String,
      default() {
        return ''
      }
    },
    permissionData: {
      type: Array,
      default() {
        return []
      }
    }
  },
  data() {
    return {
      levelList: { '一': 10, '二': 9, '三': 8, '四': 7, '五': 6, '六': 5, '七': 4, '八': 3, '九': 2, '十': 1 },
      persionList: [],
      dialogVisible: false,
      updatePermission: {}
    }
  },
  computed: {
    ...mapGetters(['channelLevel', 'channelType']),
    optionList() {
      return this.$store.state.channel[this.permissionKey]
    },
    axiosUrl() {
      switch (this.permissionKey) {
        case 'channelLevel' :
          return '/v1/channelslevel'
        case 'channelType':
          return '/v1/channelstype/'
        default:
          return ''
      }
    }
  },
  watch: {
    permissionData(val) {
      this.setPersionList()
      val.forEach(item => {
        const index = this.persionList.findIndex(perssion => perssion.levels === item.levels)
        this.persionList[index].id = item.id
        if (this.permissionKey === 'channelTime') {
          this.persionList[index].contents = item.contents.split('-')
        } else {
          this.persionList[index].contents = item.contents
        }
        this.persionList[index].levels = item.levels
      })
    }
  },
  created() {
    this.setPersionList()
    if (!this.channelLevel.length) this.getChannelLevel()
    if (!this.channelType.length) this.getChannelType()
  },

  mounted() {
  },
  methods: {
    setPersionList() {
      this.persionList = []
      for (var key in this.levelList) {
        this.persionList.push({
          id: null,
          name: key + '级权重',
          levels: this.levelList[key],
          contents: ''
        })
      }
    },
    showAddDialog() {
      this.dialogVisible = true
    },
    close() {
      this.dialogVisible = false
    },
    // 获取频道分级列表
    async getChannelLevel() {
      await this.$store.dispatch('channel/getChannelLevel', { page: 1, limit: 10000 })
    },
    // 获取频道类型列表
    async getChannelType() {
      await this.$store.dispatch('channel/getChannelType', { page: 1, limit: 10000 })
    },
    async addTag(val) {
      const params = {
        id: null,
        names: val,
        vals: null
      }
      const { err, data } = await this.$post(this.axiosUrl, params)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '新增成功'
        })
      }
      this.refresh()
    },

    async deleteTag(id) {
      const { err, data } = await this.$deleteRun(`${this.axiosUrl}/${id}`)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '删除成功'
        })
      }
      this.refresh()
    },
    refresh() {
      switch (this.permissionKey) {
        case 'channelLevel' :
          this.getChannelLevel()
          break
        case 'channelType':
          this.getChannelType()
          break
      }
    },
    isDisable(content) {
      const arr = this.persionList.filter(item => item.contents === content)
      if (arr.length) return true
    },
    handelChange(value, item) {
      this.updatePermission = {
        contents: item.contents,
        id: item.id,
        levels: item.levels
      }
    }
  }
}
</script>

<style scoped lang="scss">
.tab-content {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  height: 450px;
  flex-direction: column;

  .persion-item {
    width: 46%;
    margin-bottom: 30px;
    display: flex;

    ::v-deep .el-select {
      flex: 1;

      .el-input,
      .el-input__inner {
        height: 100%;
      }
    }

    .el-date-editor {
      height: 100%;
      flex: 1;

      ::v-deep .el-range-separator {
        line-height: .27rem;
      }
    }
  }
}
</style>
