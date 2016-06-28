package com.ast.ast1949.persist.exhibit;

import java.util.List;

import com.ast.ast1949.domain.exhibit.PreviouExhibitors;
import com.ast.ast1949.dto.PageDto;

public interface PreviouExhibitorsDao {
	
		/**
		 * 插入 previou_exhibitors 记录
		 * @param previouExhibitors
		 * @return
		 */
		public Integer insert (PreviouExhibitors previouExhibitors);
		/**
		 * 查找所有展商
		 * @param page
		 * @param previouExhibitors
		 * @return
		 */
		public List<PreviouExhibitors> queryList(PageDto<PreviouExhibitors>page, PreviouExhibitors previouExhibitors);
		
		public Integer queryListCount(PreviouExhibitors previouExhibitors);
		
		public  PreviouExhibitors queryPreviouExhibitorsById(Integer id);
		
		public Integer updatePreviouExhibitors(PreviouExhibitors previouExhibitors);
		
		public Integer delPreviouExhibitors(Integer id);
		
		public List<PreviouExhibitors> queryAllList(PreviouExhibitors previouExhibitors);
		
	}


