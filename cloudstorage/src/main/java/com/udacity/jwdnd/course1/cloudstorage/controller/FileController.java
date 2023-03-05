package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class FileController {
    private final UserService userService;
    private final FileService fileService;

    public FileController(UserService userService, FileService fileService) {
        this.userService = userService;
        this.fileService = fileService;
    }

    @PostMapping("/Add-file")
    public String addFile(@RequestParam("fileUpload") MultipartFile fileUpload, Model model, Authentication authentication) throws IOException {
        User user = userService.getUser(authentication.getName());

        Files file = new Files(null , fileUpload.getOriginalFilename(), fileUpload.getContentType(), fileUpload.getSize(), user.getUserId(), fileUpload.getBytes());

        if (fileUpload.isEmpty()) {

            model.addAttribute("result", "error");

        }else {

            if (fileService.existingFile(fileUpload.getOriginalFilename()) == null) {
                fileService.addFile(file);
                model.addAttribute("result", "success");
                model.addAttribute("files", fileService.getFiles(user.getUserId()));

            } else {
                model.addAttribute("result", "error");
                model.addAttribute("files", fileService.getFiles(user.getUserId()));

            }
        }
        return "result";
    }


    @GetMapping("/deleteFile")
    public String deleteFile(Authentication authentication, @RequestParam Integer fileId,Model model){
        User user = userService.getUser(authentication.getName());

        fileService.removeFile(fileId);
        model.addAttribute("result", "success");

        model.addAttribute("files", fileService.getFiles(user.getUserId()));

        return "result";
    }

    //source that help me to understand the Download Files  https://www.bezkoder.com/thymeleaf-file-upload/
    @GetMapping("/downloadFiles")
    public ResponseEntity<Files> downloadFile(@RequestParam String filename , Authentication authentication , Model model){
        User user = userService.getUser(authentication.getName());
        Files file = fileService.downloadFiles(user.getUserId() , filename);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
