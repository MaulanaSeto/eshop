package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class ProductRepositoryTest {

    @InjectMocks
    ProductRepository productRepository;
    @BeforeEach
    void setUp() {
    }
    @Test
    void testCreateAndFind() {
        Product product = new Product();
        product.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductId(), savedProduct.getProductId());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }
    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }
    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductId("a0f9de47-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductId(), savedProduct.getProductId());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductId(), savedProduct.getProductId());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEditProduct() {
        // Added logs to make sure that this code is doing exactly what I'm thinking of it doing
        System.out.println("Starting testEditProduct...");

        Product originalProduct = new Product();
        originalProduct.setProductId("7a1c18b7-e61f-4723-b535-8bfa88c9201d");
        originalProduct.setProductName("Horikita");
        originalProduct.setProductQuantity(10);
        productRepository.create(originalProduct);
        System.out.println("Original product created: " + originalProduct.getProductName() + ", quantity: " + originalProduct.getProductQuantity());

        String updatedName = "Ichinose";
        int updatedQuantity = 5;
        System.out.println("Updating product with name: " + updatedName + ", quantity: " + updatedQuantity);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(originalProduct.getProductId());
        updatedProduct.setProductName(updatedName);
        updatedProduct.setProductQuantity(updatedQuantity);
        productRepository.edit(updatedProduct);
        System.out.println("Product updated: " + updatedProduct.getProductName() + ", quantity: " + updatedQuantity);

        System.out.println("End of testEditProduct.");
    }

    @Test
    void testEditProductToNegativeQuantity() {
        // Added logs to make sure that this code is doing exactly what I'm thinking of it doing
        System.out.println("Starting testEditProductToNegativeQuantity...");

        Product originalProduct = new Product();
        originalProduct.setProductId("7a1c18b7-e61f-4723-b535-8bfa88c9201d");
        originalProduct.setProductName("Ayanokoji");
        originalProduct.setProductQuantity(10);
        productRepository.create(originalProduct);
        System.out.println("Original product created: " + originalProduct.getProductName() + ", quantity: " + originalProduct.getProductQuantity());

        String updatedName = "Hirata";
        int updatedQuantity = -1;
        System.out.println("Updating product with name: " + updatedName + ", quantity: " + updatedQuantity);

        Product updatedProduct = new Product();
        updatedProduct.setProductId(originalProduct.getProductId());
        updatedProduct.setProductName(updatedName);
        updatedProduct.setProductQuantity(updatedQuantity);

        try {
            productRepository.edit(updatedProduct);
            fail("Expected IllegalArgumentException was not thrown.");
        } catch (IllegalArgumentException e) {
            System.out.println("Caught expected exception: " + e.getMessage());
            assertTrue(e.getMessage().contains("Negative quantities are not allowed."));
        }
    }

    @Test
    void testDeleteProduct() {
        // Added logs to make sure that this code is doing exactly what I'm thinking of it doing
        System.out.println("Starting testDeleteProduct...");

        Product productToDelete = new Product();
        productToDelete.setProductId("fb2871a9-40c6-4e4d-a4c2-102f9d8b3d39");
        productToDelete.setProductName("Cannibal Corpse");
        productToDelete.setProductQuantity(100);
        productRepository.create(productToDelete);
        System.out.println("Product to delete: " + productToDelete.getProductName());

        System.out.println("Deleting product: " + productToDelete.getProductName());
        boolean deletionResult = productRepository.delete(productToDelete);
        System.out.println("Deletion result: " + deletionResult);

        assertTrue(deletionResult);
        System.out.println("End of testDeleteProduct.");
    }

    @Test
    void testDeleteNonExistentProduct() {
        // Added logs to make sure that this code is doing exactly what I'm thinking of it doing
        System.out.println("Starting testDeleteNonExistentProduct...");

        Product nonExistentProduct = new Product();
        nonExistentProduct.setProductId("non-existent-id");
        nonExistentProduct.setProductName("Non-Existent Product");
        nonExistentProduct.setProductQuantity(0);
        System.out.println("Product to delete (non-existent): " + nonExistentProduct.getProductName());

        System.out.println("Deleting product (non-existent): " + nonExistentProduct.getProductId());
        boolean deletionResult = productRepository.delete(nonExistentProduct);
        System.out.println("Deletion result (non-existent): " + deletionResult);

        assertFalse(deletionResult);
        System.out.println("End of testDeleteNonExistentProduct.");
    }

}