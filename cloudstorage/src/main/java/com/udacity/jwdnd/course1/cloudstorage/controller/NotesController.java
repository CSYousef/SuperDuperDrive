package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import com.udacity.jwdnd.course1.cloudstorage.services.NotesServices;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/notes")
public class NotesController {
    private final NotesServices notesServices;
    private final UserService userService;
    private final UserMapper userMapper;

    public NotesController(NotesServices notesServices, UserService userService, UserMapper userMapper) {
        this.notesServices = notesServices;
        this.userService = userService;
       this.userMapper = userMapper;
    }

    public NotesServices getNotesServices() {
        return notesServices;
    }

    public UserService getUserService() {
        return userService;
    }


    @GetMapping
    public String getnotes(@ModelAttribute("notes") Notes not, Model model, Authentication authentication) {

        model.addAttribute("notes", this.notesServices.getAllNotes(authentication));

        return "result";
    }

    @PostMapping("addNote")
    public String addNewNote(@ModelAttribute("notes") Notes notes, Model model, Authentication authentication) {

        String name = authentication.getName();
        notes.setUserid(userMapper.getUser(name).getUserId());
        if (notes.getNoteid() != null) {
            UpdateNote(notes, model, authentication);
            model.addAttribute("result", "success");

        } else {
            notesServices.CreateNote(notes);
            model.addAttribute("result", "success");

        }
        model.addAttribute("notes", this.notesServices.getAllNotes(authentication));

        return "result";

    }

    public void UpdateNote(Notes notes, Model model, Authentication authentication) {
        notesServices.updateNote(notes.getNoteid(), notes.getTitle(), notes.getDescription());
        model.addAttribute("notes", this.notesServices.getAllNotes(authentication));
    }

    @GetMapping("/delete/{noteid}")
    public String DeleteNote(@ModelAttribute("notes") Notes note, Model model, @PathVariable Integer noteid, Authentication authentication) {
        notesServices.DeleteNote(noteid);
        model.addAttribute("notes", this.notesServices.getAllNotes(authentication));

        model.addAttribute("result", "success");

        return "result";
    }


}
