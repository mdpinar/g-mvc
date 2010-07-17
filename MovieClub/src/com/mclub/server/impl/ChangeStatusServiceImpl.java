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

import com.gmvc.client.base.IModel;
import com.gmvc.client.meta.EffectedDetail;
import com.gmvc.client.meta.RetVal;
import com.gmvc.server.impl.CRUDImpl;
import com.gmvc.server.util.DBUtils;
import com.mclub.client.enums.ActionType;
import com.mclub.client.model.ChangeStatusDTO;
import com.mclub.client.service.ChangeStatusService;
import com.mclub.server.model.ChangeStatus;
import com.mclub.server.model.ChangeStatusDetail;
import com.mclub.server.model.Trans;
import com.mclub.server.util.DBWorks;

/**
 * Meslek Tanitimi servisi
 * 
 * @see CRUDImpl
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings("serial")
public class ChangeStatusServiceImpl 
	extends CRUDImpl<ChangeStatus, ChangeStatusDTO> implements ChangeStatusService {

	public ChangeStatusServiceImpl() {
		super(ChangeStatus.class, ChangeStatusDTO.class);
	}

	/**
	 * Silinen bir "verilen film" hareketinin herbir filmi icin;
	 * 
	 * Filmin (varsa) bir onceki Trans bilgisi setlenir
	 * Ilgili Trans silinir
	 * 
	 */
	@Override
	public RetVal beforeRemove(Session session, ChangeStatus model) {
		for (ChangeStatusDetail detail: model.getDetails()) {
			DBWorks.undoMovieStatus(session, detail.getMovie(), detail.getTransId());
		}
		return null;
	}
	
	/**
	 * Bir film verildiginde Trans tablosuna hareket eklenir ve filmin durumu degistirilir
	 *  
	 */
	@Override
	public RetVal afterSave(Session session, ChangeStatus serverModel, List<EffectedDetail> effectedDetailList) {

		for (ChangeStatusDetail detail: serverModel.getDetails()) {
			Trans trans = null;
			if (detail.getTransId() != null) {
				trans = (Trans) session.get(Trans.class, detail.getTransId());
			}
			
			if (trans == null) trans = new Trans();
			
			trans.setActionId(detail.getId());
			trans.setType(ActionType.ChangeStatus);
			trans.setTransDate(serverModel.getTransDate());
			trans.setMovie(detail.getMovie());
			trans.setStatus(detail.getStatus());
			trans.setUser(serverModel.getUser());
			session.save(trans);
			
			detail.setTransId(trans.getId());
			detail.setChangeStatus(serverModel);
			
			detail.getMovie().setLastTransId(trans.getId());
			detail.getMovie().setLastTransDate(serverModel.getTransDate());
			detail.getMovie().setLastStatus(trans.getStatus());
			session.merge(detail.getMovie());
		}
		
		//Varsa, silinen detaylardaki filmler eski haline getirilir
		List<IModel> deletedList = effectedDetailList.get(0).getDeletedList();
		for (int i=0; i<deletedList.size(); i++) {
			ChangeStatusDetail detail = convertToServer(deletedList.get(i));
			DBWorks.undoMovieStatus(session, detail.getMovie(), detail.getTransId());
		}
		return null;
	}
	
	/**
	 * DTO sinifi Model sinifa cevirir
	 * 
	 * @param changeStatusDetail client tarafindaki DTO sinif
	 * 
	 * @return changeStatusDetail server tarafindaki Model sinif
	 * 
	 */
	private ChangeStatusDetail convertToServer(IModel changeStatusDetail) {
		return DBUtils.getMapper().map(changeStatusDetail, ChangeStatusDetail.class);
	}
	
}
