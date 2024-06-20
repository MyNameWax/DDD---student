package cn.rzpt.base.util;


import cn.rzpt.base.exception.BusinessException;
import cn.rzpt.base.exception.ResponseCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.util.Date;
import java.util.Map;


@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expTime}")
    private Duration expTime;

    /**
     * 生成Token
     *
     * @param subject 主题
     * @param claims  内容
     * @return 令牌
     */
    public String createToken(String subject, Map<String, Object> claims) {
        JwtBuilder builder = Jwts.builder();
        if (claims != null) {
            builder.setClaims(claims);
        }
        if (StringUtils.hasLength(subject)) {
            builder.setSubject(subject);
        }
        long currentTimeMillis = System.currentTimeMillis();
        builder.setIssuedAt(new Date(currentTimeMillis));
        long millis = expTime.toMillis();
        if (millis > 0) {
            long expTime = currentTimeMillis + millis;
            builder.setExpiration(new Date(expTime));
        }
        if (StringUtils.hasLength(secretKey)) {
            SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;
            builder.signWith(signatureAlgorithm, DatatypeConverter.parseBase64Binary(secretKey));
        }
        return builder.compact();
    }

    /**
     * 解析Token
     *
     * @param token 命令
     * @return Jwt信息
     */
    public Claims parseToken(String token) {
        try {
            return Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(secretKey))
                    .parseClaimsJws(token).getBody();
        } catch (Exception e) {
            log.error("Jwt解析异常:{}", e.toString());
            throw new BusinessException(ResponseCode.NOT_AUTHORIZATION);
        }
    }

    /**
     * 获取用户ID
     *
     * @param token 令牌
     * @return 用户ID
     */
    public String getUserId(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            return claims.getSubject();
        }
        return null;
    }

    /**
     * 校验Token是否过期
     *
     * @param token 令牌
     * @return 是否过期
     */
    public boolean validateToken(String token) {
        Claims claims = parseToken(token);
        if (claims != null) {
            Date exp = claims.getExpiration();
            if (exp != null) {
                return exp.after(new Date());
            }
        }
        return false;
    }


}
