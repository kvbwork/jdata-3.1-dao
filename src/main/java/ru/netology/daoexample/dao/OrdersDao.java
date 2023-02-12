package ru.netology.daoexample.dao;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class OrdersDao {
    private static final String PRODUCT_NAME_BY_CUSTOMER_SQL_FILE_NAME = "productNameByCustomer.sql";
    private final String productNameByCustomerSql;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public OrdersDao(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
        productNameByCustomerSql = readScript(PRODUCT_NAME_BY_CUSTOMER_SQL_FILE_NAME);
    }

    private static String readScript(String scriptFileName) {
        try (InputStream is = new ClassPathResource(scriptFileName).getInputStream();
             BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(is))) {
            return bufferedReader.lines().collect(Collectors.joining("\n"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getProductName(String name) {
        SqlParameterSource sqlParams = new MapSqlParameterSource("name", name);
        return namedParameterJdbcTemplate.queryForList(productNameByCustomerSql, sqlParams, String.class);
    }
}
