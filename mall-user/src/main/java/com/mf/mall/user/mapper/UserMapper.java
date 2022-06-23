package com.mf.mall.user.mapper;

import com.mf.mall.user.model.UserDO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    UserDO selectUserById(Long id);

    int updateUser(UserDO userDO);
}
