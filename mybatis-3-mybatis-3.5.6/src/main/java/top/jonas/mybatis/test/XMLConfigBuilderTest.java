package top.jonas.mybatis.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jonas.mybatis.entity.User;
import top.jonas.mybatis.mapper.UserMapper;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class XMLConfigBuilderTest {

    private static final Logger logger = LoggerFactory.getLogger(XMLConfigBuilderTest.class);

    public static void main(String[] args) throws IOException {
        String resource = "config/mybatis-config.xml";
        //new XMLConfigBuilderTest().getUsers1(resource);
        //new XMLConfigBuilderTest().getUsers2(resource);
        new XMLConfigBuilderTest().getUserWithCache(resource);
    }

    /**
     * get resource from parameter
     *
     * @param resource
     *
     * @see org.apache.ibatis.cache.CacheKey
     *
     * MyBatis 缓存见如下接口
     * @see org.apache.ibatis.cache.Cache
     * @see org.apache.ibatis.cache.impl.PerpetualCache
     * @see org.apache.ibatis.cache.decorators.SynchronizedCache
     */
    private void getUsers1(String resource) {
        InputStream inputStream;
        SqlSession sqlSession = null;
        try {
            // 1.获取配置文件资源
            inputStream = Resources.getResourceAsStream(resource);
            // 2.初始化mybatis，创建SqlSessionFactory类实例
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            // 3.创建session（默认是开启一级缓存的）
            /*
            TODO 一级缓存和二级缓存的区别
            MyBatis 一级缓存和二级缓存的区别
            1.一级缓存默认开启，一级缓存的作用域为一个sqlSession；
            执行相同的sql，第一次去库里查，会写入到缓存，第二次直接从缓存获取；
            此版本的缓存放置在 HashMap 中，hashmap非线程安全，具体缓存实现类还有SynchronizedCache;
            每创建一个 sqlSession，就表示打开一次数据库会话
             */
            sqlSession = sqlSessionFactory.openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            // test add a user
            userMapper.insert(addUser());
            List<User> userList = userMapper.queryAll(new User());
            userList.forEach(
                user -> {
                    logger.debug("user's info:{}", user.toString());
                }
            );
        } catch (IOException e) {
            logger.debug("Can't get resource as stream:{}", e.toString());
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(sqlSession).close();
        }
    }


    private void getUsers2(String resource) {
        SqlSessionFactory sqlSessionFactory;
        SqlSession sqlSession = null;
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            String nameSpace = "top.jonas.mybatis.mapper.UserMapper" + ".";
            logger.debug("query all users");
            List<User> userList = sqlSession.selectList(nameSpace + "queryAll");
            userList.forEach(user -> {
                logger.debug("user's info:{}", user.toString());
            });
            logger.debug("---------------------");
            int id = 1;
            logger.debug("query user by id: " + id);
            User user = sqlSession.selectOne(nameSpace + "queryById", id);
            logger.debug("user: " + user);
            logger.debug("---------------------");
        } catch (IOException e) {
            logger.debug("Can't get resource as stream:{}", e.toString());
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(sqlSession).close();
        }
    }

    private void getUserWithCache(String resource) {
        SqlSessionFactory sqlSessionFactory;
        SqlSession sqlSession = null;
        try {
            InputStream inputStream = Resources.getResourceAsStream(resource);
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            String nameSpace = "top.jonas.mybatis.mapper.UserMapper" + ".";
            List<User> userList = sqlSession.selectList(nameSpace + "queryAll");
            userList.forEach(user -> {
                logger.debug("get user's info:{}", user.toString());
            });
            System.out.println();
            System.out.println();
            System.out.println();
            // 在使用缓存之前，对数据库做修改（此处做插入操作），缓存失效，会重新执行sql语句，查询数据库
            //sqlSession.getMapper(UserMapper.class).insert(addUser());
            System.out.println();
            System.out.println();
            System.out.println();
            List<User> userListWithCache = sqlSession.selectList(nameSpace + "queryAll");
            userList.forEach(user -> {
                logger.debug("get user's info with cache:{}", userListWithCache.toString());
            });
        } catch (IOException e) {
            logger.debug("Can't get resource as stream:{}", e.toString());
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }
    }

    private User addUser() {
        User user = new User();
        user.setUsername("testUsername");
        user.setPassword("testUsername-password");
        user.setDeptment("testUsername-deptment");
        user.setEmail("testUsername-email");
        user.setPhone("13188888888");
        user.setStatus(1);
        user.setRemark("testUsername-remark");
        user.setCreateDate(new Date());
        return user;
    }
}
