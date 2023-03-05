package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotesServices {

    private NotesMapper notesMapper;
    private UserMapper userMapper;

    public NotesServices(NotesMapper notesMapper, UserMapper userMapper) {
        this.notesMapper = notesMapper;
        this.userMapper = userMapper;
    }

    public NotesMapper getNotesMapper() {
        return notesMapper;
    }

    public UserMapper getUserMapper() {
        return userMapper;
    }

    public void setNotesMapper(NotesMapper notesMapper) {
        this.notesMapper = notesMapper;
    }

    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    public Integer CreateNote(Notes note){
        Notes new_note = new Notes(null,note.getTitle(),note.getDescription(), note.getUserid());
        return notesMapper.insert(new_note);
    }

    public List<Notes> getAllNotes(Authentication authentication ){
        return notesMapper.getNotes(userMapper.getUserId(authentication.getName()));
    }

    public void DeleteNote(Integer noteid){
        notesMapper.delete(noteid);
    }

    public Notes getNotebyId(Integer noteid){
        return notesMapper.getNote(noteid);
    }



    public void updateNote(Integer noteid,String notetitle,String notedescription){
        notesMapper.update(noteid,notetitle,notedescription);
    }








}
