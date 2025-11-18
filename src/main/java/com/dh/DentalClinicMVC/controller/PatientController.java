package com.dh.DentalClinicMVC.controller;


import com.dh.DentalClinicMVC.model.Patient;
import com.dh.DentalClinicMVC.service.IPatientService;
import com.dh.DentalClinicMVC.service.impl.PatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patient")
public class PatientController {

    private IPatientService patientService;

    @Autowired
    public PatientController(IPatientService patientService) {
        this.patientService = patientService;
    }

    //AGREGAR UN PACIENTE
    @PostMapping
    public Patient save(@RequestBody Patient patient) {
        return patientService.save(patient);
    }

    //EDITAR UN PACIENTE
    @PutMapping
    public void update(@RequestBody Patient patient) {
        patientService.update(patient);
    }

    @GetMapping
    public List<Patient> findAll() {
        return patientService.findAll();
    }

}
