package com.practice.ShoppingCart.service.User;

import com.practice.ShoppingCart.dto.UserDto;
import com.practice.ShoppingCart.model.User;
import com.practice.ShoppingCart.requests.CreateUserRequest;
import com.practice.ShoppingCart.requests.UpdateUserRequest;

public interface IUserService {

     User getUserById(Long id);
     User createUser(CreateUserRequest request);
     User updateUser(UpdateUserRequest request, Long id);
     void deleteUser(Long id);
     UserDto getCovertedUserDto(User user);

}
