/*
 * @Author: your name
 * @Date: 2021-04-16 15:09:40
 * @LastEditTime: 2021-04-22 19:37:28
 * @LastEditors: Please set LastEditors
 * @Description: In User Settings Edit
 * @FilePath: /tm-admin/src/view/public_sentiment/add_monitor/js/params.js
 */
import { toArray, toString } from './utils'

export const paramsMixin = {
  methods: {
    getParams() {
      const entity = this.$refs.entity
      const startTime = this.monitorDate[0]
      const endTime = this.monitorDate[1]
      const params = {
        id: Number(this.monitorId),
        channels: toString(this.channelTags),
        diffimages: toString(this.$store.state.monitor.reportList.images),
        diffkeywords: toString(this.$store.state.monitor.reportList.keyword),
        // diffkeywords: "百度,腾讯,阿里,字节跳动,拼多多,美团,滴滴",
        frequency: this.radio,
        mcompetitorname: toString(entity.entityList.entityListCompetitor.personageMap),
        // mcompetitorname: "百度,腾讯,阿里,字节跳动,拼多多,美团,滴滴",
        mcompetitoravatar: toString(entity.entityList.entityListCompetitor.avatarMap),
        // mcompetitorbrand: "百度,腾讯,阿里,字节跳动,拼多多,美团,滴滴",
        mcompetitorbrand: toString(entity.entityList.entityListCompetitor.brandMap),
        mcompetitorlogo: toString(entity.entityList.entityListCompetitor.logoMap),
        monitortimestart: startTime,
        monitortimeend: endTime,
        mselfname: toString(entity.entityList.entityListSelf.personageMap),
        // mselfname: "百度,腾讯,阿里,字节跳动,拼多多,美团,滴滴",
        mselfavatar: toString(entity.entityList.entityListSelf.avatarMap),
        mselfbrand: toString(entity.entityList.entityListSelf.brandMap),
        // mselfbrand: "百度,腾讯,阿里,字节跳动,拼多多,美团,滴滴",
        mselflogo: toString(entity.entityList.entityListSelf.logoMap),
        programs: toString(this.programTags),
        projectname: this.monitorName,
        status: 1,
        uid: 1
      }
      return params
    }
  }
}
