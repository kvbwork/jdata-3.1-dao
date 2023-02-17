package ru.netology.daoexample.dao;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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

    @PersistenceContext
    private EntityManager entityManager;

    public OrdersDao() {
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
        return (List<String>) entityManager.createNativeQuery(productNameByCustomerSql)
                .setParameter("name", name)
                .getResultList();
    }
}
