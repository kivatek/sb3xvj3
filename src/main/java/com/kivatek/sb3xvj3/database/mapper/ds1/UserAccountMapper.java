package com.kivatek.sb3xvj3.database.mapper.ds1;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

import com.kivatek.sb3xvj3.model.UserAccount;

@Mapper
@Transactional(transactionManager = "tx1")
public interface UserAccountMapper {

    @Select("SELECT * FROM USER_ACCOUNT WHERE DELETED = 0 AND ID = #{id}")
    UserAccount findById(@Param("id") Long id);

    @Select("SELECT * FROM USER_ACCOUNT WHERE DELETED = 0 AND USERNAME = #{username}")
    UserAccount findByUserName(@Param("username") String username);

    // xmlを使用する事例として用意
    void updateUsername(@Param("id") Long id, @Param("username") String username);

}
