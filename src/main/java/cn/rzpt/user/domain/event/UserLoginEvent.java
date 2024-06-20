package cn.rzpt.user.domain.event;

import cn.rzpt.base.model.event.BaseDomainEvent;
import cn.rzpt.user.infrastructure.repository.po.User;


/**
 * 用户登录领域事件
 */
public class UserLoginEvent extends BaseDomainEvent<User> {

    public UserLoginEvent(User user) {
        super(user);
    }

}
