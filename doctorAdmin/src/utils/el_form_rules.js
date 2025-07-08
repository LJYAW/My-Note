export default {
  intro: [
    {
      required: true,
      message: '请输入视频简介',
      trigger: 'blur'
    },
    {
      min: 1,
      max: 200,
      message: '视频简介长度在 200 个字符以内',
      trigger: 'blur'
    }
  ],
  title: [
    {
      required: true,
      message: '请输入视频标题',
      trigger: 'blur'
    },
    {
      min: 8,
      max: 40,
      message: '视频标题长度在 8 到 40 个字符',
      trigger: 'blur'
    }
  ],
  tagText: [
    {
      required: false,
      message: '请添加视频标签',
      trigger: 'blur'
    }
  ]
}
