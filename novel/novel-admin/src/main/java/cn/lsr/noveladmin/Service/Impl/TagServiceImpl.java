package cn.lsr.noveladmin.Service.Impl;

import cn.lsr.noveladmin.Service.TagService;
import cn.lsr.noveladmin.mapping.TagMapper;
import cn.lsr.noveladmin.model.Tag;
import cn.lsr.noveladmin.model.TagExample;
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
public class TagServiceImpl implements TagService {
    @Autowired
    private TagMapper tagMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    private String pre = "tag_";

    @Override
    public List<Tag> getAll() {
        return tagMapper.getAll();
    }

    /**
     * 根据ID搜索策略：先查询redis是否存在对应id的key，不存在则查数据库->更新缓存，存在则直接从缓存中取出数据。
     * @param id
     * @return
     */
    @Override
    public Tag getOne(Integer id) {
        String key = pre + id;
        Tag tag = null;
        ValueOperations<String, Tag> operations = redisTemplate.opsForValue();
        boolean hasKey = redisTemplate.hasKey(key);
        if(hasKey){
            tag = operations.get(key);
            return tag;
        }else{
            tag = tagMapper.selectByPrimaryKey(id);
            operations.set(key, tag, 5, TimeUnit.HOURS);
            return tag;
        }
    }

    @Override
    public Integer GetAllTagsCount() {
        int countTags = 0;
        countTags = (int)tagMapper.countByExample(new TagExample());
        return new Integer(countTags);
    }

    @Override
    public PageInfo<Tag> getAllTags(Integer page, Integer pageSize) {
        if(page == null){
            page = 1; //设置默认当前页
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//设置默认每页显示的数据条数
        }

        PageInfo<Tag> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            List<Tag> tagList = getAll();
            pageInfo = new PageInfo<Tag>(tagList);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            PageHelper.clearPage();
        }
        return pageInfo;
    }

    @Override
    public List<Tag> fuzzyQuery(String querySequence) {
        if(querySequence == null || querySequence.length() == 0) return null;
        querySequence = querySequence.trim();
        String[] split = querySequence.split("&");
        int n = split.length;
        TagExample tagExample = new TagExample();
        TagExample.Criteria criteria = tagExample.createCriteria();
        if(n >= 1) if(!split[0].equals("")) criteria.andTagNameLike(split[0] + "%");
        if(n >= 2){
            if(!split[1].equals("")){
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
                Date date = null;
                try {
                    date = sdf.parse(split[1]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                criteria.andCreateTimeEqualTo(date == null ? null : date);
            }
        }
        return tagMapper.selectByExample(tagExample);
    }

    @Override
    public PageInfo<Tag> getFuzzyQueryAllTags(Integer page, Integer pageSize, String querySequence) {
        if(page == null){
            page = 1; //设置默认当前页
        }
        if(page <= 0){
            page = 1;
        }
        if(pageSize == null){
            pageSize = 5;//设置默认每页显示的数据条数
        }

        PageInfo<Tag> pageInfo = null;
        try {
            PageHelper.startPage(page, pageSize);
            List<Tag> tagList = fuzzyQuery(querySequence);
            pageInfo = new PageInfo<Tag>(tagList);
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
    public int deleteTag(Integer id) {
        int result = tagMapper.deleteByPrimaryKey(id);
        String key = pre + id;
        if(result != 0){
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey) {
                redisTemplate.delete(key);
            }
        }
        return result;
    }

    public int insertTag(Tag tag){
        tag.setCreateTime(new Date());
        return tagMapper.insert(tag);
    }

    /**
     * 更新策略：在数据库中进行更新，成功则检测是否存在对应key，存在则删除后加入新的，不存在直接加入。
     * @param tag
     * @return
     */
    @Override
    public int updateTag(Tag tag) {
        ValueOperations<String, Tag> operations = redisTemplate.opsForValue();
        int result = tagMapper.updateByPrimaryKey(tag);
        if(result != 0){
            String key = pre + tag.getId();
            boolean hasKey = redisTemplate.hasKey(key);
            if(hasKey){
                redisTemplate.delete(key);
            }
            Tag newTag= getOne(tag.getId());
            if(newTag != null){
                operations.set(key, newTag, 3, TimeUnit.HOURS);
            }
        }
        return result;
    }

    @Override
    public List<String> getAllTagName() {
        List<Tag> all = getAll();
        List<String> tagNames = new ArrayList<>();
        for(Tag tag : all){
            tagNames.add(tag.getTagName());
        }
        return tagNames;
    }
}
