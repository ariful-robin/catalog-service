package com.sputnik.ena.catalogservice.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.sputnik.ena.catalogservice.config.tenant.TenantContext;


@Component
public class TransactionFilter implements Filter {
	private static Logger logger = LoggerFactory.getLogger(TenantContext.class.getName());

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		logger.debug(
				"Starting a transaction for req : {}", 
				req.getRequestURI());

		chain.doFilter(request, response);
		logger.debug(
	          "Committing a transaction for req : {}", 
	          req.getRequestURI());
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
