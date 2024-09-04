package in.ashokit.service;

import java.util.List;

import in.ashokit.dto.CategoryDto;

public interface CategoryService {

	public CategoryDto addCategory(CategoryDto categoryDto);

	public CategoryDto updateCategory(Integer cId, CategoryDto categoryDto);

	public List<CategoryDto> getAllCategories();

	public CategoryDto getCategoryById(Integer categoryId);

	public CategoryDto deleteCategoryId(Integer categoryId);
}
