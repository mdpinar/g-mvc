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

import com.extjs.gxt.ui.client.widget.Info;
import com.gmvc.client.base.AbstractBaseController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.Right;
import com.gmvc.client.service.ICRUDServiceAsync;
import com.gmvc.client.util.Tags;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.mclub.client.app.Rights;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.image.Images;
import com.mclub.client.model.RoleDTO;
import com.mclub.client.model.UserDTO;
import com.mclub.client.service.UserService;
import com.mclub.client.service.UserServiceAsync;

/**
 * Kullanici Tanitimi, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class UserController extends AbstractBaseController<UserDTO> {

	/**
	 * Server tarafi iletisim servisi
	 * 
	 */
	private UserServiceAsync service = GWT.create(UserService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserBrowser createBrowser() {
		return new UserBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UserEditor createEditor() {
		return new UserEditor(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICRUDServiceAsync<UserDTO> getService() {
		return this.service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeEvent(Event event) {
		switch (event.getType()) {

			/*
			 * Bu istek Editor den yapiliyor
			 * 
			 * Admin kullanicisi ise ve Hak bilgisi FALSE ise true yapilir 
			 */
			case SpecialEvent.SaveAndClose:
			case SpecialEvent.SaveAndContinue: {
				UserDTO user = getBrowser().getSelectedModel();
				if (user.getIsAdmin() != null && user.getIsAdmin().equals(Boolean.TRUE)) {
					for (RoleDTO role : user.getRoles()) {
	
						if (role.getHasAddNew() == null || role.getHasAddNew().equals(Boolean.FALSE))
							role.setHasAddNew(Boolean.TRUE);
	
						if (role.getHasUpdate() == null || role.getHasUpdate().equals(Boolean.FALSE))
							role.setHasUpdate(Boolean.TRUE);
	
						if (role.getHasDelete() == null || role.getHasDelete().equals(Boolean.FALSE))
							role.setHasDelete(Boolean.TRUE);
	
						if (role.getHasExecute() == null || role.getHasExecute().equals(Boolean.FALSE))
							role.setHasExecute(Boolean.TRUE);
	
					}
				}
				break;
			}
	
			/*
			 * Bu istek Browser dan yapiliyor
			 * 
			 * Admin kullanicisi ise silinemez
			 */
			case SpecialEvent.Delete: {
				UserDTO user = getBrowser().getSelectedModel();
				if (user != null && user.getId().equals(1L)) {
					event.setCancel(true);
					Info.display(Tags.get("error"), Tags.get("cannotDeleteAdminUser"));
				}
				break;
			}

		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Right getRight() {
		return Rights.Users;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 300;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageResource getIcon() {
		return Images.Instance.group();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("userTitle");
	}

}
