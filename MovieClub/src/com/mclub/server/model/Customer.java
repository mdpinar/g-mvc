/*
 * Copyleft 2010 Mustafa DUMLUPINAR
 *
 * Distributable under GPL license.
 * See terms of license at gnu.org.
 * http://www.gnu.org/licenses/gpl.html
 *
 * dumlupinar01@gmail.com
 * http://mdpinar.blogspot.com/
 *
 */
package com.mclub.server.model;

import java.io.Serializable;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.mclub.client.enums.AgeGroup;
import com.mclub.client.enums.CustomerGroup;
import com.mclub.client.enums.Sex;
import com.mclub.client.model.CustomerDTO;

/**
 * Aciklamalar icin client tarafinda ayni isimli 
 * sinifin javadoclarina bakiniz
 * 
 * @see CustomerDTO
 * 
 * @author mdpinar
 * 
 */
@Entity
@Table
public class Customer implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;

	@Column(length=50, nullable=false, unique=true)
	private String name;

	@Column(length=1)
	@Enumerated(EnumType.STRING)
	private CustomerGroup customerGroup;

	@Column(length=6)
	@Enumerated(EnumType.STRING)
	private AgeGroup ageGroup;

	@ManyToOne
	private Job job;

	@Column(length=6)
	@Enumerated(EnumType.STRING)
	private Sex sex;

	@Column(length=100)
	private String email;

	@Column(length=15)
	private String mobilePhone;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name="phone", column = @Column(name="homePhone")),
			@AttributeOverride(name="address", column = @Column(name="homeAddress")) })
	@AssociationOverrides({ @AssociationOverride(name="location", joinColumns = @JoinColumn(name="homeLocation_id")) })
	private Address homeAddress;

	@Embedded
	@AttributeOverrides({
			@AttributeOverride(name="phone", column = @Column(name="workPhone")),
			@AttributeOverride(name="address", column = @Column(name="workAddress")) })
	@AssociationOverrides({ @AssociationOverride(name = "location", joinColumns = @JoinColumn(name="workLocation_id")) })
	private Address workAddress;
	
	public Customer() {
		this.homeAddress = new Address();
		this.workAddress = new Address();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CustomerGroup getCustomerGroup() {
		return customerGroup;
	}

	public void setCustomerGroup(CustomerGroup customerGroup) {
		this.customerGroup = customerGroup;
	}

	public AgeGroup getAgeGroup() {
		return ageGroup;
	}

	public void setAgeGroup(AgeGroup ageGroup) {
		this.ageGroup = ageGroup;
	}

	public Job getJob() {
		return job;
	}

	public void setJob(Job job) {
		this.job = job;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public Address getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(Address homeAddress) {
		this.homeAddress = homeAddress;
	}

	public Address getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(Address workAddress) {
		this.workAddress = workAddress;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof Customer) {
			Customer model = (Customer) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
