package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.Bill;
import com.omar.hmis.domain.Patient;
import com.omar.hmis.service.dto.BillDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2019-11-14T05:15:39+0500",
    comments = "version: 1.3.1.Final, compiler: javac, environment: Java 1.8.0_231 (Oracle Corporation)"
)
@Component
public class BillMapperImpl implements BillMapper {

    @Autowired
    private PatientMapper patientMapper;

    @Override
    public List<Bill> toEntity(List<BillDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Bill> list = new ArrayList<Bill>( dtoList.size() );
        for ( BillDTO billDTO : dtoList ) {
            list.add( toEntity( billDTO ) );
        }

        return list;
    }

    @Override
    public List<BillDTO> toDto(List<Bill> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<BillDTO> list = new ArrayList<BillDTO>( entityList.size() );
        for ( Bill bill : entityList ) {
            list.add( toDto( bill ) );
        }

        return list;
    }

    @Override
    public BillDTO toDto(Bill bill) {
        if ( bill == null ) {
            return null;
        }

        BillDTO billDTO = new BillDTO();

        billDTO.setPatientId( billPatientId( bill ) );
        billDTO.setId( bill.getId() );
        billDTO.setDoctorFee( bill.getDoctorFee() );
        billDTO.setMedicineCharges( bill.getMedicineCharges() );
        billDTO.setTestsFee( bill.getTestsFee() );
        billDTO.setRoomCharges( bill.getRoomCharges() );
        billDTO.setOtherCharges( bill.getOtherCharges() );
        billDTO.setTotalBill( bill.getTotalBill() );
        billDTO.setPaidStatus( bill.getPaidStatus() );

        return billDTO;
    }

    @Override
    public Bill toEntity(BillDTO billDTO) {
        if ( billDTO == null ) {
            return null;
        }

        Bill bill = new Bill();

        bill.setPatient( patientMapper.fromId( billDTO.getPatientId() ) );
        bill.setId( billDTO.getId() );
        bill.setDoctorFee( billDTO.getDoctorFee() );
        bill.setMedicineCharges( billDTO.getMedicineCharges() );
        bill.setTestsFee( billDTO.getTestsFee() );
        bill.setRoomCharges( billDTO.getRoomCharges() );
        bill.setOtherCharges( billDTO.getOtherCharges() );
        bill.setTotalBill( billDTO.getTotalBill() );
        bill.setPaidStatus( billDTO.getPaidStatus() );

        return bill;
    }

    private Long billPatientId(Bill bill) {
        if ( bill == null ) {
            return null;
        }
        Patient patient = bill.getPatient();
        if ( patient == null ) {
            return null;
        }
        Long id = patient.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
