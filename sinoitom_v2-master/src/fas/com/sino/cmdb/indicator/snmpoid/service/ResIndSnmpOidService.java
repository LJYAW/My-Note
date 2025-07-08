package com.sino.cmdb.indicator.snmpoid.service;

import com.sino.base.common.util.ExcelUtil;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.product.entity.ProdSnmpIndItems;
import com.sino.cmdb.indicator.snmpoid.dao.ResIndSnmpOidDao;
import com.sino.cmdb.indicator.snmpoid.entity.ResIndSnmpOid;
import com.sino.cmdb.product.prodType.entity.ResItemParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;

/**
 * @ProjectName: SinoITOM_V2
 * @Package: com.sino.cmdb.indicator.snmpoid.service
 * @ClassName: ResIndSnmpOidService
 * @auther: Mr.Lp
 * @date: 2020/6/8 15:19
 * @desc: DOTO
 * Created with: IntelliJ IDEA
 */
@Service
@Transactional
public class ResIndSnmpOidService {

    private static Logger logger = LoggerFactory.getLogger(ResIndSnmpOidService.class);

    private static String objAttNames ="resIndSnmpOid,vendorId,vendorId@CmdbVendor@vendorID@dispName,resClassCode,resClassName,resTypeCode,resTypeName,prodModelID,prodModel,modelOID,osType,osVersion,osFeature,resTypeIndId,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indicatorItem,indItemName,mibFile,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status";
    private static String jsonAttNames ="id,vendorId,vendorName,resClassCode,resClassName,resTypeCode,resTypeName,prodModelID,prodModel,modelOID,osType,osVersion,osFeature,resTypeIndId,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indicatorItem,indItemName,mibFile,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status";
    private static String excelAttNames="resIndSnmpOid,vendorId,resClassCode,resClassName,resTypeCode,resTypeName,prodModelID,prodModel,modelOID,osType,osVersion,osFeature,resTypeIndId,indClassCode,indClassName,indGroupID,indGroupName,indItemID,indicatorItem,indItemName,mibFile,snmpObjName,snmpOID,getMethod,oidFlag,valueType,length,decimals,measureUnit,remark,status";

    @Autowired
    private ResIndSnmpOidDao resIndSnmpOidDao;

    private String lastMassage = "";
    @Transactional(readOnly = true)
    public String getLastMassage() {
        return lastMassage;
    }
    @Transactional(readOnly = true)
    public List<ResIndSnmpOid> getAll() {
        logger.debug("getAll...");

        return resIndSnmpOidDao.getAll();
    }
    @Transactional(readOnly = true)
    public String getJsonListStr(final List<ResIndSnmpOid> list) {
        return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
    }

    public void save(ResIndSnmpOid entity){

        logger.debug("Enter save...");
        resIndSnmpOidDao.save(entity);
        resIndSnmpOidDao.flush();
    }
    public void add(ResIndSnmpOid entity) throws SQLException {

        logger.debug("Enter add...");
        entity.setResIndSnmpOid(null);
        resIndSnmpOidDao.save(entity);
        //prodSnmpIndItemDao.addProdSnmpIndItems(item);
        resIndSnmpOidDao.flush();
    }
    @Transactional(readOnly = true)
    public String getJsonObjStr(final ResIndSnmpOid obj) {
        return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
    }

    public ResIndSnmpOid getById(int id){
        logger.debug("Enter getById...");
        return resIndSnmpOidDao.get(id);
    }
    public void batchSave(List<ResIndSnmpOid> items){
        logger.debug("Enter batchSave...");
        resIndSnmpOidDao.batchSave(items, 20);
    }

    public void delete(int id){
        logger.debug("Enter delete...");
        resIndSnmpOidDao.delete(id);
    }

    public void batchAudit(List<Integer> ids){
        logger.debug("Enter batchAudit...");
        resIndSnmpOidDao.batchAudit(ids);
    }

    public void deletes(String [] ids){
        logger.debug("Enter deletes...");
        for(String id:ids){
            delete(Integer.parseInt(id));
        }
    }

    public List<ResIndSnmpOid> getByDevTypeCode(String devTypeCode){
        logger.info("Enter getByDevTypeCode..");
        String hql=" from ResIndSnmpOid where status=1 and (resTypeCode= -1 or resTypeCode=?) and indClassCode=8 order by indicatorItem";
        return resIndSnmpOidDao.find(hql, Integer.parseInt(devTypeCode));
    }

    public List<ResIndSnmpOid> getByIndClass(){
        logger.info("Enter getByDevTypeCode..");
        String hql=" from ResIndSnmpOid where status=1 and indClassCode=9 order by indicatorItem";
        return resIndSnmpOidDao.find(hql);
    }


    public List<ResIndSnmpOid> getIfIndItemsByVendorAndmodelOID(String vendorId,String modelOID){
        logger.info("Enter getByVendorAndMOID..");
        String hql=" from ResIndSnmpOid where vendorId=? and indClassCode=8 and (modelOID='X' or modelOID=?) order by indicatorItem";
        return resIndSnmpOidDao.find(hql, Integer.parseInt(vendorId),modelOID);
    }

    public List<ResIndSnmpOid> getIfIndItemsByVendorAndmodelOIDAndItemName(String vendorId,String modelOID){
        logger.info("Enter getIfIndItemsByVendorAndmodelOIDAndItemName..");
        String hql=" from ResIndSnmpOid where vendorId=? and indClassCode=8 and (modelOID='X' or modelOID=?) and indicatorItem like 'Line_BwUtilRatio' order by indicatorItem";
        return resIndSnmpOidDao.find(hql, Integer.parseInt(vendorId),modelOID);
    }
    public List<ResIndSnmpOid> getByVendorId(int vendor) {
        String hql=" from ResIndSnmpOid where vendorId=? order by vendorId,resClassCode,resTypeCode";
        return resIndSnmpOidDao.find(hql, vendor);
    }
    public List<ResIndSnmpOid> getByVendorAndClass(int vendor,int classCode) {
        String hql=" from ResIndSnmpOid where vendorId=? and resClassCode=? order by vendorId,resClassCode,resTypeCode";
        return resIndSnmpOidDao.find(hql, vendor,classCode);
    }
    public List<ResIndSnmpOid> getByVendorAndClassAndType(int vendorId,int resClassCode, int resTypeCode) {
        String hql=" from ResIndSnmpOid where vendorId=? and resClassCode=? and resTypeCode=? order by vendorId,resClassCode,resTypeCode ";
        return resIndSnmpOidDao.find(hql, vendorId,resClassCode,resTypeCode);
    }
    public List<ResIndSnmpOid> getAllOrderByVendor() {
        String hql=" from ResIndSnmpOid order by vendorId,resClassCode,resTypeCode";
        return resIndSnmpOidDao.find(hql);
    }

    public List<ResItemParam> getTreeByVendor() {
        return resIndSnmpOidDao.getVendor();
    }
    public List<ResItemParam> getTreeVendor(int parseInt) {
        return resIndSnmpOidDao.getByVendor(parseInt);
    }
    public List<ResItemParam> getTreeByClassCode(int parseInt, int parseInt2) {
        return resIndSnmpOidDao.getByClassCode(parseInt,parseInt2);
    }
    public boolean importExcel(InputStream stream) throws SQLException{
        this.lastMassage = "";

        ExcelUtil<ResIndSnmpOid> excelUtil = new ExcelUtil<ResIndSnmpOid>(ResIndSnmpOid.class);
        List<ResIndSnmpOid> list = excelUtil.getObjListFrom(stream, excelAttNames);

        if( list.isEmpty() ){
            this.lastMassage = "导入失败！请检查导入文件数据！";
            System.out.println("导入失败");
            return false;
        }
        int addNum=0;
        int updateNum=0;
        for(int i=0; i<list.size(); i++){
            ResIndSnmpOid entity = list.get(i);
            if(entity.getResIndSnmpOid()==null){
                add(entity);
                addNum++;
            }else{
                ResIndSnmpOid resIndSnmpOid =getById(entity.getResIndSnmpOid());
                if(resIndSnmpOid!=null){//存在 update
                    save(entity);
                    updateNum++;
                }else{//不存在add
                    add(entity);
                    addNum++;
                }
            }
        }
        this.lastMassage = String.format("导入完毕！更新%d条记录,新增%d条记录。",updateNum, addNum);

        return true;
    }

}
