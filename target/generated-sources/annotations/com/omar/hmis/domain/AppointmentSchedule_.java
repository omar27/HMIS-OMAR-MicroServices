package com.omar.hmis.domain;

import com.omar.hmis.domain.enumeration.AppointmentScheduleStatus;
import java.time.LocalDate;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(AppointmentSchedule.class)
public abstract class AppointmentSchedule_ {

	public static volatile SingularAttribute<AppointmentSchedule, Patient> patient;
	public static volatile SingularAttribute<AppointmentSchedule, LocalDate> scheduledDate;
	public static volatile SingularAttribute<AppointmentSchedule, Staff> staff;
	public static volatile SingularAttribute<AppointmentSchedule, Staff> scheduledBy;
	public static volatile SingularAttribute<AppointmentSchedule, Long> id;
	public static volatile SingularAttribute<AppointmentSchedule, AppointmentScheduleStatus> status;

	public static final String PATIENT = "patient";
	public static final String SCHEDULED_DATE = "scheduledDate";
	public static final String STAFF = "staff";
	public static final String SCHEDULED_BY = "scheduledBy";
	public static final String ID = "id";
	public static final String STATUS = "status";

}

