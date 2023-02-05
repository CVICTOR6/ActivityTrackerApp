package com.cvictor.activitytrackerapp.Service.Serviceimplementation;

import com.cvictor.activitytrackerapp.DTO.UserDTO;
import com.cvictor.activitytrackerapp.Model.User;
import com.cvictor.activitytrackerapp.Repository.UserRepository;
import com.cvictor.activitytrackerapp.Service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class UserImplementation implements UserService {
    private UserRepository userRepository;


    @Override
    public boolean saveUserData(UserDTO userDto) {
        boolean isSuccess = false;
//check if exists in email
        User user = userRepository.findUserByEmail(userDto.getEmail()).orElse(null);

        if (user != null) {
            return false;
        }
        // save userdto and convert to new user

        User newUser = new User();
        newUser.setEmail(userDto.getEmail());
        newUser.setFullName(userDto.getFullName());
        newUser.setPassword(userDto.getPassword());

        userRepository.save(newUser);

        return true;

    }

    @Override
    public UserDTO validateUser(String email, String password) {

        //check user exists in db
        User user = userRepository.findUserByEmailAndPassword(email, password).orElse(null);

        if (user == null) {
            return null;
        }
        // convert the found user to user Dto
        UserDTO userDTO = new UserDTO();

        userDTO.setEmail(user.getEmail());
        userDTO.setId(user.getId());
        userDTO.setFullName(user.getFullName());
        return userDTO;
    }
}
