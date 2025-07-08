<!--
 * @Author: your name
 * @Date: 2021-08-03 14:42:17
 * @LastEditTime: 2021-08-25 18:02:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/manage-center/enterprise-info/index.vue
-->
<template>
  <div class="enterprise-info-wrap">
    <Info @update="update" />
    <Content @update="update" />
  </div>
</template>

<script>
import Info from './info.vue'
import Content from './content.vue'
import { mapGetters } from 'vuex'
export default {
  components: {
    Info,
    Content
  },
  props: {

  },
  data() {
    return {
    }
  },
  computed: {
    ...mapGetters(['companyInfo'])
  },
  watch: {

  },
  created() {
  },
  mounted() {

  },
  methods: {
    async update(obj) {
      const { err, res } = await this.$put(`/companies?${obj.id}`, obj)
      if (err) {
        this.$message.error(err.msg)
      } else {
        this.$message({
          type: 'success',
          message: '更新成功'
        })
        this.$store.dispatch('company/getCompanyInfo')
      }
    }
  }
}
</script>

<style scoped lang="scss">
.enterprise-info-wrap {
  min-height: 500px;
}
</style>
