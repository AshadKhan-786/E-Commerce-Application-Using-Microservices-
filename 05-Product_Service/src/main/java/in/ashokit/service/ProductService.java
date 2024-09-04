package in.ashokit.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.ashokit.dto.ProductDto;

public interface ProductService {

	public ProductDto addProduct(ProductDto productDto, MultipartFile file);

	public ProductDto updateProduct(Integer pId, ProductDto productDto, MultipartFile file);

	public List<ProductDto> getAllProducts();

	public ProductDto getProductById(Integer pId);

	public ProductDto deleteProductById(Integer pId);

	public boolean updateStock(Integer productId, Integer quantity);
}
