<!--
 * @Author: your name
 * @Date: 2021-10-20 18:21:19
 * @LastEditTime: 2021-10-21 11:09:17
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/components/base/FormPresend.vue
-->
<template>
  <div class="form-presend">
    <p class="form-label">{{ label }}</p>
    <slot />
  </div>
</template>

<script>
export default {
  components: {

  },
  props: {
    label: {
      type: String,
      default() {
        return ''
      }
    }
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

<style scoped lang="scss">
.form-presend {
  display : inline-flex;
  align-items : center;
  border-radius : 4px;
  border : 1px solid rgba(64,64,64,.4);
  padding : 0px 10px;
  &:hover {
    border-color : #5675E8;
  }
  .form-label {
    color : #404040;
    opacity : .4;
    border-right : 1px solid rgba(216,216,216,.6);
  }
}

</style>
