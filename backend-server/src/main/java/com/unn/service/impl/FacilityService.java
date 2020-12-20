package com.unn.service.impl;

import com.unn.model.Facility;
import com.unn.repository.FacilityRepo;
import com.unn.service.IFacilityService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FacilityService implements IFacilityService {
    private final FacilityRepo facilityRepo;

    @Override
    public Optional<List<Facility>> findAllfacilities() {
        return Optional.of(facilityRepo.findAll());
    }

    @Override
    public Optional<Facility> createFacility(Facility facility) {
        facilityRepo.save(facility);
        return Optional.of(facility);
    }

    @Override
    public Optional<Facility> findFacilityById(Long id) {
        return facilityRepo.findById(id);
    }
}
