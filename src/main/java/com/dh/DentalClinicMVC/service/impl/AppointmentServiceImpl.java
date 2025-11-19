package com.dh.DentalClinicMVC.service.impl;

import com.dh.DentalClinicMVC.dto.AppointmentDTO;
import com.dh.DentalClinicMVC.entity.Appointment;
import com.dh.DentalClinicMVC.entity.Dentist;
import com.dh.DentalClinicMVC.entity.Patient;
import com.dh.DentalClinicMVC.repository.IAppointmentRepository;
import com.dh.DentalClinicMVC.service.IAppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentServiceImpl implements IAppointmentService {

    private IAppointmentRepository appointmentRepository;

    @Autowired
    public AppointmentServiceImpl(IAppointmentRepository appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }


    @Override
    public AppointmentDTO save(AppointmentDTO appointmentDTO) {
        //MAPEAR ENTIDADES COMO DTO
        //INSTANCIAR UN TURNO
        Appointment appointmentEntity = new Appointment();

        //INSTANCIAR UN PACIENTE
        Patient patientEntity = new Patient();
        patientEntity.setId(appointmentDTO.getPatientId());

        //INSTANCIAR UN ODONTOLOGO
        Dentist dentistEntity = new Dentist();
        dentistEntity.setId(appointmentDTO.getDentistId());

        //SETEAR EL PACIENTE Y EL ODONTOLOGO
        appointmentEntity.setPatient(patientEntity);
        appointmentEntity.setDentist(dentistEntity);

        //SETEAR LA FECHA
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date = LocalDate.parse(appointmentDTO.getDate(), formatter);
        appointmentEntity.setDate(date);

        //PERSISTIR EN BBDD
        appointmentRepository.save(appointmentEntity);

        //TURNO A DEVOLVER
        AppointmentDTO appointmentDTOReturn = new AppointmentDTO();

        appointmentDTOReturn.setId(appointmentEntity.getId());
        appointmentDTOReturn.setPatientId(appointmentEntity.getPatient().getId());
        appointmentDTOReturn.setDentistId(appointmentEntity.getDentist().getId());
        appointmentDTOReturn.setDate(appointmentEntity.getDate().toString());

        return appointmentDTOReturn;
    }

    @Override
    public Optional<AppointmentDTO> findById(Long id) {

        //BUSCAR LA ENTIDAD POR ID
        Optional<Appointment> appointmentToLookFor = appointmentRepository.findById(id);

        //CREAR OPTIONAL PARA CONVERTIR DESPUÉS
        Optional<AppointmentDTO> appointmentDTO = null;

        if (appointmentToLookFor.isPresent()) {
            //RECUPERAR LA ENTIDAD
            Appointment appointment = appointmentToLookFor.get();

            //SETEAR DTO
            AppointmentDTO appointmentDTOReturn = new AppointmentDTO();
            appointmentDTOReturn.setId(appointment.getId());
            appointmentDTOReturn.setPatientId(appointment.getPatient().getId());
            appointmentDTOReturn.setDentistId(appointment.getDentist().getId());
            appointmentDTOReturn.setDate(appointment.getDate().toString());

            //TRANSFORMAR EL OPTIONAL DTO
            appointmentDTO = Optional.of(appointmentDTOReturn);
        }

        return appointmentDTO;
    }

    @Override
    public AppointmentDTO update(AppointmentDTO appointmentDTO) throws Exception {

        //CONTROLO QUE EL TURNO EXISTA
        if (appointmentRepository.findById(appointmentDTO.getId()).isPresent()) {

            //BUSCAR Y GUARDAR LA ENTIDAD
            Optional<Appointment> appointmentEntity = appointmentRepository.findById(appointmentDTO.getId());

            //INSTANCIAR UN PACIENTE
            Patient patientEntity = new Patient();
            patientEntity.setId(appointmentDTO.getPatientId());

            //INSTANCIAR UN ODONTOLOGO
            Dentist dentistEntity = new Dentist();
            dentistEntity.setId(appointmentDTO.getDentistId());

            //SETEAR EL PACIENTE Y EL ODONTOLOGO
            appointmentEntity.get().setPatient(patientEntity);
            appointmentEntity.get().setDentist(dentistEntity);

            //SETEAR LA FECHA
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(appointmentDTO.getDate(), formatter);
            appointmentEntity.get().setDate(date);

            //PERSISTIR EN BBDD
            appointmentRepository.save(appointmentEntity.get());

            //RESPUESTA A DEVOLVER
            AppointmentDTO appointmentDTOReturn = new AppointmentDTO();
            appointmentDTOReturn.setId(appointmentEntity.get().getId());
            appointmentDTOReturn.setPatientId(appointmentEntity.get().getPatient().getId());
            appointmentDTOReturn.setDentistId(appointmentEntity.get().getDentist().getId());
            appointmentDTOReturn.setDate(appointmentEntity.get().getDate().toString());

            return appointmentDTOReturn;

        } else {
            throw new Exception("No se pudo actualizar el turno");
        }

    }

    @Override
    public void delete(Long id) {
        appointmentRepository.deleteById(id);
    }

    @Override
    public List<AppointmentDTO> findAll() {
        //TRAER ENTIDADES
        List<Appointment> appointments = appointmentRepository.findAll();

        List<AppointmentDTO> appointmentDTOList = new ArrayList<>();

        for (Appointment appointment : appointments) {
            AppointmentDTO appointmentDTO = new AppointmentDTO();

            //CARGAR DATOS
            appointmentDTO.setId(appointment.getId());
            appointmentDTO.setPatientId(appointment.getPatient().getId());
            appointmentDTO.setDentistId(appointment.getDentist().getId());
            appointmentDTO.setDate(appointment.getDate().toString());

            //AGREGAR A LISTA
            appointmentDTOList.add(appointmentDTO);
        }

        return appointmentDTOList;
    }
}
