<!--
 * @Author: your name
 * @Date: 2021-07-02 16:13:03
 * @LastEditTime: 2021-07-09 11:19:51
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin-template/src/views/permission/index.vue
-->
<template>
  <div class="permission-wrap">
    <p class="form-title">权重设置</p>
    <div class="tab-wrap">
      <div class="tab-header">
        <base-btn :class="[activeIndex==1&&'active-btn','btn']" @click="setOptionList('channelLevel',1)">频道分级权限划分</base-btn>
        <base-btn :class="[activeIndex==2&&'active-btn','btn']" @click="setOptionList('channelTime',2)">时间权限划分</base-btn>
        <base-btn :class="[activeIndex==3&&'active-btn','btn']" @click="setOptionList('channelType',3)">频道类型权限划分</base-btn>
      </div>
      <TabContent ref="tabContent" :permission-key="permissionKey" :permission-data="permissionData" />
      <div class="tab-footer">
        <base-btn type="info" class="btn">取消</base-btn>
        <base-btn class="btn active-btn" @click="saveData">保存</base-btn>
      </div>
    </div>
  </div>
</template>

<script>
import TabContent from './tabContent.vue'
import setQueryParams from '@/utils/setQueryParams'
export default {
  components: {
    TabContent
  },
  props: {

  },
  data() {
    return {
      activeIndex: 1,
      permissionKey: 'channelLevel',
      permissionData: []
    }
  },
  computed: {
  },
  watch: {
    activeIndex(val) {
      this.getPermission()
    }
  },
  created() {
    this.getPermission()
  },
  mounted() {
  },
  methods: {
    setOptionList(key, index) {
      this.activeIndex = index
      this.permissionKey = key
    },
    async getPermission() {
      const query = {
        types: this.activeIndex
      }
      const params = {
        query: setQueryParams(query)
      }
      const { err, data } = await this.$get('/v1/grades', params)
      if (err) {
        this.$message.error(err.msg)
        return
      }
      this.permissionData = data.data
    },
    async saveData() {
      const params = JSON.parse(JSON.stringify(this.$refs.tabContent.updatePermission))
      params.types = this.activeIndex
      if (this.permissionKey === 'channelTime') {
        params.contents = params.contents.join('-')
      }
      const { err, data } = params.id ? await this.$put(`/v1/grades/${params.id}`, params) : await this.$post('/v1/grades', params)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '更新成功'
        })
        this.getPermission()
      }
    }

  }
}
</script>

<style scoped lang="scss">
.permission-wrap {
  padding: 57px 133px;

  .tab-wrap {
    margin-top: 51px;

    .tab-header {
      text-align: center;
      margin-bottom: 60px;

    }

    .tab-footer {
      text-align: center;
      margin-top: 28px;
    }

    .btn {
      width: 287px;
      height: 60px;
      font-size: 24px;
      background: #5675e8;
      border-radius: 4px;
      margin-right: 30px;
      opacity: .6;

      &.active-btn {
        opacity: 1;
      }
    }

  }
}
</style>
