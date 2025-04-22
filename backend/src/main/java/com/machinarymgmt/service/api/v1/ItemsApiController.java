package com.machinarymgmt.service.api.v1;

import com.machinarymgmt.service.api.ItemsApi;
import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
import com.machinarymgmt.service.api.data.model.Item;
import com.machinarymgmt.service.api.mapper.ItemMapper;
import com.machinarymgmt.service.api.service.ItemService;
import com.machinarymgmt.service.dto.ItemDto;
import com.machinarymgmt.service.dto.ItemRequestDto;
import com.machinarymgmt.service.dto.ItemListResponse;
import com.machinarymgmt.service.dto.ItemResponse;
import com.machinarymgmt.service.dto.MachinaryMgmtBaseApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_URL)
public class ItemsApiController implements ItemsApi {

    private final ItemService itemService;
    private final ItemMapper itemMapper;
    private final ApiResponseBuilder responseBuilder;

    @Override
    public ResponseEntity<ItemListResponse> getAllItems() throws Exception {
        List<ItemDto> itemsDto = itemMapper.toDtoList(itemService.findAll());
        ItemListResponse response = itemMapper.toDtoList(responseBuilder.buildSuccessApiResponse("All items retrieved successfully"));
        response.setData(itemsDto);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ItemResponse> getItemById(Long id) throws Exception {
        ItemDto itemDto = itemService.findDtoById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        ItemResponse response = itemMapper.toItemApiResponse(
                responseBuilder.buildSuccessApiResponse("Item retrieved successfully"));
        response.setData(itemDto);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<ItemResponse> createItem(@Valid ItemRequestDto itemRequestDto) throws Exception {
        Item item = itemMapper.toEntity(itemRequestDto);
        Item savedItem = itemService.save(item);
        ItemDto savedItemDto = itemMapper.toDto(savedItem);

        ItemResponse response = itemMapper.toItemApiResponse(responseBuilder.buildSuccessApiResponse("Item created successfully"));
        response.setData(savedItemDto);

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> deleteItem(Long id) throws Exception {
        itemService.deleteById(id);

        MachinaryMgmtBaseApiResponse response = itemMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Item deleted successfully"));

        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<MachinaryMgmtBaseApiResponse> updateItem(Long id, @Valid ItemRequestDto itemRequestDto)
            throws Exception {
        // TODO Auto-generated method stub
        Item existingItem= itemService.findById(id).orElseThrow(() -> new Exception("Item not found"));
        itemMapper.updateEntityFromDto(itemRequestDto, existingItem);
        Item updatedItem= itemService.save(existingItem);
        MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse=itemMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Item updated successfully"));
        return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
    }



    // @Override
    // public ResponseEntity<MachinaryMgmtBaseApiResponse> updateDepartment(Long id, @Valid DepartmentRequestDto departmentRequestDto)
    //              throws Exception {
    //      // TODO Auto-generated method stub
    //      Department existingDepartment= departmentService.findById(id).orElseThrow(() -> new Exception("Department not found"));
    //      departmentMapper.updateEntityFromDto(departmentRequestDto, existingDepartment);
    //      Department updatedDepartment= departmentService.save(existingDepartment);
    //      MachinaryMgmtBaseApiResponse machinaryMgmtBaseApiResponse=departmentMapper.toBaseApiResponse(responseBuilder.buildSuccessApiResponse("Department details updated successfully"));
    //      return ResponseEntity.ok(machinaryMgmtBaseApiResponse);
    // }

}
