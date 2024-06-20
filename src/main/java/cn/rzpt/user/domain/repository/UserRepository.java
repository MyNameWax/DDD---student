package cn.rzpt.user.domain.repository;

import cn.rzpt.user.infrastructure.repository.po.User;

/**
 * 用户仓储接口
 */
public interface UserRepository {
    void registerUser(User user);

    String loginUser(User user);
}
