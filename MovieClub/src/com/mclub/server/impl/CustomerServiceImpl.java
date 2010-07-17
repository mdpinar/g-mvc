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
package com.mclub.server.impl;

import java.util.List;

import org.hibernate.Session;

import com.gmvc.client.meta.RetVal;
import com.gmvc.server.impl.CRUDImpl;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.service.CustomerService;
import com.mclub.server.model.Customer;
import com.mclub.server.model.Trans;

/**
 * Musteri Tanitimi servisi
 * 
 * @see CRUDImpl
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings("serial")
public class CustomerServiceImpl 
	extends	CRUDImpl<Customer, CustomerDTO> implements CustomerService {

	public CustomerServiceImpl() {
		super(Customer.class, CustomerDTO.class);
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 * </p>
	 * Silinecek musteriye ait hareket var mi bakilir, varsa silinmesine izin verilmez!
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public RetVal beforeRemove(Session session, Customer serverModel) {
		List<Trans> transList = 
			session.createQuery("from Trans t " +
								"where t.customer = :customer")
							.setParameter("customer", serverModel)
						.setFetchSize(1)
						.list();
		
		if (transList != null && transList.size() > 0) {
			//-1 degeri verildiginde mesajin icerigi i18n dosyasindan alinir, 
			//aksi durumda ileti oldugu gibi basilir
			RetVal result = new RetVal(-1); 
			result.setMessage("cantDeleteThisCustomer");
			return result;
		}
		
		return null;
	}

}
