package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {
    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata)" + " VALUES(#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(Files file );

    @Select("select * from FILES where userid = #{userId}")
    List<Files> getFilesByUserId(Integer userId);



    @Select("SELECT * FROM FILES WHERE userid = #{userId} and filename = #{filename}")
    Files downloadFiles(Integer userId, String filename);

    @Select("SELECT * FROM FILES WHERE  filename = #{filename}")
    Files existingFile(String filename);


    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    void delete(int fileId);
}
