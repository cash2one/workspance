package com.ast.feiliao91.service.logistics;

import java.util.Map;

import com.ast.feiliao91.domain.logistics.Logistics;

public interface LogisticsService {
	final static String STATE_ONE="0";// 0：在途，即货物处于运输过程中；
	final static String STATE_TWO="1";// 1：揽件，货物已由快递公司揽收并且产生了第一条跟踪信息；
	final static String STATE_THREE="2";// 2：疑难，货物寄送过程出了问题；
	final static String STATE_FOUR="3";// 3：签收，收件人已签收；
	final static String STATE_FIVE="4";// 4：退签，即货物由于用户拒签、超区等原因退回，而且发件人已经签收；
	final static String STATE_SIX="5";// 5：派件，即快递正在进行同城派件；
	final static String STATE_SEVEN="6";// 6：退回，货物正处于退回发件人的途中；
	
   /**
    * 根据物流编号查询物流详细信息
 　　* @throws ParseException 
    * @param type 1 运货物流信息　　２　退货物流信息
    */
     public Map<String, Object> selectLogisticsByCode(String code, String type);
     /**
      * 插入物流信息
      */
     public Integer insertLogistics(Logistics logistics);
     
     public Map<String,Object> getmap(String json);
     
     /**
      * 根据code获取物流信息
      * @param code
      * @return
      */
     public Map<String , Object> getLogistics(String code);
     
     /**
      * 根据code获取物流信息
      * @author zhujq
      * @param 物流单号code,type=1为发货，type=2为退货
      * @return
      */
     public Integer updaLogisticsByCode(String code,String type);
}
