package com.omar.hmis.domain;

import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(InPatient.class)
public abstract class InPatient_ {

	public static volatile SingularAttribute<InPatient, LocalDate> dischargeDate;
	public static volatile SingularAttribute<InPatient, Patient> patient;
	public static volatile SingularAttribute<InPatient, LocalDate> admitDate;
	public static volatile SingularAttribute<InPatient, Staff> suggestedBy;
	public static volatile SingularAttribute<InPatient, Long> id;
	public static volatile SingularAttribute<InPatient, Room> room;

	public static final String DISCHARGE_DATE = "dischargeDate";
	public static final String PATIENT = "patient";
	public static final String ADMIT_DATE = "admitDate";
	public static final String SUGGESTED_BY = "suggestedBy";
	public static final String ID = "id";
	public static final String ROOM = "room";

}

