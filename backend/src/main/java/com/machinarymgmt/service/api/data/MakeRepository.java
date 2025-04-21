package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Make;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MakeRepository extends JpaRepository<Make, Long> {
    Make findByName(String name);
    boolean existsByName(String name);
 

}

