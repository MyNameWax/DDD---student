package cn.rzpt.user.application.model.command;


import cn.rzpt.user.infrastructure.repository.po.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "登录用户指令")
public class LoginUserCommand implements Serializable {

    /**
     * 用户名
     */
    @Schema(description = "用户名", example = "MyNameWax")
    private String username;

    /**
     * 登录密码
     */
    @Schema(description = "登录密码", example = "123456")
    private String password;


    /**
     * 将Command转换为聚合根
     * 逻辑简单，只是做一些字段的映射则在command里面直接转化返回给应用服务层使用即可。
     * 如果逻辑复杂，command参数进来需要做一些比较复杂的逻辑处理，则使用工厂
     *
     * @return User对象
     */
    public User toUser() {
        return User.builder()
                .username(username)
                .password(password)
                .lastLoginTime(LocalDateTime.now())
                .build();
    }

}
