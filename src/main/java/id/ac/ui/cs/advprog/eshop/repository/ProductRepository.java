package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private List<Product> productData = new ArrayList<>();
    private long idCounter = 0;

    public Product create(Product product) {
        product.setProductID(String.valueOf(idCounter++));
        productData.add(product);
        return product;
    }

    public void delete(String productID) {
        productData.removeIf(product -> product.getProductID().equals(productID));
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }
}
