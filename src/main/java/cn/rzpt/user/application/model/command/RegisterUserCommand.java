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
@Schema(description = "注册用户指令")
public class RegisterUserCommand implements Serializable {

    @Schema(description = "用户名", example = "MyNameWax")
    private String username;
    @Schema(description = "用户昵称", example = "追风少年")
    private String nickname;
    @Schema(description = "用户密码", example = "123456")
    private String password;
    @Schema(description = "用户邮箱", example = "1022623504@qq.com")
    private String email;
    @Schema(description = "性别 - 0:保密 1:男 2:女", example = "1")
    private Integer sex;

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
                .nickname(nickname)
                .password(password)
                .sex(sex)
                .email(email)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }


}
