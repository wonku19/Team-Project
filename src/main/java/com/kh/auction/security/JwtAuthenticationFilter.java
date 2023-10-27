package com.kh.auction.security;

import com.kh.auction.domain.Member;
import com.kh.auction.service.MemberService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter { // 한 번만 인증하는 필터

    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private MemberService service;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 요청에서 토큰 가져오기
        String token = parseBearerToken(request);
        // 토큰 검사
        if(token!=null && !token.equalsIgnoreCase("null")) {
            // Member -> id
            String id = tokenProvider.validateAndGetUserId(token);
            String authority = tokenProvider.validateAndGetUserAuthority(token);
            log.info("jwt 토큰값 : " + token);
            log.info(authority.equals("ROLE_USER")+"jwt 확인");
            log.info("jwt 권한 : "+authority);
            log.info(id);

                // 사용자가 USER 권한인 경우
                if (authority.equals("ROLE_USER")) {
                    AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            id, // 인증된 사용자 정보
                            null,
                            AuthorityUtils.NO_AUTHORITIES
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                    securityContext.setAuthentication(authentication);
                    SecurityContextHolder.setContext(securityContext);
                } else {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                }
            }
        filterChain.doFilter(request, response);
    }

    private String parseBearerToken(HttpServletRequest request) {
        // Http 요청의 헤더를 파싱해 Bearer 토큰을 리턴한다.
        String bearerToken = request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }


}
