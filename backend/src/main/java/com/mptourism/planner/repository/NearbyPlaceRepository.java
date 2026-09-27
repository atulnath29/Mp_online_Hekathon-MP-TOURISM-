package com.mptourism.planner.repository;

import com.mptourism.planner.model.NearbyPlace;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NearbyPlaceRepository extends JpaRepository<NearbyPlace, Long> {
    List<NearbyPlace> findByLocationId(Long locationId);
}
