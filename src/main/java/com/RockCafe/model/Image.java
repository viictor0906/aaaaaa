package com.RockCafe.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Image
{
   @Column(name = "image_id")
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private String urlimg;

   public Long getId() 
   {
    return id;
   }

   public void setId(Long id) 
   {
    this.id = id;
   }

   public String getUrlimg() 
   {
    return urlimg;
   }

   public void setUrlimg(String newUrlImg) 
   {
    this.urlimg = newUrlImg;
   }
}