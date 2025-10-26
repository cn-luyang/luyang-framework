package io.github.luyang.starter.security;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * 统一身份主体
 *
 * @param clientId     用户ID
 * @param userId       客户端ID
 * @param zhName       中文名
 * @param enName       英文名
 * @param expireTime   Token过期时间
 * @param attachedInfo 通用扩展字段，可附加组织机构、登录时间等上下文
 * @author yang.lu
 */
public record AuthUser(
    String clientId,
    String userId,
    String zhName,
    String enName,
    LocalDateTime expireTime,
    Map<String, Object> attachedInfo
) implements Serializable {

    @Serial
    private static final long serialVersionUID = 4758668578675934182L;
}
