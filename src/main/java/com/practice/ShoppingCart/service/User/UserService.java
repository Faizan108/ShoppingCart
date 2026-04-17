package com.practice.ShoppingCart.service.User;

import com.practice.ShoppingCart.dto.UserDto;
import com.practice.ShoppingCart.exception.AlreadyExistException;
import com.practice.ShoppingCart.exception.UserNotFound;
import com.practice.ShoppingCart.model.User;
import com.practice.ShoppingCart.repository.UserRepository;
import com.practice.ShoppingCart.requests.CreateUserRequest;
import com.practice.ShoppingCart.requests.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new UserNotFound("User not found"));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user ->!userRepository.existsByEmail(user.getEmail()))
                .map(req->{
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    user.setPassword(request.getPassword());
                    return userRepository.save(user);
                }).orElseThrow(()-> new AlreadyExistException("User already exist!"));

    }

    @Override
    public User updateUser(UpdateUserRequest request, Long id) {

       return userRepository.findById(id)
                .map(req -> {
                    req.setFirstName(request.getFirstName());
                    req.setLastName(request.getLastName());
                    return userRepository.save(req);
                }).orElseThrow(()-> new UserNotFound("User not found!"));
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id).ifPresentOrElse(userRepository::delete,()->{throw new UserNotFound("User not found");});
    }

    @Override
    public UserDto getCovertedUserDto(User user){
        return modelMapper.map(user,UserDto.class);
    }

}
