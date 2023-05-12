package com.test.crud.service;

import com.test.crud.exception.ImageNotFoundException;
import com.test.crud.model.Photo;
import com.test.crud.repository.PhotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.List;
@Service
public class PhotoService {
    @Autowired
    PhotoRepository photoRepository;
    public List<Photo> getAllPhotos(){return photoRepository.findAll();}
    public Photo getById(Integer id) throws ImageNotFoundException {
        return photoRepository.findById(id).orElseThrow(ImageNotFoundException::new);
    }
    public Photo savePhoto(Photo formPhoto) {
        Photo newPhoto = new Photo();
        newPhoto.setId(formPhoto.getId());
        newPhoto.setTitle(formPhoto.getTitle());
        newPhoto.setDescription(formPhoto.getDescription());
        newPhoto.setUrl(formPhoto.getUrl());
        return photoRepository.save(newPhoto);
    }
    public boolean isValidTitle(Photo formPhoto){
        if(formPhoto.getId() == null){
            return !photoRepository.existsByTitle(formPhoto.getTitle());
        }
        return !photoRepository.existsByTitleAndIdNot(formPhoto.getTitle(), formPhoto.getId());
    }
    public Photo updatePhoto(Photo formPhoto, Integer id) throws RuntimeException {
        Photo photoToUpdate = getById(id);
        photoToUpdate.setId(formPhoto.getId());
        photoToUpdate.setTitle(formPhoto.getTitle());
        photoToUpdate.setDescription(formPhoto.getDescription());
        photoToUpdate.setUrl(formPhoto.getUrl());
        return photoRepository.save(photoToUpdate);
    }

    public boolean deleteById(Integer id) throws RuntimeException {
        try {
            photoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
