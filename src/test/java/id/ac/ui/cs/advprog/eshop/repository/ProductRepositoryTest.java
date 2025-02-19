package id.ac.ui.cs.advprog.eshop.repository;

import id.ac.ui.cs.advprog.eshop.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

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
        product.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Sampo Cap Bambang");
        product.setProductQuantity(100);
        productRepository.create(product);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product.getProductID(), savedProduct.getProductID());
        assertEquals(product.getProductName(), savedProduct.getProductName());
        assertEquals(product.getProductQuantity(), savedProduct.getProductQuantity());
    }

    @Test
    void testCreateWithNullProductID() {
        Product product = new Product();
        product.setProductName("Unnamed Product");
        product.setProductQuantity(5);
        Product createdProduct = productRepository.create(product);

        assertNotNull(createdProduct.getProductID());
        assertEquals("Unnamed Product", createdProduct.getProductName());
        assertEquals(5, createdProduct.getProductQuantity());
    }

    @Test
    void testEditWithNullProductID() {
        Product product = new Product();
        product.setProductName("Invalid Product");
        product.setProductQuantity(10);
        assertThrows(NullPointerException.class, () -> productRepository.edit(product));
    }

    @Test
    void testFindAllIfEmpty() {
        Iterator<Product> productIterator = productRepository.findAll();
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testFindAllIfMoreThanOneProduct() {
        Product product1 = new Product();
        product1.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(100);
        productRepository.create(product1);

        Product product2 = new Product();
        product2.setProductID("a0f9de46-90b1-437d-a0bf-d0821dde9096");
        product2.setProductName("Sampo Cap Usep");
        product2.setProductQuantity(50);
        productRepository.create(product2);

        Iterator<Product> productIterator = productRepository.findAll();
        assertTrue(productIterator.hasNext());
        Product savedProduct = productIterator.next();
        assertEquals(product1.getProductID(), savedProduct.getProductID());
        savedProduct = productIterator.next();
        assertEquals(product2.getProductID(), savedProduct.getProductID());
        assertFalse(productIterator.hasNext());
    }

    @Test
    void testEdit() {
        Product product = new Product();
        product.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("Original Name");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product updatedProduct = new Product();
        updatedProduct.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        updatedProduct.setProductName("Updated Name");
        updatedProduct.setProductQuantity(20);
        productRepository.edit(updatedProduct);

        Product retrieved = productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNotNull(retrieved);
        assertEquals("Updated Name", retrieved.getProductName());
        assertEquals(20, retrieved.getProductQuantity());
    }

    @Test
    void testDelete() {
        Product product = new Product();
        product.setProductID("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product.setProductName("To Be Deleted");
        product.setProductQuantity(5);
        productRepository.create(product);

        productRepository.delete("eb558e9f-1c39-460e-8860-71af6af63bd6");
        assertNull(productRepository.findById("eb558e9f-1c39-460e-8860-71af6af63bd6"));
    }

    @Test
    void testFindById() {
        Product product = new Product();
        product.setProductID("abc");
        product.setProductName("Find Me");
        product.setProductQuantity(15);
        productRepository.create(product);

        Product retrieved = productRepository.findById("abc");
        assertNotNull(retrieved);
        assertEquals("Find Me", retrieved.getProductName());
        assertEquals(15, retrieved.getProductQuantity());
    }

    @Test
    void testFindByIdNonExistent() {
        assertNull(productRepository.findById("non-existent"));
    }

    @Test
    void testDeleteNonExistent() {
        Product product = new Product();
        product.setProductID("1");
        product.setProductName("Existing Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        productRepository.delete("non-existent");
        assertNotNull(productRepository.findById("1"));
    }

    @Test
    void testEditNonExistent() {
        Product product = new Product();
        product.setProductID("1");
        product.setProductName("Original Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        Product nonExistent = new Product();
        nonExistent.setProductID("non-existent");
        nonExistent.setProductName("Updated Name");
        nonExistent.setProductQuantity(20);
        productRepository.edit(nonExistent);

        Product retrieved = productRepository.findById("1");
        assertEquals("Original Product", retrieved.getProductName());
        assertEquals(10, retrieved.getProductQuantity());
    }

    @Test
    void testCreateNullProduct() {
        assertThrows(NullPointerException.class, () -> productRepository.create(null));
    }

    @Test
    void testEditWithNullProduct() {
        assertThrows(NullPointerException.class, () -> productRepository.edit(null));
    }

    @Test
    void testDeleteWithNullId() {
        Product product = new Product();
        product.setProductID("1");
        product.setProductName("Existing Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        productRepository.delete(null);
        assertNotNull(productRepository.findById("1"));
    }

    @Test
    void testFindByIdWithNullId() {
        Product product = new Product();
        product.setProductID("1");
        product.setProductName("Existing Product");
        product.setProductQuantity(10);
        productRepository.create(product);

        assertNull(productRepository.findById(null));
    }
}