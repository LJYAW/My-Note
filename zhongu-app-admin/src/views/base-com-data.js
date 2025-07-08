/*
 * @Author: your name
 * @Date: 2021-10-25 18:26:53
 * @LastEditTime: 2021-11-06 23:24:34
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /zhongu-app-admin/src/views/home-manage/base-com-data.js
 */

// 基础 banner 数据格式
export const baseHeaderBanner = () => ({
  bannerSrc: '',
  subBannerFileName: '',
  subBannerSrc: '',
  bannerFileName: '',
  calssData: {},
  group: {}
})

// 基础每周上新配置
export const baseWeekUpdate = () => ({
  title: '每日上新',
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
  banner: [
    {
      bannerSrc: '',
      bannerFileName: '',
      calssData: {},
      group: {}
    }
  ],
  commodity: (() => {
    const arr = []
    for (let i = 0; i < total; i++) {
      arr.push({
        bannerSrc: '',
        bannerFileName: '',
        calssData: {},
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
