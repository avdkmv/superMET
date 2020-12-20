package com.unn.service;

import com.unn.model.Facility;
import java.util.List;
import java.util.Optional;

public interface IFacilityService {
    Optional<Facility> createFacility(Facility hospital);

    Optional<List<Facility>> findAllfacilities();
    Optional<Facility> findFacilityById(Long id);
}
