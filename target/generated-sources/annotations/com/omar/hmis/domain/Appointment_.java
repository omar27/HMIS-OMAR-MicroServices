package com.omar.hmis.domain;

import com.omar.hmis.domain.enumeration.PatientStatus;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Appointment.class)
public abstract class Appointment_ {

	public static volatile SingularAttribute<Appointment, String> diseaseIdentified;
	public static volatile SingularAttribute<Appointment, String> testsSuggested;
	public static volatile SingularAttribute<Appointment, Bill> bill;
	public static volatile SingularAttribute<Appointment, AppointmentSchedule> appointmentSchedule;
	public static volatile SingularAttribute<Appointment, Long> id;
	public static volatile SingularAttribute<Appointment, PatientStatus> patientStatus;
	public static volatile SingularAttribute<Appointment, String> cureSuggested;

	public static final String DISEASE_IDENTIFIED = "diseaseIdentified";
	public static final String TESTS_SUGGESTED = "testsSuggested";
	public static final String BILL = "bill";
	public static final String APPOINTMENT_SCHEDULE = "appointmentSchedule";
	public static final String ID = "id";
	public static final String PATIENT_STATUS = "patientStatus";
	public static final String CURE_SUGGESTED = "cureSuggested";

}

