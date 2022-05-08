package cn.lsr.noveladmin.initialize;

import cn.lsr.noveladmin.Service.ElasticSearchDocumentService;
import cn.lsr.noveladmin.Service.ElasticSearchIndexService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 项目启动时加入book索引库
 */

public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //初始化索引
        ElasticSearchIndexService s = contextRefreshedEvent.getApplicationContext().getBean(ElasticSearchIndexService.class);
        s.initializeIndex();
        //将数据库对应数据批量注入elasticsearch
        ElasticSearchDocumentService d = contextRefreshedEvent.getApplicationContext().getBean(ElasticSearchDocumentService.class);
        d.initializeDocuments();
    }
}
