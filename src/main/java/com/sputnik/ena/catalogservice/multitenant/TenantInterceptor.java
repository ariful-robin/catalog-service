package com.sputnik.ena.catalogservice.multitenant;

import java.util.Base64;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import io.jsonwebtoken.*;

@Component
public class TenantInterceptor extends HandlerInterceptorAdapter {
	private static final String AUTHENTICATION_SCHEME = "Bearer";
	
	@Value("${oauth.jwt.signing.key}")
	private String signingKey;
   /* @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.header}")
    private String tokenHeader;*/
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
    	String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

    	String authToken = authHeader
    			.substring(AUTHENTICATION_SCHEME.length()).trim();
    	
    	boolean isSigned = Jwts.parser().isSigned(authToken);
    	String secret = Base64.getEncoder().encodeToString(signingKey.getBytes());

    	Claims claims = Jwts.parser()
    			.setSigningKey(secret)
    			.parseClaimsJws(authToken)
    			.getBody();

    	String tenantId = "";
    	authToken.length();
    	//        String tenantId = jwtTokenUtil.getTenantIdFromToken(authToken);
    	TenantContext.setCurrentTenant(tenantId);
    	return true;
    }
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView)
            throws Exception {
        TenantContext.clear();
    }
}
