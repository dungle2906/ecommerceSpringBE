package com.example.securityApiJWT.Service.UserSerivce;

import com.example.securityApiJWT.DTO.ChangePasswordRequest;
import com.example.securityApiJWT.DTO.PageableResponse;
import com.example.securityApiJWT.DTO.UserDTO;
import com.example.securityApiJWT.Model.User;

import java.security.Principal;
import java.util.Optional;

public interface IUserService {
    UserDTO updateUserInfo(UserDTO UserDTO, Integer userId);
    void changePassword(ChangePasswordRequest request, Principal connectedUser);
    void deleteUser(Integer userId);
    PageableResponse<UserDTO> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
    UserDTO getUserById(Integer userId);
    UserDTO getUserByEmail(String email);
    PageableResponse<UserDTO> searchUser(String keyword,int pageNumber,int pageSize, String sortBy,String sortDir);
}
