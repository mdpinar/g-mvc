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

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;

import com.gmvc.client.base.IModel;
import com.gmvc.client.meta.EffectedDetail;
import com.gmvc.client.meta.RetVal;
import com.gmvc.server.impl.CRUDImpl;
import com.gmvc.server.util.DBUtils;
import com.mclub.client.enums.ActionType;
import com.mclub.client.enums.FilmStatus;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.model.IncomingMovieDTO;
import com.mclub.client.model.IncomingMovieDetailDTO;
import com.mclub.client.model.MovieDTO;
import com.mclub.client.service.IncomingMovieService;
import com.mclub.server.model.Customer;
import com.mclub.server.model.IncomingMovie;
import com.mclub.server.model.IncomingMovieDetail;
import com.mclub.server.model.Movie;
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
public class IncomingMovieServiceImpl 
	extends	CRUDImpl<IncomingMovie, IncomingMovieDTO> implements IncomingMovieService {

	public IncomingMovieServiceImpl() {
		super(IncomingMovie.class, IncomingMovieDTO.class);
	}

	/**
	 * Silinen bir "verilen film" hareketinin herbir filmi icin;
	 * 
	 * Filmin (varsa) bir onceki Trans bilgisi setlenir
	 * Ilgili Trans silinir
	 * 
	 */
	@Override
	public RetVal beforeRemove(Session session, IncomingMovie serverModel) {
		for (IncomingMovieDetail detail: serverModel.getDetails()) {
			DBWorks.undoMovieStatus(session, detail.getMovie(), detail.getTransId());
		}
		return null;
	}
	
	/**
	 * Bir film verildiginde Trans tablosuna hareket eklenir ve filmin durumu degistirilir 
	 * 
	 */
	@Override
	public RetVal afterSave(Session session, IncomingMovie serverModel, List<EffectedDetail> effectedDetailList) {

		for (IncomingMovieDetail detail: serverModel.getDetails()) {
			Trans trans = null;
			if (detail.getTransId() != null) {
				trans = (Trans) session.get(Trans.class, detail.getTransId());
			}
			
			if (trans == null) {
				trans = new Trans();
			} else {
				if (! trans.getMovie().equals(detail.getMovie())) {
					DBWorks.undoMovieStatus(session, trans.getMovie(), trans.getId());
				}
			}
			
			trans.setActionId(detail.getId());
			trans.setType(ActionType.IncomingMovie);
			trans.setCustomer(serverModel.getCustomer());
			trans.setTransDate(serverModel.getTransDate());
			trans.setMovie(detail.getMovie());
			trans.setStatus(FilmStatus.IN_STORE);
			trans.setUser(serverModel.getUser());
			session.save(trans);
			
			detail.setTransId(trans.getId());
			detail.setIncomingMovie(serverModel);
			
			detail.getMovie().setLastTransId(trans.getId());
			detail.getMovie().setLastTransDate(serverModel.getTransDate());
			detail.getMovie().setLastCustomer(serverModel.getCustomer());
			detail.getMovie().setLastStatus(trans.getStatus());
			session.merge(detail.getMovie());
		}
		
		//Varsa, silinen detaylardaki filmler eski haline getirilir
		List<IModel> deletedList = effectedDetailList.get(0).getDeletedList();
		for (int i=0; i<deletedList.size(); i++) {
			IncomingMovieDetail detail = convertToServer(deletedList.get(i));
			DBWorks.undoMovieStatus(session, detail.getMovie(), detail.getTransId());
		}
		return null;
	}

	/**
	 * Musterinin daha onceden aldigi filmler secilip geldi diye isaretlenebilsin diye, liste seklinde doner
	 * 
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<IncomingMovieDetailDTO> getMovieList(CustomerDTO cus) {
		Session session = DBUtils.getSession();
		session.beginTransaction();
		
		FilmStatus[] wantedStatus = {FilmStatus.RENT, FilmStatus.LOST};
		Customer customer = DBUtils.getMapper().map(cus, Customer.class);
		
		List<Movie> movieList = session.createQuery("from Movie m " +
													"where m.lastCustomer = :customer " +
													"  and m.lastStatus IN (:status)")
												.setParameter("customer", customer)
												.setParameterList("status", wantedStatus)
											.list();

		List<IncomingMovieDetailDTO> result = new ArrayList<IncomingMovieDetailDTO>(movieList.size());
		for (Object model: movieList) {
			IncomingMovieDetailDTO incomingDetail = new IncomingMovieDetailDTO();
			incomingDetail.setMovie(DBUtils.getMapper().map(model, MovieDTO.class));

			result.add(incomingDetail);
		}

		session.getTransaction().commit();
		
		return result;
	}
	
	/**
	 * DTO sinifi Model sinifa cevirir
	 * 
	 * @param incomingMovieDetail client tarafindaki DTO sinif
	 * 
	 * @return incomingMovieDetail server tarafindaki Model sinif
	 * 
	 */
	private IncomingMovieDetail convertToServer(IModel incomingMovieDetail) {
		return DBUtils.getMapper().map(incomingMovieDetail, IncomingMovieDetail.class);
	}
	
}
