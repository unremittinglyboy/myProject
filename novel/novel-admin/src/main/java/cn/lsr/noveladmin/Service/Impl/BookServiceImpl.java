package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.RequestParam.BookParam;
import cn.lsr.noveladmin.Service.*;
import cn.lsr.noveladmin.constants.mqConstants;
import cn.lsr.noveladmin.mapping.AuthorMapper;
import cn.lsr.noveladmin.mapping.BookMapper;
import cn.lsr.noveladmin.model.Author;
import cn.lsr.noveladmin.model.Book;
import cn.lsr.noveladmin.model.BookExample;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private bookDataSyncService bookDataSyncService;

    @Autowired
    private IndexService indexService;

    @Autowired
    private ChapterService chapterService;

    private String pre = "book_";

    @Override
    public Book findById(Long id) {
        return bookMapper.selectByPrimaryKey(id);
    }

    @Override
    public Integer getAllBooksCount() {
        int countBooks = 0;
        SearchResponse all = getAll();
        countBooks = (int)all.getHits().getTotalHits().value;
        return new Integer(countBooks);
    }

    @Override
    public SearchResponse getAll() {
        SearchRequest request = new SearchRequest("book");
        request.source().query(QueryBuilders.matchAllQuery());
        SearchResponse response = null;
        try {
            response = client.search(request, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * basicSearch
     * @param bookParam
     * @param page
     * @param pageSize
     * @return
     */
    @Override
    public SearchResponse search(BookParam bookParam, Integer page, Integer pageSize) {
        SearchResponse response = null;
        try {
            SearchRequest request = new SearchRequest("book");
            //query
            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            //???????????????
            String key = bookParam.getKey();
            if (key == null || key.equals("")) {
                boolQuery.must(QueryBuilders.matchAllQuery());
            } else {
                boolQuery.must(QueryBuilders.matchQuery("all", key));
            }

            //????????????
            //???????????? 0:?????? 1:??????
            if (bookParam.getWorkDirection() != null) {
                boolQuery.filter(QueryBuilders.termQuery("workDirection", bookParam.getWorkDirection()));
            }

            //????????????
            if (!StringUtils.isEmpty(bookParam.getTypeName())) {
                System.out.println(bookParam.getTypeName());
                boolQuery.filter(QueryBuilders.termQuery("typeName", bookParam.getTypeName()));
            }

            //?????????0-3???4-6???7-10???
            if (bookParam.getMaxScore() != null && bookParam.getMinScore() != null) {
                boolQuery.filter(QueryBuilders.rangeQuery("score").lte(bookParam.getMaxScore()).gte(bookParam.getMinScore()));
            }

            //????????????
            if (bookParam.getIsVip() != null) {
                boolQuery.filter(QueryBuilders.termQuery("isVip", bookParam.getIsVip()));
            }

            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(
                    boolQuery,
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder[]{
                            new FunctionScoreQueryBuilder.FilterFunctionBuilder(
                                    QueryBuilders.termQuery("isRecommend", true),
                                    ScoreFunctionBuilders.weightFactorFunction(100)
                            )
                    });

            request.source().query(functionScoreQueryBuilder);

            //?????? ????????????????????????0???
            if (bookParam.getScore() != null) {
                request.source().sort(SortBuilders
                        .fieldSort("score")
                        .order(SortOrder.DESC)
                );
            }
            response = client.search(request, RequestOptions.DEFAULT);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * ??????ID????????????????????????redis??????????????????id???key???????????????????????????->?????????????????????????????????????????????????????????
     * @param id
     * @return
     */
    @Override
    public Book getOne(Long id) {
        String key = pre + id;
        Book book = null;
        ValueOperations<String, Book> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            book = operations.get(key);
            return book;
        }else{
            book = bookMapper.selectByPrimaryKey(id);
            operations.set(key, book, 5, TimeUnit.HOURS);
            return book;
        }
    }

    /**
     * pageHelper
     * @param page
     * @param pageSize
     * @param bookParam
     * @return
     */
    @Override
    public PageInfo<Book> getFuzzyQueryAllBooks(Integer page, Integer pageSize, BookParam bookParam) {
        if(page == null){
            page = 1; //?????????????????????
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//???????????????????????????????????????
        }
        SearchResponse response = null;
        PageInfo<Book> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            if(bookParam != null){
                response = search(bookParam, page, pageSize);
            }else{
                response = getAll();
            }
            List<Book> bookList = SearchResponseHandler(response);
            pageInfo = getPageInfo(page, pageSize, bookList);
            pageInfo.setNavigatePages(pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }


    /**
     * ??????ID???????????????????????????????????????????????????????????????
     *
     * @param id
     * @return
     */
    @Override
    public int deleteBook(Long id) {
        Long indexId = indexService.selectIndexIdByBookId(id);
        int deleteChapters = chapterService.deleteChaptersByIndexId(indexId);
        int deleteIndex = indexService.deleteIndexByBookId(id);
        int delete = bookMapper.deleteByPrimaryKey(id);
        String key = pre + id;
        if(delete != 0){
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey) {
                redisTemplate.delete(key);
            }
            bookDataSyncService.deleteById(id);
        }
        if(deleteChapters < 0) return -3;
        if(deleteIndex < 0)  return -2;
        return delete;
    }

    public int insertBook(Book book){
        book.setCreateTime(new Date());
        book.setScore(10.0f);
        book.setBookStatus(false);
        book.setStatus(false);
        book.setCommentCount(0);
        book.setVisitCount(0l);
        book.setWordCount(0);
        if(book.getIsVip() == null) book.setIsVip(false);
        Author author = authorService.getByName(book.getAuthorName());
        book.setAuthorId(author.getId());
        if(author == null) return -4;
        int insert = bookMapper.insert(book);
        int insertIndex = -1;
        if(insert > 0){
            BookExample bookExample = new BookExample();
            BookExample.Criteria criteria = bookExample.createCriteria();
            criteria.andBookNameEqualTo(book.getBookName());
            List<Book> books = bookMapper.selectByExample(bookExample);
            Book ans = null;
            for(Book b : books){
                if(book.getAuthorName().equals(b.getAuthorName())){
                    ans = b;
                }
            }
            insertIndex = indexService.insertIndexByBookId(ans);
        }
        if(insertIndex < 0 && insert < 0) return -3;
        if(insertIndex < 0) return -2;
        return insert;
    }

    @Override
    public int insertBook(Book book, RabbitTemplate rabbitTemplate) {
        book.setCreateTime(new Date());
        book.setScore(10.0f);
        book.setBookStatus(false);
        book.setStatus(false);
        book.setCommentCount(0);
        book.setVisitCount(0l);
        book.setWordCount(0);
        Author author = authorService.getByName(book.getAuthorName());
        book.setAuthorId(author.getId());
        if(book.getPicUrl() == null) book.setPicUrl("https://seopic.699pic.com/photo/40018/8375.jpg_wh1200.jpg");
        if(author == null) return -4;
        int insert = bookMapper.insert(book);
        int insertIndex = -1;
        if(insert > 0){
            BookExample bookExample = new BookExample();
            BookExample.Criteria criteria = bookExample.createCriteria();
            criteria.andBookNameEqualTo(book.getBookName());
            List<Book> books = bookMapper.selectByExample(bookExample);
            Book ans = null;
            for(Book b : books){
                if(book.getAuthorName().equals(b.getAuthorName())){
                    ans = b;
                }
            }
            insertIndex = indexService.insertIndexByBookId(ans);
            if(ans != null) rabbitTemplate.convertAndSend(mqConstants.BOOK_EXCHANGE, mqConstants.BOOK_INSERT_KEY, ans.getId());
        }
        if(insertIndex < 0 && insert < 0) return -3;
        if(insertIndex < 0) return -2;
        return insert;
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????key????????????????????????????????????????????????????????????
     * @param book
     * @return
     */
    @Override
    public int updateBook(Book book) {
        ValueOperations<String, Book> operations = redisTemplate.opsForValue();
        int result = bookMapper.updateByPrimaryKey(book);
        if(result != 0){
            String key = pre + book.getId();
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey){
                redisTemplate.delete(key);
            }
            Book newBook= getOne(book.getId());
            if(newBook != null){
                operations.set(key, newBook, 3, TimeUnit.HOURS);
            }
            bookDataSyncService.insertById(book.getId());
        }
        return result;
    }

    @Override
    public List<String> getAllBookName() {
        SearchResponse response = getAll();
        List<Book> all = SearchResponseHandler(response);
        List<String> bookNames = new ArrayList<>();
        for(Book book : all){
            bookNames.add(book.getBookName());
        }
        return bookNames;
    }

    public List<String> getAllBookNameNotMe(String MeName) {
        SearchResponse response = getAll();
        List<Book> all = SearchResponseHandler(response);
        List<String> bookNames = new ArrayList<>();
        for(Book book : all){
            if(book.getBookName().equals(MeName)) continue;
            bookNames.add(book.getBookName());
        }
        return bookNames;
    }

    private List<Book> SearchResponseHandler(SearchResponse response){
        //???????????????????????????
        SearchHits shs = response.getHits();

        //?????????????????????????????????
        SearchHit[] hits = shs.getHits();

        //?????????????????????????????????????????????
        LinkedList<Book> list = new LinkedList<>();

        for(SearchHit h : hits){
            Book book = JSON.parseObject(h.getSourceAsString(), Book.class);
            if(book != null){
                list.add(book);
            }
        }
        return list;
    }

    //???????????????????????????????????????????????????mapper?????????????????????????????????elasticsearch?????????????????????????????????????????????
    private <T> PageInfo<T> getPageInfo(int currentPage, int pageSize, List<T> list) {
        int total = list.size();
        if (total > pageSize) {
            int toIndex = pageSize * currentPage;
            if (toIndex > total) {
                toIndex = total;
            }
            list = list.subList(pageSize * (currentPage - 1), toIndex);
        }
        Page<T> page = new Page<>(currentPage, pageSize);
        page.addAll(list);
        page.setPages((total + pageSize - 1) / pageSize);
        page.setTotal(total);

        PageInfo<T> pageInfo = new PageInfo<>(page);
        return pageInfo;
    }
}
