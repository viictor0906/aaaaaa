package com.RockCafe.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.RockCafe.model.Image;

public interface ImageRepository extends JpaRepository<Image, Long> 
{
    Optional<Image> findById(Long id);
    boolean existsById(Long id);
} 