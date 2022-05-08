package cn.lsr.noveladmin;


import cn.lsr.noveladmin.Service.AuthorService;
import cn.lsr.noveladmin.Service.TypeService;
import cn.lsr.noveladmin.model.Author;
import cn.lsr.noveladmin.model.Type;
import com.github.pagehelper.PageInfo;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;


import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class NovelAdminApplicationTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private AuthorService authorService;

	@Autowired
	private TypeService typeService;

	@Autowired
	private RedisTemplate redisTemplate;

	@Autowired
	private RestHighLevelClient client;

	@Test
	public void contextLoads(){
		PageInfo<Author> allAuthors = authorService.getAllAuthors(1, 5);
		List<Author> list = allAuthors.getList();
		System.out.println(list.size());
	}

	@Test
	public void testConnection() throws SQLException {
		Connection connection = dataSource.getConnection();
		System.out.println(connection);
	}

	@Test
	public void testRedis(){
		redisTemplate.delete("fff");
	}

	@Test
	public void getAllTest(){
		List<Type> all = typeService.getAll();
		System.out.println(Arrays.toString(all.toArray()));
	}

	@Test
	public void keySe(){
		String s = DigestUtils.md5DigestAsHex("200013".getBytes());
		System.out.println(s);
	}

	@Test
	public void testESConnection() throws IOException {
		GetIndexRequest g = new GetIndexRequest("book");
		boolean exists = client.indices().exists(g, RequestOptions.DEFAULT);
		System.out.println(exists);
	}
}
