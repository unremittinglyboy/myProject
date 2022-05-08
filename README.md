  个人自制的小说预览网站的后台管理系统，用于对前台展示的小说数据、登录前台的用户数据进行统一监控和管理，有利于改善人工搜寻、整理数据的繁杂性。
  
  

项目功能模块介绍：

  1、总体系统监控：对于总体数据库的一系列数据变化的监控，拥有图表显示数据状况。
  2、前台登录用户管理、管理员管理、作家管理：对于前台登录的用户的信息、登录本系统的用户的信息、小说作家信息的
CRUD。

3、小说及其属性的管理：使用 redis 缓存来实现对小说属性的常规增、删、改，使用 elasticsearch 来实现小说的过滤器查找、范围查找，用消息中间件来保证数据的同步。

4、系统用户权限控制：对登录该系统的用户存在登录信息检验。

5、全局数据格式验证：对待提交数据的合法性验证。


项目后续改进：加入多级评论功能，设计前台展示页面来对接已实现的后台数据管理接口。

收获：对于实际开发流程更加清晰，对于一些开发细节掌握的更加全面。



所用技术栈：

Springboot	Java常用的开发框架

Mybatis	数据访问层框架

Ehcache	简易缓存

Redis	作为小说删改查的二级缓存

Shiro	安全框架

Elasticsearch	小说核心搜索引擎；搜索框架

Rabbitmq	保证数据库与elasticsearch 数据的一致性（数据同步）

Devtool	伪热部署

Druid	阿里提供的数据源

pagehelper	Mybatis分页辅助插件

Mybatis generator	Mybatis简易sql语句与Mapping自动生成插件

Thymeleaf	服务器端Java模板引擎

fastjson	用于解决es或者springboot中出现的json字符串转换问题

Jquery	用于渲染前端模板的通用框架

Semantic ui	已有一系列模板的前端框架

u-charts	前端图表插件



页面展示：

![image](https://user-images.githubusercontent.com/78966244/167295860-5ed2de4f-db0d-464e-b5d5-e881803cf1e4.png)

![image](https://user-images.githubusercontent.com/78966244/167295865-7c934c4e-06a2-45b0-b751-f8c858868312.png)

![image](https://user-images.githubusercontent.com/78966244/167295870-0ae8c406-fe4e-44e3-8e73-c9dca9bc29e4.png)

![image](https://user-images.githubusercontent.com/78966244/167295881-bc624ae0-8357-42d5-9ef7-eec4201e4544.png)

![image](https://user-images.githubusercontent.com/78966244/167295891-2fa51469-f2c7-4d19-bc10-3c80124d9e6d.png)

![image](https://user-images.githubusercontent.com/78966244/167295894-6576a0ee-f2f7-4722-880b-ce12aa8145ab.png)

![image](https://user-images.githubusercontent.com/78966244/167295898-074fff2a-02de-4797-a266-54ea6d09ec0f.png)

![image](https://user-images.githubusercontent.com/78966244/167295902-19ea86da-9def-413e-b30c-4bd45f4824d3.png)



**特别提示**：

	页面默认进入用户名为admin，密码为200013，密码采用MD5加密，各类技术实现策略在业务逻辑代码中有。
