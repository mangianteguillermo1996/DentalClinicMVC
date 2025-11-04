package com.dh.DentalClinicMVC.dao;

import com.dh.DentalClinicMVC.model.Address;
import com.dh.DentalClinicMVC.model.Patient;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class PatientDaoH2 implements IDao<Patient> {

    private static final String SQL_INSERT = "INSERT INTO PATIENTS (FIRST_NAME, LAST_NAME, EMAIL, CARD_IDENTITY, ADMISSION_OF_DATE, ADDRESS_ID) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_SELECT_ID = "SELECT * FROM PATIENTS WHERE FIRST_NAME = ?";

    private static final String SQL_UPDATE = "UPDATE PATIENTS SET FIRST_NAME=?, SET LAST_NAME=?, SET EMAIL=?, SET CARD_IDENTITY=?, SET ADMISSION_OF_DATE=?, SET ADDRESS_ID=?  WHERE ID=?";

    private static final String SQL_DELETE = "DELETE FROM PATIENTS WHERE ID = ?";

    private static final String SQL_FINDALL = "SELECT * FROM PATIENTS";

    private static final String SQL_SELECT_EMAIL = "SELECT * FROM PATIENTS WHERE EMAIL = ?";

    @Override
    public Patient save(Patient patient) {

        Connection connection = null;

        try {

            AddressDaoH2 addressDao = new AddressDaoH2();
            addressDao.save(patient.getAddress());

            connection = DB.getConnection();

            PreparedStatement ps = connection.prepareStatement(SQL_INSERT,  Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, patient.getFirstName());
            ps.setString(2, patient.getLastName());
            ps.setString(3, patient.getEmail());
            ps.setInt(4, patient.getCardIdentity());
            ps.setDate(5, Date.valueOf(patient.getAdmissionOfDate()));
            ps.setInt(6, patient.getAddress().getId());
            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            while(rs.next()) {
                patient.setId(rs.getInt(1));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return patient;
    }

    @Override
    public Patient findById(Integer id) {

        Connection connection = null;

        Patient patient = null;

        try {

            connection = DB.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            AddressDaoH2 addressDao = new AddressDaoH2();


            while(rs.next()) {

                Address address = addressDao.findById(rs.getInt(7));

                patient = new Patient(rs.getInt(1), rs.getString(2),rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDate(6).toLocalDate(), address);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return patient;
    }

    @Override
    public void update(Patient patient) {

        Connection connection = null;

        try {

            connection = DB.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_UPDATE);
            ps.setString(1, patient.getFirstName());
            ps.setString(2, patient.getLastName());
            ps.setInt(3, patient.getCardIdentity());
            ps.setDate(4, Date.valueOf(patient.getAdmissionOfDate()));
            ps.setInt(5, patient.getAddress().getId());
            ps.setInt(6, patient.getId());
            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void delete(Integer id) {

        Connection connection = null;

        try {

            connection = DB.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_DELETE);
            ps.setInt(1, id);
            ps.execute();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public List<Patient> findAll() {

        List<Patient> patients = new ArrayList<>();
        Patient patient = null;
        Connection connection = null;
        Address address;

        try {

            connection = DB.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_FINDALL);
            ResultSet rs = ps.executeQuery();

            AddressDaoH2 addressDao = new AddressDaoH2();
            while(rs.next()) {
                address = addressDao.findById(rs.getInt(7));
                patient = new Patient(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDate(6).toLocalDate(), address);
                patients.add(patient);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return patients;
    }

    @Override
    public Patient findByString(String value) {

        Connection connection = null;
        Patient patient  = null;
        Address address;

        try {

            connection = DB.getConnection();
            PreparedStatement ps = connection.prepareStatement(SQL_SELECT_EMAIL);
            ps.setString(1, value);
            ResultSet rs = ps.executeQuery();

            AddressDaoH2 addressDao = new AddressDaoH2();

            while(rs.next()) {
                address = addressDao.findById(rs.getInt(7));
                patient = new Patient(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5), rs.getDate(6).toLocalDate(), address);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return patient;
    }

}
