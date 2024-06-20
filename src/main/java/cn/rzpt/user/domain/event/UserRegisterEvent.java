package cn.rzpt.user.domain.event;

import cn.rzpt.base.model.event.BaseDomainEvent;
import cn.rzpt.user.infrastructure.repository.po.User;

/**
 * 用户注册领域事件
 */
public class UserRegisterEvent extends BaseDomainEvent<User> {

    public UserRegisterEvent(User user) {
        super(user);
    }

}
