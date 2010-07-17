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
package com.mclub.client.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.extjs.gxt.ui.client.Registry;
import com.gmvc.client.enums.RightGroup;
import com.gmvc.client.enums.RightType;
import com.gmvc.client.meta.IUserController;
import com.gmvc.client.meta.Right;
import com.gmvc.client.util.Utils;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mclub.client.app.AppModel;
import com.mclub.client.model.RoleDTO;
import com.mclub.client.model.UserDTO;


/**
 * Kullanici hak denetimlerinin yapildigi siniftir
 * 
 * @author mdpinar
 * 
 */
public final class UserUtil implements IUserController {

	/**
	 * Aktif kullanici
	 * 
	 */
	private UserDTO user;
	
	/**
	 * Hak map i
	 * 
	 */
	private Map<String, RoleDTO> rightMap;

	/**
	 * Kullanici giris onayi icin server tarafina basvurulur
	 * 
	 * @param username
	 * @param password
	 */
	public void login(String username, String password) {
		AppModel.getSystemService().login(username, password,
				new AsyncCallback<UserDTO>() {
					@Override
					public void onFailure(Throwable caught) {
						Utils.showError(caught);
					}

					@Override
					public void onSuccess(UserDTO result) {
						user = result;
						Registry.register("user", user);

						if (user != null) {
							rightMap = new HashMap<String, RoleDTO>();
							List<RoleDTO> roleList = user.getRoles();
							for (RoleDTO role : roleList) {
								rightMap.put(role.getRight(), role);
							}
						}
					}
				});
	}

	/**
	 * Verilen Hak icin izni olup olmadigina bakilir
	 * 
	 * @param right
	 * 
	 * @return boolean
	 */
	public boolean hasRight(Right right) {
		if (hasAllRight())
			return true;
		else
			return hasRight(right, RightType.Execute);
	}

	/**
	 * Admin yetkisi verilmis mi?
	 * 
	 * @return boolean
	 */
	public boolean hasAllRight() {
		return user.getIsAdmin();
	}

	/**
	 * Verilen Hakta uzerinde verilen Tipte izni var mi?
	 * 
	 * @param right
	 * @param type
	 * 
	 * @return boolean
	 */
	public boolean hasRight(Right right, RightType type) {

		if (hasAllRight()) {

			return true;

		} else {

			RoleDTO role = rightMap.get(right);
			if (role != null) {
				boolean isAddNew = role.getHasAddNew() != null
						&& role.getHasAddNew().equals(Boolean.TRUE);
				
				boolean isRead = role.getHasRead() != null
						&& role.getHasRead().equals(Boolean.TRUE);
				
				boolean isUpdate = role.getHasUpdate() != null
						&& role.getHasUpdate().equals(Boolean.TRUE);
				
				boolean isDelete = role.getHasDelete() != null
						&& role.getHasDelete().equals(Boolean.TRUE);

				if (right.getGroup().equals(RightGroup.Report)) {
					return role.getHasExecute() != null
							&& role.getHasExecute().equals(Boolean.TRUE);
				} else {
					switch (type) {
						case AddNew:
							return isAddNew;
						case Read:
							return isRead;
						case Update:
							return isUpdate;
						case Delete:
							return isDelete;
						default:
							return (isAddNew || isRead || isUpdate || isDelete);
					}
				}
			}
		}

		return false;
	}

}
