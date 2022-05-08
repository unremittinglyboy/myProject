package cn.lsr.noveladmin.constants;

public class mqConstants {
    /**
     * 交换机
     */
    public final static String BOOK_EXCHANGE = "novel.topic";
    /**
     * 监听新增和修改的队列
     */
    public final static String BOOK_INSERT_QUEUE = "novel.insert.queue";
    /**
     * 监听删除的队列
     */
    public final static String BOOK_DELETE_QUEUE = "novel.delete.queue";
    /**
     * 新增和修改的 RoutingKey
     */
    public final static String BOOK_INSERT_KEY = "novel.insert";
    /**
     * 删除的 RoutingKey
     */
    public final static String BOOK_DELETE_KEY = "novel.delete";
}
