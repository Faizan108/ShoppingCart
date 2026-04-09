package com.practice.ShoppingCart.controller;

import com.practice.ShoppingCart.dto.UserDto;
import com.practice.ShoppingCart.exception.AlreadyExistException;
import com.practice.ShoppingCart.exception.UserNotFound;
import com.practice.ShoppingCart.model.User;
import com.practice.ShoppingCart.requests.CreateUserRequest;
import com.practice.ShoppingCart.requests.UpdateUserRequest;
import com.practice.ShoppingCart.responses.ApiResponse;
import com.practice.ShoppingCart.service.User.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @GetMapping("{id}/user")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id){
        try{
            User user = userService.getUserById(id);
            UserDto userDto = userService.getCovertedUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Success!",userDto));
        }catch(UserNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User Not Found!",e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody CreateUserRequest request){
        try{
            User user = userService.createUser(request);
            UserDto userDto = userService.getCovertedUserDto(user);
            return ResponseEntity.ok(new ApiResponse("Success!",userDto));
        }catch(AlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(new ApiResponse("User Already Exist!",e.getMessage()));
        }
    }

    @PutMapping("{id}/update")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable Long id,
                                                  @RequestBody UpdateUserRequest request){
        try{
            User user = userService.updateUser(request,id);
            UserDto userDto = userService.getCovertedUserDto(user);
            return ResponseEntity.ok(new ApiResponse("User Updated!",userDto));
        }catch(UserNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User Not Found!",e.getMessage()));
        }
    }

    @DeleteMapping("{userId}/delete")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long userId){
        try{
            userService.deleteUser(userId);
            return ResponseEntity.ok(new ApiResponse("Successfully Deleted!",null));
        }catch(UserNotFound e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse("User Not Found",e.getMessage()));
        }
    }
}
