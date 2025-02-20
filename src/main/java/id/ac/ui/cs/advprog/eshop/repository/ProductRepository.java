package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Repository
public class ProductRepository {
    private final List<Product> productData = new ArrayList<>();
    private long idCounter = 0;

    public Product create(Product product) {
        if (product == null) throw new NullPointerException("Product cannot be null");

        if (product.getProductId() == null) product.setProductId(String.valueOf(idCounter++));

        productData.add(product);
        return product;
    }

    public void edit(Product product) {
        if (product == null) throw new NullPointerException("Product cannot be null");

        if (product.getProductId() == null) throw new NullPointerException("Product ID cannot be null");

        for (int i = 0; i < productData.size(); i++) {
            if (productData.get(i).getProductId().equals(product.getProductId())) {
                productData.set(i, product);
                break;
            }
        }
    }

    public void delete(String productID) {
        productData.removeIf(product -> product.getProductId().equals(productID));
    }

    public Iterator<Product> findAll() {
        return productData.iterator();
    }

    public Product findById(String id) {
        for (Product product : productData) {
            if (product.getProductId().equals(id)) {
                return product;
            }
        }
        return null;
    }
}