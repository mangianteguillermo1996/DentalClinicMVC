package com.dh.DentalClinicMVC.service;

import com.dh.DentalClinicMVC.dao.IDao;
import com.dh.DentalClinicMVC.model.Patient;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientService {

    private IDao<Patient> patientDao;

    public PatientService(IDao<Patient> patientDao) {
        this.patientDao = patientDao;
    }

    public Patient save(Patient patient) {
        patientDao.save(patient);
        return patient;
    }

    public Patient findById(Integer id) {
        return patientDao.findById(id);
    }

    public void update(Patient patient) {
        patientDao.update(patient);
    }

    public void delete(Integer id) {
        patientDao.delete(id);
    }

    public List<Patient> findAll() {
        return patientDao.findAll();
    }

    public Patient findByEmail(String email) {
        return patientDao.findByString(email);
    }

}
