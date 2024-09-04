package in.ashokit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.ashokit.constant.AppConstants;
import in.ashokit.dto.ProductDto;
import in.ashokit.entity.Category;
import in.ashokit.entity.Products;
import in.ashokit.exception.ProductServiceException;
import in.ashokit.mapper.CategoryMapper;
import in.ashokit.mapper.ProductMapper;
import in.ashokit.repo.CategoryRepo;
import in.ashokit.repo.ProductsRepo;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductsRepo productRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public ProductDto addProduct(ProductDto productDto, MultipartFile file) {
		// Handle file processing if needed

		Category category = categoryRepo.getById(productDto.getCategoryId());
		Products product = ProductMapper.convertToEntity(productDto);
		product.setCategory(category);
		Products save = productRepo.save(product);
		return ProductMapper.convertToDto(save);
	}

	@Override
	public ProductDto updateProduct(Integer pid, ProductDto productDto, MultipartFile file) {
		// Handle file processing if needed
		Products existingProduct = productRepo.findById(pid)
				.orElseThrow(() -> new ProductServiceException(AppConstants.PRODUCT_NOT_FOUND_ERR_CD,
						AppConstants.PRODUCT_NOT_FOUND));
		existingProduct.setProductName(productDto.getProductName());
		existingProduct.setDescription(productDto.getDescription());
		existingProduct.setImage(productDto.getImage());
		existingProduct.setPrice(productDto.getPrice());
		existingProduct.setDiscount(productDto.getDiscount());
		existingProduct.setPriceBeforeDiscount(productDto.getPriceBeforeDiscount());
		existingProduct.setStock(productDto.getStock());

		// Assuming you have a method to convert CategoryDto to Category entity
		existingProduct.setCategory(CategoryMapper.convertToEntity(productDto.getCategory()));

		Products updatedProduct = productRepo.save(existingProduct);

		return ProductMapper.convertToDto(updatedProduct);
	}

	@Override
	public List<ProductDto> getAllProducts() {
		List<Products> products = productRepo.findAll();

		return products.stream().map(ProductMapper::convertToDto).collect(Collectors.toList());
	}

	@Override
	public ProductDto getProductById(Integer pId) {

		Products product = productRepo.findById(pId)
				.orElseThrow(() -> new ProductServiceException(AppConstants.PRODUCT_NOT_FOUND_ERR_CD,
						AppConstants.PRODUCT_NOT_FOUND));

		return ProductMapper.convertToDto(product);
	}

	@Override
	public ProductDto deleteProductById(Integer pId) {
		Products product = productRepo.findById(pId)
				.orElseThrow(() -> new ProductServiceException(AppConstants.PRODUCT_NOT_FOUND_ERR_CD,
						AppConstants.PRODUCT_NOT_FOUND));
		productRepo.delete(product);

		return ProductMapper.convertToDto(product);
	}

	@Override
	public boolean updateStock(Integer productId, Integer quantity) {
		Products product = productRepo.findById(productId)
				.orElseThrow(() -> new ProductServiceException(AppConstants.PRODUCT_NOT_FOUND_ERR_CD,
						AppConstants.PRODUCT_NOT_FOUND));
		product.setStock(quantity);
		Products save = productRepo.save(product);
		return save != null ? true : false;
	}

}
