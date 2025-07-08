<template>
  <div>
    <div class="question-box cp"
         @click="playVideo()">
      <img src="../assets/images/question.svg"
           width="20"
           height="20">
    </div>
    <!-- 播放片头片尾 -->
    <showVideoM v-if="modal_show_name == 'showVideoM'"
                @Modalclose="Modalclose"
                :video_details="video_details"
                :video_options="video_options" />
  </div>

</template>

<script>
import showVideoM from './show_video_m'
export default {
  props: ['video_url', 'video_title'],
  data() {
    return {
      modal_show_name: '',
      video_details: {
        title: ''
      },
      video_options: ''
    }
  },
  computed: {},
  watch: {},
  methods: {
    Modalclose() {
      this.modal_show_name = ''
    },
    playVideo() {
      this.modal_show_name = 'showVideoM'
      this.video_details.title = this.video_title
      this.video_options = {
        width: '800',
        height: '450',
        autoplay: false,
        src: this.video_url
      }

      this.$nextTick(() => {
        this.$store.commit('modalShow', 'showVideoM')
      })
    }
  },
  components: {
    showVideoM
  },
  created() {},
  mounted() {}
}
</script>

<style scoped lang="scss">
</style>
