package cn.rzpt.user.interaction.controller;

import cn.rzpt.base.result.Result;
import cn.rzpt.base.result.Results;
import cn.rzpt.user.application.model.command.LoginUserCommand;
import cn.rzpt.user.application.model.command.RegisterUserCommand;
import cn.rzpt.user.application.service.UserApplicationService;
import cn.rzpt.user.application.service.UserQueryApplicationService;
import cn.rzpt.user.infrastructure.elasticsearch.entity.EsUserEntity;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户接口
 */

@Tag(name = "用户管理")
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {


    private final UserApplicationService userApplicationService;
    private final UserQueryApplicationService userQueryApplicationService;

    /**
     * 用户注册
     *
     * @param command 注册用户命令
     */
    @PostMapping("/register")
    public Result<Void> registerUser(@RequestBody RegisterUserCommand command) {
        userApplicationService.registerUser(command);
        return Results.success();
    }

    /**
     * 用户登录
     *
     * @param command 登录用户命令
     * @return Token令牌
     */
    @PostMapping("/login")
    public Result<String> loginUser(@RequestBody LoginUserCommand command) {
        return Results.success(userApplicationService.login(command));
    }

    @GetMapping("/list")
    public Result<List<EsUserEntity>> list() {
        return Results.success(userQueryApplicationService.list());
    }


}
