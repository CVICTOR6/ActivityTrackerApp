package com.cvictor.activitytrackerapp.Service;

import com.cvictor.activitytrackerapp.DTO.UserDTO;
import com.cvictor.activitytrackerapp.Model.User;

public interface UserService {
    boolean saveUserData(UserDTO userDto);

    UserDTO validateUser(String userName, String password);

}
