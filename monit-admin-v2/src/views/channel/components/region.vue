<!--
 * @Author: your name
 * @Date: 2021-07-07 15:44:20
 * @LastEditTime: 2021-07-07 18:32:25
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /monit-admin-v2/src/views/channel/components/region.vue
-->
<template>
  <el-cascader
    v-model="selectedOptions"
    class="region-cascader"
    size="large"
    :options="options"
    @change="handleChange"
  />
</template>

<script>
import { provinceAndCityData, CodeToText } from 'element-china-area-data'
export default {
  components: {

  },
  props: {
    region: {
      type: String,
      default() {
        return ''
      }
    }
  },
  data() {
    return {
      selectedOptions: [],
      options: provinceAndCityData,
      address: ''
    }
  },
  computed: {

  },
  watch: {
    region(val) {
      if (val) {
        this.selectedOptions = []
        this.address = val
        const regionArr = val.split(' ')
        const firstCity = provinceAndCityData.filter(item => item.label === regionArr[0])
        this.selectedOptions.push(firstCity[0].value || 0)

        if (firstCity.length) {
          const secondCity = firstCity[0].children.filter(region => region.label === regionArr[1])
          this.selectedOptions.push(secondCity[0].value || 0)
        }
      }
    }
  },
  created() {
  },
  mounted() {

  },
  methods: {
    handleChange() {
      var loc = []
      for (let i = 0; i < this.selectedOptions.length; i++) {
        loc.push(CodeToText[this.selectedOptions[i]])
      }
      this.address = loc.join(' ')
    }
  }
}
</script>

<style scoped lang="scss">
.region-cascader {
  flex: 1;

  ::v-deep .el-input {
    height: 100%;
  }
}
</style>
