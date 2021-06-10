package com.example.javaserver.service;

import com.example.javaserver.entity.User;
import com.example.javaserver.model.dto.UserDTO;
import com.example.javaserver.respository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class UserServiceIml implements UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> getListUser() {

        List<UserDTO> result = new ArrayList<UserDTO>();
        List<User> users = userRepository.findAll();

        for (User user : users) {
            result.add(modelMapper.map(user, UserDTO.class));
        }
        return result;

      }


    @Override
    public UserDTO getUserById(int id) {
        UserDTO result = null;
        try {
            result = modelMapper.map(userRepository.findUserById(id), UserDTO.class);
        } catch (Exception e) {
        }
        return result;
    }

    @Override
    public UserDTO addUser(User user) {
        UserDTO result = new UserDTO();
        try {
            User userdb = userRepository.findUserByUsername(user.getUsername());
            if (userRepository.existsByUsername((user.getUsername()))) {
                if (user.getIdsalary() == null) user.setIdsalary("1");
                result = modelMapper.map(userRepository.save(user), UserDTO.class);
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }


    @Override
    public UserDTO updateUser(int id, UserDTO userDto) {
        UserDTO result = null;
        try {
            Optional<User> userData = Optional.ofNullable(userRepository.findUserById(id));
            if (userData.isPresent()) {
                User userUpdate = userData.get();
                System.out.println(userData);

                userUpdate.setId(id);
//                if (userDto.getUsername() != null) {
//                    userUpdate.setUsername(userDto.getUsername());
//                }
                if ((userDto.getFullname()) != null)
                    userUpdate.setFullname(userDto.getFullname());
                if (userDto.getPhone() != null) userUpdate.setPhone(userDto.getPhone());
                if (userUpdate.getIdrole() != userDto.getRole()) userUpdate.setIdrole(userDto.getRole());
                result = modelMapper.map(userRepository.save(userUpdate), UserDTO.class);
            }

        } catch (Exception e) {
            throw e;
        }
        return result;
    }

    public ResponseEntity<?> deleteUser(int id) {
        try {
            userRepository.deleteById(id);
            return ResponseEntity.ok("deleted");
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public UserDTO login(User user) {
        try {
            String username = user.getUsername();
            String password = user.getPassword();
            User result = userRepository.findUserByUsernameAndPassword(username, password);
            return modelMapper.map(result, UserDTO.class);
        } catch (Exception e) {
            throw e;

        }
    }
}