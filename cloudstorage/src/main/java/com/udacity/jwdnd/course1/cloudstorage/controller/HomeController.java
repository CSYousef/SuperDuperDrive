package com.udacity.jwdnd.course1.cloudstorage.controller;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialServices;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesServices;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController implements ErrorController {

    private final NotesServices noteService;

    private final CredentialServices credentialService;

    private final FileService fileServices;

    public HomeController(NotesServices noteService, CredentialServices credentialService, FileService fileServices) {
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.fileServices = fileServices;
    }

    @GetMapping("/home")
    public String GetHomePage(@ModelAttribute("files") Files files, Model model, @ModelAttribute("notes") Notes notes, @ModelAttribute("cred") Credential credentials, Authentication authentication){

        model.addAttribute("files",fileServices.getFiles(authentication));
        model.addAttribute("notes", this.noteService.getAllNotes(authentication));
        model.addAttribute("credentials",this.credentialService.getAllCredentials(authentication));

        return "home";
    }
    @GetMapping("/error")
    @ResponseBody
    public String handleError(HttpServletRequest request) {

        return String.format("<html> <body><h2> Error Page</h2><div> <a href=\"/login\">click here to return Login page </a> </div>");
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}

