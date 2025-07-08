<!--
 * @Author: your name
 * @Date: 2021-07-02 16:11:57
 * @LastEditTime: 2021-07-05 15:01:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin-template/src/views/home/index.vue
-->
<template>
  <div class="homepage">
    <summaryMessage />
    <notesNotice />
  </div>
</template>

<script>
import summaryMessage from './components/summaryMessage'
import notesNotice from './components/notesNotice'
export default {
  components: {
    summaryMessage,
    notesNotice
  },
  props: {

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

  }
}
</script>
<style lang="scss">
@import "../home/scss/index.scss"
</style>
