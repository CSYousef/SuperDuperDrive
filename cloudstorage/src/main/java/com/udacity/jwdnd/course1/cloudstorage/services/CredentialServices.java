package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialServices {
    private CredentialMapper credentialsMapper;
    private UserMapper userMapper ;

    public CredentialServices(CredentialMapper credentialsMapper, UserMapper userMapper) {
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
    }

    public int addCredentials(Credential credentials) {

        return credentialsMapper.addCredential(credentials);
    }


    public void updateCredentials (Credential credential) {
        credentialsMapper.updateCredential(credential);
    }

    public void removeCredentials(Integer credentialid  ){
        credentialsMapper.delete(credentialid);
    }

    public List<Credential> getCredentials(int userId ){
        return credentialsMapper.getCredentialByUserId(userId);
    }


    public List<Credential> getAllCredentials(Authentication authentication){
        return credentialsMapper.getCredentialByUserId(userMapper.getUserId(authentication.getName()));

    }

}
