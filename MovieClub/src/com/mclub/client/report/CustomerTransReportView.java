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
package com.mclub.client.report;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.DateField;
import com.gmvc.client.base.AbstractReportView;
import com.gmvc.client.base.IReportController;
import com.gmvc.client.util.Tags;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.mclub.client.util.GUIHelper;
import com.mclub.client.util.General;

/**
 * Musteri Hareket raporunun View'i
 * 
 * @see AbstractReportView
 * 
 * @author mdpinar
 * 
 */
public class CustomerTransReportView extends AbstractReportView {

	/**
	 * Musteri secim combosu
	 * 
	 */
	private ComboBox<BeanModel> customer;
	
	/**
	 * Raporun alinacagi Ilk tarih, verilmek zorunda degil
	 */
	private DateField firstDate;
	
	/**
	 * Raporun alinabilecegi son tarih, verilmek zorunda degil
	 */
	private DateField lastDate;

	public CustomerTransReportView(IReportController controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getReportName() {
		return "CustomerTransReport";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void buildPanel() {
		customer = GUIHelper.getCustomerSuggestCombo();
		addWidget(customer);
		
		firstDate = new DateField();
		firstDate.setFieldLabel(Tags.get("firstDate"));
		firstDate.getPropertyEditor().setFormat(DateTimeFormat.getFormat("dd-MM-yyyy"));
		addWidget(firstDate);
		
		lastDate = new DateField();
		lastDate.setFieldLabel(Tags.get("lastDate"));
		lastDate.getPropertyEditor().setFormat(DateTimeFormat.getFormat("dd-MM-yyyy"));
		addWidget(lastDate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQueryString() {
		StringBuilder queryBuilder = new StringBuilder("");

		if (customer.getValue() != null) {
			queryBuilder.append(" and c.id = ");
			queryBuilder.append(customer.getValue().get("id"));
		}

		if (firstDate.getValue() != null) {
			queryBuilder.append(" and transDate >= '");
			queryBuilder.append(General.formatDate(firstDate.getValue()));
			queryBuilder.append("'");
		}

		if (lastDate.getValue() != null) {
			queryBuilder.append(" and transDate <= '");
			queryBuilder.append(General.formatDate(lastDate.getValue()));
			queryBuilder.append("'");
		}

		return queryBuilder.toString();
	}

}
