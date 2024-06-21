package cn.rzpt.user.application.service.impl;

import cn.rzpt.base.util.GsonUtil;
import cn.rzpt.user.application.service.UserQueryApplicationService;
import cn.rzpt.user.infrastructure.elasticsearch.entity.EsUserEntity;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserQueryApplicationServiceImpl implements UserQueryApplicationService {

    private final RestHighLevelClient client;

    @SneakyThrows
    @Override
    public List<EsUserEntity> list() {
        List<EsUserEntity> list = new ArrayList<>();
        SearchRequest request = new SearchRequest().indices("user");
        request.source(new SearchSourceBuilder());
        SearchResponse response = client.search(request, RequestOptions.DEFAULT);
        for (SearchHit hit : response.getHits().getHits()) {
            EsUserEntity esUserEntity = GsonUtil.gsonToBean(hit.getSourceAsString(), EsUserEntity.class);
            list.add(esUserEntity);
        }
        return list;
    }


}
