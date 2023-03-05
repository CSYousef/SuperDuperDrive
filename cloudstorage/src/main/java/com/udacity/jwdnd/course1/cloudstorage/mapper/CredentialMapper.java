package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS (url, username, password ,key ,userid) VALUES(#{url}, #{username}, #{password},#{key}  ,#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int addCredential(Credential credential);

    @Select("select * from CREDENTIALS where userid = #{userId}")
    List<Credential> getCredentialByUserId(Integer userId);

    @Update("UPDATE CREDENTIALS set url = #{url}, username = #{username} , password = #{password} , key = #{key}  WHERE credentialid = #{credentialid}")
    void updateCredential(Credential credentials);


    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    void delete(int credentialid);
}
