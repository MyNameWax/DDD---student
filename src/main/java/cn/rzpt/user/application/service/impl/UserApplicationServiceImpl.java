package cn.rzpt.user.application.service.impl;

import cn.hutool.core.lang.Assert;
import cn.rzpt.share.event.DomainEventPublisher;
import cn.rzpt.user.application.model.command.LoginUserCommand;
import cn.rzpt.user.application.model.command.RegisterUserCommand;
import cn.rzpt.user.application.service.UserApplicationService;
import cn.rzpt.user.domain.event.UserLoginEvent;
import cn.rzpt.user.domain.event.UserRegisterEvent;
import cn.rzpt.user.domain.repository.UserRepository;
import cn.rzpt.user.domain.service.UserDomainService;
import cn.rzpt.user.infrastructure.elasticsearch.entity.EsUserEntity;
import cn.rzpt.user.infrastructure.repository.po.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserApplicationServiceImpl implements UserApplicationService {

    private final UserRepository userRepository;
    private final UserDomainService userDomainService;
    private final DomainEventPublisher domainEventPublisher;


    /**
     * 用户注册
     *
     * @param command 注册用户命令
     */
    @Override
    public void registerUser(RegisterUserCommand command) {
        User user = command.toUser();
        // 校验用户入参数据
        checkUserRegisterParams(user);
        // 存储用户
        userRepository.registerUser(user);
        // 发布注册用户的领域事件
        domainEventPublisher.publishEvent(new UserRegisterEvent(user));
    }

    /**
     * 用户登录
     *
     * @param command 登录用户命令
     * @return Token令牌
     */
    @Override
    public String login(LoginUserCommand command) {
        User user = command.toUser();
        // 校验用户登录命令参数
        checkUserLoginParams(user);
        // 用户登录仓储
        String token = userRepository.loginUser(user);
        // 发布登录用户的领域对象
        domainEventPublisher.publishEvent(new UserLoginEvent(user));
        return token;
    }



    private static void checkUserRegisterParams(User user) {
        Assert.notNull(user.getUsername(), "用户名不能为空");
        Assert.notNull(user.getNickname(), "用户昵称不能为空");
        Assert.notNull(user.getPassword(), "密码不能为空");
        Assert.notNull(user.getSex(), "性别不能为空");
        Assert.notNull(user.getEmail(), "用户邮箱不能为空");
    }

    private static void checkUserLoginParams(User user) {
        Assert.notNull(user.getUsername(), "用户名不能为空");
        Assert.notNull(user.getPassword(), "密码不能为空");
    }


}
