package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FilesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private FilesMapper filesMapper;
    private UserMapper userMapper;

    public FileService(FilesMapper filesMapper, UserMapper userMapper) {
        this.filesMapper = filesMapper;
        this.userMapper = userMapper;
    }

    public int addFile(Files file ) {
        return filesMapper.insertFile(file);
    }

    public List<Files> getFiles(Integer userId) {
        return filesMapper.getFilesByUserId(userId) ;
    }

    public Files downloadFiles(Integer userId , String filename){
        return filesMapper.downloadFiles(userId , filename);
    }

    public Files existingFile( String filename){
        return filesMapper.existingFile(filename);
    }

    public void removeFile(int userId) {
        filesMapper.delete(userId) ;

    }

    public List<Files> getFiles(Authentication authentication){
        return filesMapper.getFilesByUserId(userMapper.getUserId(authentication.getName()));

    }
}
