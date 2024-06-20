package cn.rzpt.share.event;

import cn.rzpt.base.model.event.BaseDomainEvent;
import cn.rzpt.base.util.GsonUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * 领域事件发布实现类
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DomainEventPublisherImpl implements DomainEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publishEvent(BaseDomainEvent event) {
        log.debug("发布事件,cn.rzpt.user.infrastructure.handler：{}", GsonUtil.gsonToString(event));
        applicationEventPublisher.publishEvent(event);
    }
}
