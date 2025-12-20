package com.springsecurity.Repository;

import com.springsecurity.entities.PlaceName;
import com.springsecurity.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<PlaceName,Long> {

    // Optional<PlaceName> findByPlacename(String placename);

    Optional<PlaceName> findById(Long placeId);

    Optional<PlaceName> findByUser(User userId);
    //PlaceName findByUser(User userId);
}
