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
import com.mclub.client.model.GivenMovieDTO;
import com.mclub.client.service.GivenMovieService;
import com.mclub.server.model.GivenMovie;
import com.mclub.server.model.GivenMovieDetail;
import com.mclub.server.model.Trans;
import com.mclub.server.util.DBWorks;

/**
 * Verilen Filmler servisi
 * 
 * @see CRUDImpl
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings("serial")
public class GivenMovieServiceImpl 
	extends	CRUDImpl<GivenMovie, GivenMovieDTO> implements GivenMovieService {

	public GivenMovieServiceImpl() {
		super(GivenMovie.class, GivenMovieDTO.class);
	}

	/**
	 * Silinen bir "verilen film" hareketinin herbir filmi icin;
	 * 
	 * Filmin (varsa) bir onceki Trans bilgisi setlenir
	 * Ilgili Trans silinir
	 * 
	 */
	@Override
	public RetVal beforeRemove(Session session, GivenMovie serverModel) {
		for (GivenMovieDetail detail: serverModel.getDetails()) {
			DBWorks.undoMovieStatus(session, detail.getMovie(), detail.getTransId());
		}
		return null;
	}
	
	/**
	 * Bir film verildiginde Trans tablosuna hareket eklenir ve filmin durumu degistirilir 
	 */
	@Override
	public RetVal afterSave(Session session, GivenMovie serverModel, List<EffectedDetail> effectedDetailList) {

		for (GivenMovieDetail detail: serverModel.getDetails()) {
			Trans trans = null;
			if (detail.getTransId() != null) {
				trans = (Trans) session.get(Trans.class, detail.getTransId());
			}
			
			if (trans == null) trans = new Trans();
			
			trans.setActionId(detail.getId());
			trans.setType(ActionType.GivenMovie);
			trans.setCustomer(serverModel.getCustomer());
			trans.setTransDate(serverModel.getTransDate());
			trans.setMovie(detail.getMovie());
			trans.setStatus(detail.getStatus());
			trans.setUser(serverModel.getUser());
			session.save(trans);
			
			detail.setTransId(trans.getId());
			detail.setGivenMovie(serverModel);
			
			detail.getMovie().setLastTransId(trans.getId());
			detail.getMovie().setLastTransDate(serverModel.getTransDate());
			detail.getMovie().setLastCustomer(serverModel.getCustomer());
			detail.getMovie().setLastStatus(trans.getStatus());
			session.merge(detail.getMovie());
		}
		
		//Varsa, silinen detaylardaki filmler eski haline getirilir
		List<IModel> deletedList = effectedDetailList.get(0).getDeletedList();
		for (int i=0; i<deletedList.size(); i++) {
			GivenMovieDetail detail = convertToServer(deletedList.get(i));
			DBWorks.undoMovieStatus(session, detail.getMovie(), detail.getTransId());
		}
		return null;
	}
	
	/**
	 * DTO sinifi Model sinifa cevirir
	 * 
	 * @param givenMovieDetail client tarafindaki DTO sinif
	 * 
	 * @return givenMovieDetail server tarafindaki Model sinif
	 * 
	 */
	private GivenMovieDetail convertToServer(IModel givenMovieDetail) {
		return DBUtils.getMapper().map(givenMovieDetail, GivenMovieDetail.class);
	}
	
}
