package com.omar.hmis.domain;

import com.omar.hmis.domain.enumeration.RoomCategory;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Room.class)
public abstract class Room_ {

	public static volatile SingularAttribute<Room, Boolean> availablity;
	public static volatile SingularAttribute<Room, Long> id;
	public static volatile SingularAttribute<Room, RoomCategory> category;
	public static volatile SingularAttribute<Room, Long> rent;
	public static volatile SingularAttribute<Room, Department> department;
	public static volatile SingularAttribute<Room, String> roomId;

	public static final String AVAILABLITY = "availablity";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String RENT = "rent";
	public static final String DEPARTMENT = "department";
	public static final String ROOM_ID = "roomId";

}

