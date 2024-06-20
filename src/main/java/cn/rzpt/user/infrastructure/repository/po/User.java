package cn.rzpt.user.infrastructure.repository.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * 用户实体
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Schema(description = "用户信息表")
@Slf4j
@TableName(value = "sys_user")
public class User implements Serializable, UserDetails {

    /**
     * ID主键
     */
    @TableId(type = IdType.AUTO)
    @Schema(description = "用户ID", example = "1")
    private Long id;

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "test")
    private String username;

    /**
     * 用户昵称
     */
    @Schema(description = "用户昵称", example = "追风少年")
    private String nickname;
    /**
     * 用户密码
     */
    @Schema(description = "用户密码", example = "1123456")
    private String password;

    /**
     * 邮箱
     */
    @Schema(description = "用户邮箱", example = "1022623504@qq.com")
    private String email;

    /**
     * 性别
     */
    @Schema(description = "性别 - 0:保密 1:男 2:女", example = "1")
    private Integer sex;

    /**
     * 删除标识
     */
    @Schema(description = "删除标识 - 0:未删除 1:删除", example = "0")
    private Integer delFlag;

    /**
     * 最后登录时间
     */
    @Schema(description = "最后登录时间")
    private LocalDateTime lastLoginTime;

    /**
     * 创建时间
     */
    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
