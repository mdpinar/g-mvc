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
package com.mclub.client.app;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.gmvc.client.app.AppController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.util.Tags;
import com.gmvc.client.util.UserController;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.mclub.client.image.Images;

/**
 * Uygulamanin ilk formudur, kullanici adi ve sifresi alinarak
 * giris kontrolu yapilir. 
 *
 * @author mdpinar
 * 
 */
public class LoginDialog extends Dialog {

	/**
	 * Kullanici adi
	 */
	private TextField<String> userName;
	
	/**
	 * Sifresi
	 * 
	 */
	private TextField<String> password;
	
	/**
	 * Onay butonu
	 * 
	 */
	private Button login;
	
	/**
	 * Durum yazisinin cikacagi kisim (lutfen bekleyiniz... gibi)
	 * 
	 */
	private Status status;

	/**
	 * Yeni bir login formu olusturur
	 * 
	 */
	public LoginDialog() {
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(80);
		layout.setDefaultWidth(130);
		setLayout(layout);

		setButtonAlign(HorizontalAlignment.CENTER);
		setButtons("");
		setIcon(AbstractImagePrototype.create(Images.Instance.shield()));
		setHeading("Login");
		setModal(true);
		setResizable(false);
		setClosable(false);
		setWidth(250);
		setBodyStyle("padding: 8px;background: none");
		setShadow(false);

		userName = new TextField<String>();
		userName.setFieldLabel(Tags.get("username"));
		add(userName);

		password = new TextField<String>();
		password.setPassword(true);
		password.setFieldLabel(Tags.get("password"));
		add(password);

		//her form goruntulenmesinde sahalarin ici bosaltilir ve ilk sahaya konumlanilir
		addListener(Events.Show, new Listener<ComponentEvent>() {
			@Override
			public void handleEvent(ComponentEvent be) {
				userName.setValue("admin");
				password.setValue("admin");
				setFocusWidget(userName);
			}
		});

		//kullanici adinda iken enter a basilirsa sifre sahasina gecilir
		userName.addKeyListener(new KeyListener() {
			@Override
			public void handleEvent(ComponentEvent e) {
				if (e.getKeyCode() == KeyCodes.KEY_ENTER) {
					setFocusWidget(password);
				}
			}
		});

		//sifrede iken enter a basilirsa login olmaya calisilir
		password.addKeyListener(new KeyListener() {
			@Override
			public void handleEvent(ComponentEvent e) {
				if (e.getKeyCode() == KeyCodes.KEY_ENTER) {
					login.disable();
					onSubmit();
				}
			}
		});

	}

	@Override
	protected void createButtons() {
		super.createButtons();
		status = new Status();
		status.setBusy(Tags.get("pleaseWait"));
		status.setAutoWidth(true);
		status.hide();
		getButtonBar().add(status);

		getButtonBar().add(new FillToolItem());

		login = new Button(Tags.get("ok"), AbstractImagePrototype.create(Images.Instance.ok()));
		login.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				login.disable();
				onSubmit();
			}
		});

		addButton(login);

	}

	/**
	 * Login icin server a danisilir, asenkron programlamada sonucun gelmesi beklenemez
	 * fakat bu kisimda bu lazim, bu yuzden timer ile 1 saniye kadar beklendikten sonra
	 * devam ediliyor
	 * 
	 */
	protected void onSubmit() {
		status.show();
		UserController.login(userName.getValue(), password.getValue());

		Timer t = new Timer() {

			@Override
			public void run() {
				status.hide();
				login.enable();
				AppController.fireEvent(new Event(SpecialEvent.Login));
			}

		};
		t.schedule(1000);
		login.enable();
	}

}
