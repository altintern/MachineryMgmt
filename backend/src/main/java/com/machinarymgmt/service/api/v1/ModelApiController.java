package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.ModelsApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Make;
import com.machinarymgmt.service.api.data.model.Model;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import com.machinarymgmt.service.dto.ModelDto;
import com.machinarymgmt.service.api.mapper.ModelMapper;
import com.machinarymgmt.service.api.service.MakeService;
import com.machinarymgmt.service.api.service.ModelService;
import com.machinarymgmt.service.dto.ModelListResponse;
import com.machinarymgmt.service.dto.ModelRequestDto;
import com.machinarymgmt.service.dto.ModelResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class ModelApiController implements ModelsApi {

   private final ModelService modelService;
   private final ModelMapper modelMapper;
   private final ApiResponseBuilder responseBuilder;
   private final MakeService makeService;
   @Override
   public ResponseEntity<ModelListResponse> getAllModels() throws Exception {
    
    List<ModelDto> models = modelMapper.toDtoList(modelService.findAll());
    ModelListResponse modelListResponse = modelMapper.toDtoList(responseBuilder.buildSuccessApiResponse("All models are retrieved successfully"));
    modelListResponse.data(models);
    return ResponseEntity.ok(modelListResponse);
   }

   @Override
   public ResponseEntity<ModelResponse> getModelById(Long id) throws Exception {
      ModelDto modelDto = modelMapper.toDto(modelService.findById(id).orElseThrow(() -> new Exception("Project not found")));
      ModelResponse modelResponse = modelMapper.toModelResponse(responseBuilder.buildSuccessApiResponse("Model retrieved successfully"));
      modelResponse.data(modelDto);
      System.out.println(modelResponse);
      return ResponseEntity.ok(modelResponse);
   }

   @Override
   public ResponseEntity<ModelResponse> createModel(@Valid ModelRequestDto modelRequestDto) throws Exception {
     


      Model model = modelMapper.toEntity(modelRequestDto);
      model.setMake(makeService.findById(modelRequestDto.getMakeId()).get());
      Model ModelSaved = modelService.save(model);
      ModelResponse modelResponse = modelMapper.toModelResponse(responseBuilder.buildSuccessApiResponse("Model created successfully"));
      modelResponse.data(modelMapper.toDto(ModelSaved));
      System.out.println(modelResponse);  
       return new ResponseEntity<>(modelResponse, HttpStatus.CREATED);
   }

   @Override
   public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteModel(Long id) throws Exception {
      // Check if model exists
      if (!modelService.existsById(id)) {
         throw new Exception("Model not found with id: " + id);
      }

      // Delete the model
      modelService.deleteById(id);

      // Create and return success response
      BaseApiResponse baseResponse = responseBuilder.buildSuccessApiResponse("Model deleted successfully");
      MachinaryMgmtBaseApiResponse response = new MachinaryMgmtBaseApiResponse();
      response.setRespType(baseResponse.getRespType());
      response.setMetadata(baseResponse.getMetadata());
      response.setStatus(baseResponse.getStatus());
      response.setMessages(baseResponse.getMessages());
      return ResponseEntity.ok(response);
   }

   @Override
   public ResponseEntity<ModelResponse> updateModel(Long id, @Valid ModelDto modelDto) throws Exception {
      // Check if model exists
      if (!modelService.existsById(id)) {
         throw new Exception("Model not found with id: " + id);
      }

      // Validate make exists
      Optional<Make> makeOpt = makeService.findById(modelDto.getMake().getId());
      if (makeOpt.isEmpty()) {
         throw new Exception("Make not found with id: " + modelDto.getMake().getId());
      }

      // Get existing model
      Model existingModel = modelService.findById(id)
            .orElseThrow(() -> new Exception("Model not found with id: " + id));

      // Check for duplicate model name within the same make
      if (!existingModel.getName().equals(modelDto.getName()) || 
            !existingModel.getMake().getId().equals(modelDto.getMake().getId())) {
         if (modelService.existsByNameAndMake(modelDto.getName(), makeOpt.get())) {
            throw new Exception("Model already exists with name: " + modelDto.getName() + " for this make");
         }
      }

      // Update model
      modelMapper.updateEntityFromDto(modelDto, existingModel);
      existingModel.setMake(makeOpt.get());
      Model updatedModel = modelService.save(existingModel);

      // Create response
      ModelResponse response = modelMapper.toModelResponse(responseBuilder.buildSuccessApiResponse("Model updated successfully"));
      response.setData(modelMapper.toDto(updatedModel));

      return ResponseEntity.ok(response);
   }

   





//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<ModelDto>>> getAllModels() {
//        List<ModelDto> models = modelService.findAllDto();
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(models));
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<ModelDto>> getModelById(@PathVariable Long id) {
//        return modelService.findDtoById(id)
//                .map(model -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(model)))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Model not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }

//    @GetMapping("/make/{makeId}")
//    public ResponseEntity<BaseApiResponse<List<ModelDto>>> getModelsByMake(@PathVariable Long makeId) {
//        if (!makeService.existsById(makeId)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Make not found with id: " + makeId,
//                    ErrorType.NOT_FOUND));
//        }
//        List<ModelDto> models = modelService.findDtoByMakeId(makeId);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(models));
//    }

//    @PostMapping
//    public ResponseEntity<BaseApiResponse<ModelDto>> createModel(@Valid @RequestBody ModelDto modelDto) {
//        Optional<Make> makeOpt = makeService.findById(modelDto.getMakeId());
//        if (makeOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Make not found with id: " + modelDto.getMakeId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Make make = makeOpt.get();
//        if (modelService.existsByNameAndMake(modelDto.getModelName(), make)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Model already exists with name: " + modelDto.getModelName() + " for this make",
//                    ErrorType.DUPLICATE));
//        }

//        Model model = modelMapper.fromDtoWithReferences(modelDto, make);
//        Model savedModel = modelService.save(model);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                modelMapper.toDto(savedModel),
//                "Model created successfully"));
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<ModelDto>> updateModel(
//            @PathVariable Long id,
//            @Valid @RequestBody ModelDto modelDto) {
//        if (!modelService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Model not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }

//        Optional<Make> makeOpt = makeService.findById(modelDto.getMakeId());
//        if (makeOpt.isEmpty()) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Make not found with id: " + modelDto.getMakeId(),
//                    ErrorType.NOT_FOUND));
//        }

//        Model existingModel = modelService.findById(id).get();
//        Make make = makeOpt.get();

//        if (!existingModel.getName().equals(modelDto.getModelName()) ||
//                !existingModel.getMake().getId().equals(modelDto.getMakeId())) {
//            if (modelService.existsByNameAndMake(modelDto.getModelName(), make)) {
//                return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Model already exists with name: " + modelDto.getModelName() + " for this make",
//                        ErrorType.DUPLICATE));
//            }
//        }

//        modelMapper.updateEntityFromDto(modelDto, existingModel);
//        existingModel.setMake(make);
//        Model updatedModel = modelService.save(existingModel);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                modelMapper.toDto(updatedModel),
//                "Model updated successfully"));
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteModel(@PathVariable Long id) {
//        if (!modelService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Model not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        modelService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Model deleted successfully"));
//    }
}

