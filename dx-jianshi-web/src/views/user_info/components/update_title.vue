<!--
 * @Author: your name
 * @Date: 2020-12-10 16:09:16
 * @LastEditTime: 2020-12-10 16:50:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhijian_web/src/views/user_info/components/update_title.vue
-->
<template>
  <div>
    <model :id="'updateTitle'"
      @close="close">
      <div slot="title">修改素材名称</div>
      <div slot="body"
        class="d-flex flex-column pt-40px">
        <div class="mb-50px d-flex">
          <p>素材名称:</p>
          <el-input placeholder="请输入素材名称" v-model='title'></el-input>
        </div>

        <div class="text-center mb-30px">
          <el-button type="primary"
            size="mini"
            round
            @click="save()">确定</el-button>

        </div>
      </div>
    </model>
  </div>
</template>

<script>
export default {
  props: ['updateItem', 'type'],
  data() {
    return {
      title: ''
    }
  },
  computed: {},
  watch: {},
  methods: {
    close() {
      this.$emit('Modalclose')
    },
    save() {
      let params = {}
      let url = ''
      if (this.type) {
        params = {
          bg_music_id: this.updateItem.id,
          title: this.title
        }
        url = '/intelligent-creation/edit-bg-music-info'
      } else {
        params = {
          user_resource_id: this.updateItem.id,
          title: this.title
        }
        url = '/intelligent-creation/edit-user-resource-info'
      }
      this.$post(url, params).then(res => {
        if (res.data.code == '0000') {
          this.$parent.getData()
          this.close()
        }
        this.$alertMsg(res.data.msg)
      })
    }
  },
  components: {},
  created() {},
  mounted() {
    this.title = this.updateItem.title
  }
}
</script>

<style scoped lang="scss">
#updateTitle {
  /deep/.modal-wrap {
    width: 300px;
    p {
      line-height: 40px;
      width: 100px;
    }
  }
}
</style>
