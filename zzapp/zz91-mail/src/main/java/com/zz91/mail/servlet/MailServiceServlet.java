/**
 * Project name: zz91-mail
 * File name: MailServlet.java
 * Copyright: 2005-2011 ASTO Info TechCo.,Ltd. All rights reserved
 */
package com.zz91.mail.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.zz91.util.mail.MailInfoDomain;

/**
 * @author kongsj
 * @email kongsj@zz91.net
 * @date 2011-10-26
 */
@Deprecated
@SuppressWarnings("serial")
public class MailServiceServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ObjectInputStream in = new ObjectInputStream(request.getInputStream());
        MailInfoDomain mto = null;
        com.zz91.mail.domain.MailInfoDomain utilMto = new com.zz91.mail.domain.MailInfoDomain();
        try {
            mto = (MailInfoDomain) in.readObject();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        utilMto.setEmailTitle(mto.getEmailTitle());
        utilMto.setTemplateId(mto.getTemplateId());
        utilMto.setSender(mto.getSender());
        utilMto.setReceiver(mto.getReceiver());

        in.close();
        // 输出流
        response.setContentType("application/octet-stream");
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(mto);

        out.flush();
        byte[] buf = byteOut.toByteArray();
        response.setContentLength(buf.length);
        ServletOutputStream servletOut = response.getOutputStream();

        servletOut.write(buf);
        servletOut.close();
    }
}
