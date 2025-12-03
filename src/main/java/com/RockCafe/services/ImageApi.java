package com.RockCafe.services;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.RockCafe.model.Image;
import com.RockCafe.repository.ImageRepository;

@RestController
@RequestMapping("/image")
public class ImageApi 
{
    private final ImageRepository repo;

    public ImageApi(ImageRepository repo) 
    {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Image> createNewImage(@RequestBody Image image)
    {
        if (repo.existsById(image.getId())) 
        {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(repo.save(image));
    }

    @GetMapping
    public ResponseEntity<List<Image>> list() 
    {
        java.util.List<Image> items = repo.findAll();
        
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Image> search(@PathVariable Long id) 
    {
        return repo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Image> update(@PathVariable Long id, @RequestBody Image dto) 
    {
        if (!repo.existsById(id)) 
        {
            return ResponseEntity.notFound().build();
        }
        
        Image image = repo.findById(id).orElse(null);
        if (image == null ) 
        {
            return ResponseEntity.badRequest().build();
        }
        
        image.setId(dto.getId());
        
        Image saved = repo.save(image);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) 
    {
        if (!repo.existsById(id)) 
        {
            return ResponseEntity.notFound().build();
        }
        
        Image image = repo.findById(id).orElse(null);

        if (image != null) 
        {
            return ResponseEntity.badRequest().build();
        }
        
        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
