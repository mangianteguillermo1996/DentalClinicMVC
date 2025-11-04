package com.dh.DentalClinicMVC.controller;

import org.springframework.ui.Model;
import com.dh.DentalClinicMVC.model.Patient;
import com.dh.DentalClinicMVC.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/patient")
public class PatientController {

    private PatientService patientService;

    @Autowired
    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping
    public String findPatientByEmail(Model model,@RequestParam("email") String email) {
        Patient patient = patientService.findByEmail(email);
        model.addAttribute("firstName", patient.getFirstName());
        model.addAttribute("lastName", patient.getLastName());
        return "index";
    }

}
