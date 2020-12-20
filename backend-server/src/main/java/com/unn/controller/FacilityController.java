package com.unn.controller;

import com.unn.model.Doctor;
import com.unn.model.Facility;
import com.unn.service.impl.FacilityService;
import com.unn.service.impl.ValidationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;
    private final ValidationService validationService;

    @PostMapping("/registration")
    public ResponseEntity<Facility> registerFacility(@RequestBody Facility facility) {
        if (validationService.validateFacilityCreation(facility)) {
            facilityService.createFacility(facility);
            return ResponseEntity.ok(facility);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/facilities")
    public ResponseEntity<List<Facility>> getAllFacilities() {
        Optional<List<Facility>> facilities = facilityService.findAllfacilities();
        if (facilities.isPresent()) {
            return ResponseEntity.ok(facilities.get());
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Doctor>> getFacilityDoctors(@PathVariable("id") Long id) {
        Optional<Facility> facility = facilityService.findFacilityById(id);
        if (facility.isPresent()) {
            return ResponseEntity.ok(new ArrayList<>(facility.get().getDoctorIds()));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }
}
