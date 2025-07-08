import self from '@/main.js'
export default function(params) {
  let obj = Object.assign(
    {
      content: null, //必传
      width: 500,
      height: 300,
      data: {},
      title: '',
      shade: true,
      callBack: () => {}
    },
    params
  )
  // let $layer = layer(Vue)
  self.$layer.iframe({
    content: {
      content: obj.content,
      parent: self,
      data: obj.data
    },
    area: [obj.width, obj.height],
    title: obj.title,
    maxmin: false,
    shade: obj.shade, //是否显示遮罩
    shadeClose: false,
    cancel: obj.callBack,
    move: false
  })
}
