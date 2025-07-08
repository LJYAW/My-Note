/**
 * 
 */
package com.sino.cmdb.indicator.report.tmplcell.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.ack.resources.entity.AckResources;
import com.sino.ack.resources.entity.ParamSnmpRes;
import com.sino.base.common.util.BeanUtils;
import com.sino.base.common.util.JsonUtils;
import com.sino.cmdb.indicator.report.template.dao.RptTmplFieldsCellDao;
import com.sino.cmdb.indicator.report.template.entity.RptTemplate;
import com.sino.cmdb.indicator.report.template.entity.RptTmplFieldsCell;
import com.sino.cmdb.indicator.report.template.service.RptTemplateService;
import com.sino.cmdb.indicator.report.tmplcell.dao.RptTmplCellDao;
import com.sino.cmdb.indicator.report.tmplcell.entity.RptTmplCell;


/**
 * @author Mr.LP
 * @date 2019-7-22上午9:37:24
 * @className RptTmplCellService
 *
 * @Description TODO
 *
 */

//Spring Bean的标识.
@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class RptTmplCellService {
	
	private static Logger logger = LoggerFactory.getLogger(RptTmplCellService.class);
	
	private static String objAttNames = "tmplCellId,tmplId,cellEnName,cellDispName,borderStyle,rowNo,colNo,keyWidth,valueWidth,height,align,color,font,size,isBold,valuePos,valueType,valueCellWidth,valueAlign";
	private static String jsonAttNames = "id,tmplId,cellEnName,cellDispName,borderStyle,rowNo,colNo,keyWidth,valueWidth,height,align,color,font,size,isBold,valuePos,valueType,valueCellWidth,valueAlign";
	
	@Autowired
	private RptTmplCellDao rptTmplCellDao;
	
	@Autowired
	private RptTmplFieldsCellDao rptTmplFieldsCellDao;
	
	@Autowired
	private RptTemplateService rptTemplateService;
	
	@Transactional(readOnly = true)
	public String getJsonListStr(final List<RptTmplCell> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public String getJsonObjStr(final RptTmplCell obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	
	@Transactional(readOnly = true)
	public RptTmplCell getById(int tmplCellId){
		logger.debug("enter getById...");
		return rptTmplCellDao.get(tmplCellId);
	}
	
	@Transactional(readOnly = true)
	public List<RptTmplCell> getAll() {
		
		return rptTmplCellDao.getAll();
	}
	
	public List<RptTmplCell> getByTmplId(int tmplId) {
		String hql=" from RptTmplCell where tmplId = ? order by rowNo,colNo ASC";
		return rptTmplCellDao.find(hql, tmplId);
	}
	
	public void save(List<RptTmplCell> celllist,String id,String totalWidth,List<RptTmplFieldsCell> fieldsCelllist,String indItemID){
		logger.debug("Enter save...");
		
		RptTemplate template = new RptTemplate();
		template = rptTemplateService.getById(Integer.parseInt(id));
		if(totalWidth!=null){
			String total [] = totalWidth.split(",");
			int widths = 0;
			widths = (Integer.parseInt(total[0]) + Integer.parseInt(total[1])) * Integer.parseInt(total[2]) ;
			template.setTableWidth(widths);
			rptTemplateService.save(template);
		}
		
		
		List<RptTmplCell> cellList = new ArrayList<RptTmplCell>();
		for(int i=0;i<celllist.size();i++){
        	
        	RptTmplCell tmplCell = new RptTmplCell();
        	tmplCell.setRowNo(celllist.get(i).getRowNo());
        	tmplCell.setColNo(celllist.get(i).getColNo());
        	tmplCell.setCellDispName(celllist.get(i).getCellDispName());
        	tmplCell.setCellEnName(celllist.get(i).getCellEnName());
        	tmplCell.setBorderStyle(1);
        	tmplCell.setTmplId(celllist.get(i).getTmplId());
        	tmplCell.setKeyWidth(celllist.get(i).getKeyWidth());
        	tmplCell.setValueWidth(celllist.get(i).getValueWidth());
        	tmplCell.setHeight(celllist.get(i).getHeight());
        	tmplCell.setAlign(celllist.get(i).getAlign());
        	tmplCell.setColor(celllist.get(i).getColor());
        	tmplCell.setFont(celllist.get(i).getFont());
        	tmplCell.setSize(celllist.get(i).getSize());
        	tmplCell.setIsBold(celllist.get(i).getIsBold());
        	tmplCell.setValuePos("right");
        	tmplCell.setValueCellWidth(celllist.get(i).getValueCellWidth());
        	tmplCell.setValueType(celllist.get(i).getValueType());
        	cellList.add(tmplCell);
        }
		
		rptTmplCellDao.batchSave(cellList, 20);
		
		List<RptTmplFieldsCell> fieldsList = new ArrayList<RptTmplFieldsCell>();
		if(fieldsCelllist!=null&&fieldsCelllist.size()>0){
			for (int i = 0; i < fieldsCelllist.size(); i++) {
				RptTmplFieldsCell fieldsCell = new RptTmplFieldsCell();
				fieldsCell = fieldsCelllist.get(i);
				fieldsCell.setTmplId(Integer.parseInt(id));
				if(fieldsCelllist.get(0).getIndItemID()!=null){
					fieldsCell.setIndItemID(fieldsCelllist.get(0).getIndItemID());
				}else{
					fieldsCell.setIndItemID(Long.parseLong(indItemID));
				}
				fieldsCell.setValuePos("down");
				fieldsList.add(fieldsCell);
			}
			rptTmplFieldsCellDao.batchSave(fieldsList, 20);
		}
		
	}
	
	public void delete(Integer id){
		rptTmplCellDao.delete(id);
	}
	
	public void deleteByTmplId(List<RptTmplCell> list){
		rptTmplCellDao.delete(list);
	}
	
	

}
