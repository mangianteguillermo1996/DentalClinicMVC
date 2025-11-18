package com.dh.DentalClinicMVC.controller;

import com.dh.DentalClinicMVC.model.Appointment;
import com.dh.DentalClinicMVC.service.IAppointmentService;
import com.dh.DentalClinicMVC.service.IDentistService;
import com.dh.DentalClinicMVC.service.IPatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/turnos")
public class AppointmentController {

    private IAppointmentService appointmentService;
    private IDentistService dentistService;
    private IPatientService patientService;

    @Autowired
    public AppointmentController(IAppointmentService appointmentService,  IDentistService dentistService, IPatientService patientService) {
        this.appointmentService = appointmentService;
        this.dentistService = dentistService;
        this.patientService = patientService;
    }

    @GetMapping
    public ResponseEntity<List<Appointment>> findAll() {
        return ResponseEntity.ok(appointmentService.findAll());
    }

    @PostMapping
    public ResponseEntity<Appointment> save(@RequestBody Appointment appointment) {

        ResponseEntity<Appointment> response;

        if (dentistService.findById(appointment.getDentist().getId()).isPresent()
        && patientService.findById(appointment.getPatient().getId()).isPresent()) {
            response = ResponseEntity.ok(appointmentService.save(appointment));
        } else {
            response = ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return response;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> findById(@PathVariable Long id) {
        Optional<Appointment> appointmentToLookFor = appointmentService.findById(id);

        if (appointmentToLookFor.isPresent()) {
            return ResponseEntity.ok(appointmentToLookFor.get());
        }  else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping
    public ResponseEntity<String> update(@RequestBody Appointment appointment) {
        ResponseEntity<String> response;

        if (dentistService.findById(appointment.getDentist().getId()).isPresent()
           && patientService.findById(appointment.getPatient().getId()).isPresent()) {
            appointmentService.update(appointment);
            response = ResponseEntity.ok("Turno actualizado exitosamente");
        } else  {
            response = ResponseEntity.notFound().build();
        }
        return response;

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        ResponseEntity<String> response;
        if (dentistService.findById(id).isPresent()){
            appointmentService.delete(id);
            response = ResponseEntity.ok("Turno eliminado exitosamente");
        } else   {
            response = ResponseEntity.notFound().build();
        }
        return response;
    }

}
