
package sg.edu.nus.iss.product_service.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import sg.edu.nus.iss.product_service.dto.ProductFilterDTO;
import sg.edu.nus.iss.product_service.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import sg.edu.nus.iss.product_service.model.Product;
import sg.edu.nus.iss.product_service.repository.CategoryRepository;
import sg.edu.nus.iss.product_service.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
        import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    public void setup() {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private ProductService productService;

    public ProductServiceTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetFilteredProducts_WithPincode() {
        // Given
        Product product1 = createProduct( "Product1", "12345", BigDecimal.valueOf(10.00), BigDecimal.valueOf(8.00));
        Product product2 = createProduct( "Product2", "12345", BigDecimal.valueOf(20.00), BigDecimal.valueOf(18.00));
        Product product3 = createProduct( "Product3", "54321", BigDecimal.valueOf(30.00), BigDecimal.valueOf(28.00));

        // Ensure that the repository returns the correct products
        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2, product3));
    public void testDeleteProduct() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        when(productRepository.save(product)).thenReturn(product);

        // Set up filter DTO
        ProductFilterDTO filterDTO = new ProductFilterDTO();
        filterDTO.setPincode("12345"); // The pincode you want to filter by
        Product result = productService.deleteProduct(product);

        // When
        List<Product> filteredProducts = productService.getFilteredProducts(filterDTO);

        // Then
        assertEquals(2, filteredProducts.size());
        assertTrue(result.isDeleted());
        assertEquals("merchant", result.getUpdatedBy());
        assertNotNull(result.getUpdatedAt());
    }


    @Test
    public void testGetFilteredProducts_WithCategory() {
        // Given
        UUID categoryId = UUID.randomUUID();
        Category category = new Category();
        category.setCategoryId(categoryId);

        Product product1 = createProduct("Product1", category, BigDecimal.valueOf(10.00), BigDecimal.valueOf(8.00));
        Product product2 = createProduct("Product2", category, BigDecimal.valueOf(20.00), BigDecimal.valueOf(18.00));
    public void testUpdateProduct() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        when(productRepository.save(product)).thenReturn(product);

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2));
        Product result = productService.updateProduct(product);

        ProductFilterDTO filterDTO = new ProductFilterDTO();
        filterDTO.setCategoryId(categoryId);

        // When
        List<Product> filteredProducts = productService.getFilteredProducts(filterDTO);

        // Then
        assertEquals(2, filteredProducts.size());
        assertEquals("merchant", result.getUpdatedBy());
        assertNotNull(result.getUpdatedAt());
    }

    @Test
    public void testGetFilteredProducts_WithPriceRange() {
        // Given
        Product product1 = createProduct("Product1", "12345", BigDecimal.valueOf(10.00), BigDecimal.valueOf(8.00));
        Product product2 = createProduct("Product2", "12345", BigDecimal.valueOf(20.00), BigDecimal.valueOf(18.00));
        Product product3 = createProduct("Product3", "54321", BigDecimal.valueOf(30.00), BigDecimal.valueOf(28.00));

        when(productRepository.findAll()).thenReturn(Arrays.asList(product1, product2, product3));

        ProductFilterDTO filterDTO = new ProductFilterDTO();
        filterDTO.setMinPrice(BigDecimal.valueOf(15.00));
        filterDTO.setMaxPrice(BigDecimal.valueOf(25.00));

        // When
        List<Product> filteredProducts = productService.getFilteredProducts(filterDTO);

        // Then
        assertEquals(1, filteredProducts.size()); // Only product2 should match
    }

    private Product createProduct( String name, String pincode, BigDecimal originalPrice, BigDecimal listingPrice) {
    public void testAddProduct() {
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        product.setProductName(name);
        product.setAvailableStock(10);
        product.setPincode(pincode);
        product.setOriginalPrice(originalPrice);
        product.setListingPrice(listingPrice);
        product.setMerchantId(UUID.randomUUID()); // Set a random merchant ID
        // Set other attributes as needed
        return product;
        when(productRepository.save(product)).thenReturn(product);

        Product result = productService.addProduct(product);

        assertEquals("merchant", result.getCreatedBy());
        assertNotNull(result.getCreatedAt());
    }


    private Product createProduct(String name, Category category, BigDecimal originalPrice, BigDecimal listingPrice) {
    @Test
    public void testGetAllProducts() {
        UUID merchantId = UUID.randomUUID();
        PageRequest pageable = PageRequest.of(0, 10);
        Product product = new Product();
        product.setProductName(name);
        product.setAvailableStock(10);
        product.setOriginalPrice(originalPrice);
        product.setListingPrice(listingPrice);
        product.setCategory(category);
        return product;
        product.setProductId(UUID.randomUUID());
        Page<Product> page = new PageImpl<>(Collections.singletonList(product));
        when(productRepository.findByMerchantIdAndDeletedFalse(merchantId, pageable)).thenReturn(page);

        Page<Product> result = productService.getAllProducts(merchantId, pageable);

        assertEquals(1, result.getTotalElements());
        assertEquals(product, result.getContent().get(0));
    }

    @Test
    public void testGetProductsByMerchantId() {
        UUID merchantId = UUID.randomUUID();
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        when(productRepository.findByMerchantIdAndDeletedFalse(merchantId)).thenReturn(Collections.singletonList(product));

        List<Product> result = productService.getProductsByMerchantId(merchantId);

        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
    }

    @Test
    public void testGetProductsByMerchantIdAndCategoryId() {
        UUID merchantId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();
        Product product = new Product();
        product.setProductId(UUID.randomUUID());
        when(productRepository.findByMerchantIdAndCategory_CategoryIdAndDeletedFalse(merchantId, categoryId)).thenReturn(Collections.singletonList(product));

        List<Product> result = productService.getProductsByMerchantIdAndCategoryId(merchantId, categoryId);

        assertEquals(1, result.size());
        assertEquals(product, result.get(0));
    }

    @Test
    public void testGetProductByIdAndMerchantId() {
        UUID merchantId = UUID.randomUUID();
        UUID productId = UUID.randomUUID();
        Product product = new Product();
        product.setProductId(productId);
        when(productRepository.findByMerchantIdAndProductIdAndDeletedFalse(merchantId, productId)).thenReturn(product);

        Product result = productService.getProductByIdAndMerchantId(merchantId, productId);

        assertEquals(product, result);
    }
}
