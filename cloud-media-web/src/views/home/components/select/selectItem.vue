<!--
 * @Author: your name
 * @Date: 2021-07-27 17:13:23
 * @LastEditTime: 2021-08-24 16:45:11
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/select/selectItem.vue
-->
<template>

  <div class="select-item">
    <!-- <input v-model="defaultVal" class="select" readonly @click.stop="showOption()">
    <ul v-show="optionShow" class="select-option">
      <li v-for="val in option" :key="val" class="option" @click.stop="handlerSelect(val)">{{ val }}</li>
    </ul> -->
    <el-select v-model="defaultVal" value-key="id" @change="handlerSelect">
      <el-option
        v-for="item in option"
        :key="item.id"
        :label="item.name"
        :value="item"
      />
    </el-select>
  </div>
</template>

<script>
export default {
  components: {

  },

  props: {
    option: {
      type: Array,
      default: () => []
    },
    defaultValue: {
      type: Object,
      default: () => {}
    }
  },
  data() {
    return {
      defaultVal: '',
      optionShow: false
    }
  },
  computed: {

  },
  watch: {
    defaultValue: {
      deep: true,
      immediate: true,
      handler(val, oldVal) {
        this.defaultVal = val || this.option[0]
      }
    }
  },
  created() {

  },
  mounted() {

  },
  methods: {
    // showOption() {
    //   this.optionShow = true
    // },
    // closeOption() {
    //   this.optionShow = false
    // },
    handlerSelect() {
      this.$emit('onChange', this.defaultVal)
    }
  }
}
</script>

<style scoped lang="scss">
//.select {
//   width: 100%;
//   height: 30px;
//   text-align: center;
//   cursor: pointer;
//   background: #efefef;
//   opacity: .8;
//   flex: 1;
//   font-size: 12px;
//   color: #404040;

//   &:hover {
//     opacity: .5;
//     color: #5675e8;
//   }
// }

// .select-option {
//   position: absolute;
//   width: 100%;
//   top: 30px;
//   left: 0;

//   .option {
//     background: #eee;
//     cursor: pointer;
//     font-size: 12px;
//     line-height: 30px;
//     color: #404040;
//     text-align: center;

//     &:hover {
//       //   opacity: .8;
//       color: #5675e8;
//     }
//   }
// }
::v-deep.el-select,
::v-deep.el-select>.el-input ,
::v-deep.el-select .el-input__inner {
  width: 100%;
  height: 30px;
  line-height: 30px;
  text-align: center;
  cursor: pointer;
  background: rgba(239,239,239,.8);
  flex: 1;
  font-size: 12px;
  color: #404040;
  border: 0;

  .el-input__icon {
    display: flex;
    justify-content: center;
    align-items: center;

    &::before {
      content: " ";
      width: 0;
      height: 0;
      border-left: 3px solid transparent;
      border-right: 3px solid transparent;
      border-top: 5px solid black;
    }
  }
}

</style>
