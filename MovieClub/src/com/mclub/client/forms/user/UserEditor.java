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
package com.mclub.client.forms.user;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.TabItem;
import com.extjs.gxt.ui.client.widget.TabPanel;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.FitData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.gmvc.client.base.AbstractEditor;
import com.gmvc.client.base.IController;
import com.gmvc.client.enums.RightGroup;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.Right;
import com.gmvc.client.meta.RightHelper;
import com.gmvc.client.util.Hash;
import com.gmvc.client.util.Tags;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.image.Images;
import com.mclub.client.model.RoleDTO;
import com.mclub.client.model.UserDTO;

/**
 * Kullanici Tanitimi, Editor sinifi
 * 
 * @see AbstractEditor
 * 
 * @author mdpinar
 * 
 */
public class UserEditor extends AbstractEditor<UserDTO> {

	/**
	 * Aksiyonlar icin mini kontrolor
	 *  
	 */
	private IController<RoleDTO> actionRolesController;

	/**
	 * Formlar icin mini kontrolor
	 *  
	 */
	private IController<RoleDTO> formRolesController;

	/**
	 * Raporlar icin mini kontrolor
	 *  
	 */
	private IController<RoleDTO> reportRolesController;

	/**
	 * Isim alani
	 * 
	 */
	private TextField<String> nameText;

	/**
	 * Eski sifre alani
	 * 
	 */
	private TextField<String> oldPasswordText;

	/**
	 * Yeni sifre alani
	 * 
	 */
	private TextField<String> newPasswordText;

	/**
	 * Yeni sifre Tekrar alani
	 * 
	 */
	private TextField<String> rePasswordText;

	/**
	 * Kullanici tipi alani (standart, admin)
	 * 
	 */
	private SimpleComboBox<String> userType;
	

	/**
	 * Roller paneli
	 * 
	 */
	private TabPanel rolesTabPanel;

	/**
	 * Aksiyonlar
	 * 
	 */
	protected List<RoleDTO> actionRoleList;

	/**
	 * Formlar
	 * 
	 */
	protected List<RoleDTO> formRoleList;

	/**
	 * Raporlar
	 * 
	 */
	protected List<RoleDTO> reportRoleList;

	public UserEditor(IController<UserDTO> controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserDTO getNewModel() {
		return new UserDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Size getSize() {
		return new Size(550, 450);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String isValid() {
		StringBuilder sb = new StringBuilder();

		if (nameText.getValue() == null || nameText.getValue().isEmpty())
			sb.append(Tags.get("nameCanNotBeEmpty"));

		if (model.getId() != null) {
			if (oldPasswordText.getValue() == null || oldPasswordText.getValue().isEmpty())
				sb.append(Tags.get("oldPasswordCanNotBeEmpty"));
			else {
				String oldPass = Hash.md5(oldPasswordText.getValue());
				if (!model.getPassword().equals(oldPass)) sb.append(Tags.get("oldPasswordInvalid"));
			}
		} else {
			if (newPasswordText.getValue() == null || newPasswordText.getValue().isEmpty())
				sb.append(Tags.get("newPasswordCanNotBeEmpty"));
		}

		if (newPasswordText.getValue() != null 
		&& !newPasswordText.getValue().equals(rePasswordText.getValue()))
			sb.append(Tags.get("newAndReTypePasswordAreNotEquals"));

		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("userTitle");
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
		
		if (model.getId() == null 
		|| (newPasswordText.getValue() != null 
			&& newPasswordText.getValue().equals(rePasswordText.getValue()))) {
			
				model.setPassword(Hash.md5(newPasswordText.getValue()));
		}
		model.setIsAdmin(userType.getSelectedIndex() == 1);

		model.getRoles().clear();

		actionRolesController.fireEvent(new Event(SpecialEvent.MiniDoUpdates));
		for (RoleDTO r : actionRoleList) {
			model.getRoles().add(r);
		}

		formRolesController.fireEvent(new Event(SpecialEvent.MiniDoUpdates));
		for (RoleDTO r : formRoleList) {
			model.getRoles().add(r);
		}

		reportRolesController.fireEvent(new Event(SpecialEvent.MiniDoUpdates));
		for (RoleDTO r : reportRoleList) {
			model.getRoles().add(r);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void popFields() {
		nameText.setValue(model.getName());
		oldPasswordText.setValue(null);
		newPasswordText.setValue(null);
		rePasswordText.setValue(null);

		if (model.getIsAdmin())
			userType.setValue(userType.findModel("Administrator"));
		else
			userType.setValue(userType.findModel("Standart"));

		actionRoleList = new ArrayList<RoleDTO>();
		formRoleList = new ArrayList<RoleDTO>();
		reportRoleList = new ArrayList<RoleDTO>();

		loadActionRoles();
		loadFormRoles();
		loadReportRoles();

		if (model.getId() != null)
			userType.setEnabled(!model.getId().equals(1L));
		else
			userType.setEnabled(true);

		actionRolesController.fireEvent(
			new Event(SpecialEvent.MiniSetModels)
				.addParam("modelList", actionRoleList)
		);
		formRolesController.fireEvent(
			new Event(SpecialEvent.MiniSetModels)
				.addParam("modelList", formRoleList)
		);
		reportRolesController.fireEvent(
			new Event(SpecialEvent.MiniSetModels)
				.addParam("modelList", reportRoleList)
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		actionRolesController = new ActionRolesMiniController(getController());
		formRolesController = new FormRolesMiniController(getController());
		reportRolesController = new ReportsRolesMiniController(getController());

		nameText = new TextField<String>();
		nameText.setFieldLabel(Tags.get("name"));
		addFieldToForm(nameText);

		userType = new SimpleComboBox<String>();
		userType.setFieldLabel(Tags.get("userType"));
		userType.add("Standart");
		userType.add("Administrator");
		userType.setTriggerAction(TriggerAction.ALL);
		userType.setAllowBlank(false);
		userType.setForceSelection(true);
		addFieldToForm(userType);

		oldPasswordText = new TextField<String>();
		oldPasswordText.setPassword(true);
		oldPasswordText.setFieldLabel(Tags.get("oldPassword"));
		addFieldToForm(oldPasswordText);

		newPasswordText = new TextField<String>();
		newPasswordText.setPassword(true);
		newPasswordText.setFieldLabel(Tags.get("newPassword"));
		addFieldToForm(newPasswordText);

		rePasswordText = new TextField<String>();
		rePasswordText.setPassword(true);
		rePasswordText.setFieldLabel(Tags.get("rePassword"));
		addFieldToForm(rePasswordText);

		/*-------------------------------------------------------------------*/
		rolesTabPanel = new TabPanel();
		rolesTabPanel.setLayoutData(new FitLayout());
		rolesTabPanel.setPlain(true);
		rolesTabPanel.setHeight(200);

		rolesTabPanel.add(getActionRolesTabItem());
		rolesTabPanel.add(getFormRolesTabItem());
		rolesTabPanel.add(getReportRolesTabItem());

		addWidgetToForm(rolesTabPanel);
		/*-------------------------------------------------------------------*/

		userType.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>() {
				@Override
				public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
					rolesTabPanel.setEnabled(se.getSelectedItem() != null
							&& se.getSelectedItem().getValue().equals("Standart"));
				}
			});

		addWidgetToPanel(getFormPanel(), new RowData(1, 1, new Margins(4)));
	}
	
	/**
	 * Beni buna mahkum ettigin icin tesekkur ederim(?) java
	 * 
	 * @return IModel user
	 */
	UserDTO getModel() {
		return model;
	}

	/**
	 * Aksiyon rolleri icin tabitem olusturur
	 * 
	 */
	private TabItem getActionRolesTabItem() {
		TabItem tabItem = new TabItem(Tags.get("actionRights"));
		tabItem.setIcon(AbstractImagePrototype.create(Images.Instance.arrow_switch()));
		tabItem.setLayout(new FitLayout());

		tabItem.add(actionRolesController.getBrowser().popup(), new FitData(1));

		return tabItem;
	}

	/**
	 * Form rolleri icin tabitem olusturur
	 * 
	 */
	private TabItem getFormRolesTabItem() {
		TabItem tabItem = new TabItem(Tags.get("formRights"));
		tabItem.setIcon(AbstractImagePrototype.create(Images.Instance.text()));
		tabItem.setLayout(new FitLayout());

		tabItem.add(formRolesController.getBrowser().popup(), new FitData(1));

		return tabItem;
	}

	/**
	 * Rapor rolleri icin tabitem olusturur
	 * 
	 */
	private TabItem getReportRolesTabItem() {
		TabItem tabItem = new TabItem(Tags.get("reportRights"));
		tabItem.setIcon(AbstractImagePrototype.create(Images.Instance.report()));
		tabItem.setLayout(new FitLayout());

		tabItem.add(reportRolesController.getBrowser().popup(), new FitData(1));

		return tabItem;
	}

	/**
	 * Aksiyon rollerini asil listeden gecici listeye yukler
	 * 
	 */
	private void loadActionRoles() {
		for (RoleDTO r : model.getRoles()) {
			Right right = RightHelper.findRight(r.getRight());
			if (right.getGroup().equals(RightGroup.Action)) {
				actionRoleList.add(r);
			}
		}

		List<Right> actionRights = RightHelper.getRightList(RightGroup.Action);
		for (Right right : actionRights) {
			boolean isContains = false;
			for (RoleDTO role : actionRoleList) {
				if (role.getRight().equals(right.getName())) {
					isContains = true;
					break;
				}
			}
			if (!isContains) {
				actionRoleList.add(new RoleDTO(right));
			}
		}
	}

	/**
	 * Form rollerini asil listeden gecici listeye yukler
	 * 
	 */
	private void loadFormRoles() {
		for (RoleDTO r : model.getRoles()) {
			Right right = RightHelper.findRight(r.getRight());
			if (right.getGroup().equals(RightGroup.Form)) {
				formRoleList.add(r);
			}
		}

		List<Right> formRights = RightHelper.getRightList(RightGroup.Form);
		for (Right right : formRights) {
			boolean isContains = false;
			for (RoleDTO role : formRoleList) {
				if (role.getRight().equals(right.getName())) {
					isContains = true;
					break;
				}
			}
			if (!isContains) {
				formRoleList.add(new RoleDTO(right));
			}
		}
	}

	/**
	 * Rapor rollerini asil listeden gecici listeye yukler
	 * 
	 */
	private void loadReportRoles() {
		for (RoleDTO r : model.getRoles()) {
			Right right = RightHelper.findRight(r.getRight());
			if (right.getGroup().equals(RightGroup.Report)) {
				reportRoleList.add(r);
			}
		}

		List<Right> reportRights = RightHelper.getRightList(RightGroup.Report);
		for (Right right : reportRights) {
			boolean isContains = false;
			for (RoleDTO role : reportRoleList) {
				if (role.getRight().equals(right.getName())) {
					isContains = true;
					break;
				}
			}
			if (!isContains) {
				reportRoleList.add(new RoleDTO(right));
			}
		}
	}

}
