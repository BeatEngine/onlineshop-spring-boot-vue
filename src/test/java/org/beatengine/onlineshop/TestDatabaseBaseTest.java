package org.beatengine.onlineshop;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.convention.TestBean;

import javax.sql.DataSource;

/**
 * ROOT BaseTest defines the test-database as source must be extended from all integration-tests!
 */
@SpringBootTest
public class TestDatabaseBaseTest {

    public static String TEST_DATABASE_NAME = "test_springboot_onlineshop";

    @TestBean
    private DataSource dataSource;

    static DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl("jdbc:postgresql://localhost/"+TEST_DATABASE_NAME);
        dataSource.setUsername( "postgres" );
        dataSource.setPassword( "123456" );
        return dataSource;
    }

}
