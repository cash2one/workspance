/**
 * Copyright 2010 ASTO.
 * All right reserved.
 * Created on 2010-8-25
 */
package com.ast.ast1949.service.information.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jcifs.smb.SmbFile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ast.ast1949.domain.information.Periodical;
import com.ast.ast1949.domain.information.PeriodicalDetails;
import com.ast.ast1949.dto.PageDto;
import com.ast.ast1949.dto.information.PeriodicalDTO;
import com.ast.ast1949.persist.information.PeriodicalDAO;
import com.ast.ast1949.persist.information.PeriodicalDetailsDAO;
import com.ast.ast1949.service.facade.ParamFacade;
import com.ast.ast1949.service.information.PeriodicalService;
import com.ast.ast1949.util.Assert;
import com.ast.ast1949.util.RemoteFileUtil;
import com.ast.ast1949.util.ScaleImage;
import com.ast.ast1949.util.SmbUnZip;
import com.ast.ast1949.util.Upload;
import com.zz91.util.param.ParamUtils;

/**
 * @author Mays (x03570227@gmail.com)
 *
 */
@Component("periodicalService")
public class PeriodicalServiceImpl implements PeriodicalService {

	@Autowired
	private PeriodicalDAO periodicalDAO;
	@Autowired
	private PeriodicalDetailsDAO periodicalDetailsDAO;

	public Integer batchDeletePeriodical(Integer[] periodicalIdsArray) {
		Assert.notNull(periodicalIdsArray, "the periodicalIdsArray must not be null");
		int impact=0;
		for(Integer i:periodicalIdsArray){
			impact+=periodicalDAO.deletePeriodicalById(i);
			periodicalDetailsDAO.deleteDetailsByPeriodicalId(i);
			//TODO 删除周刊子页对应的图片文件
//			Map<String, String> paramMap = ParamFacade.getInstance().getParamByType("upload_config");
//			RemoteFileUtil remoteFileUtil = new RemoteFileUtil(paramMap.get("remote_host_ip"), 
//					paramMap.get("remote_account"), 
//					paramMap.get("remote_password"), 
//					paramMap.get("remote_share_folder"));
//			remoteFileUtil.deleteFile(paramMap.get("upload_folder")+);
			
//			String destPath = MemcachedFacade.getInstance().get("baseConfig.upload_folder")
//				+"/"+PERIODICAL_MODEL+"/"+PERIODICAL_FILE_TYPE+"/"+i+"b/";
//			String destThumbPath=MemcachedFacade.getInstance().get("baseConfig.upload_folder")
//				+"/"+PERIODICAL_MODEL+"/"+PERIODICAL_FILE_TYPE+"/"+i+"s/";
//			FileUtils.deleteFolderAndFile(new File(destPath));
//			FileUtils.deleteFolderAndFile(new File(destThumbPath));
		}
		return impact;
	}

	public Integer batchDeletePeriodicalOnlyDetails(Integer[] periodicalIdsArray) {
		Assert.notNull(periodicalIdsArray, "the periodicalIdsArray must not be null");
		int impact=0;
		for(Integer i:periodicalIdsArray){
			impact+=periodicalDetailsDAO.deleteDetailsByPeriodicalId(i);
			//TODO 删除周刊子页对应的图片文件
//			String destPath = MemcachedFacade.getInstance().get("baseConfig.upload_folder")
//				+"/"+PERIODICAL_MODEL+"/"+PERIODICAL_FILE_TYPE+"/"+i+"b/";
//			String destThumbPath=MemcachedFacade.getInstance().get("baseConfig.upload_folder")
//				+"/"+PERIODICAL_MODEL+"/"+PERIODICAL_FILE_TYPE+"/"+i+"s/";
//			FileUtils.deleteFolderAndFile(new File(destPath));
//			FileUtils.deleteFolderAndFile(new File(destThumbPath));
		}
		return impact;
	}

	public Integer createPeriodical(Periodical periodical) {
		Assert.notNull(periodical, "the periodical must not be null");
		Assert.notNull(periodical.getName(), "the periodical.name must not be null");
		return periodicalDAO.createPeriodical(periodical);
	}

	public Periodical listOnePeriodicalById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return periodicalDAO.listOnePeriodicalById(id);
	}

	public PageDto pagePeriodicalWithoutSearch(PageDto page) {
		Assert.notNull(page, "the periodicalIdsArray must not be null");
		page.setTotalRecords(periodicalDAO.countPagePeriodicalWithoutSearch());
		page.setRecords(periodicalDAO.pagePeriodicalWithoutSearch(page));
		return page;
	}

	final static String PERIODICAL_MODEL = "periodical";
	final static String PERIODICAL_FILE_TYPE = "doc";

	public Integer unzipUploadedDetails(String path, Integer periodicalId) throws IOException {
		Assert.notNull(periodicalId, "the periodicalId must not be null");
		Assert.notNull(path, "the path must not be null");
		//TODO 解压上传的zip文件
		//判断文件是否存在
		//保存文件信息到期刊表
		//解压文件到指定目录
		//同时生成缩略图
		
		//读取
		//解压到临时目录
		//上传到共享文件夹
		//生成缩略图到临时目录
		//上传到共享文件夹
		
		Map<String, String> paramMap = ParamUtils.getInstance().getChild("upload_config");
		RemoteFileUtil remoteFileUtil = new RemoteFileUtil(paramMap.get("remote_host_ip"), 
				paramMap.get("remote_account"), 
				paramMap.get("remote_password"), 
				paramMap.get("remote_share_folder"));
		
		String dest = PERIODICAL_MODEL+"/"+Upload.getInstance().getDateFolder()+"/";
		String destPath = paramMap.get("upload_folder")+dest+periodicalId+"b/";
		String destThumbPath = paramMap.get("upload_folder")+dest+periodicalId+"s/";
		
		remoteFileUtil.writeFolder(destPath);
		remoteFileUtil.writeFolder(destThumbPath);
		
		Integer u1=periodicalDAO.updatePeriodicalZipPath(periodicalId, path);
		if(u1==null || u1<=0){
			return null;
		}
		
		SmbUnZip smbUnZip = new SmbUnZip(paramMap.get("remote_host_ip"), 
				paramMap.get("remote_account"), 
				paramMap.get("remote_password"), 
				paramMap.get("remote_share_folder"));
		smbUnZip.unzipToRemote(paramMap.get("upload_folder")+path, destPath);

		List<PeriodicalDetails> detailsList = new ArrayList<PeriodicalDetails>();
		Map<String, String> detailsmap=new HashMap<String, String>();

		List<PeriodicalDetails> detailsDbList = periodicalDetailsDAO.listPreviewDetailsByPeriodicalId(periodicalId);
		for(PeriodicalDetails d:detailsDbList){
			detailsmap.put(d.getImageName(), d.getName());
		}


		ScaleImage si = new ScaleImage();
		SmbFile[] files = remoteFileUtil.listFiles(destPath);
		
		for(SmbFile smbfile:files){
			String filename = smbfile.getName();
			if(detailsmap.get(filename)==null){
				PeriodicalDetails pd = new PeriodicalDetails();
				pd.setImageName(filename);
				pd.setName(filename);
				pd.setPeriodicalId(periodicalId);
				pd.setPageType(periodicalDetailsDAO.PAGE_TYPE_BODY);
				pd.setPreviewUrl(dest);
				detailsList.add(pd);
				SmbFile outputfile = remoteFileUtil.smbFile(destThumbPath+smbfile.getName());
				si.saveImageAsJpgRemote(smbfile.getInputStream(), outputfile.getOutputStream(),
						200, 160);
			}
			
		}
		
		return periodicalDetailsDAO.batchInsertPeriodicalDetails(detailsList);
	}

	public Integer updatePeriodical(Periodical periodical) {
		Assert.notNull(periodical, "the periodical must not be null");
		Assert.notNull(periodical.getId(), "the periodical.id must not be null");
		return periodicalDAO.updatePeriodical(periodical);
	}

	public String zipPeriodicalDetails(Integer periodicalId) {
		Assert.notNull(periodicalId, "the periodicalId must not be null");
		//TODO 打包下载期刊子页
		//将指定目录打成zip包
		//命令为 时间截+5位随机数.zip
		//返回路径和文件名
		return null;
	}

	public List<PeriodicalDTO> listFrontCoverPeriodicalBySize(Integer size) {
       Assert.notNull(size, "size is not null");
		return periodicalDAO.listFrontCoverPeriodicalBySize(size);
	}

	public Integer updateNumUpById(Integer id) {
		Assert.notNull(id, "the id can not be null");
		return periodicalDAO.updateNumUpById(id, DEFAULT_NUM_ADD);
	}

	public Integer updateNumViewById(Integer periodicalId) {
		Assert.notNull(periodicalId, "the id can not be null");
		return periodicalDAO.updateNumViewById(periodicalId, DEFAULT_NUM_ADD);
	}




}
