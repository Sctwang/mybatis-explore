package top.jonas.mybatis.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.jonas.mybatis.entity.User;
import top.jonas.mybatis.mapper.UserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;

public class XMLConfigBuilderTest {

    private static final Logger logger = LoggerFactory.getLogger(XMLConfigBuilderTest.class);

    public static void main(String[] args) throws IOException {
        String resource = "config/mybatis-config.xml";
        new XMLConfigBuilderTest().getResource(resource);
    }

    /**
     * get resource from parameter
     * @param resource
     */
    private void getResource(String resource) {
        InputStream inputStream;
        SqlSession sqlSession = null;
        try {
            inputStream = Resources.getResourceAsStream(resource);
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            sqlSession = sqlSessionFactory.openSession();
            UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
            List<User> userList = userMapper.queryAll(new User());
            userList.forEach(
                user -> {
                    logger.info("user's info:{}", user.toString());
                }
            );
        } catch (IOException e) {
            logger.debug("Can't get resource as stream:{}", e.toString());
            e.printStackTrace();
        } finally {
            Objects.requireNonNull(sqlSession).close();
        }
    }
}