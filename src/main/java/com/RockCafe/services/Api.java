package com.RockCafe.services;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.RockCafe.model.Item;
import com.RockCafe.model.Image;
import com.RockCafe.repository.ImageRepository;
import com.RockCafe.repository.ItemRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@Controller
@RequestMapping("/api")
public class Api 
{
    private final ItemRepository itemRepository;
    private final ImageRepository imageRepository;

    public Api(ItemRepository itemRepository, ImageRepository imageRepository) 
    {
        this.itemRepository = itemRepository;
        this.imageRepository = imageRepository;
    }

    @GetMapping("/list_items")
    public String listItems(Model model, @RequestParam(required = false) String name, @RequestParam(required = false) Float price, HttpServletRequest req) 
    {
        var items = itemRepository.findAll();

        if(name != null && !name.trim().isEmpty()) 
        {
            items = items.stream().filter(t -> t.getName().toLowerCase().contains(name.toLowerCase())).toList();
        }

        if(price != null)
        {
            items = items.stream().filter(t -> t.getPrice() != null && Math.abs(t.getPrice() - price) < 0.0001).toList();
        }

        model.addAttribute("items", items);
        model.addAttribute("name", name);
        model.addAttribute("price", price);
        model.addAttribute("images", imageRepository.findAll());

        return "items_list";
    }

    @GetMapping("/list_images")
    public String listImages(Model model, @RequestParam(required = false) Long id, HttpServletRequest req) 
    {
        var images = imageRepository.findAll();
        if(id != null) {
            images = images.stream().filter(t -> t.getId() == id).toList();
        }
        model.addAttribute("images", images);
        model.addAttribute("id", id);
        return "images_list";
    }

    @GetMapping("/new_item")
    public String novaTarefa(Model model) 
    {
        model.addAttribute("item", new Item());
        // Garante que a lista de imagens vá para o dropdown
        model.addAttribute("images", imageRepository.findAll()); 
        return "new_item";
    }

    @GetMapping("/new_image")
    public String novaImagem(Model model) 
    {
        model.addAttribute("image", new Image());
        model.addAttribute("images", imageRepository.findAll());
        return "new_image";
    }

    @PostMapping("/save_item")
    public String saveItem(@Valid @ModelAttribute("item") Item item, BindingResult br, Model model, RedirectAttributes ra) 
    {
        if(br.hasErrors()) 
        {
            model.addAttribute("item", item);
            model.addAttribute("images", imageRepository.findAll());
            model.addAttribute("erros", "Erro ao salvar item, preencha os campos corretamente.");
            return "new_item";
        }

        ra.addFlashAttribute("sucesso", "Item salvo com sucesso!");
        itemRepository.save(item);
        return "redirect:/api/list_items";
    }

    @PostMapping("/save_image")
    public String saveImage(@Valid @ModelAttribute("image") Image image, BindingResult br, Model model, RedirectAttributes ra) 
    {
        if (br.hasErrors()) 
        {
            model.addAttribute("image", image);
            model.addAttribute("erros", "Erro ao salvar imagem.");
            return "new_image";
        }
        ra.addFlashAttribute("sucesso", "Image salva com sucesso!");
        imageRepository.save(image);
        return "redirect:/api/list_images";
    }

    @PostMapping("/{id}/deleteItem")
    public String deleteItem(@PathVariable("id") Long id) 
    {
        itemRepository.deleteById(id);
        return "redirect:/api/list_items";
    }

    // --- FUNÇÃO PARA DELETAR IMAGEM ---
    @PostMapping("/{id}/deleteImage")
    public String deleteImage(@PathVariable("id") Long id) 
    {
        // Nota: Isso deleta do banco, mas idealmente deveria deletar o arquivo da pasta também
        imageRepository.deleteById(id);
        return "redirect:/api/list_images";
    }

    @GetMapping("/{id}/editItem")
    public String editItem(@PathVariable("id") Long id, Model model) 
    {
        var item = itemRepository.findById(id);
        if(item.isEmpty()) 
        {
            return "redirect:/api/list_items";
        }
        model.addAttribute("item", item.get());
        model.addAttribute("images", imageRepository.findAll());
        return "new_item";
    }
}