package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.*;
import com.omar.hmis.service.dto.BillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Bill} and its DTO {@link BillDTO}.
 */
@Mapper(componentModel = "spring", uses = {PatientMapper.class})
public interface BillMapper extends EntityMapper<BillDTO, Bill> {

    @Mapping(source = "patient.id", target = "patientId")
    BillDTO toDto(Bill bill);

    @Mapping(source = "patientId", target = "patient")
    Bill toEntity(BillDTO billDTO);

    default Bill fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bill bill = new Bill();
        bill.setId(id);
        return bill;
    }
}
