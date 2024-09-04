package in.ashokit.rest;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.ashokit.dto.CategoryDto;
import in.ashokit.props.AppProps;
import in.ashokit.response.ApiResponse;
import in.ashokit.service.CategoryService;

@RestController
@RequestMapping("/categories")
public class CategoryRestController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AppProps props;

	@PostMapping("/add")
	public ResponseEntity<ApiResponse<CategoryDto>> addCategory(@RequestBody CategoryDto categoryDto) {

		Map<String, String> message = props.getMessage();

		CategoryDto category = categoryService.addCategory(categoryDto);
		ApiResponse<CategoryDto> response = new ApiResponse<>();

		if (category != null) {
			response.setStatusCode("201");
			response.setMessage(message.get("categoryAdded"));
			response.setData(category);
		} else {
			response.setStatusCode("500");
			response.setMessage(message.get("categoryAddErr"));
		}

		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ApiResponse<CategoryDto>> updateCategory(@PathVariable("id") Integer cId,
			@RequestBody CategoryDto categoryDto) {

		CategoryDto updatedCategory = categoryService.updateCategory(cId, categoryDto);
		ApiResponse<CategoryDto> response = new ApiResponse<>();
		Map<String, String> messages = props.getMessage();

		if (updatedCategory != null) {
			response.setStatusCode("200");
			response.setMessage(messages.get("categoryUpdate"));
			response.setData(updatedCategory);
		} else {
			response.setStatusCode("500");
			response.setMessage(messages.get("categoryUpdateErr"));
		}

		return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
	}

	@GetMapping("/categories")
	public ResponseEntity<ApiResponse<List<CategoryDto>>> getAllCategories() {

		List<CategoryDto> allCategories = categoryService.getAllCategories();
		Map<String, String> messages = props.getMessage();
		ApiResponse<List<CategoryDto>> response = new ApiResponse<>();

		if (allCategories != null) {
			response.setStatusCode("200");
			response.setMessage(messages.get("categoryFetch"));
			response.setData(allCategories);
		} else {
			response.setStatusCode("500");
			response.setMessage(messages.get("categoryFetchErr"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/{id}")
	public ResponseEntity<ApiResponse<CategoryDto>> getCategoryById(@PathVariable("id") Integer id) {
		Map<String, String> messages = props.getMessage();
		ApiResponse<CategoryDto> response = new ApiResponse<>();

		CategoryDto categoryById = categoryService.getCategoryById(id);

		if (categoryById != null) {
			response.setStatusCode("200");
			response.setMessage(messages.get("categorySelect"));
			response.setData(categoryById);
		} else {
			response.setStatusCode("500");
			response.setMessage(messages.get("categorySelectErr"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ApiResponse<CategoryDto>> deleteCategoryById(@PathVariable("id") Integer id) {
		Map<String, String> messages = props.getMessage();
		ApiResponse<CategoryDto> response = new ApiResponse<>();

		CategoryDto categoryById = categoryService.deleteCategoryId(id);

		if (categoryById != null) {
			response.setStatusCode("200");
			response.setMessage(messages.get("categoryDelete"));
			response.setData(categoryById);
		} else {
			response.setStatusCode("500");
			response.setMessage(messages.get("categoryDeleteErr"));
		}

		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

}
