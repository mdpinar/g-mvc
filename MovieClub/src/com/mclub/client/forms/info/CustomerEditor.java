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
package com.mclub.client.forms.info;

import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.gmvc.client.base.AbstractEditor;
import com.gmvc.client.base.IController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.ImageItem;
import com.gmvc.client.util.InstantComponent;
import com.gmvc.client.util.Tags;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.enums.AgeGroup;
import com.mclub.client.enums.CustomerGroup;
import com.mclub.client.enums.Sex;
import com.mclub.client.image.Images;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.model.JobDTO;
import com.mclub.client.model.LocationDTO;
import com.mclub.client.util.GUIHelper;

/**
 * Musteri Tanitimi, Editor sinifi
 * 
 * @see AbstractEditor
 * 
 * @author mdpinar
 * 
 */
public class CustomerEditor extends AbstractEditor<CustomerDTO> {

	/**
	 * Meslek secim combosu, kontrolorden erisiliyor
	 * 
	 */
	protected SimpleComboBox<JobDTO> job;
	
	/**
	 * Ev Bolgesi secim combosu, kontrolorden erisiliyor
	 * 
	 */
	protected SimpleComboBox<LocationDTO> homeLocation;
	
	/**
	 * Is Bolgesi secim combosu, kontrolorden erisiliyor
	 * 
	 */
	protected SimpleComboBox<LocationDTO> workLocation;

	/**
	 * Cep telefonu alani
	 * 
	 */
	private TextField<String> mobilePhoneText;
	
	/**
	 * Ev telefonu alani
	 * 
	 */
	private TextField<String> homePhoneText;
	
	/**
	 * Is telefonu alani
	 * 
	 */
	private TextField<String> workPhoneText;
	
	/**
	 * Ev adresi alani
	 * 
	 */
	private TextArea homeAddress;
	
	/**
	 * Is adresi alani
	 * 
	 */
	private TextArea workAddress;
	
	/**
	 * Musteri gurubu secim combosu
	 * 
	 */
	private ComboBox<ImageItem> customerGroup;
	
	/**
	 * Isim alani
	 * 
	 */
	private TextField<String> nameText;
	
	/**
	 * e-posta alani
	 * 
	 */
	private TextField<String> emailText;

	/**
	 * Yas gurubu secim combosu
	 * 
	 */
	private ComboBox<ImageItem> ageGroup;
	
	/**
	 * Cinsiyet secim combosu
	 * 
	 */
	private ComboBox<ImageItem> sexCombo;

	public CustomerEditor(IController<CustomerDTO> controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public CustomerDTO getNewModel() {
		return new CustomerDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Size getSize() {
		return new Size(450, 460);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String isValid() {
		StringBuilder sb = new StringBuilder();
		if (nameText.getValue() == null || nameText.getValue().isEmpty())
			sb.append(Tags.get("nameCanNotBeEmpty"));
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("customerBrowserTitle");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<?> getFirstField() {
		return nameText;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pushFields() {
		model.setName(nameText.getValue());
		model.setMobilePhone(mobilePhoneText.getValue());
		model.setEmail(emailText.getValue());

		model.getHomeAddress().setPhone(homePhoneText.getValue());
		model.getHomeAddress().setAddress(homeAddress.getValue());
		model.getHomeAddress().setLocation(
				homeLocation.getValue() != null ? homeLocation.getSimpleValue() : null);

		model.getWorkAddress().setPhone(workPhoneText.getValue());
		model.getWorkAddress().setAddress(workAddress.getValue());
		model.getWorkAddress().setLocation(
				workLocation.getValue() != null ? workLocation.getSimpleValue() : null);

		model.setJob(job.getValue() != null ? (JobDTO) job.getSimpleValue() : null);
		
		model.setAgeGroup(ageGroup.getValue() != null 
							? (AgeGroup) ageGroup.getValue().getEnumVal() : null);
		
		model.setCustomerGroup(customerGroup.getValue() != null 
							? (CustomerGroup) customerGroup.getValue().getEnumVal() : null);
		
		model.setSex(sexCombo.getValue() != null ? (Sex) sexCombo.getValue().getEnumVal() : null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void popFields() {
		nameText.setValue(model.getName());
		mobilePhoneText.setValue(model.getMobilePhone());
		emailText.setValue(model.getEmail());

		if (model.getHomeAddress() != null) {
			homePhoneText.setValue(model.getHomeAddress().getPhone());
			homeAddress.setValue(model.getHomeAddress().getAddress());
		}

		if (model.getWorkAddress() != null) {
			workPhoneText.setValue(model.getWorkAddress().getPhone());
			workAddress.setValue(model.getWorkAddress().getAddress());
		}
		sexCombo.setValue(sexCombo.getStore().findModel("enumVal", model.getSex()));
		ageGroup.setValue(ageGroup.getStore().findModel("enumVal", model.getAgeGroup()));
		customerGroup.setValue(customerGroup.getStore().findModel("enumVal", model.getCustomerGroup()));

		getController().fireEvent(new Event(SpecialEvent.LoadJob, model));
		getController().fireEvent(new Event(SpecialEvent.LoadLocation, model));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		nameText = new TextField<String>();
		nameText.setFieldLabel(Tags.get("name"));
		nameText.setName("name");
		addFieldToForm(nameText);

		homeLocation = InstantComponent.getStandartCombo(Tags.get("homeLocation"));
		addFieldToForm(homeLocation);

		mobilePhoneText = new TextField<String>();
		mobilePhoneText.setFieldLabel(Tags.get("mobilePhone"));
		mobilePhoneText.setName("mobilePhone");
		addFieldToForm(mobilePhoneText);

		homePhoneText = new TextField<String>();
		homePhoneText.setFieldLabel(Tags.get("homePhone"));
		homePhoneText.setName("homePhone");
		addFieldToForm(homePhoneText);

		homeAddress = new TextArea();
		homeAddress.setPreventScrollbars(true);
		homeAddress.setFieldLabel(Tags.get("homeAddress"));
		homeAddress.setName("homeAddress");
		addFieldToForm(homeAddress);

		emailText = InstantComponent.getTextEmail();
		addFieldToForm(emailText);

		/*-------------------------------------------------------------------*/
		TabPanel othersTabPanel = new TabPanel();
		othersTabPanel.setHeight(160);
		othersTabPanel.setPlain(true);

		othersTabPanel.add(getPrivateTabItem());
		othersTabPanel.add(getWorkInfoTabItem());

		addWidgetToForm(othersTabPanel);
		/*-------------------------------------------------------------------*/

		addWidgetToPanel(getFormPanel(), new RowData(1, 1));
		
	}

	/**
	 * Kisisel bilgiler paneli
	 * 
	 */
	private TabItem getPrivateTabItem() {

		FormPanel form = new FormPanel();
		form.setHeaderVisible(false);
		form.setLabelWidth(100);
		form.setLabelAlign(LabelAlign.RIGHT);
		form.setFrame(true);

		TabItem tabItem = new TabItem(Tags.get("privte"));
		tabItem.setIcon(AbstractImagePrototype.create(Images.Instance.home()));

		job = InstantComponent.getStandartCombo(Tags.get("job"));
		form.add(job);

		customerGroup = GUIHelper.getComboCustomerGroup();
		form.add(customerGroup);

		ageGroup = GUIHelper.getComboAgeGroup();
		form.add(ageGroup);

		sexCombo = GUIHelper.getComboSex();
		form.add(sexCombo);

		tabItem.add(form, new RowData(1, 1, new Margins(0)));

		return tabItem;
	}

	/**
	 * Is bilgileri paneli
	 * 
	 */
	private TabItem getWorkInfoTabItem() {
		FormPanel form = new FormPanel();
		form.setHeaderVisible(false);
		form.setLabelWidth(100);
		form.setLabelAlign(LabelAlign.RIGHT);
		form.setFrame(true);

		TabItem tabItem = new TabItem(Tags.get("work"));
		tabItem.setIcon(AbstractImagePrototype.create(Images.Instance.building()));

		FormLayout formLayout = new FormLayout(LabelAlign.RIGHT);
		formLayout.setLabelWidth(100);
		tabItem.setLayout(formLayout);

		workLocation = InstantComponent.getStandartCombo(Tags.get("workLocation"));
		form.add(workLocation);

		workPhoneText = new TextField<String>();
		workPhoneText.setFieldLabel(Tags.get("workPhone"));
		workPhoneText.setName("workPhone");
		form.add(workPhoneText);

		workAddress = new TextArea();
		workAddress.setPreventScrollbars(true);
		workAddress.setFieldLabel(Tags.get("workAddress"));
		workAddress.setName("workAddress");
		form.add(workAddress);

		tabItem.add(form, new RowData(1, 1, new Margins(0)));

		return tabItem;
	}

}
