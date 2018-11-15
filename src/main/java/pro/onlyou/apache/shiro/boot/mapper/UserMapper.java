package pro.onlyou.apache.shiro.boot.mapper;

import org.apache.ibatis.annotations.Param;
import pro.onlyou.apache.shiro.boot.model.User;

public interface UserMapper {

    User findUserByUsername(@Param("username") String username);
}
