package com.zz91.ep.myesite.controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.ep.service.comp.CompAccountService;
import com.zz91.util.auth.ep.EpAuthUser;
import com.zz91.util.auth.ep.EpAuthUtils;
import com.zz91.util.exception.AuthorizeException;
import com.zz91.util.http.HttpUtils;
import com.zz91.util.lang.StringUtils;
import com.zz91.util.param.ParamUtils;
import com.zz91.util.velocity.AddressTool;

@Controller
public class AdminLoginController extends BaseController{

    @Resource
    private CompAccountService compAccountService;
    
    /**
     * 函数名称：adminLogin 功能描述：从后台登录
     * 
     * @param request
     *            HttpServletRequest
     * @param out
     *            Map 异　　常：无 变更履历：修改日期　　　　　修改者　　　　　　　版本号　　　　　 修改内容
     *            　　　　　               2013/11/27　　 周宗坤　　 　　 　 1.0.0　　 　　 创建方法函数
     */
       @RequestMapping
        public ModelAndView adminLogin(HttpServletRequest request, HttpServletResponse response, Map<String, Object> out,
                String account, String password){
            //查找用户信息
            //用ssoutil登录
            String ip=HttpUtils.getInstance().getIpAddr(request);
            String adminIp=ParamUtils.getInstance().getValue("baseConfig", "admin_ip");
            
            if(ip!=null && ip.contains(",")){
                ip=ip.split(",")[0].trim();
            }
            
            if(StringUtils.isEmpty(adminIp)){
                adminIp="61.234.184.252";
            }
            
            Set<String> ipSet = new HashSet<String>();
            if(adminIp.indexOf(",")!=-1){
                String[] ipArray = adminIp.split(",");
                for(String str :ipArray){
                    ipSet.add(str);
                }
            }else{
                ipSet.add(adminIp);
            }
            if(!ipSet.contains(ip)){
                System.out.println("ip not contain"+":"+ip);
                return new ModelAndView("redirect:"+AddressTool.getAddress("www"));
            }
//        SsoUser user=getCachedUser(request);
            
            EpAuthUtils.getInstance().logout(request, response, null);
            EpAuthUtils.getInstance().clearnEpAuthUser(request, null);
            
            EpAuthUser user = null;
            try {
                user = EpAuthUtils.getInstance().validateUser(response, account,
                        password, null);
                compAccountService.updateLoginInfo(user.getUid(), null, user
                        .getCid());
                EpAuthUtils.getInstance().setEpAuthUser(request, user, null);
               
            } catch (NoSuchAlgorithmException e) {
                out.put("error", AuthorizeException.ERROR_SERVER);
                
                return new ModelAndView("redirect:"+AddressTool.getAddress("www"));
            } catch (IOException e) {
                out.put("error", AuthorizeException.ERROR_SERVER);
                
                return new ModelAndView("redirect:" +AddressTool.getAddress("www"));
            } catch (AuthorizeException e) {
                out.put("error", e.getMessage());
               
                return new ModelAndView("redirect:"+AddressTool.getAddress("www"));
            }

            return new ModelAndView("redirect:"+AddressTool.getAddress("myesite")+"/index.htm");
            
            /*Integer a = null;
            try {
                a = compAccountService.validateUser(account, MD5.encode(password));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (AuthorizeException e) {
                e.printStackTrace();
            }
            
            if(a == null || a <= 0){
                System.out.println("account is null");
                return new ModelAndView("redirect:"+AddressTool.getAddress("www"));
            }
            SsoUser ssoUser = compAccountService.initSessionUser(account);*/
            
        /*    if(ssoUser!=null){
                String key=UUID.randomUUID().toString();
                String ticket="";
                try {
                    ticket = MD5.encode(account+password+key);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                ssoUser.setTicket(ticket);
                HttpUtils.getInstance().setCookie(response, SsoConst.TICKET_KEY, ticket, SsoConst.SSO_DOMAIN, null);
                MemcachedUtils.getInstance().getClient().set(ticket, 1*60*60, ssoUser);
//            SsoUtils.getInstance().setSessionUser(request, ssoUser, null);
                return new ModelAndView("redirect:"+AddressTool.getAddress("myesite"));
            }*/
            
           /* System.out.println("nothing is happen");
            return new ModelAndView("redirect:"+AddressTool.getAddress("www"));*/
        }
}
