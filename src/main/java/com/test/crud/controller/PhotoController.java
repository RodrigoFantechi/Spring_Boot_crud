package com.test.crud.controller;


import com.test.crud.model.Photo;
import com.test.crud.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/photos")
public class PhotoController {
    @Autowired
    private PhotoService photoService;

    @GetMapping
    public String index(Model model){
        List<Photo> photos = photoService.getAllPhotos();
        model.addAttribute("photos", photos);
        return "photos/index";
    }
    @GetMapping("/{id}")
    public String show(Model model,@PathVariable(name = "id") Integer photoId){
        model.addAttribute("photo", photoService.getById(photoId));
        return "photos/show";
    }
    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("photo", new Photo());
        return "photos/create";
    }
    @PostMapping("/create")
    public String store( Model model, @Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (!photoService.isValidTitle(formPhoto))
            bindingResult.addError(new FieldError("image", "title", formPhoto.getTitle(), false, null, null, "il titolo deve essere unico"));

        if (bindingResult.hasErrors()){
            return "photos/create";
        }
         photoService.savePhoto(formPhoto);
        redirectAttributes.addFlashAttribute("message", "Foto aggiunta con successo!");
        return "redirect:/photos";
    }

    @GetMapping("/edit/{id}")
    public String edit(){
        return "photos/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(){
        return "redirect:/photos";
    }

    @GetMapping("delete/{id}")
    public String delete (@PathVariable Integer id, RedirectAttributes redirectAttributes){
        try {
            boolean success = photoService.deleteById(id);
            if (success)
                redirectAttributes.addFlashAttribute("message", "Foto eliminata!");
            else {
                redirectAttributes.addFlashAttribute("message", "Non puoi eliminare!");
            }
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("message", "Errore 404!");
        }
        return "redirect:/photos";
    }

}
