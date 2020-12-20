package com.unn.service;

import java.util.List;
import java.util.Optional;

import com.unn.model.Facility;

public interface IFacilityService {
    Optional<Facility> createFacility(Facility hospital);

    Optional<List<Facility>> findAllfacilities();
    Optional<Facility> findFacilityById(Long id);
}
