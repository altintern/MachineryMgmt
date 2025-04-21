//package com.machinarymgmt.service.api.v1;
//
//import com.machinarymgmt.service.api.builder.ApiResponseBuilder;
//import com.machinarymgmt.service.api.config.dto.BaseApiResponse;
//import com.machinarymgmt.service.api.config.dto.ErrorType;
//import com.machinarymgmt.service.api.data.model.User;
//import com.machinarymgmt.service.api.service.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import static com.machinarymgmt.service.api.utils.Constants.BASE_URL;
//
//@RestController
//@RequiredArgsConstructor
//@RequestMapping(BASE_URL + "/users")
//public class UserController {
//
//    private final UserService userService;
//    private final ApiResponseBuilder responseBuilder;
//
//    @GetMapping("/{username}")
//    public ResponseEntity<BaseApiResponse<User>> getUserByUsername(@PathVariable String username) {
//        return userService.findByUsername(username)
//                .map(user -> ResponseEntity.ok(responseBuilder.buildSuccessResponse(user)))
//                .orElseGet(() -> ResponseEntity.ok(responseBuilder.buildErrorResponse(
//                        "User not found with username: " + username,
//                        ErrorType.NOT_FOUND)));
//    }
//}
//
