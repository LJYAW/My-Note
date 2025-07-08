<template>
  <div>
    <modal :id="id">
      <div slot="title">
        <span>{{ title }}</span>
      </div>
      <div slot="body">
        <el-image
          style="width: 640px; height: 320px"
          :src="img_url"
          fit="contain"
        ></el-image>
      </div>
    </modal>
  </div>
</template>

<script>
export default {
  props: ['id', 'img_url', 'title'],
  data() {
    return {}
  },
  components: {},

  computed: {},

  watch: {},

  methods: {},

  created() {},

  mounted() {}
}
</script>

<style scoped lang="scss"></style>
