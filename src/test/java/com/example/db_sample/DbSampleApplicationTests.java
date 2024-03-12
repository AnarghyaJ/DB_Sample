package com.example.db_sample;

import com.example.db_sample.model.User;
import org.apache.commons.dbutils.*;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

@SpringBootTest
class DbSampleApplicationTests {
    private static final Logger logger = LoggerFactory.getLogger(DbSampleApplicationTests.class);
    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() {
    }

    @Test
    void testIncorrectMapping() {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        ResultSetHandler<List<User>> h = new BeanListHandler<User>(User.class);
        try {
            String sql = "SELECT * FROM USERS where 1=?";
            List<User> result = queryRunner.query(sql, h, "1");
            result.forEach(user -> {
                        Assertions.assertNull(user.getFirstName());
                        Assertions.assertNull(user.getLastName());
                    }
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testCorrectMappingUsingGenerousBeanProcessor() {
        QueryRunner queryRunner = new QueryRunner(dataSource);
        try {
            String sql = "SELECT * FROM USERS where 1=?";
            List<User> result = queryRunner.query(sql, new BeanListHandler<>(User.class,
                    new BasicRowProcessor(new GenerousBeanProcessor())), "1");

            result.forEach(user -> {
                        Assertions.assertNotNull(user.getFirstName());
                        Assertions.assertNotNull(user.getLastName());
                    }
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void pmdBrokenTest() {
        QueryRunner queryRunner = new QueryRunner(dataSource, true);
        try {
            String sql = " /* mySampleComment */  SELECT * FROM USERS where 1 = ?";
            List<User> result = queryRunner.query(sql, new BeanListHandler<>(User.class,
                    new BasicRowProcessor(new GenerousBeanProcessor())), "1");

            result.forEach(user -> {
                        Assertions.assertNotNull(user.getFirstName());
                        Assertions.assertNotNull(user.getLastName());
                    }
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    void improvedQueryRunner() {
        improvements.QueryRunner runner = new improvements.QueryRunner()
                .withDataSource(dataSource)
                .usingStatementConfig(new StatementConfiguration.Builder().fetchDirection(ResultSet.FETCH_FORWARD).build())
                .withDriverProperties(new java.util.Properties())
                .validate();
    }

    @Test
    void asyncQueryRunner() {
        AsyncQueryRunner queryRunner = new AsyncQueryRunner(dataSource, false, Executors.newSingleThreadExecutor());
        try {
            String sql = " /* mySampleComment */  SELECT * FROM USERS where 1 = ?";
            Future<List<User>> result = queryRunner.query(sql, new BeanListHandler<>(User.class,
                    new BasicRowProcessor(new GenerousBeanProcessor())), "1");

            result.get().forEach(user -> {
                        logger.info("User: {}", user.getFirstName() + " " + user.getLastName());
                        Assertions.assertNotNull(user.getFirstName());
                        Assertions.assertNotNull(user.getLastName());
                    }
            );
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
