package cn.rzpt.share.event;

import cn.rzpt.base.model.event.BaseDomainEvent;

/**
 * 领域事件发布接口
 */
public interface DomainEventPublisher {

    /**
     * 发布事件
     *
     * @param event 领域事件
     */
    void publishEvent(BaseDomainEvent event);

}
