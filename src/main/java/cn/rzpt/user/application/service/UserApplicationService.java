package cn.rzpt.user.application.service;

import cn.rzpt.user.application.model.command.LoginUserCommand;
import cn.rzpt.user.application.model.command.RegisterUserCommand;
import cn.rzpt.user.infrastructure.elasticsearch.entity.EsUserEntity;

import java.util.List;

public interface UserApplicationService {

    /**
     * 用户注册
     *
     * @param command 注册用户命令
     */
    void registerUser(RegisterUserCommand command);


    /**
     * 用户登录
     *
     * @param command 登录用户命令
     * @return Token令牌
     */
    String login(LoginUserCommand command);




}
