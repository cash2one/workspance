package com.nala.csd.data

import com.nala.csd.ErrorReasonForTrade
import com.nala.csd.ErrorTrade
import com.nala.csd.Express
import com.nala.csd.ExpressProblem
import com.nala.csd.Hero
import com.nala.csd.Item
import com.nala.csd.Store

import java.awt.ItemSelectable;
import java.io.FileInputStream;
import javax.print.attribute.standard.SheetCollate;

import org.apache.poi.hssf.usermodel.HSSFWorkbook
import org.apache.poi.ss.usermodel.*
import com.nala.csd.ChajianInitiative
import com.nala.csd.ChajianCode
import com.nala.csd.CodeForTableEnum

class DataImportService {
	
	def errorTradeService
	
	/**
	 * 导入excel格式的快递问题件
	 * @param fis
	 * @param express
	 * 物流单号	问题类型	查询备注
	 * @return
	 */
	List<ExpressProblem> importExpressProblem(FileInputStream fis, Express express){
		log.debug("importExpressProblem")
		Workbook wb = WorkbookFactory.create(fis)
		
		Sheet sheet = wb.getSheetAt(0)
		List<ExpressProblem> list = new ArrayList<ExpressProblem>();
		// 遍历行和列
		for(Row row : sheet){
			// 不保存表头(既第一行)
			if(row.getRowNum() == 0){
				continue;
			}
			ExpressProblem ep = new ExpressProblem();
			
			// 物流单号
			String excelLogisticsID = ''
			if (row.getCell(0)){
				switch(row.getCell(0).getCellType()){
					case Cell.CELL_TYPE_STRING:
						excelLogisticsID = row.getCell(0).getStringCellValue()
						break;
					case Cell.CELL_TYPE_NUMERIC:
						Cell cell = row.getCell(0)
						cell.setCellType(Cell.CELL_TYPE_STRING)
						excelLogisticsID = cell.getStringCellValue()
						break;
					default :
						excelLogisticsID = row.getCell(0)
						log.debug("Error type for cell: " + row.getCell(0))
				}
			}else{
				log.info("Row.Cell(0) is null.")
			}
            excelLogisticsID = excelLogisticsID.trim()
			
			if (excelLogisticsID == null || excelLogisticsID.equals('')){
				log.info("ExcelLogisticsID is null, continue;")
				continue;
			}
				
			// 问题类型
			String excelProblemType
			if (row.getCell(1)){
				excelProblemType = row.getCell(1).getStringCellValue()
			}else{
				excelProblemType = ''
			}
			// 问题描述
			String excelDescription
			if (row.getCell(2)){
				excelDescription = row.getCell(2).getStringCellValue()
			}else{
				excelDescription = ''
			}				
			
			def tmpEP = ExpressProblem.executeQuery("from ExpressProblem e where logisticsID='" + excelLogisticsID + "'")
			if (!tmpEP){
				log.debug "--------------------"
				log.debug excelLogisticsID
				log.debug excelProblemType
				log.debug row.getCell(2)
				ep.express = express
				ep.setLogisticsID(excelLogisticsID)
				ep.setProblemType(excelProblemType)
				ep.setDescription(excelDescription)
				
				if (!ep.save(flush: true)){
					log.error("save ExpressProblem error: " + excelLogisticsID)
					ep.errors.each {
						log.error it
					}
				}
				list.add(ep)
			}else{
				log.info ("logisticsID has already exists : " + excelLogisticsID + ". Reject this data!!!")
			}
		}
		
		return list;
	}
	
	/**
	 * 导入异常单
	 * @param fis
	 * 店铺	顾客ID	订单编号	异常原因	商品名称	商品SKU	商家编码	规格编码	买家备注	卖家备注	购买数量
	 * @return
	 */
	List<ErrorTrade> importErrorTrade(FileInputStream fis){
		log.debug("importErrorTrade")
		Workbook wb = WorkbookFactory.create(fis)
		
		Sheet sheet = wb.getSheetAt(0)
		List<ErrorTrade> list = new ArrayList<ErrorTrade>();
		// 遍历行和列
		for(Row row : sheet){
			// 不保存表头(既第一行)
			if(row.getRowNum() == 0){
				continue;
			}			
			
			// 店铺
			Store curStore;
			if (row.getCell(0)){
				Cell tmpCell = row.getCell(0)
				if (tmpCell){
					tmpCell.setCellType(Cell.CELL_TYPE_STRING)
					curStore = Store.findByName(tmpCell.getStringCellValue())
					if (!curStore){
						log.info('I can not mean about the store name: ' + row.getCell(0))
					}
				}
			}
			
			String userId;
			if (row.getCell(1)){
				Cell tmpCell = row.getCell(1)
				if (tmpCell){
					tmpCell.setCellType(Cell.CELL_TYPE_STRING)
					userId = tmpCell.getStringCellValue()
				}
			}
			
			// 订单编号
			String tradeId = ''
			if (row.getCell(2)){
				switch(row.getCell(2).getCellType()){
					case Cell.CELL_TYPE_STRING:
						tradeId = row.getCell(2).getStringCellValue()
						break;
					case Cell.CELL_TYPE_NUMERIC:
						Cell cell = row.getCell(2)
						cell.setCellType(Cell.CELL_TYPE_STRING)
						tradeId = cell.getStringCellValue()
						break;
					default :
						tradeId = row.getCell(2)
						log.debug("Error type for cell: " + row.getCell(2))
				}
			}else{
				log.info("trade id is null, skip the line.")
				continue
			}
			
			// 异常原因
			ErrorReasonForTrade errorReason
			if (row.getCell(3)){
				Cell tmpCell = row.getCell(3)
				if (tmpCell){
					tmpCell.setCellType(Cell.CELL_TYPE_STRING)
					errorReason = ErrorReasonForTrade.findByName(tmpCell.getStringCellValue())
					if (!errorReason){
						log.info('I can not mean about the error reason: ' + row.getCell(3).getStringCellValue())
					}
				}
			}
			
			// 买家备注
			String buyerRemarks
			if (row.getCell(8)){
				Cell tmpCell = row.getCell(8)
				if (tmpCell){
					tmpCell.setCellType(Cell.CELL_TYPE_STRING)
					buyerRemarks = tmpCell.getStringCellValue()
				}
				
			}
			
			// 卖家备注
			String sellerRemarks
			if (row.getCell(9)){
				Cell tmpCell = row.getCell(9)
				if (tmpCell){
					tmpCell.setCellType(Cell.CELL_TYPE_STRING)
					sellerRemarks = tmpCell.getStringCellValue()
				}
			}
			
			// 商品
			Item item
			// 商品名称
			if (row.getCell(4)){
				if (!item){
					item = new Item()
				}				
				item.setName(row.getCell(4).getStringCellValue())
			}
			// 商品SKU
			if (row.getCell(5)){
				if (!item){
					item = new Item()
				}				
				item.setSku(row.getCell(5).getStringCellValue())
			}
			// 商家编码
			if (row.getCell(6)){
				if (!item){
					item = new Item()
				}				
				item.setCode(row.getCell(6).getStringCellValue())
			}
			
			// 购买数量
			if (row.getCell(10)){
				if (!item){
					item = new Item()
				}
				Cell tmpCell = row.getCell(10)
				if (tmpCell){
					tmpCell.setCellType(Cell.CELL_TYPE_STRING)
					def tmpNum = tmpCell.getStringCellValue()
					Integer num = 0
					try{
						num = Integer.parseInt(tmpNum)
						item.setName(num)
					}catch(Exception e){
						log.warn("${tmpCell} to Integer error.")
					}
				}
				
			}
			
			// 收货人
			String receiver
			if (row.getCell(11)){
				Cell tmpCell = row.getCell(11)
				if (tmpCell){
					tmpCell.setCellType(Cell.CELL_TYPE_STRING)
					receiver = tmpCell.getStringCellValue()
				}
			}
			
			// 收货地址
			String address
			if (row.getCell(12)){
				Cell tmpCell = row.getCell(12)
				if (tmpCell){
					tmpCell.setCellType(Cell.CELL_TYPE_STRING)
					address = tmpCell.getStringCellValue()
				}
			}

			// 电话
			String phone
			if (row.getCell(13)){
				Cell tmpCell = row.getCell(13)
				if (tmpCell){
					tmpCell.setCellType(Cell.CELL_TYPE_STRING)
					phone = tmpCell.getStringCellValue()
				}
			}
			
			ErrorTrade errorTrade
			
			// 看看是否已有同订单的数据
			ErrorTrade oldTrade = ErrorTrade.findByTradeId(tradeId)
			if (oldTrade){
				errorTrade = oldTrade
			}else{
				// 新建问题单
				errorTrade = new ErrorTrade();
			}
						
			errorTrade.setUserId(userId)
			errorTrade.setTradeId(tradeId)
			errorTrade.setStore(curStore)
			errorTrade.setErrorReason(errorReason)
			errorTrade.setBuyerRemarks(buyerRemarks)
			errorTrade.setSellerRemarks(sellerRemarks)
			errorTrade.setReceiver(receiver)
			errorTrade.setAddress(address)
			errorTrade.setPhone(phone)
			
			Hero createCS = Hero.findByName('系统')
			if (createCS){
				errorTrade.setCreateCS(createCS)
			}

			// save
			if (item && (item.name != null || item.sku != null)){
				if (item.hasErrors() || !item.save(flush: true)) {
					log.error('Save item error. ')
				}
			}
			if (errorTrade.hasErrors() || !errorTrade.save(flush: true)) {
				log.error('Save errorTrade error. ' + tradeId)
				errorTrade.errors.each {
					log.error(it)
				}
			}
			
			// item关联到error trade
			if (item && (item.name != null || item.sku != null)){
				errorTrade = errorTradeService.addItemToErrorTrade(item, errorTrade)
			}			
			
			list.add(errorTrade)
			
		}
		
		return list;
	}

    /**
     * 导入主动查件记录
     * @param fis
     * @return
     */
    List<ErrorTrade> importChajianInitiative(FileInputStream fis){
        log.debug("importErrorTrade")
        Workbook wb = WorkbookFactory.create(fis)

        Sheet sheet = wb.getSheetAt(0)
        List<ChajianInitiative> list = new ArrayList<ChajianInitiative>();
        // 遍历行和列
        for(Row row : sheet){
            // 不保存表头(既第一行)
            if(row.getRowNum() == 0){
                continue;
            }

            // 店铺
            Store curStore;
            if (row.getCell(0)){
                Cell tmpCell = row.getCell(0)
                if (tmpCell){
                    tmpCell.setCellType(Cell.CELL_TYPE_STRING)
                    curStore = Store.findByName(tmpCell.getStringCellValue())
                    if (!curStore){
                        log.info('I can not mean about the store name: ' + row.getCell(0))
                    }
                }
            }

            // 用户ID
            String userId;
            if (row.getCell(1)){
                Cell tmpCell = row.getCell(1)
                if (tmpCell){
                    tmpCell.setCellType(Cell.CELL_TYPE_STRING)
                    userId = tmpCell.getStringCellValue().trim()
                }
            }

            // 快递单号
            String logistics;
            if (row.getCell(2)){
                Cell tmpCell = row.getCell(2)
                if (tmpCell){
                    tmpCell.setCellType(Cell.CELL_TYPE_STRING)
                    logistics = tmpCell.getStringCellValue().trim()
                }
            }


            // 查件代码
            ChajianCode chajianCode
            if (row.getCell(3)){
                Cell tmpCell = row.getCell(3)
                if (tmpCell){
                    tmpCell.setCellType(Cell.CELL_TYPE_STRING)

                    def strCode = tmpCell.getStringCellValue().trim()
                    chajianCode = ChajianCode.findByName(strCode)
                    if (!chajianCode){
                        log.info('I can not mean about the chajianCode: ' + strCode)
                    }
                }
            }

            // 看看是否已有同数据
            ChajianInitiative chajianInitiative = ChajianInitiative.findByLogistics(logistics)
            if (!chajianInitiative){
                chajianInitiative = new ChajianInitiative()
                chajianInitiative.store  = curStore
                chajianInitiative.userId = userId
                chajianInitiative.logistics = logistics
                chajianInitiative.chajianCode = chajianCode

                // 保存
                if (chajianInitiative.hasErrors() || !chajianInitiative.save(flush: true)) {
                    log.error('Save chajianInitiative error. ' + logistics)
                    chajianInitiative.errors.each {
                        log.error(it)
                    }
                }else{
                    list.add(chajianInitiative)
                }
            }
        }

        return list;
    }
}
