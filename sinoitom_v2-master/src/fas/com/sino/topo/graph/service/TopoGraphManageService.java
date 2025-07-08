package com.sino.topo.graph.service;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sino.base.common.util.JsonUtils;
import com.sino.topo.graph.dao.TopoGraphManageDao;
import com.sino.topo.graph.entity.ParamTopoGraph;
import com.sino.topo.graph.entity.TopoGraph;
import com.sino.topo.topoLine.dao.TopoLineDao;
import com.sino.topo.topoLine.entity.TopoLine;
import com.sino.topo.topoLine.service.TopoLineService;
import com.sino.topo.topoNode.dao.TopoNodeDao;
import com.sino.topo.topoNode.entity.TopoNode;
import com.sino.topo.topoNode.service.TopoNodeService;

@Service
//默认将类中的所有public函数纳入事务管理.
@Transactional
public class TopoGraphManageService {

	private static Logger logger = LoggerFactory.getLogger(TopoGraphManageService.class);
	private static String objAttNames = "graphId,graphName,graphDesc,flag,seedNodeId,bgPicture,creator,createTime";
	private static String jsonAttNames = "id,graphName,graphDesc,flag,SeedNodeId,bgPicture,creator,createTime";
	private static String objAtt = "graphId,graphName,graphDesc,flag,seedNodeIp,bgPicture,creator,createTime,height,width,createDate";
	private static String jsonAtt = "id,graphName,graphDesc,flag,SeedNodeIp,bgPicture,creator,createTime,height,width,createDate";

	private String lastMassage="";
	
	public String getLastMassage() {
		return lastMassage;
	}

	public void setLastMassage(String lastMassage) {
		this.lastMassage = lastMassage;
	}
	
	@Autowired
	private TopoGraphManageDao topoGraphManageDao;
	
	@Autowired
	private TopoNodeDao topoNodeDao;
	
	@Autowired
	private TopoNodeService topoNodeService;
	
	@Autowired
	private TopoLineService topoLineService;
	
	@Autowired
	private TopoLineDao topoLineDao;
	
	@Transactional(readOnly = true)
	public String getJsonTopoGraphObjStr(final TopoGraph obj) {
		return JsonUtils.getJsonObjInfo(obj, objAttNames, jsonAttNames);
	}
	@Transactional(readOnly = true)
	public String getJsonParamTopoGraphObjStr(final ParamTopoGraph obj) {
		return JsonUtils.getJsonObjInfo(obj, objAtt, jsonAtt);
	}
	@Transactional(readOnly = true)
	public String getJsonTopoGraphListStr(final List<TopoGraph> list) {
		return JsonUtils.getJsonListInfo(list, objAttNames, jsonAttNames);
	}
	@Transactional(readOnly = true)
	public String getJsonParamTopoGraphListStr(final List<ParamTopoGraph> list) {
		return JsonUtils.getJsonListInfo(list, objAtt, jsonAtt);
	}
    //查询所有
	@Transactional(readOnly = true)
	public List<TopoGraph> getAllTopoGraph() {
		String hql=" from TopoGraph order by createTime desc";
		return topoGraphManageDao.getAll();
	}
	//查询单个
	
	
	@Transactional(readOnly = true)
	public TopoGraph getTopoGraphByID(Long id) {
		return topoGraphManageDao.get(id);
	}
	public void saveTopoGraph(TopoGraph entity) {
		logger.debug("addTopoGraph(name:{})...", entity.getGraphName());
		
		topoGraphManageDao.save(entity);
	}
	public void deleteTopoGraph(long id) {
		logger.debug("deleteTopoGraph(id:{})...", id);
		topoGraphManageDao.delete(id);
		topoGraphManageDao.flush();
		
		topoNodeDao.deleteByGraphId(id);
		topoNodeDao.flush();
		
		topoLineDao.deleteLineByGraphId(id);
		topoLineDao.flush();
		
		
	}
	public void deleteTopoGraph(String [] ids) {
		logger.debug("deleteTopos(id:{})...", ids);
		for(String id:ids){
			topoGraphManageDao.delete(Long.parseLong(id));
			topoGraphManageDao.flush();
			
			topoNodeDao.deleteByGraphId(Long.parseLong(id));
			topoNodeDao.flush();
			
			topoLineDao.deleteLineByGraphId(Long.parseLong(id));
			topoLineDao.flush();			
			
		}
		
	}
	public void settingTopoDefault(Long id){
		logger.debug("settingFalg(list)...");
		topoGraphManageDao.updata(id);
	}

	public void editPictureName(String imagePath,String editName, String imgName) throws IOException{
        File file=new File(imagePath); 
        if (!file.exists()) {
            return ;
        }
        
        File newFile = new File(imagePath + File.separator + editName);
        file.renameTo(newFile);
	}
}
