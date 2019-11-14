package com.omar.hmis.domain;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Department.class)
public abstract class Department_ {

	public static volatile SingularAttribute<Department, String> address;
	public static volatile SingularAttribute<Department, String> name;
	public static volatile SingularAttribute<Department, String> details;
	public static volatile SingularAttribute<Department, Long> id;

	public static final String ADDRESS = "address";
	public static final String NAME = "name";
	public static final String DETAILS = "details";
	public static final String ID = "id";

}

