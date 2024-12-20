package com.example.personnes.Repository;

import com.example.personnes.models.Personne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonneRepository extends JpaRepository<Personne,Integer> {
    public Page<Personne> findByNom(String keyword, Pageable pageable);
}
