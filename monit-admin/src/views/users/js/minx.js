/*
 * @Author: your name
 * @Date: 2021-07-08 11:26:46
 * @LastEditTime: 2021-07-16 11:12:15
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/users/js/minx.js
 */
import { mapGetters } from 'vuex'
export default {
  data: function() {
    return {
    }
  },
  computed: {
    ...mapGetters(['channelData'])
  },
  created() {
    this.channelData.length <= 0 ? this.initChannel() : ''
  },
  methods: {
    async initChannel() {
      const params = {
        page: 1,
        limit: 200,
        query: ''
      }
      await this.$store.dispatch('channel/getChannelData', params)
    }
  }
}
