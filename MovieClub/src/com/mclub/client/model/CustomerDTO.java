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
package com.mclub.client.model;

import com.gmvc.client.base.IModel;
import com.mclub.client.enums.AgeGroup;
import com.mclub.client.enums.CustomerGroup;
import com.mclub.client.enums.Sex;

/**
 * Musteri tanitim modeli
 * 
 * @author mdpinar
 * 
 */
public class CustomerDTO implements IModel {

	private static final long serialVersionUID = 1L;

	private Long id;

	/**
	 * Isim alani
	 * 
	 */
	private String name;

	/**
	 * Musteri gurubu
	 * 
	 */
	private CustomerGroup customerGroup;

	/**
	 *  Yas gurubu
	 * 
	 */
	private AgeGroup ageGroup;

	/**
	 *  Meslek iliski alani
	 * 
	 */
	private JobDTO job;

	/**
	 * Cinsiyet alani
	 * 
	 */
	private Sex sex;

	/**
	 * e-posta alani
	 * 
	 */
	private String email;

	/**
	 * Cep telefonu alani
	 * 
	 */
	private String mobilePhone;

	/**
	 * Ev adresi alani (gomulu)
	 * 
	 */
	private AddressDTO homeAddress;

	/**
	 * Is adresi alani (gomulu)
	 * 
	 */
	private AddressDTO workAddress;

	public CustomerDTO() {
		this.homeAddress = new AddressDTO();
		this.workAddress = new AddressDTO();
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

	public JobDTO getJob() {
		return job;
	}

	public void setJob(JobDTO job) {
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

	public AddressDTO getHomeAddress() {
		return homeAddress;
	}

	public void setHomeAddress(AddressDTO homeAddress) {
		this.homeAddress = homeAddress;
	}

	public AddressDTO getWorkAddress() {
		return workAddress;
	}

	public void setWorkAddress(AddressDTO workAddress) {
		this.workAddress = workAddress;
	}

	public String getSuggestText() {
		return this.name + " - " + this.mobilePhone;
	}
	
	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj != null && obj instanceof CustomerDTO) {
			CustomerDTO model = (CustomerDTO) obj;
			if (model.getId() != null && model.getId().equals(this.id)) {
				return true;
			}
		}
		return super.equals(obj);
	}

}
