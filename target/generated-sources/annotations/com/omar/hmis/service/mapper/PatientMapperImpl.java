package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.Department;
import com.omar.hmis.domain.Patient;
import com.omar.hmis.service.dto.PatientDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-11-14T05:15:40+0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_231 (Oracle Corporation)"
)
@Component
public class PatientMapperImpl implements PatientMapper {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Patient> toEntity(List<PatientDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Patient> list = new ArrayList<Patient>( dtoList.size() );
        for ( PatientDTO patientDTO : dtoList ) {
            list.add( toEntity( patientDTO ) );
        }

        return list;
    }

    @Override
    public List<PatientDTO> toDto(List<Patient> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<PatientDTO> list = new ArrayList<PatientDTO>( entityList.size() );
        for ( Patient patient : entityList ) {
            list.add( toDto( patient ) );
        }

        return list;
    }

    @Override
    public PatientDTO toDto(Patient patient) {
        if ( patient == null ) {
            return null;
        }

        PatientDTO patientDTO = new PatientDTO();

        patientDTO.setDepartmentName( patientDepartmentName( patient ) );
        patientDTO.setDepartmentId( patientDepartmentId( patient ) );
        patientDTO.setId( patient.getId() );
        patientDTO.setPatientId( patient.getPatientId() );
        patientDTO.setIsRegular( patient.isIsRegular() );

        return patientDTO;
    }

    @Override
    public Patient toEntity(PatientDTO patientDTO) {
        if ( patientDTO == null ) {
            return null;
        }

        Patient patient = new Patient();

        patient.setDepartment( departmentMapper.fromId( patientDTO.getDepartmentId() ) );
        patient.setId( patientDTO.getId() );
        patient.setPatientId( patientDTO.getPatientId() );
        patient.setIsRegular( patientDTO.isIsRegular() );

        return patient;
    }

    private String patientDepartmentName(Patient patient) {
        if ( patient == null ) {
            return null;
        }
        Department department = patient.getDepartment();
        if ( department == null ) {
            return null;
        }
        String name = department.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private Long patientDepartmentId(Patient patient) {
        if ( patient == null ) {
            return null;
        }
        Department department = patient.getDepartment();
        if ( department == null ) {
            return null;
        }
        Long id = department.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
