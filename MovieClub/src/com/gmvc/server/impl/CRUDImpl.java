/*
 * Copyleft 2010 Mustafa DUMLUPINAR
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 * http://www.gnu.org/licenses/lgpl.html
 *
 * dumlupinar01@gmail.com
 * http://mdpinar.blogspot.com/
 *
 */
package com.gmvc.server.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.gmvc.client.base.IModel;
import com.gmvc.client.enums.SearchType;
import com.gmvc.client.meta.EffectedDetail;
import com.gmvc.client.meta.FilterItem;
import com.gmvc.client.meta.RetVal;
import com.gmvc.server.util.DBUtils;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * Server tarafi icin;
 * 
 * <p/>
 * Tum tanitim formlari icin CRUD (Create-Read-Update-Delete) islemlerini yapan ortak servis.
 * 
 * <p/>
 * Tanitim formlarinin basvurduklari servisler bu sinifi extends edecekler
 *
 * <p/>
 * Suan icin java, generic lerin sinif adini vermiyor, hibernate buna ihtiyac 
 * duydugu icin mecburen yapilandirici kanali ile clientClass ve serverClass 
 * bilgilerini aliyoruz
 * 
 * @param <C> Client model sinifi
 * @param <S> Server model sinifi
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings( { "serial", "unchecked" })
public class CRUDImpl<S extends Serializable, C extends IModel> extends RemoteServiceServlet {

	/**
	 * Client tarafindaki modelin class bilgisi
	 * 
	 */
	private Class<C> clientClass;

	/**
	 * Server tarafindaki modelin class bilgisi
	 * 
	 */
	private Class<S> serverClass;

	public CRUDImpl() {
		;
	}

	/**
	 * Anlamsiz olmasina ragmen kullanmak zorunda kaldigim yerlerden biri
	 * 
	 * @param clientClass
	 * @param serverClass
	 */
	public CRUDImpl(Class<S> serverClass, Class<C> clientClass) {
		this.serverClass = serverClass;
		this.clientClass = clientClass;
	}

	/**
	 * Model listesi
	 * 
	 * Simdilik numeric ve string sahalar icin, esit ve yakin aramalar yapiyor
	 * 
	 * TODO: Gelistirilecek!
	 * 
	 * @param filter kayit eleme yapilacaksa bu parametre kullanilacak
	 * 
	 * @return List client model listesi
	 */
	public List<C> getModelList(FilterItem masterFilter, FilterItem filter) {
		Session session = DBUtils.getSession();
		
		try {
			session.beginTransaction();
			
			StringBuilder querySB = new StringBuilder("from ");
			querySB.append(serverClass.getSimpleName());
			
			if (masterFilter != null 
				&& masterFilter.getValue() != null
				&& ! masterFilter.getValue().equals("")) {
				
				querySB.append(" where ");
				querySB.append(masterFilter.getFieldName());
				
				if (masterFilter.getSearchType().equals(SearchType.EQUAL)) {
					querySB.append(" = ");
					querySB.append(masterFilter.getValue());
				} else {
					querySB.append(" like '");
					querySB.append(masterFilter.getValue());
					querySB.append("%'");
				}
				
			}
			
			if (filter != null 
				&& filter.getValue() != null
				&& !filter.getValue().equals("")) {
				
				if (masterFilter != null)
					querySB.append(" and ");
				else
					querySB.append(" where ");
				
				querySB.append(filter.getFieldName());
				
				if (filter.getSearchType().equals(SearchType.EQUAL)) {
					querySB.append(" = ");
					querySB.append(filter.getValue());
				} else {
					querySB.append(" like '");
					querySB.append(filter.getValue());
					querySB.append("%'");
				}
				
			}
			
			Query query = session.createQuery(querySB.toString());
			if (getMaxResultSize() > 0) query.setMaxResults(getMaxResultSize());
			
			List<S> serverModelList = query.list();
			
			List<C> result = new ArrayList<C>(serverModelList.size());
			for (S serverModel : serverModelList) {
				result.add(DBUtils.getMapper().map(serverModel, clientClass));
			}
	
			session.getTransaction().commit();
			return result;
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			return null;
		}
	}
	
	/**
	 * Grid uzerinde gosterilecek maksimum kayit sayisi,
	 * 
	 *<p/>
	 * 0 verilirse tum kayitlar donulur
	 * 
	 * @return gosterilecek kayit sayisi siniri
	 */
	public int getMaxResultSize() {
		return 50;
	}

	/**
	 * Kayit silme islemi
	 * 
	 * @param clientModel
	 * 
	 * @return RetVal sonuc nesnesi
	 */
	public RetVal remove(C clientModel) {
		RetVal result = new RetVal();
		
		Session session = DBUtils.getSession();
		try {
			session.beginTransaction();
			
			S model = DBUtils.getMapper().map(clientModel, serverClass);
			
			RetVal before = beforeRemove(session, model);
			if (before != null) {
				session.getTransaction().rollback();
				return before;
			}
			
			session.delete(model);
			
			RetVal after = afterRemove(session, model);
			if (after != null) {
				session.getTransaction().rollback();
				return after;
			}
			
			session.getTransaction().commit();
			result.setEffectedRows(1);
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			
			if (e.getCause() != null && e.getCause().getMessage() != null)
				result.setMessage(e.getCause().getMessage());
			else
				result.setMessage(e.getMessage());
			result.setEffectedRows(0);
		}

		return result;
	}

	/**
	 * Kayit isleme islemi (insert ve update)
	 * 
	 * @param clientModel
	 * 
	 * @return RetVal sonuc nesnesi
	 */
	public RetVal save(C clientModel, List<EffectedDetail> effectedDetailList) {
		RetVal result = new RetVal();
		
		Session session = DBUtils.getSession();
		try {
			session.beginTransaction();
			
			S model = DBUtils.getMapper().map(clientModel, serverClass);
			
			RetVal before = beforeSave(session, model, effectedDetailList);
			if (before != null) {
				session.getTransaction().rollback();
				return before;
			}
			
			session.saveOrUpdate(model);

			RetVal after = afterSave(session, model, effectedDetailList);
			if (after != null) {
				session.getTransaction().rollback();
				return after;
			}
			clearJunkDetailModels(session, effectedDetailList);
			
			session.getTransaction().commit();
			result.setEffectedRows(1);
			
		} catch (Exception e) {
			session.getTransaction().rollback();
			
			if (e.getCause() != null && e.getCause().getMessage() != null)
				result.setMessage(e.getCause().getMessage());
			else
				result.setMessage(e.getMessage());
			result.setEffectedRows(0);
		}

		return result;
	}
	
	/**
	 * Miras alan siniflarda, kayit isleminden hemen once yapilmasi gerekenler icin
	 * "kopruden once son cikis" levhasi niyetine metoddur
	 * 
	 * @param session
	 * @param model
	 * @param effectedDetails - eklenen, duzenlenen ve silinen detaylarin listesi
	 * 
	 * @return RetVal sonuc nesnesi
	 */
	public RetVal beforeSave(Session session, S serverModel, List<EffectedDetail> effectedDetailList) {
		return null;
	}
	
	/**
	 * Kayit isleminden hemen sonra tetiklenen metoddur
	 * 
	 * @param session
	 * @param model
	 * 
	 * @return RetVal sonuc nesnesi
	 */
	public RetVal afterSave(Session session, S serverModel, List<EffectedDetail> effectedDetailList) {
		return null;
	}
	
	/**
	 * Miras alan siniflarda, silme isleminden hemen once yapilmasi gerekenler icin
	 * "kopruden once son cikis" levhasi niyetine olan metodun ikincisidir, digeri beforeSave
	 * 
	 * @param session
	 * @param model
	 * 
	 * @return RetVal sonuc nesnesi
	 */
	public RetVal beforeRemove(Session session, S serverModel) {
		return null;
	}
	
	/**
	 * Silme isleminden hemen sonra tetiklenen metoddur
	 * 
	 * @param session
	 * @param model
	 * 
	 * @return RetVal sonuc nesnesi
	 */
	public RetVal afterRemove(Session session, S serverModel) {
		return null;
	}
	
	/**
	 * Master kaydin detaylarindan silinen bir model varsa Detail tablosundan da silinmeli
	 * Aslinda bu isi hibernate yapmali ama arada dozer oldugu icin biz yapmak zorunda kaliyoruz
	 * 
	 */
	private void clearJunkDetailModels(Session session, List<EffectedDetail> effectedDetailList) {
		if (effectedDetailList != null && effectedDetailList.size() > 0) {
			
			for (int i=0; i<effectedDetailList.size(); i++) {
				
				EffectedDetail effectedDetail = effectedDetailList.get(i);
				List<IModel> deletedList = effectedDetail.getDeletedList();
				
				if (deletedList.size() > 0) {
					List<Long> ids = new ArrayList<Long>();
					for (IModel model: deletedList) {
						ids.add(model.getId());
					}
					session.createQuery("delete from " + effectedDetail.getModelName() + " m " +
										"where m.id IN (:ids)")
									.setParameterList("ids", ids)
								.executeUpdate();
				}
			}
			
		}
	}

}
