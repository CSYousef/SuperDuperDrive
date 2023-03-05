package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {

    @Select("SELECT * FROM NOTES WHERE userid=#{ userid} ")
    List<Notes> getNotes(Integer userid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{title}, #{description}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Notes notes);


    @Delete("DELETE FROM Notes WHERE noteid = #{noteid}")
    void delete(Integer noteid);

    @Update("UPDATE NOTES set notetitle=#{notetitle},notedescription=#{notedescription} WHERE noteid=#{noteid}")
    void update(Integer noteid,String notetitle,String notedescription);

    @Select("SELECT * FROM NOTES WHERE  noteid= #{ noteid} ")
    Notes getNote(Integer id);



}
