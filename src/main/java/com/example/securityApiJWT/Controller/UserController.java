package com.example.securityApiJWT.Controller;

import com.example.securityApiJWT.DTO.ChangePasswordRequest;
import com.example.securityApiJWT.DTO.PageableResponse;
import com.example.securityApiJWT.DTO.ResponseMessageAPI;
import com.example.securityApiJWT.DTO.UserDTO;
import com.example.securityApiJWT.Service.UserSerivce.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/User")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable("userId") Integer userId,
            @Valid @RequestBody UserDTO userDto
    ) {
        UserDTO updatedUserDto = userService.updateUserInfo(userDto, userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @PatchMapping
    public ResponseEntity<?> changePassword(
        @RequestBody ChangePasswordRequest request,
        Principal connectedUser
    ){
        userService.changePassword(request, connectedUser);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/userId")
    public ResponseEntity<ResponseMessageAPI> deleteUser(
            @PathVariable Integer userId
    ){
        userService.deleteUser(userId);
        ResponseMessageAPI responseMessageAPI = ResponseMessageAPI.builder()
                .message("Delete user successfully")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<>(responseMessageAPI, HttpStatus.OK);
    }

    @GetMapping
    @Operation(summary = "Get all of user")
    public ResponseEntity<PageableResponse<UserDTO>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get a user by their id")
    public ResponseEntity<UserDTO> getUser(@PathVariable Integer userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(@PathVariable String email) {
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/search/{keywords}")
    public ResponseEntity<PageableResponse<UserDTO>> searchUser(
            @PathVariable String keywords,  @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<UserDTO> response = userService.searchUser(keywords, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
