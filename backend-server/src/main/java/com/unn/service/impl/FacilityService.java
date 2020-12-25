package com.unn.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.unn.model.Doctor;
import com.unn.model.Facility;
import com.unn.repository.FacilityRepo;
import com.unn.service.IFacilityService;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FacilityService implements IFacilityService {
    private final FacilityRepo facilityRepo;

    private final ValidationService validation;

    @Override
    public Optional<List<Facility>> findAllfacilities() {
        return Optional.of(facilityRepo.findAll());
    }

    @Override
    public Optional<Facility> createFacility(Facility facility) {
        if (!validation.validateFacilityCreation(facility)) {
            return Optional.empty();
        }

        return Optional.of(facilityRepo.save(facility));
    }

    @Override
    public Optional<Facility> findFacilityById(Long id) {
        return facilityRepo.findById(id);
    }

    public Optional<List<Doctor>> findDoctorsInFacility(Long facilityId) {
        Optional<Facility> facility = findFacilityById(facilityId);
        List<Doctor> doctors = new ArrayList<>();

        facility.ifPresent(f -> f.getDoctors().forEach(d -> doctors.add(d)));
        return Optional.ofNullable(doctors);
    }
}
