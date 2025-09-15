package com.rana.chatapp.chatapp.services;

import com.rana.chatapp.chatapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

@Autowired
private UserRepository userRepository;


public boolean userExists(String userName){
    return userRepository.existsByUserName(userName);
}

public void setUserOnlineStatus(String userName , boolean isOnline){
    userRepository.updateUserOnlineStatus(userName, isOnline);

}


}
