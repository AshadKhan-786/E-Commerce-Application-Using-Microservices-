package in.ashokit.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.ashokit.dto.ProductDto;
import in.ashokit.props.AppProps;
import in.ashokit.response.ApiResponse;
import in.ashokit.service.ProductService;

@RestController
@RequestMapping("/product")
public class ProductRestController {

	@Autowired
	private ProductService productService;

	@Autowired
	private AppProps props;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse<ProductDto>> addCategory(@RequestParam("product") String productJson,
			@RequestParam("file") MultipartFile file) throws Exception {

		ObjectMapper mapper = new ObjectMapper();
		ProductDto productDto = mapper.readValue(productJson, ProductDto.class);

		ApiResponse<ProductDto> response = new ApiResponse<>();

		Map<String, String> messages = props.getMessage();

		ProductDto product = productService.addProduct(productDto, file);

		if (product != null) {
			response.setStatusCode("201");
			response.setMessage(messages.get("productAdded"));
			response.setData(product);
		} else {
			response.setStatusCode("500");
			response.setMessage(messages.get("productAddedErr"));
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> updateProduct(@PathVariable("id") Integer cId,
			@RequestParam("product") String productJson, @RequestParam("file") MultipartFile file) throws Exception {

		ApiResponse<ProductDto> response = new ApiResponse<>();
		Map<String, String> messages = props.getMessage();

		ObjectMapper mapper = new ObjectMapper();
		ProductDto productDto = mapper.readValue(productJson, ProductDto.class);

		ProductDto updateProduct = productService.updateProduct(cId, productDto, file);

		if (updateProduct != null) {
			response.setStatusCode("200");
			response.setMessage(messages.get("productUpdate"));
			response.setData(updateProduct);
		} else {
			response.setStatusCode("500");
			response.setMessage(messages.get("productUpdateErr"));
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	@GetMapping("/products")
	public ResponseEntity<ApiResponse<List<ProductDto>>> getAllProducts() {

		List<ProductDto> allProducts = productService.getAllProducts();
		Map<String, String> messages = props.getMessage();
		ApiResponse<List<ProductDto>> response = new ApiResponse<>();

		if (allProducts != null) {
			response.setStatusCode("200");
			response.setMessage(messages.get("productFetch"));
			response.setData(allProducts);
		} else {
			response.setStatusCode("500");
			response.setMessage(messages.get("productFetchErr"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> getProductById(@PathVariable("id") Integer id) {
		Map<String, String> messages = props.getMessage();
		ApiResponse<ProductDto> response = new ApiResponse<>();

		ProductDto productById = productService.getProductById(id);

		if (productById != null) {
			response.setStatusCode("200");
			response.setMessage(messages.get("productSelect"));
			response.setData(productById);
		} else {
			response.setStatusCode("500");
			response.setMessage(messages.get("productSelectErr"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> deleteProductById(@PathVariable("id") Integer id) {
		Map<String, String> messages = props.getMessage();
		ApiResponse<ProductDto> response = new ApiResponse<>();

		ProductDto productById = productService.deleteProductById(id);

		if (productById != null) {
			response.setStatusCode("200");
			response.setMessage(messages.get("productDelete"));
			response.setData(productById);
		} else {
			response.setStatusCode("500");
			response.setMessage(messages.get("productDeleteErr"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@PatchMapping("/{id}")
	public ResponseEntity<ApiResponse<ProductDto>> updateStock(@PathVariable("id") Integer id,
			@RequestParam("quantity") Integer quantity) {
		Map<String, String> messages = props.getMessage();
		ApiResponse<ProductDto> response = new ApiResponse<>();

		boolean updateStock = productService.updateStock(id, quantity);

		if (updateStock == true) {
			response.setStatusCode("200");
			response.setMessage(messages.get("stockUpdate"));
		} else {
			response.setStatusCode("500");
			response.setMessage(messages.get("stockUpdateErr"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
