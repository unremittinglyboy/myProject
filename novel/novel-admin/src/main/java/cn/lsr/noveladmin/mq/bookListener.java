package cn.lsr.noveladmin.mq;

import cn.lsr.noveladmin.Service.bookDataSyncService;
import cn.lsr.noveladmin.constants.mqConstants;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class bookListener {

    @Autowired
    private bookDataSyncService bookDataSyncService;

    /**
     * 监听酒店新增或修改的业务
      * @param id
     */
    @RabbitListener(queues = mqConstants.BOOK_INSERT_QUEUE)
    public void listenBookInsertOrUpdate(long id){
        bookDataSyncService.insertById(id);
    }

    /**
     * 监听酒店删除的业务
     * @param id
     */
    @RabbitListener(queues = mqConstants.BOOK_DELETE_QUEUE)
    public void listenBookDelete(long id){
        bookDataSyncService.deleteById(id);
    }
}
