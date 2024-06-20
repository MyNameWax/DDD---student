package cn.rzpt.base.model.event;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class BaseDomainEvent<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1465328245048581896L;

    /**
     * 发生时间
     */
    private LocalDateTime publishTime;

    /**
     * 领域事件数据
     */
    private T data;

    public BaseDomainEvent(T data) {
        this.data = data;
        this.publishTime = LocalDateTime.now();
    }


}
