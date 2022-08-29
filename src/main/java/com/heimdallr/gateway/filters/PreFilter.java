package com.heimdallr.gateway.filters;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;

public class PreFilter extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		// TODO Auto-generated method stub
		System.out.println("Inside Pre Filter");
		
//		RequestContext ctx = RequestContext.getCurrentContext();
//		
//		HttpServletRequest req = ctx.getRequest();
//		
//	    System.out.println("Request Method : " + req.getMethod() + " Request URL : " + req.getRequestURL().toString());

		return null;
	}

	@Override
	public String filterType() {
		// TODO Auto-generated method stub
		return "pre";
	}

	@Override
	public int filterOrder() {
		// TODO Auto-generated method stub
		return 1;
	}
}
