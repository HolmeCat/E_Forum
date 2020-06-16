package com.Springboot.service.Impl;

import com.Springboot.domain.User;
import com.Springboot.repository.UserRepository;
import com.Springboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean checkByUserName(String name) {
        return userRepository.findById(name).orElse(null) != null;
    }
    @Override
    public boolean checkUser(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        Example<User> example = Example.of(user);
        return userRepository.findOne(example).orElse(null) != null;
    }
    @Override
    public boolean saveOne(String name, String password) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        userRepository.save(user);
        return true;
    }
}
