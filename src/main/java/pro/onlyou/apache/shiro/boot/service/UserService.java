package pro.onlyou.apache.shiro.boot.service;

import pro.onlyou.apache.shiro.boot.model.User;

public interface UserService {
    User findByUsername(String username);
}
