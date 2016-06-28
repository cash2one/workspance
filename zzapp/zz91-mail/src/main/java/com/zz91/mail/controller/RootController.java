/**
 * 
 */
package com.zz91.mail.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.zz91.mail.domain.dto.ExtResult;
import com.zz91.mail.domain.dto.Monitor;
import com.zz91.mail.service.MailInfoService;
import com.zz91.mail.thread.ControlThread;
import com.zz91.mail.thread.MailScanThread;
import com.zz91.mail.util.TimeHelper;
import com.zz91.util.auth.AuthConst;
import com.zz91.util.auth.AuthMenu;
import com.zz91.util.auth.AuthUtils;
import com.zz91.util.auth.SessionUser;
import com.zz91.util.datetime.DateUtil;

/**
 * @author root
 * 
 */
@Controller
public class RootController extends BaseController {

    private final static Queue<Monitor> secondQueue = new ArrayBlockingQueue<Monitor>(100);

    @Resource
    private MailInfoService mailInfoService;

    @RequestMapping
    public ModelAndView index(Map<String, Object> out, HttpServletRequest request) {
        out.put("staffName", getCachedUser(request).getName());
        return null;
    }

    @RequestMapping
    public ModelAndView error(Map<String, Object> out, HttpServletRequest request) {
        return null;
    }

    @RequestMapping
    public ModelAndView forbiden(Map<String, Object> out, HttpServletRequest request) {
        return null;
    }

    @RequestMapping
    public ModelAndView login(Map<String, Object> out, HttpServletRequest request) {
        return null;
    }

    @RequestMapping
    public ModelAndView welcome(Map<String, Object> out, HttpServletRequest request) {

        return null;
    }

    @RequestMapping
    public ModelAndView checkuser(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response, String account,
            String password) {
        SessionUser sessionUser = AuthUtils.getInstance().validateUser(response, account, password, AuthConst.PROJECT_CODE,
                AuthConst.PROJECT_PASSWORD);
        ExtResult result = new ExtResult();
        if (sessionUser != null) {
            setSessionUser(request, sessionUser);
            result.setSuccess(true);
        } else {
            result.setData("用户名或者密码写错了，检查下大小写是否都正确了，再试一次吧 :)");
        }
        return printJson(result, out);
    }

    @RequestMapping
    public ModelAndView logout(Map<String, Object> out, HttpServletRequest request, HttpServletResponse response) {
        AuthUtils.getInstance().logout(request, response, null);
        return new ModelAndView("redirect:login.htm");
    }

    @RequestMapping
    public ModelAndView mymenu(String parentCode, Map<String, Object> out, HttpServletRequest request) {
        if (parentCode == null) {
            parentCode = "";
        }
        SessionUser sessionUser = getCachedUser(request);
        List<AuthMenu> list = AuthUtils.getInstance().queryMenuByParent(parentCode, AuthConst.PROJECT_CODE, sessionUser.getAccount());
        return printJson(list, out);
    }

    @RequestMapping
    public ModelAndView monitor(Map<String, Object> out, HttpServletRequest request) {
        return null;
    }

    @RequestMapping
    public ModelAndView getMonitor(HttpServletRequest request, Map<String, Object> out) {

        long now = System.currentTimeMillis();
        if ((now - ControlThread.getLastMonitorTime()) > 1000) {
            Monitor monitor = new Monitor(DateUtil.toString(new Date(), "HH:mm:ss"), ControlThread.getNumTask(),
                    TimeHelper.formatTime(ControlThread.getTotalTime() / 1000000), ControlThread.getNumQueue(),
                    MailScanThread.mailQueue.size(), ControlThread.getActiveThread());

            int size = secondQueue.size();
            if (size < 100) {
                secondQueue.add(monitor);
            } else {
                secondQueue.poll();
                secondQueue.add(monitor);
            }
        }

        Monitor[] array = new Monitor[100];
        array = secondQueue.toArray(array);

        List<Monitor> list = new ArrayList<Monitor>();

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                list.add(array[i]);
            }
        }

        return printJson(list, out);
    }

    @RequestMapping
    public ModelAndView shutdownRecovery(HttpServletRequest request, Map<String, Object> out) {
        ExtResult result = new ExtResult();
        result.setSuccess(mailInfoService.shutdownRecovery(MailInfoService.SEND_SENDING, MailInfoService.SEND_WAITING));
        return printJson(result, out);
    }
}
