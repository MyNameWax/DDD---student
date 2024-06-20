package cn.rzpt.user.application.service;

import cn.rzpt.user.infrastructure.elasticsearch.entity.EsUserEntity;

import java.util.List;

/**
 * 用户查询应用服务
 * 复杂逻辑在此组合,简单逻辑可以直接CQRS,直接查询仓储层进行数据聚合
 */
public interface UserQueryApplicationService {

    /**
     * 用户列表查询
     *
     * @return 用户列表
     */
    List<EsUserEntity> list();

}
