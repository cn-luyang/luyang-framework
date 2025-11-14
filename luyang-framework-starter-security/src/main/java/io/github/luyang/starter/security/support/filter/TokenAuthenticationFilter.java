package io.github.luyang.starter.security.support.filter;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.servlet.JakartaServletUtil;
import io.github.luyang.starter.base.common.constant.BaseConstant;
import io.github.luyang.starter.base.common.model.Result;
import io.github.luyang.starter.security.AuthUser;
import io.github.luyang.starter.security.common.enums.SecurityErrorEnum;
import io.github.luyang.starter.security.remote.feign.RemoteAuthApi;
import io.github.luyang.starter.security.util.SecurityUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Optional;

/**
 * Token 认证过滤器
 *
 * @author yang.lu
 */
@RequiredArgsConstructor
public class TokenAuthenticationFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    /**
     * 远程Token验证服务
     */
    private final RemoteAuthApi remoteAuthApi;

    /**
     * 对每个请求进行Token验证和认证处理
     *
     * @param request  HTTP请求对象
     * @param response HTTP响应对象
     * @param chain    过滤器链
     * @throws ServletException Servlet异常
     * @throws IOException      IO异常
     * @author yang.lu
     */
    @Override
    @SuppressWarnings("NullableProblems")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
        throws ServletException, IOException {

        // 从请求中提取Access Token
        String accessToken = SecurityUtil.getAccessTokenValue(request);

        // 如果Token为空，直接放行（由后续认证机制处理）
        if (StrUtil.isBlank(accessToken)) {
            chain.doFilter(request, response);
            return;
        }

        try {
            // 验证Token有效性
            AuthUser authUser = validateToken(accessToken);
            // 设置认证信息到Security上下文
            setAuthentication(request, authUser);
            // 填充认证信息到请求属性
            fillAuthInfo(request, authUser);
            // 继续过滤器链
            chain.doFilter(request, response);
        } catch (AuthenticationException e) {
            logger.error("访问令牌认证异常", e);
            // 处理认证异常
            handleAuthenticationException(response);
        }
    }


    /**
     * 验证Token有效性
     * 调用远程服务验证Token并返回用户主体信息
     *
     * @param accessToken 访问令牌
     * @return 统一用户主体信息
     * @author yang.lu
     */
    private AuthUser validateToken(String accessToken) {
        // 调用远程Token验证服务
        Result<AuthUser> authUserResult = null;
//        Result<AuthUser> authUserResult = remoteAuthApi.checkToken(accessToken);

        // 验证失败时抛出异常
        if (!authUserResult.isSuccess() || BeanUtil.isEmpty(authUserResult.getData())) {
            throw new AuthenticationServiceException("Token validation failed: " + authUserResult.getMessage());
        }

        return authUserResult.getData();
    }

    /**
     * 设置认证信息到 Security 上下文
     * 创建 Authentication 对象并设置到 SecurityContextHolder
     *
     * @param request  HTTP请求
     * @param authUser 统一用户主体信息
     * @author yang.lu
     */
    private void setAuthentication(HttpServletRequest request, AuthUser authUser) {
        // 创建认证令牌（无凭证，无权限）
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
            authUser, null, Collections.emptyList()
        );
        // 设置Web认证详情（IP地址、Session ID等）
        authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        // 设置认证信息到安全上下文
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    }

    /**
     * 填充认证信息到请求属性
     * 将用户信息设置到请求属性中供业务层使用
     *
     * @param request  HTTP请求
     * @param authUser 统一用户主体信息
     * @author yang.lu
     */
    private void fillAuthInfo(HttpServletRequest request, AuthUser authUser) {
        Optional.ofNullable(authUser).ifPresent(principal -> {
            // 设置用户ID到请求属性
            request.setAttribute(BaseConstant.ATTR_USER_ID, principal.userId());
            // 设置客户端ID到请求属性
            request.setAttribute(BaseConstant.ATTR_CLIENT_ID, principal.clientId());
        });
    }


    /**
     * 处理认证异常
     * 返回统一的错误响应格式
     *
     * @param response HTTP响应
     * @author yang.lu
     */
    private void handleAuthenticationException(HttpServletResponse response) {
        // 设置HTTP状态码为401未授权
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 设置响应内容类型为JSON
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        // 设置字符编码
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // 创建错误响应结果
        Result<?> errorResult = Result.failure(SecurityErrorEnum.VALIDATE_EXCEPTION_ACCESS_TOKEN);
        // 将错误结果序列化为JSON并写入响应
        JakartaServletUtil.write(response, errorResult.toString(), MediaType.APPLICATION_JSON_UTF8_VALUE);
    }
}
