package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;

    @BeforeEach
    void setUp() {
        product = new Product();
        product.setProductId("1");
        product.setProductName("Test Product");
        product.setProductQuantity(69);
    }

    @Test
    void testCreate() {
        when(productRepository.create(product)).thenReturn(product);
        Product createdProduct = productService.create(product);
        assertEquals(product, createdProduct);
        verify(productRepository, times(1)).create(product);
    }

    @Test
    void testEdit() {
        doNothing().when(productRepository).edit(product);
        productService.edit(product);
        verify(productRepository, times(1)).edit(product);
    }

    @Test
    void testFindAll() {
        List<Product> productList = Collections.singletonList(product);
        Iterator<Product> iterator = productList.iterator();
        when(productRepository.findAll()).thenReturn(iterator);
        List<Product> result = productService.findAll();
        assertEquals(1, result.size());
        assertEquals(product, result.getFirst());
        verify(productRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productRepository.findById("1")).thenReturn(product);
        Product foundProduct = productService.findById("1");
        assertEquals(product, foundProduct);
        verify(productRepository, times(1)).findById("1");
    }

    @Test
    void testDelete() {
        doNothing().when(productRepository).delete("1");
        productService.delete("1");
        verify(productRepository, times(1)).delete("1");
    }
}