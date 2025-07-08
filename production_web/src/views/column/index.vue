/* eslint-disable no-undef */
/* eslint-disable no-undef */
/* eslint-disable no-undef */
/* eslint-disable no-undef */
/* eslint-disable no-undef */
<!--
 * @Author: your name
 * @Date: 2021-01-15 15:21:23
 * @LastEditTime: 2021-03-08 16:13:48
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /work_order_web/src/views/tv_program/CreateProgram.vue
-->
<template>
  <div class="create-program">
    <channel ref="channel" class="channel" />

    <div class="btns">
      <base-btn @click="createdItem()">提交</base-btn>
      <base-btn type="" @click="cancel">取消</base-btn>
    </div>
  </div>
</template>

<script>
import channel from './components/CreateItem'
import { createdColumn } from '@/utils/send-msg-qt.js'
export default {
  name: 'CreateProgram',
  components: {
    channel
  },
  props: {

  },
  data() {
    return {
      active_id: null,
      ruleForm: {
        channel_name: '',
        column_name: '',
        column_type: ''
      }
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
    createdItem() {
      this.$refs.channel.checkFrom(this.submit)
    },
    submit(form) {
      const second_category_ids = this.$refs.channel.secondCheckId.join()// 数组转成字符串
      const first_category_ids = this.$refs.channel.firstCheckId.join()// 数组转成字符串

      const parasm = {
        'channel_id': this.$refs.channel.form.channel_id,
        'item_name': this.$refs.channel.form.item_name,
        'first_category_ids': first_category_ids,
        'second_category_ids': second_category_ids
      }
      this.$post('/epg-task/item-add', parasm).then(res => {
        this.active_id = res.data.item_id
        this.$message({
          message: '创建成功!',
          type: 'success'
        })
        createdColumn(true)
      }).catch(res => {
        this.$message({
          message: res.msg,
          type: 'warning'
        })
        createdColumn(false)
      })
    },
    cancel() {
      createdColumn(false)
    }
  }
}
</script>

<style scoped lang="scss">
.create-program{
  background: #fff;
  padding: 20px;
//   width: 100%;
  height: 100%;
  box-sizing: border-box;
  .channel {
    width: 100%;
  }
  .btns {
    display: flex;
    justify-content: center;
    margin-top: 20px;
  }
}
</style>
