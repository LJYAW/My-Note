<template>
  <div>
    <model :id="'bgMusicM'" ref="bgMusicM" @close="close">
      <div slot="title">
        请选择音乐

        <audioPlay :url="audio_url" :play="play" />
      </div>
      <div slot="body">
        <div>
          <ul class="d-flex fc-999 type-select">
            <li class="fc-333 mr-20px">分类：</li>
            <li :class="['cp mr-10px hove-c', { active: type_name == '全部' }]" @click="changeType('全部')">全部</li>
            <li :class="['cp mr-10px hove-c', { active: type_name == name }]" v-for="(data, name) in type_list" @click="changeType(name)" :key="name">{{ name }}</li>
          </ul>
          <div class="mt-20px mb-25px w-50"></div>

          <div>
            <ul class="muisc-list d-flex flex-column">
              <li v-for="(item, index) in bg_muisc_list" :key="index" @click="changeId(item, index)" class="item flex-1 d-flex justify-content-between align-items-center px-15px">
                <div>
                  <i :class="['iconfont  fc-c fz-20px', [play && audio_index == index ? 'iconzanting' : 'iconbofang1']]" @click.stop="audioPaly(item, index)"></i>
                  {{ item.name }}
                </div>

                <div>
                  <!-- <span class="mr-20px fc-999">{{ '00000' }}</span> -->
                  <span>{{ item.duration_ms | msToTime }}</span>
                  <input type="checkbox" :checked="bg_music_id == item.id" />
                </div>
              </li>
            </ul>
          </div>

          <div class="my-muisc-list">
            <div class="d-flex justify-content-between align-items-center mb-10px">
              <p class="mb-15px">上传的本地音乐</p>

              <upload :file_type="'audio/mpeg'" @getFileUrl="getFileUrl" />
            </div>

            <ul class="muisc-list d-flex flex-column">
              <li v-for="(item, j) in my_muisc_list" :key="j" @click="changeId(item, j)" class="item flex-1 d-flex justify-content-between align-items-center px-15px">
                <div>
                  <i :class="['iconfont  fc-c fz-20px', [play && audio_index == 'my_' + j ? 'iconzanting' : 'iconbofang1']]" @click.stop="audioPaly(item, 'my_' + j)"></i>
                  {{ item.name }}
                </div>
                <div>
                  <span>{{ item.duration_ms | msToTime }}</span>
                  <input type="checkbox" :checked="bg_music_id == item.id" />
                  <i class="iconfont icondelect hove-c ml-10px fz-14px cp" @click.stop="deletMusic(item)" style="color: #797979"></i>
                </div>
              </li>
            </ul>
          </div>

          <div slot="foot">
            <div class="text-center mb-30px">
              <el-button @click="subMit()" round type="primary" size="mini" style="width: 100px">
                确认
              </el-button>
            </div>
          </div>
        </div>
      </div>
    </model>
  </div>
</template>

<script>
import audioPlay from '../../../components/audio_play'

export default {
  name: 'bgMuisc',
  props: ['bg_music', 'custom'],
  data() {
    return {
      key_word: '',
      type_list: [],
      type_name: '全部',
      bg_muisc_list: [],
      bg_music_id: null,
      audio_url: '',
      play: false,
      audio_index: null
    }
  },
  components: { audioPlay },

  computed: {
    my_muisc_list() {
      return this.copyList(this.custom)
    }
  },

  watch: {},

  methods: {
    getFileUrl(url, file) {
      console.log(url)
      var formData = new FormData()
      formData.append('title', file.name)
      formData.append('resource', file)

      this.$post('/intelligent-creation/upload-bg-music', formData).then((res) => {
        if (res.data.code == '0000') {
          this.$alertMsg('上传成功')
          this.$emit('upDataBgMusic')
        } else {
          this.$alertMsg('上传失败')
        }
      })
    },
    audioPaly(item, index) {
      this.audio_index = index
      if (this.audio_url == item.audio_url) {
        this.play = !this.play
      } else {
        this.audio_url = item.audio_url
        this.play = true
      }
    },
    close() {
      this.$emit('close')
    },
    copyList(data) {
      let list = JSON.parse(JSON.stringify(data))
      list.forEach((item) => {
        item.checkout = false
      })
      return list.reverse()
    },
    changeType(name) {
      this.type_name = name
      this.play = false
      let list = JSON.parse(JSON.stringify(this.type_list))
      if (name == '全部') {
        this.bg_muisc_list = this.copyList(this.bg_music)
      } else {
        this.bg_muisc_list = list[name]
      }
    },
    changeId(item, index) {
      this.bg_music_id = this.bg_music_id == item.id ? null : item.id
      this.$emit('getBgmusicId', this.bg_music_id)
    },
    setTypeLst() {
      let list = JSON.parse(JSON.stringify(this.bg_music)).reverse()
      let type_list = {}

      list.forEach((item) => {
        let key = item.category_name
        if (!type_list[key]) {
          type_list[key] = [item]
        } else {
          type_list[key].push(item)
        }
      })
      this.type_list = type_list
    },
    subMit() {
      this.$refs.bgMusicM.close()
      console.log('确认')
    },
    deletMusic(item) {
      this.$deleteRun('/intelligent-creation/delete-bg-music', { data: { id: item.id } }).then((res) => {
        this.$alertMsg(res.data.msg)
        this.$emit('upDataBgMusic')
      })
    }
  },

  created() {
    this.setTypeLst()
    this.changeType('全部')
  },

  mounted() {}
}
</script>

<style scoped lang="scss">
.muisc-list {
  max-height: 320px;
  overflow: hidden;
  overflow-y: auto;
  .item {
    height: 40px;
    min-height: 40px;
    background-color: #f0f0f0;
    margin-bottom: 14px;
  }
  margin-bottom: 15px;
  padding-bottom: 15px;
}
.type-select {
  .active {
    color: $c;
  }
}
.my-muisc-list {
  padding-top: 20px;
  border-top: 1px solid rgb(230, 230, 230);
}
</style>
