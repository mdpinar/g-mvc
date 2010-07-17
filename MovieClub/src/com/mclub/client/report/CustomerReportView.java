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

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.gmvc.client.base.AbstractReportView;
import com.gmvc.client.base.IReportController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.ImageItem;
import com.gmvc.client.util.InstantComponent;
import com.gmvc.client.util.Tags;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.model.JobDTO;
import com.mclub.client.model.LocationDTO;
import com.mclub.client.util.GUIHelper;

/**
 * Musteri Listesi raporunun View'i
 * 
 * @see AbstractReportView
 * 
 * @author mdpinar
 * 
 */
public class CustomerReportView extends AbstractReportView {

	/**
	 * Tanimli meslekler combosu
	 * 
	 */
	protected SimpleComboBox<JobDTO> job;

	/**
	 * Ev bolgesi secimi icin tanimli bolgeler combosu
	 * 
	 */
	protected SimpleComboBox<LocationDTO> homeLocation;

	/**
	 * Isim
	 * 
	 */
	private TextField<String> name;
	
	/**
	 * Cinsiyet
	 * 
	 */
	private ComboBox<ImageItem> sexCombo;
	
	/**
	 * Musteri gurubu
	 * 
	 */
	private ComboBox<ImageItem> customerGroup;
	
	/**
	 * Yas gurubu
	 * 
	 */
	private ComboBox<ImageItem> ageGroup;

	public CustomerReportView(IReportController controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getReportName() {
		return "CustomerReport";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void buildPanel() {
		job = InstantComponent.getStandartCombo(Tags.get("job"));
		sexCombo = GUIHelper.getComboSex();
		homeLocation = InstantComponent.getStandartCombo(Tags.get("homeLocation"));
		customerGroup = GUIHelper.getComboCustomerGroup();
		ageGroup = GUIHelper.getComboAgeGroup();

		name = new TextField<String>();
		name.setFieldLabel(Tags.get("name"));
		name.setName("name");
		
		getController().fireEvent(new Event(SpecialEvent.LoadJob));
		getController().fireEvent(new Event(SpecialEvent.LoadLocation));

		addWidget(name);
		addWidget(job);
		addWidget(sexCombo);
		addWidget(homeLocation);
		addWidget(customerGroup);
		addWidget(ageGroup);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQueryString() {
		StringBuilder queryBuilder = new StringBuilder("");

		if (name.getValue() != null && ! name.getValue().equals("")) {
			queryBuilder.append(" and c.name >= '");
			queryBuilder.append(name.getValue());
			queryBuilder.append("'");
		}

		if (job.getValue() != null && job.getSimpleValue() != null) {
			queryBuilder.append(" and job_id = ");
			queryBuilder.append(job.getSimpleValue().getId());
		}

		if (sexCombo.getValue() != null) {
			queryBuilder.append(" and sex = '");
			queryBuilder.append(sexCombo.getValue().getEnumVal().toString());
			queryBuilder.append("'");
		}

		if (homeLocation.getValue() != null && homeLocation.getSimpleValue() != null) {
			queryBuilder.append(" and homeLocation_id = ");
			queryBuilder.append(homeLocation.getSimpleValue().getId());
		}

		if (customerGroup.getValue() != null) {
			queryBuilder.append(" and customerGroup = '");
			queryBuilder.append(customerGroup.getValue().getEnumVal().toString());
			queryBuilder.append("'");
		}

		if (ageGroup.getValue() != null) {
			queryBuilder.append(" and ageGroup = '");
			queryBuilder.append(ageGroup.getValue().getEnumVal().toString());
			queryBuilder.append("'");
		}

		return queryBuilder.toString();
	}

}
