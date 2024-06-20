package cn.rzpt.user.infrastructure.repository;

import cn.hutool.core.util.ObjectUtil;
import cn.rzpt.base.exception.BusinessException;
import cn.rzpt.base.util.JwtUtil;
import cn.rzpt.base.util.RedisCache;
import cn.rzpt.user.domain.repository.UserRepository;
import cn.rzpt.user.infrastructure.repository.mapper.UserMapper;
import cn.rzpt.user.infrastructure.repository.po.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import static cn.rzpt.base.exception.ResponseCode.USER_NAME_ALWAYS_EXISTS;
import static cn.rzpt.user.domain.constant.UserConstant.UserLoginConstant.LOGIN_USER_KEY;

@Slf4j
@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final JwtUtil jwtUtil;
    private final UserMapper userMapper;
    private final RedisCache redisCache;
    private final AuthenticationManager authenticationManager;

    @Override
    public void registerUser(User user) {
        LambdaQueryWrapper<User> usernameUnionWrapper = Wrappers.lambdaQuery(User.class)
                .eq(User::getUsername, user.getUsername());
        if (userMapper.selectCount(usernameUnionWrapper) != 0) {
            throw new BusinessException(USER_NAME_ALWAYS_EXISTS);
        }
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userMapper.insert(user);
    }

    @Override
    public String loginUser(User user) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        if (ObjectUtil.isNull(authenticate)) {
            throw new UsernameNotFoundException("用户名或密码错误");
        }
        User loginUser = (User) authenticate.getPrincipal();
        redisCache.setCacheObject(LOGIN_USER_KEY + loginUser.getId(), loginUser);
        return jwtUtil.createToken(loginUser.getId().toString(), null);
    }
}
