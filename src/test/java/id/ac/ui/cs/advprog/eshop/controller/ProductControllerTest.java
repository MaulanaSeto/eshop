package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.service.ProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {
    private MockMvc mockMvc;

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    private Product product;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
        product = new Product();
        product.setProductId("1abc");
        product.setProductName("Test Product");
        product.setProductQuantity(69);
    }

    @Test
    void testCreateProductPage() throws Exception {
        mockMvc.perform(get("/product/create"))
            .andExpect(status().isOk())
            .andExpect(view().name("CreateProduct"))
            .andExpect(model().attributeExists("product"));
    }

    @Test
    void testCreateProductPost() throws Exception {
        doReturn(product).when(productService).create(any(Product.class));
        mockMvc.perform(post("/product/create")
                .flashAttr("product", product))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("list"));
    }

    @Test
    void testEditProductPage() throws Exception {
        when(productService.findById("1abc")).thenReturn(product);
        mockMvc.perform(get("/product/edit/1abc"))
            .andExpect(status().isOk())
            .andExpect(view().name("EditProduct"))
            .andExpect(model().attributeExists("product"));
    }

    @Test
    void testEditProductPageNotFound() throws Exception {
        when(productService.findById("1abc")).thenReturn(null);
        mockMvc.perform(get("/product/edit/1abc"))
            .andExpect(status().isOk())
            .andExpect(view().name("error"))
            .andExpect(model().attributeExists("error"));
    }

    @Test
    void testEditProduct() throws Exception {
        doNothing().when(productService).edit(any(Product.class));
        mockMvc.perform(put("/product/edit")
                .contentType("application/json")
                .content("{\"productID\":\"1abc\",\"productName\":\"Updated Product\",\"productQuantity\":69}"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/products"));
    }

    @Test
    void testProductListPage() throws Exception {
        List<Product> products = Collections.singletonList(product);
        when(productService.findAll()).thenReturn(products);
        mockMvc.perform(get("/product/list"))
            .andExpect(status().isOk())
            .andExpect(view().name("ProductList"))
            .andExpect(model().attributeExists("products"));
    }

    @Test
    void testDeleteProduct() throws Exception {
        doNothing().when(productService).delete("1abc");
        mockMvc.perform(delete("/product/delete/1abc"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("list"));
    }
}