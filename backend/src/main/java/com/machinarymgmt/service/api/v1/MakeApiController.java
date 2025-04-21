package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.EquipmentsApi;
import com.machinarymgmt.service.api.MakesApi;
import com.machinarymgmt.service.api.OvertimeReportApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.config.dto.ErrorType;
import com.machinarymgmt.service.api.data.model.Make;
import com.machinarymgmt.service.api.mapper.MakeMapper;
import com.machinarymgmt.service.api.service.MakeService;
import com.machinarymgmt.service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class MakeApiController implements MakesApi{

    private final MakeService makeService;
    private final MakeMapper makeMapper;
    private final ApiResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> createMake(MakeRequestDto makeRequestDto) throws Exception {
        Make make = makeMapper.toEntity(makeRequestDto);
        Make savedMake = makeService.save(make);
        MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse =
                makeMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Make api created Succesfully"));
        return new ResponseEntity<>(machinaryMgmtBaseApiResponse, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteMake(Long id) throws Exception {
        makeService.deleteById(id);
        MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse =
                makeMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Make api deleted Succesfully"));
        return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
    }

    @Override
    public ResponseEntity<MakeListResponse> getAllMakes() throws Exception {
        List<MakeDto> makes = makeService.findAllDto();
        MakeListResponse makeListResponse = makeMapper.toDtoList(responseBuilder.buildSuccessApiResponse("All makes are retrieved successfully"));
        makeListResponse.setData(makes);
        return ResponseEntity.ok(makeListResponse);
    }

    @Override
    public ResponseEntity<MakeResponse> getMakeById(Long id) throws Exception {
        MakeDto makeDto = makeMapper.toDto(makeService.findById(id).orElseThrow(() -> new Exception("Project not found")));
        MakeResponse makeResponse = makeMapper.toMakeApiResponse(responseBuilder.buildSuccessApiResponse("Model retrieved succesfully"));
        makeResponse.setData(makeDto);
        return ResponseEntity.ok(makeResponse);
    }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> updateMake(Long id, MakeRequestDto makeRequestDto) throws Exception {
        Make existingMake = makeService.findById(id).orElseThrow(() -> new Exception("Make not found"));
        makeMapper.updateMakeFromDto(makeRequestDto, existingMake);
        Make updatedMake = makeService.save(existingMake);  //Optional if you want to return the updated make
        MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse = makeMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Make API updated successfully"));
        return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
    }

//    @Override
//    public ResponseEntity<MakeResponse> updateMake(Long id, MakeRequestDto makeRequestDto) throws Exception {
//        Make existingMake = makeService.findById(id).orElseThrow(() -> new Exception("Make not found"));
//        Make make = makeMapper.toEntity(makeRequestDto);
//        Make updatedMake = makeService.save(existingMake);
//        return ResponseEntity.ok();
//    }


//    @GetMapping
//    public ResponseEntity<BaseApiResponse<List<MakeDto>>> getAllMakes() {
//        List<MakeDto> makes = makeService.findAllDto();
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(makes));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<MakeDto>> getMakeById(@PathVariable Long id) {
//        return makeService.findDtoById(id)
//                .map(make -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(make)))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "Make not found with id: " + id,
//                        ErrorType.NOT_FOUND)));
//    }
//
//    @PostMapping
//    public ResponseEntity<BaseApiResponse<MakeDto>> createMake(@Valid @RequestBody MakeDto makeDto) {
//        if (makeService.existsByName(makeDto.getMakeName())) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Make already exists with name: " + makeDto.getMakeName(),
//                    ErrorType.DUPLICATE));
//        }
//
//        Make make = makeMapper.toEntity(makeDto);
//        Make savedMake = makeService.save(make);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                makeMapper.toDto(savedMake),
//                "Make created successfully"));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<MakeDto>> updateMake(
//            @PathVariable Long id,
//            @Valid @RequestBody MakeDto makeDto) {
//        if (!makeService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Make not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//
//        Make existingMake = makeService.findById(id).get();
//        if (!existingMake.getName().equals(makeDto.getMakeName()) &&
//                makeService.existsByName(makeDto.getMakeName())) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Make already exists with name: " + makeDto.getMakeName(),
//                    ErrorType.DUPLICATE));
//        }
//
//        existingMake.setName(makeDto.getMakeName());
//        Make updatedMake = makeService.save(existingMake);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(
//                makeMapper.toDto(updatedMake),
//                "Make updated successfully"));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<BaseApiResponse<Void>> deleteMake(@PathVariable Long id) {
//        if (!makeService.existsById(id)) {
//            return ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                    "Make not found with id: " + id,
//                    ErrorType.NOT_FOUND));
//        }
//        makeService.deleteById(id);
//        return ResponseEntity.ok(responseBuilder.buildSuccessResponse(null, "Make deleted successfully"));
//    }
}

