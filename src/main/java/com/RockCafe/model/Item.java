package com.RockCafe.model;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
public class Item 
{
   @Column(name = "item_id")
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @NotBlank(message = "O campo nome é obrigatório")

   @Size(min = 3, max = 100, message = "O campo nome deve ter entre 3 e 100 caracteres")
   private String name;

   @Size(max = 500, message = "A descrição deve ter no máximo 500 caracteres")
   private String itemdesc;

   @NotNull(message = "O campo preço é obrigatório")
   private Float price;

   private String imageid;

   public enum itemType {
      PERMANENTE, TEMPORARIO
   }

   @Enumerated(EnumType.STRING)
   private itemType itemType;

   @FutureOrPresent(message = "A data limite deve ser futura ou no presente")
   @DateTimeFormat(pattern = "yyyy-MM-dd")
   private LocalDate itemTime;

   public Float getPrice() 
   {
      return price;
   }

   public void setPrice(Float price) 
   {
      this.price = price;
   }

   public Long getId() 
   {
      return id;
   }

   public void setId(Long id) 
   {
      this.id = id;
   }

   public String getName() 
   {
      return name;
   }

   public void setName(String name) 
   {
      this.name = name;
   }

   public String getItemdesc() 
   {
      return itemdesc;
   }

   public void setItemdesc(String newItemDesc) 
   {
      this.itemdesc = newItemDesc;
   }

   public itemType getItemType() 
   {
      return itemType;
   }

   public void setItemType(itemType newItemType) 
   {
      this.itemType = newItemType;
   }

   public LocalDate getItemTime() {
      return itemTime;
   }

   public void setItemTime(LocalDate newItemTime) 
   {
      this.itemTime = newItemTime;
   }

   public String getImageid() 
   {
      return imageid;
   }

   public void setImageid(String newImageid) 
   {
      this.imageid = newImageid;
   }
}