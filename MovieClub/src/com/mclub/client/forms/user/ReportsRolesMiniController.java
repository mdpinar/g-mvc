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

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.gmvc.client.base.AbstractMiniController;
import com.gmvc.client.base.IBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.base.IEditor;
import com.gmvc.client.meta.Event;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.model.RoleDTO;

/**
 * Kullanici Tanitimi altindaki Rapor Rolleri kismi, Controller sinifi
 * 
 * @see AbstractMiniController
 * 
 * @author mdpinar
 * 
 */
public class ReportsRolesMiniController extends AbstractMiniController<RoleDTO> {

	/**
	 * Calistirabilir seceneklerinin tamami secilmis mi
	 * 
	 */
	private boolean allExecute;

	public ReportsRolesMiniController(IController<?> parent) {
		super(parent, "Role");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 450;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeight() {
		return 200;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IBrowser<RoleDTO> createBrowser() {
		return new ReportsRolesMiniBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IEditor<RoleDTO> createEditor() {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void afterEvent(Event event) {
		switch (event.getType()) {
			case SpecialEvent.MiniDoUpdates: {
				UserEditor parentEditor = (UserEditor) getParent().getEditor();
				
				for (RoleDTO role: realModelList) {
					role.setUser(parentEditor.getModel());
				}
				
				parentEditor = null;
				break;
			}
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void holdSpecialEvent(Event event) {
		switch (event.getType()) {
			case SpecialEvent.GridEvent: {
				Integer colIndex = event.getParam("colIndex");
				Integer rowIndex = event.getParam("rowIndex");

				setSelectColumn(colIndex, rowIndex);
				break;
			}
		}
	}

	/**
	 * Satir ve Sutun (row, col) daki secimi tersine cevirir
	 *  
	 * @param colIndex sutun
	 * @param rowIndex satir
	 */
	private void setSelectColumn(int colIndex, int rowIndex) {
		ListStore<BeanModel> beanList = getBrowser().getGrid().getStore();

		if (rowIndex == -1 && colIndex == 1) allExecute = !allExecute;

		int row = -1;
		for (BeanModel bm : beanList.getModels()) {
			row++;
			if (rowIndex > -1) {
				if (rowIndex != row) continue;
			}
			RoleDTO role = bm.getBean();
			if (colIndex == 1) {
				role.setHasExecute(rowIndex < 0 ? allExecute : ! role.getHasExecute());
			}
			if (colIndex > 0) {
				fireEvent(
					new Event(SpecialEvent.UpdateModel, role)
						.addParam("rowIndex", row)
				);
			}
		}
		
		fireEvent(new Event(SpecialEvent.Refresh));
	}

}
