package com.shipping.repository;

import com.shipping.config.MyBatisConfig;
import com.shipping.config.TestDataSource;
import com.shipping.domain.Address;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {TestDataSource.class, MyBatisConfig.class})
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD,
        scripts = {"classpath:drop.sql", "classpath:schema.sql", "classpath:data.sql"})
public class AddressMapperTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public AddressMapper sut;

    @Test
    public void testInsert() throws Exception {
        assertThat(jdbcTemplate.queryForList("SELECT * FROM ADDR").size(), is(1));
        final Address address = new Address(1L, "123-4567", "testLocation", "testName");
        sut.insertAddress(address);
        assertThat(jdbcTemplate.queryForList("SELECT * FROM ADDR").size(), is(2));
    }
}
