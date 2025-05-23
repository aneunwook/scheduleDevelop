package org.example.scheduledevelop.filter;

import jakarta.servlet.*;
import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.util.PatternMatchUtils;

public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {"/users/signup", "/users/login"};

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String requestURI = httpRequest.getRequestURI();

        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        if(!isWhiteList(requestURI)){
            HttpSession session = httpRequest.getSession(false);

            if (session == null || session.getAttribute("user") == null ){
                httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpResponse.getWriter().write("로그인을 해주세요.");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteList(String requestURI){
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURI);
    }
}
