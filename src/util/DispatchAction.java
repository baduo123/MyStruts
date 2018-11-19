package util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sun.org.apache.bcel.internal.generic.NEW;

public class DispatchAction implements Filter{
	String hz;
	ActionUtil au = new ActionUtil();
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) arg0;
		HttpServletResponse response = (HttpServletResponse) arg1;
		String uri = request.getRequestURI();
		int start = uri.lastIndexOf("/");
		if (hz != null) {
			int end = uri.lastIndexOf(hz);
			uri = uri.substring(start+1, end-1);
		}else {
			uri.substring(start);
		}
		try {
			Object object = au.createAction(uri);
			au.createParameties(object,request,uri);
			au.returnpage(request,object,response,uri);
			return;
		} catch (Exception e) {
			response.sendError(505, "没找到资源");
			e.printStackTrace();
		}
		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		hz = arg0.getInitParameter("hz");
	}



}
