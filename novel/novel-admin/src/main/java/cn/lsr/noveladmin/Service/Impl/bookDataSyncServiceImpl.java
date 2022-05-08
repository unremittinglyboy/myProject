package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.bookDataSyncService;
import cn.lsr.noveladmin.mapping.BookMapper;
import cn.lsr.noveladmin.model.Book;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@Service
@Transactional
public class bookDataSyncServiceImpl implements bookDataSyncService {

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private RestHighLevelClient client;

    @Override
    public void insertById(long id) {
        try {
            Book book = bookMapper.selectByPrimaryKey(id);
            IndexRequest request = new IndexRequest("book").id(String.valueOf(id));
            request.source(JSON.toJSONString(book), XContentType.JSON);
            client.index(request,RequestOptions.DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteById(long id) {
        try {
            if(bookMapper.selectByPrimaryKey(id) != null){
                bookMapper.deleteByPrimaryKey(id);
            }

            GetRequest eRequest = new GetRequest("book").id(String.valueOf(id));
            DeleteRequest dRequest = new DeleteRequest("book").id(String.valueOf(id));

            if(client.exists(eRequest, RequestOptions.DEFAULT)){
                client.delete(dRequest, RequestOptions.DEFAULT);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
