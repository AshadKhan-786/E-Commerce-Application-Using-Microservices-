package in.ashokit.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.constant.AppConstants;
import in.ashokit.dto.CategoryDto;
import in.ashokit.entity.Category;
import in.ashokit.exception.ProductServiceException;
import in.ashokit.mapper.CategoryMapper;
import in.ashokit.repo.CategoryRepo;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public CategoryDto addCategory(CategoryDto categoryDto) {
		Category category = categoryRepo.save(CategoryMapper.convertToEntity(categoryDto));
		return CategoryMapper.convertToDto(category);
	}

	@Override
	public CategoryDto updateCategory(Integer cId, CategoryDto categoryDto) {
		Category existingCategory = categoryRepo.findById(cId)
				.orElseThrow(() -> new ProductServiceException(AppConstants.CATEGORY_NOT_FOUND_ERR_CD,
						AppConstants.CATEGORY_NOT_FOUND));

		existingCategory.setCategoryName(categoryDto.getCategoryName());
		Category updatedCategory = categoryRepo.save(existingCategory);

		return CategoryMapper.convertToDto(updatedCategory);
	}

	@Override
	public List<CategoryDto> getAllCategories() {
		List<Category> categories = categoryRepo.findAll();

		return categories.stream().map(CategoryMapper::convertToDto).collect(Collectors.toList());
	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ProductServiceException(AppConstants.PRODUCT_NOT_FOUND_ERR_CD,
						AppConstants.PRODUCT_NOT_FOUND));

		return CategoryMapper.convertToDto(category);
	}

	@Override
	public CategoryDto deleteCategoryId(Integer categoryId) {
		Category category = categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ProductServiceException(AppConstants.PRODUCT_NOT_FOUND_ERR_CD,
						AppConstants.PRODUCT_NOT_FOUND));
		categoryRepo.delete(category);

		return CategoryMapper.convertToDto(category);
	}

}
