package ru.netology.daoexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.netology.daoexample.dao.OrdersDao;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductsController {

    private final OrdersDao ordersDao;

    public ProductsController(OrdersDao ordersDao) {
        this.ordersDao = ordersDao;
    }

    @GetMapping(path = "/fetch-product")
    public List<String> fetchProduct(@RequestParam String name) {
        return ordersDao.getProductName(name);
    }

}
