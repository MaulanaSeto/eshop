package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import java.util.List;

public interface ProductService {
    Product create(Product product);
    void edit(Product product);
    void delete(String productID);
    List<Product> findAll();
    Product findById(String id);
}