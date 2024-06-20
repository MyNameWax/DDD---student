package cn.rzpt.user.infrastructure.repository.mapper;

import cn.rzpt.user.infrastructure.repository.po.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
