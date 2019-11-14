package com.omar.hmis.domain;

import com.omar.hmis.domain.enumeration.BillPaidStatus;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Bill.class)
public abstract class Bill_ {

	public static volatile SingularAttribute<Bill, BillPaidStatus> paidStatus;
	public static volatile SingularAttribute<Bill, Double> totalBill;
	public static volatile SingularAttribute<Bill, Double> roomCharges;
	public static volatile SingularAttribute<Bill, Double> testsFee;
	public static volatile SingularAttribute<Bill, Double> otherCharges;
	public static volatile SingularAttribute<Bill, Patient> patient;
	public static volatile SingularAttribute<Bill, Double> medicineCharges;
	public static volatile SingularAttribute<Bill, Long> id;
	public static volatile SingularAttribute<Bill, Double> doctorFee;

	public static final String PAID_STATUS = "paidStatus";
	public static final String TOTAL_BILL = "totalBill";
	public static final String ROOM_CHARGES = "roomCharges";
	public static final String TESTS_FEE = "testsFee";
	public static final String OTHER_CHARGES = "otherCharges";
	public static final String PATIENT = "patient";
	public static final String MEDICINE_CHARGES = "medicineCharges";
	public static final String ID = "id";
	public static final String DOCTOR_FEE = "doctorFee";

}

