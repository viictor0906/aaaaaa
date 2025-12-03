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

import com.RockCafe.model.Item;
import com.RockCafe.repository.ItemRepository;

@RestController
@RequestMapping("/item")
public class ItemApi 
{
    private final ItemRepository repo;

    public ItemApi(ItemRepository repo) 
    {
        this.repo = repo;
    }

    @PostMapping
    public ResponseEntity<Item> createNewItem(@RequestBody Item item)
    {
        if(repo.existsByName(item.getName())) 
        {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(repo.save(item));
    }

    @GetMapping
    public ResponseEntity<List<Item>> list() 
    {
        java.util.List<Item> items = repo.findAll();
        
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> search(@PathVariable Long id) 
    {
        return repo.findById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> update(@PathVariable Long id, @RequestBody Item dto) 
    {
        if (!repo.existsById(id)) 
        {
            return ResponseEntity.notFound().build();
        }
        
        Item item = repo.findById(id).orElse(null);
        if (item == null ) 
        {
            return ResponseEntity.badRequest().build();
        }
        
        item.setName(dto.getName());
        
        Item saved = repo.save(item);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remove(@PathVariable Long id) 
    {
        if (!repo.existsById(id)) 
        {
            return ResponseEntity.notFound().build();
        }
        
        Item item = repo.findById(id).orElse(null);

        if (item != null) 
        {
            return ResponseEntity.badRequest().build();
        }
        
        repo.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}
