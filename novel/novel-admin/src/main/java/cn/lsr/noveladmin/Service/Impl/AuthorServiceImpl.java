package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.AuthorService;
import cn.lsr.noveladmin.mapping.AuthorMapper;
import cn.lsr.noveladmin.model.Author;
import cn.lsr.noveladmin.model.AuthorExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@Transactional
public class AuthorServiceImpl implements AuthorService {
    @Autowired
    private AuthorMapper authorMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private String pre = "author_";

    @Override
    public List<Author> getAll() {
        return authorMapper.getAll();
    }

    @Override
    public Author getByName(String Name) {
        AuthorExample authorExample = new AuthorExample();
        AuthorExample.Criteria criteria = authorExample.createCriteria();
        criteria.andPenNameEqualTo(Name);
        List<Author> authors = authorMapper.selectByExample(authorExample);
        if(authors == null || authors.size() == 0){
            return null;
        }
        return authors.get(0);
    }

    /**
     * 根据ID搜索策略：先查询redis是否存在对应id的key，不存在则查数据库->更新缓存，存在则直接从缓存中取出数据。
     * @param id
     * @return
     */
    @Override
    public Author getOne(Long id) {
        String key = pre + id;
        Author author = null;
        ValueOperations<String, Author> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            author = operations.get(key);
            return author;
        }else{
            author = authorMapper.selectByPrimaryKey(id);
            operations.set(key, author, 5, TimeUnit.HOURS);
            return author;
        }
    }

    @Override
    public Integer GetAllAuthorsCount() {
        int countAuthors = 0;
        countAuthors = (int)authorMapper.countByExample(new AuthorExample());
        return new Integer(countAuthors);
    }

    @Override
    public PageInfo<Author> getAllAuthors(Integer page, Integer pageSize) {
        if(page == null){
            page = 1; //设置默认当前页
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//设置默认每页显示的数据条数
        }

        PageInfo<Author> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            List<Author> authorList = getAll();
            pageInfo = new PageInfo<Author>(authorList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }


    /**
     * 根据ID删除策略：删除数据表中的数据，然后删除缓存
     * @param id
     * @return
     */
    @Override
    public int deleteAuthor(Long id) {
        int result = authorMapper.deleteByPrimaryKey(id);
        String key = pre + id;
        if(result != 0){
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey) {
                redisTemplate.delete(key);
            }
        }
        return result;
    }

    public int insertAuthor(Author author){
        author.setCreateTime(new Date());
        return authorMapper.insert(author);
    }

    /**
     * 更新策略：在数据库中进行更新，成功则检测是否存在对应key，存在则删除后加入新的，不存在直接加入。
     * @param author
     * @return
     */
    @Override
    public int updateAuthor(Author author) {
        ValueOperations<String, Author> operations = redisTemplate.opsForValue();
        int result = authorMapper.updateByPrimaryKey(author);
        if(result != 0){
            String key = pre + author.getId();
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey){
                redisTemplate.delete(key);
            }
            Author newAuthor= getOne(author.getId());
            if(newAuthor != null){
                operations.set(key, newAuthor, 3, TimeUnit.HOURS);
            }
        }
        return result;
    }


    @Override
    public PageInfo<Author> getFuzzyQueryAllAuthors(Integer page, Integer pageSize, String querySequence) {
        if(page == null){
            page = 1; //设置默认当前页
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//设置默认每页显示的数据条数
        }

        PageInfo<Author> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            List<Author> authorList = fuzzyQuery(querySequence);
            pageInfo = new PageInfo<Author>(authorList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }

    @Override
    public List<Author> fuzzyQuery(String querySequence) {
        if(querySequence == null || querySequence.length() == 0) return null;
        querySequence = querySequence.trim();
        String[] split = querySequence.split("&");
        int n = split.length;
        AuthorExample authorExample = new AuthorExample();
        AuthorExample.Criteria criteria = authorExample.createCriteria();
        if(n >= 1) if(!split[0].equals("")) criteria.andPenNameLike(split[0] + "%");
        if(n >= 2) if(!split[1].equals("")) criteria.andTelPhoneEqualTo(split[1]);
        if(n >= 3) if(!split[2].equals("")) criteria.andChatAccountEqualTo(split[2]);
        if(n >= 4) if(!split[3].equals("")) criteria.andEmailEqualTo(split[3]);
        if(n >= 5){
            byte work = -1;
            if(split[4].equals("女频")){
                work = 1;
            }else if(split[4].equals("男频")){
                work = 0;
            }
            if(!split[4].equals("")) criteria.andWorkDirectionEqualTo(work);
        }
        if(n >= 6){
            if(!split[5].equals("")){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                Date date = null;
                try {
                    date = sdf.parse(split[5]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                criteria.andCreateTimeEqualTo(date == null ? null : date);
            }
        }
        return authorMapper.selectByExample(authorExample);
    }

    @Override
    public List<String> getAllPenName() {
        List<Author> authors = authorMapper.getAll();
        List<String> penNameList = new ArrayList<>();
        for(Author author : authors){
            penNameList.add(author.getPenName());
        }
        return penNameList;
    }


    public List<String> getAllPenNameNotMe(String MeName) {
        List<Author> authors = authorMapper.getAll();
        List<String> penNameList = new ArrayList<>();
        for(Author author : authors){
            if(author.getPenName().equals(MeName)) continue;
            penNameList.add(author.getPenName());
        }
        return penNameList;
    }
}
