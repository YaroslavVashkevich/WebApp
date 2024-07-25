package org.example.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.dto.UserDto;
import org.example.util.UrlPath;
import java.io.IOException;
import java.util.Set;

@WebFilter("/*") 
public class AuthorizationFilter implements Filter {
    public static final Set<String> PUBLIC_PATH = Set.of(UrlPath.LOGIN, UrlPath.REGISTRATION, UrlPath.IMAGES, UrlPath.LOCALE);
   
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        String uri = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isPublicPath(uri) || isUserLoggedIn(servletRequest)){ 
            filterChain.doFilter(servletRequest, servletResponse);
        }else{
            String prevPage = ((HttpServletRequest) servletRequest).getHeader("referer"); // с какой страницы
            ((HttpServletResponse) servletResponse).sendRedirect(prevPage != null ? prevPage: UrlPath.LOGIN);}}
    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        UserDto user = (UserDto)((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;}
    private boolean isPublicPath(String uri) {
        return PUBLIC_PATH.stream().anyMatch(path -> uri.startsWith(path));
    }}
