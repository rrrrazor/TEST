package com.rongrong.shop.repository;

import com.rongrong.shop.bean.User;
import org.apache.ibatis.annotations.Select;

public interface UserMapper {

    @Select("select * from ec_user where login_name = #{admin}")
    User login(String loginName);
}
