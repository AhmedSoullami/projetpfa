package com.siteEcommerce.siteEcommerceTapis.controllers;

import com.siteEcommerce.siteEcommerceTapis.dto.TapisDto;
import com.siteEcommerce.siteEcommerceTapis.entities.ImageModel;
import com.siteEcommerce.siteEcommerceTapis.entities.Tapis;
import com.siteEcommerce.siteEcommerceTapis.services.TapisImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.awt.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@CrossOrigin("*")
public class TapisController {
    @Autowired
    private TapisImpl tapisImpl;
    @PostMapping(value = "/ajouterTapis",consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Tapis saveProduct(@RequestPart("tapis") Tapis tapis, @RequestPart("imageFile") MultipartFile[] file){

        try {
            Set<ImageModel>images=uploadImage(file);
            tapis.setImageModels(images);
           return tapisImpl.addTapis(tapis);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }

    }
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFile) throws IOException{
        Set<ImageModel> imageModels=new HashSet<>();
        for (MultipartFile file:multipartFile){
            ImageModel imageModel=new ImageModel(
            file.getOriginalFilename(),
            file.getContentType(),
            file.getBytes());
            imageModels.add(imageModel);

        }
        return imageModels;
    }
    @GetMapping("/getAllTapis")
    public List<Tapis> getAllTapis(){
        return tapisImpl.getAll();
    }
    //supprimer par id tapis
    @DeleteMapping("/supprimerTapis/{id}")
    public void deleteTapisByIdTapis(@PathVariable Long id){
        tapisImpl.deleteTapis(id);
    }
    @GetMapping("/getTapisByIdTapis/{id}")
    public Tapis getTapisById(@PathVariable Long id){
        return tapisImpl.getTapis(id);
    }





}
