package cn.rzpt.user.infrastructure.config;

import cn.rzpt.base.exception.BusinessException;
import cn.rzpt.base.exception.ResponseCode;
import cn.rzpt.user.application.service.UserApplicationService;
import cn.rzpt.user.infrastructure.elasticsearch.entity.EsUserEntity;
import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
@RequiredArgsConstructor
public class CanalConfig {
    private final static int BATCH_SIZE = 1000;
    private final UserApplicationService userApplicationService;

    public void run() {
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress("localhost", 11111), "example", "", "");
        try {
            connector.connect();
            connector.subscribe(".*..*");
            connector.rollback();
            while (true) {
                Message message = connector.getWithoutAck(BATCH_SIZE);
                int size = message.getEntries().size();
                if (message.getId() == -1 || size == 0) {
                    TimeUnit.SECONDS.sleep(1);
                } else {
                    dataHandle(message.getEntries());
                }
                connector.ack(message.getId());
            }
        } catch (Exception e) {
//            e.printStackTrace();
            throw new BusinessException(ResponseCode.FAIL);
        } finally {
            connector.disconnect();
        }
    }

    private void dataHandle(List<CanalEntry.Entry> entryList) throws Exception {
        for (CanalEntry.Entry entry : entryList) {
            if (entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONBEGIN || entry.getEntryType() == CanalEntry.EntryType.TRANSACTIONEND) {
                continue;
            }
            CanalEntry.RowChange rowChange = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            CanalEntry.EventType eventType = rowChange.getEventType();
            if (eventType == CanalEntry.EventType.INSERT) {
                log.info("新增数据：库名：{},--表名：{}", entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
                if (entry.getHeader().getTableName().equals("sys_user")) {
                    EsUserEntity esUserEntity = getEsUserEntity(rowChange);
                    userApplicationService.addData(esUserEntity);
                }
            } else if (eventType == CanalEntry.EventType.UPDATE) {
                log.info("修改数据：库名：{},--表名：{}", entry.getHeader().getSchemaName(), entry.getHeader().getTableName());
            } else if (eventType == CanalEntry.EventType.DELETE) {
                log.info("删除数据：库名：{},--表名：{}", entry.getHeader().getSchemaName(), entry.getHeader().getTableName());

            }
        }
    }

    private static EsUserEntity getEsUserEntity(CanalEntry.RowChange rowChange) {
        List<CanalEntry.RowData> rowDatasList = rowChange.getRowDatasList();
        EsUserEntity esUserEntity = new EsUserEntity();
        for (CanalEntry.RowData rowData : rowDatasList) {
            List<CanalEntry.Column> afterColumnsList = rowData.getAfterColumnsList();
            afterColumnsList.forEach(item -> {
                String name = item.getName();
                String value = item.getValue();

                if ("id".equals(name)) {
                    esUserEntity.setId(Long.parseLong(value));
                }
                if ("username".equals(name)) {
                    esUserEntity.setUsername(value);
                }
                if ("nickname".equals(name)) {
                    esUserEntity.setNickname(value);
                }
                if ("email".equals(name)) {
                    esUserEntity.setEmail(value);
                }
            });
        }
        return esUserEntity;
    }
}
