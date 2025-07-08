<!--
 * @Author: your name
 * @Date: 2021-07-27 17:13:23
 * @LastEditTime: 2021-09-16 10:18:22
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /cloud-media-web/src/views/home/components/select/selectItem.vue
-->
<template>

  <div class="select-item">
    <el-select
      v-model="defaultVal"
      :popper-append-to-body="false"
      value-key="name"
      :class="defaultVal.id!==0?'active':''"
      @change="handlerSelect"
    >
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
    // this.defaultVal = this.option[0]
  },
  mounted() {

  },
  methods: {
    handlerSelect() {
      this.$emit('onChange', this.defaultVal)
    }
  }
}
</script>

<style scoped lang="scss">

::v-deep.el-select {

  .el-input {

    width: 100%;
    height: 30px;
    line-height: 30px;
    text-align: center;
    cursor: pointer;
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

  .el-input__inner {
    background: rgba(239,239,239,.8);
    width: 100%;
    height: 30px;
    line-height: 30px;
    text-align: center;
    cursor: pointer;
    flex: 1;
    font-size: 12px;
    color: #404040;
    border: 0;
    border-radius: 0;
  }

  &.active {

    .el-input ,
    .el-input__inner {
      color: #5675e8;
      font-weight: 600;
    }
  }
}

.el-dropdown-menu {
  width: 128px;
  margin-top: 8px;
  padding: 10px;

  .el-dropdown-menu__item {
    padding: 9px 0 9px 16px;
    font-size: 12px;
    line-height: 12px;
    color: #404040;

    &:hover,
    &:focus {
      background: #f7f8f9;
      border-radius: 4px;
    }
  }

}

.popper__arrow {
  display: none;
}

.el-select-dropdown__item {
  padding: 9px 16px 9px 16px;
  font-size: 12px;
  line-height: 16px;
  margin: 0 10px;
  color: #404040;

  &.selected {
    color: #5675e8;
    font-weight: bold;

  }

  &:hover,
  &:focus {
    background: #f7f8f9;
    border-radius: 4px;
  }
}

</style>
