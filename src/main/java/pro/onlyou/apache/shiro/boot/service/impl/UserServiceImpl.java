package pro.onlyou.apache.shiro.boot.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pro.onlyou.apache.shiro.boot.mapper.UserMapper;
import pro.onlyou.apache.shiro.boot.model.User;
import pro.onlyou.apache.shiro.boot.service.UserService;

import javax.annotation.Resource;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userMapper.findUserByUsername(username);
    }
}
