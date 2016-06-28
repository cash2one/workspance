package com.zz91.mail.mina;

import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;

public class HelloHandler implements IoHandler{

	@Override
	public void exceptionCaught(IoSession session, Throwable cause)
			throws Exception {
		
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String msg=message.toString();
		if("quit".equalsIgnoreCase(msg.trim())){
			session.close(true);
			return ;
		}
		try {
			session.write("from server "+msg);
		} catch (Exception e) {
		}
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		
	}

	@Override
	public void sessionCreated(IoSession session) throws Exception {
		
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status)
			throws Exception {
		
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		
	}

}
