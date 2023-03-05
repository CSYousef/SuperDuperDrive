package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialServices;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.SecureRandom;
import java.util.Base64;


@Controller
public class CredentialController {
    private UserService userService;

    private CredentialServices credentialService;

    private EncryptionService encryptionService;

    public CredentialController(UserService userService, CredentialServices credentialService, EncryptionService encryptionService) {
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @PostMapping("/Add-credential")
    public String addCredential(@ModelAttribute Credential credential, Model model, Authentication authentication){
        User user = userService.getUser(authentication.getName());
        String password =  credential.getPassword();
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(password, encodedKey);
        String decryptedPassword = encryptionService.decryptValue(encryptedPassword, encodedKey);



        Credential credential0 = new Credential( credential.getCredentialid(), credential.getUrl(), credential.getUsername(), encodedKey , encryptedPassword, user.getUserId());

        if (credential0.getCredentialid() != null){

            credentialService.updateCredentials(credential0);
            model.addAttribute("result","success");
            model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));

        }else if (credential0.getCredentialid() == null) {

            credentialService.addCredentials(credential0);
            model.addAttribute("result", "success");
            model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));
        }else {
            model.addAttribute("result","error");
        }
        return "result";
    }


    @GetMapping("/deleteCredentials")
    public String deleteNote(Authentication authentication, @RequestParam Integer credentialid, Model model) {
        credentialService.removeCredentials(credentialid);
        User user = userService.getUser(authentication.getName());
        model.addAttribute("result", "success");
        model.addAttribute("credentials", credentialService.getCredentials(user.getUserId()));



        return "result";
    }
}
