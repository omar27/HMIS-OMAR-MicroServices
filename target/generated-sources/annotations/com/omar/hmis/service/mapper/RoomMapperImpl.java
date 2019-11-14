package com.omar.hmis.service.mapper;

import com.omar.hmis.domain.Department;
import com.omar.hmis.domain.Room;
import com.omar.hmis.service.dto.RoomDTO;
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
public class RoomMapperImpl implements RoomMapper {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Room> toEntity(List<RoomDTO> dtoList) {
        if ( dtoList == null ) {
            return null;
        }

        List<Room> list = new ArrayList<Room>( dtoList.size() );
        for ( RoomDTO roomDTO : dtoList ) {
            list.add( toEntity( roomDTO ) );
        }

        return list;
    }

    @Override
    public List<RoomDTO> toDto(List<Room> entityList) {
        if ( entityList == null ) {
            return null;
        }

        List<RoomDTO> list = new ArrayList<RoomDTO>( entityList.size() );
        for ( Room room : entityList ) {
            list.add( toDto( room ) );
        }

        return list;
    }

    @Override
    public RoomDTO toDto(Room room) {
        if ( room == null ) {
            return null;
        }

        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setDepartmentId( roomDepartmentId( room ) );
        roomDTO.setId( room.getId() );
        roomDTO.setCategory( room.getCategory() );
        roomDTO.setRent( room.getRent() );
        roomDTO.setRoomId( room.getRoomId() );
        roomDTO.setAvailablity( room.isAvailablity() );

        return roomDTO;
    }

    @Override
    public Room toEntity(RoomDTO roomDTO) {
        if ( roomDTO == null ) {
            return null;
        }

        Room room = new Room();

        room.setDepartment( departmentMapper.fromId( roomDTO.getDepartmentId() ) );
        room.setId( roomDTO.getId() );
        room.setCategory( roomDTO.getCategory() );
        room.setRent( roomDTO.getRent() );
        room.setRoomId( roomDTO.getRoomId() );
        room.setAvailablity( roomDTO.isAvailablity() );

        return room;
    }

    private Long roomDepartmentId(Room room) {
        if ( room == null ) {
            return null;
        }
        Department department = room.getDepartment();
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
