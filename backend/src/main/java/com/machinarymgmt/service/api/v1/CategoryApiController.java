package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.CategoriesApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.EquipmentCategory;
import com.machinarymgmt.service.api.mapper.EquipmentCategoryMapper;
import com.machinarymgmt.service.api.service.EquipmentCategoryService;
import com.machinarymgmt.service.dto.EquipmentCategoryDto;
import com.machinarymgmt.service.dto.EquipmentCategoryListResponse;
import com.machinarymgmt.service.dto.EquipmentCategoryResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.api.utils.Constants;
import com.machinarymgmt.service.dto.EquipmentCategoryRequestDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;
import static com.machinarymgmt.service.api.utils.Constants.CATEGORY_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class CategoryApiController implements CategoriesApi{

   private final EquipmentCategoryService categoryService;
   private final EquipmentCategoryMapper categoryMapper;
   private final ApiResponseBuilder responseBuilder;
   @Override
   public ResponseEntity<EquipmentCategoryListResponse> getAllCategories() throws Exception {
      // Get all categories from service
      List<EquipmentCategoryDto> categoriesDto = categoryMapper.toDtoList(categoryService.findAll());
      
      // Create response with success message
      EquipmentCategoryListResponse response = categoryMapper.toDtoList(
            responseBuilder.buildSuccessApiResponse("All categories retrieved successfully"));
      response.data(categoriesDto);
      
      return ResponseEntity.ok(response);
   }
   @Override
   public ResponseEntity<EquipmentCategoryResponse> getCategoryById(Long id) throws Exception {
       // Get category by ID from service
       EquipmentCategoryDto categoryDto = categoryMapper.toDto(categoryService.findById(id)
       .orElseThrow(() -> new Exception("Category not found with id: " + id)));
 
 // Create response with success message
 EquipmentCategoryResponse response = categoryMapper.toCategoryResponse(
       responseBuilder.buildSuccessApiResponse("Category retrieved successfully"));
 response.data(categoryDto);
 
 return ResponseEntity.ok(response);
   }
   @Override
   public ResponseEntity<EquipmentCategoryResponse> createCategory(@Valid EquipmentCategoryRequestDto equipmentCategoryRequestDto)
         throws Exception {
      // Check if category name is provided
      if (equipmentCategoryRequestDto.getName() == null || equipmentCategoryRequestDto.getName().trim().isEmpty()) {
         throw new Exception("Category name is required");
      }

      // Check if category with same name already exists
      if (categoryService.existsByName(equipmentCategoryRequestDto.getName())) {
         throw new Exception("Category already exists with name: " + equipmentCategoryRequestDto.getName());
      }

      // Convert DTO to entity
      EquipmentCategory category = categoryMapper.toEntity(equipmentCategoryRequestDto);
      
      // Save the category
      EquipmentCategory savedCategory = categoryService.save(category);

      // Create response
      EquipmentCategoryResponse response = categoryMapper.toCategoryResponse(
            responseBuilder.buildSuccessApiResponse("Category created successfully"));
      response.data(categoryMapper.toDto(savedCategory));

      return ResponseEntity.ok(response);
   }
   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteCategory(Long id) throws Exception {
      // Check if category exists
      if (!categoryService.existsById(id)) {
         throw new Exception("Category not found with id: " + id);
      }

      // Delete the category
      categoryService.deleteById(id);

      // Create and return success response
      BaseApiResponse baseResponse = responseBuilder.buildSuccessApiResponse("Category deleted successfully");
      MachinaryMgmtBaseApiResponse response = new MachinaryMgmtBaseApiResponse();
      response.setRespType(baseResponse.getRespType());
      response.setMetadata(baseResponse.getMetadata());
      response.setStatus(baseResponse.getStatus());
      response.setMessages(baseResponse.getMessages());
      return ResponseEntity.ok(response);
   }
   @Override
   public ResponseEntity<EquipmentCategoryResponse> updateCategory(Long id,
         @Valid EquipmentCategoryDto equipmentCategoryDto) throws Exception {
      // Check if category exists
      if (!categoryService.existsById(id)) {
         throw new Exception("Category not found with id: " + id);
      }

      // Check if category name is provided
      if (equipmentCategoryDto.getName() == null || equipmentCategoryDto.getName().trim().isEmpty()) {
         throw new Exception("Category name is required");
      }

      // Get existing category
      EquipmentCategory existingCategory = categoryService.findById(id)
            .orElseThrow(() -> new Exception("Category not found with id: " + id));

      // Check if new name conflicts with existing category
      if (!existingCategory.getName().equals(equipmentCategoryDto.getName()) &&
            categoryService.existsByName(equipmentCategoryDto.getName())) {
         throw new Exception("Category already exists with name: " + equipmentCategoryDto.getName());
      }

      // Update category
      existingCategory.setName(equipmentCategoryDto.getName());
      EquipmentCategory updatedCategory = categoryService.save(existingCategory);

      // Create response
      EquipmentCategoryResponse response = categoryMapper.toCategoryResponse(
            responseBuilder.buildSuccessApiResponse("Category updated successfully"));
      response.data(categoryMapper.toDto(updatedCategory));

      return ResponseEntity.ok(response);
   }

   

   

   



//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<EquipmentCategoryDto>>> getAllCategories() {
//        List<EquipmentCategoryDto> categories = categoryService.findAllDto();
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(categories));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<EquipmentCategoryDto>> getCategoryById(@PathVariable Long id) {
//        return categoryService.findDtoById(id)
//                .map(category -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(category)))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Category not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<EquipmentCategoryDto>> createCategory(@Valid @RequestBody EquipmentCategoryDto categoryDto) {
//        if (categoryService.existsByName(categoryDto.getCategoryName())) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Category already exists with name: " + categoryDto.getCategoryName(),
//                    ErrorType.DUPLICATE));
//        }

//        EquipmentCategory category = categoryMapper.toEntity(categoryDto);
//        EquipmentCategory savedCategory = categoryService.save(category);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                categoryMapper.toDto(savedCategory),
//                "Category created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<EquipmentCategoryDto>> updateCategory(
//            @PathVariable Long id,
//            @Valid @RequestBody EquipmentCategoryDto categoryDto) {
//        if (!categoryService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Category not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        EquipmentCategory existingCategory = categoryService.findById(id).get();
//        if (!existingCategory.getName().equals(categoryDto.getCategoryName()) &&
//                categoryService.existsByName(categoryDto.getCategoryName())) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Category already exists with name: " + categoryDto.getCategoryName(),
//                    ErrorType.DUPLICATE));
//        }

//        existingCategory.setName(categoryDto.getCategoryName());
//        EquipmentCategory updatedCategory = categoryService.save(existingCategory);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                categoryMapper.toDto(updatedCategory),
//                "Category updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteCategory(@PathVariable Long id) {
//        if (!categoryService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Category not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        categoryService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Category deleted successfully"));
//    }
}


