package com.mf.mall.user.filter;

import com.mf.mall.common.base.BaseResponse;
import com.mf.mall.common.exception.BusinessException;
import com.mf.mall.common.util.JwtUtil;
import com.mf.mall.user.config.AppProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private AppProperties appProperties;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (checkJwtToken(httpServletRequest) ) {
            try {
                if (!verifyToken(httpServletRequest)) {
                    httpServletResponse.getWriter().println(BaseResponse.error());
                    return;
                }
            } catch (BusinessException e) {
                httpServletResponse.getWriter()..println(BaseResponse.error(e.getResponseEnum()));
                return;
            }


        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private boolean verifyToken (HttpServletRequest request) {
        String token = request.getHeader(appProperties.getJwt().getHeader())
                .replace(appProperties.getJwt().getPrefix(), "").trim();
        return JwtUtil.validateToken(token);
    }


    private boolean checkJwtToken(HttpServletRequest request) {
        String authHeader = request.getHeader(appProperties.getJwt().getHeader());
        return authHeader != null && authHeader.startsWith(appProperties.getJwt().getPrefix());
    }
}
