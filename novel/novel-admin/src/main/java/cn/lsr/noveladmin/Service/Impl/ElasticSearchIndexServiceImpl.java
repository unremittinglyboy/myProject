package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.ElasticSearchIndexService;
import cn.lsr.noveladmin.constants.BookIndexTemplate;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class ElasticSearchIndexServiceImpl implements ElasticSearchIndexService {
    @Autowired
    private RestHighLevelClient client;

    @Override
    public void initializeIndex() {
        CreateIndexRequest cRequest = new CreateIndexRequest("book");
        GetIndexRequest gRequest = new GetIndexRequest("book");

        try {
            if(!client.indices().exists(gRequest, RequestOptions.DEFAULT)){
                //引入DSL语句
                cRequest.source(BookIndexTemplate.BookIndex, XContentType.JSON);
                client.indices().create(cRequest, RequestOptions.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
