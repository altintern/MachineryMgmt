package com.machinarymgmt.service.api.data;

import com.machinarymgmt.service.api.data.model.Make;
import com.machinarymgmt.service.api.data.model.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ModelRepository extends JpaRepository<Model, Long> {
    List<Model> findByMake(Make make);
    Model findByNameAndMake(String name, Make make);
    boolean existsByNameAndMake(String name, Make make);
    Model findByName(String name);
    boolean existsByName(String name);

}

