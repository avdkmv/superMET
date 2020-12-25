package com.unn.controller;

import java.util.List;
import com.unn.model.Doctor;
import com.unn.model.Facility;
import com.unn.service.impl.FacilityService;
import com.unn.service.impl.ResponseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/facility")
@RequiredArgsConstructor
public class FacilityController {
    private final FacilityService facilityService;
    private final ResponseService responseService;

    @PostMapping("/create")
    public ResponseEntity<Facility> createFacility(@RequestBody Facility facility) {
        return responseService.handlePostResponse(facilityService.createFacility(facility));
    }

    @GetMapping("/all")
    public ResponseEntity<List<Facility>> getAllFacilities() {
        return responseService.handleGetResponse(facilityService.findAllfacilities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Doctor>> getFacilityDoctors(@PathVariable("id") Long id) {
        return responseService.handleGetResponse(facilityService.findDoctorsInFacility(id));
    }
}
