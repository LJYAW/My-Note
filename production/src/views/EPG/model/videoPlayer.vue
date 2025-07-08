<template>
  <div class="item">
    <div class="player">
      <video-player
        ref="videoPlayer"
        class="vjs-custom-skin"
        :options="playerOptions"
        :playsinline="true"
        @play="onPlayerPlay($event)"
        @pause="onPlayerPause($event)"
        @ended="onPlayerEnded($event)"
        @loadeddata="onPlayerLoadeddata($event)"
        @waiting="onPlayerWaiting($event)"
        @playing="onPlayerPlaying($event)"
        @timeupdate="onPlayerTimeupdate($event)"
        @canplay="onPlayerCanplay($event)"
        @canplaythrough="onPlayerCanplaythrough($event)"
        @ready="playerReadied"
        @statechanged="playerStateChanged($event)"
      />
    </div>
  </div>
</template>

<script>

export default {
  props: {
    playerOptions: {
      type: Object,
      default() {
        return {
          height: '360',
          autoplay: true,
          muted: true,
          language: 'en',
          playbackRates: [0.7, 1.0, 1.5, 2.0],
          sources: [{
            type: 'video/mp4',
            // mp4
            src: 'http://vjs.zencdn.net/v/oceans.mp4'
            // webm
            // src: "https://cdn.theguardian.tv/webM/2015/07/20/150716YesMen_synd_768k_vp8.webm"
          }],
          poster: 'https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg'
        }
      }
    }
  },
  data() {
    return {
      // videojs options

    }
  },
  computed: {
    player() {
      return this.$refs.videoPlayer.player
    }
  },
  mounted() {
  },
  methods: {
    // listen event
    onPlayerPlay(player) {
      // console.log('player play!', player)
    },
    onPlayerPause(player) {
      // console.log('player pause!', player)
    },
    onPlayerEnded(player) {
      // console.log('player ended!', player)
    },
    onPlayerLoadeddata(player) {
      console.log('player Loadeddata!', player.duration)
    },
    onPlayerWaiting(player) {
      // console.log('player Waiting!', player)
    },
    onPlayerPlaying(player) {
      // console.log('player Playing!', player)
    },
    onPlayerTimeupdate(player) {
      this.$store.commit('setVideoCurrentTime', player.currentTime() * 1000)
      console.log('🚀 ~ file: videoPlayer.vue ~ line 104 ~ onPlayerTimeupdate ~ player.currentTime()', player.currentTime())
    },
    onPlayerCanplay(player) {
      // console.log('player Canplay!', player)
    },
    onPlayerCanplaythrough(player) {
      // console.log('player Canplaythrough!', player)
    },

    // or listen state event
    playerStateChanged(playerCurrentState) {
      // console.log('player current update state', playerCurrentState)
    },

    // player is ready
    playerReadied(player) {
      // seek to 10s
      // player.currentTime(10)
      //   console.log('example 2 ready!', player)
      //   player.hotkeys({
      //     volumeStep: 0.1,
      //     seekStep: 5,
      //     enableModifiersForNumbers: false,
      //     fullscreenKey: function(event, player) {
      //       console.log("🚀 ~ file: videoPlayer.vue ~ line 129 ~ playerIsReady ~ event", event)

    //         // override fullscreen to trigger when pressing the F key or Ctrl+Enter
    //       return ((event.which === 70) || (event.ctrlKey && event.which === 13));
    //     }
    //   })
      // console.log('example 01: the player is readied', player)
    }
  }
}
</script>

