/*
 * @Author: your name
 * @Date: 2021-10-25 18:26:53
 * @LastEditTime: 2021-11-25 17:18:38
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/base-com-data.js
 */

// 基础 banner 数据格式
export const baseHeaderBanner = () => ({
  bannerSrc: '',
  bannerSize: { width: 678, height: 252 },
  subBannerSize: { width: 300, height: 400 },
  subBannerFileName: '',
  subBannerSrc: '',
  bannerFileName: '',
  imgMergeUrl: '',
  imgMergeFileName: '',
  calssData: {},
  group: {}
})

// 基础每周上新配置
export const baseWeekUpdate = () => ({
  title: '新谷上拍',
  subTitle: 'In Vogue Color',
  list: [
    {
      calssData: {},
      group: {}
    },
    {
      calssData: {},
      group: {}
    },
    {
      calssData: {},
      group: {}
    }
  ],
  classification: [
    {
      calssData: {},
      group: {}
    },
    {
      calssData: {},
      group: {}
    },
    {
      calssData: {},
      group: {}
    }
  ]
})

// 活动按钮数据
export const baseBuiltBtns = () => ({})

// 基础单品 数据
export const baseSingle = (total = 4) => ({
  comType: total,
  title: '',
  subTitle: '',
  calssData: {},
  bannerSize: { width: 678, height: 300 },
  banner: [
    {
      bannerSrc: '',
      bannerFileName: '',
      group: {},
      calssData: {}
    }
  ],
  commodity: (() => {
    const arr = []
    for (let i = 0; i < total; i++) {
      arr.push({
        group: {}
      })
    }
    return arr
  })()
})

// 基础数据格式
export const baseAppData = {
  HeaderBanner: [baseHeaderBanner()],
  BuiltBtns: baseBuiltBtns(),
  WeekUpdate: baseWeekUpdate(),
  Single: [baseSingle()]
}

// 检查数据 格式是否正确
/**
 *
 * @param {Object} appData
 * @param {String} key
 * @returns return or false
 */
export const checkoutAppData = (appData, key) => {
  const arr = appData[key]

  const checkeValueEmpty = (obj) => {
    let err = false
    for (const key in obj) {
      if (!obj[key]) {
        err = true
        break
      }
    }
    return err
  }

  const checkWeekUpdate = (arr) => {
    const listArr = arr.list
    const classArr = arr.classification
    let classErr = ''
    let listErr = ''

    for (let i = 0; i < listArr.length; i++) {
      const data = listArr[i]
      if (!data.group.id) {
        listErr = '请选择 新谷上拍 单品数据'
        console.warn(listErr)
        break
      }
    }

    for (let i = 0; i < classArr.length; i++) {
      const data = classArr[i]
      classErr = checkeValueEmpty(data)
      if (!data.calssData.id) {
        classErr = '请选择 新谷上拍 分类数据'
        console.warn(listErr)
        break
      }
    }

    return listErr || classErr
  }

  const checkSingle = (arr) => {
    console.log('🚀 ~ file: base-com-data.js ~ line 144 ~ checkSingle ~ arr', arr)
    let bannerErr = ''
    let listErr = ''
    for (let i = 0; i < arr.length; i++) {
      const bannerList = arr[i].banner
      console.log('🚀 ~ file: base-com-data.js ~ line 150 ~ checkSingle ~ bannerList', bannerList)
      const commodityList = arr[i].commodity

      for (let j = 0; j < bannerList.length; j++) {
        const data = bannerList[j]
        console.log('🚀 ~ file: base-com-data.js ~ line 153 ~ checkSingle ~ data', data)
        if (!data.bannerSrc) {
          bannerErr = '请上传 单品 Banner 图'
          console.error(`${data}请上传 单品 Banner 图`)
          break
        }
        if (!data.calssData.id) {
          bannerErr = '请选择 Banner 数据'
          console.error(`${data}请选择 Banner 数据`)
          break
        }
      }

      for (let k = 0; k < commodityList.length; k++) {
        const data = commodityList[k]
        if (!data.group.id) {
          listErr = '请选择单品数据'
          console.error(`${data}请选择单品数据`)
          break
        }
      }
    }
    return bannerErr || listErr
  }

  const checkeHander = (arr) => {
    let err = ''
    for (let i = 0; i < arr.length; i++) {
      const data = arr[i]
      if (!data.bannerSrc) {
        err = '请上传 首页Banner 图'
        console.log('没有填写完整数据', data, key)
        break
      }

      if (!data.group && !data.calssData) {
        err = '请选择 首页Banner 单品数据'
        console.log('没有填写完整数据', data, key)
        break
      }
    }
    return err
  }

  const checkeBuiltBtns = () => {
    return ''
  }

  const check = {
    HeaderBanner: checkeHander,
    Single: checkSingle,
    BuiltBtns: checkeBuiltBtns,
    WeekUpdate: checkWeekUpdate
  }
  return check[key](arr)
}
