package com.test.crud.controller;


import com.test.crud.exception.ImageNotFoundException;
import com.test.crud.model.Photo;
import com.test.crud.service.PhotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
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
    public String store(@Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, RedirectAttributes redirectAttributes){
        if (!photoService.isValidTitle(formPhoto))
            bindingResult.addError(new FieldError("photo", "title", formPhoto.getTitle(), false, null, null, "il titolo deve essere unico"));

        if (bindingResult.hasErrors()){
            return "photos/create";
        }
         photoService.savePhoto(formPhoto);
        redirectAttributes.addFlashAttribute("message", "Foto aggiunta con successo!");
        return "redirect:/photos";
    }

    @GetMapping("/edit/{id}")
    public String edit(Model model, @PathVariable Integer id){
        try{
            Photo photo = photoService.getById(id);
            model.addAttribute("photo", photo);
            return "photos/edit";
        } catch (ImageNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto non trovata");
        }
    }

    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("photo") Photo formPhoto, BindingResult bindingResult, @PathVariable Integer id, RedirectAttributes redirectAttributes){

        if (!photoService.isValidTitle(formPhoto))
            bindingResult.addError(new FieldError("photo", "title", formPhoto.getTitle(), false, null, null, "il titolo deve essere unico"));

        if (bindingResult.hasErrors()){
            return "photos/edit";
        }
        try {
            photoService.updatePhoto(formPhoto, id);
        } catch (ImageNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foto non è stata trovata");
        }

        redirectAttributes.addFlashAttribute("message", "Foto aggiornata con successo!");
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
