package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.ElasticSearchDocumentService;
import cn.lsr.noveladmin.mapping.BookMapper;
import cn.lsr.noveladmin.model.Book;
import com.alibaba.fastjson.JSON;
import lombok.AllArgsConstructor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@Transactional
public class ElasticSearchDocumentServiceImpl implements ElasticSearchDocumentService {

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private BookMapper bookMapper;

    @Override
    public void initializeDocuments() {
        BulkRequest request = new BulkRequest();
        List<Book> list = bookMapper.getBooks();
        boolean flag = (list == null || list.size() == 0);
        try {
            if(!flag){
                for(Book book : list){
                    GetRequest getRequest = new GetRequest("book", String.valueOf(book.getId()));
                    request.add(
                            new IndexRequest("book")
                                    .id(String.valueOf(book.getId()))
                                    .source(JSON.toJSONString(book), XContentType.JSON)
                    );
                }
                client.bulk(request, RequestOptions.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
