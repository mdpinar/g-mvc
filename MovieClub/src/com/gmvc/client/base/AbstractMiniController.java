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
package com.gmvc.client.base;

import java.util.ArrayList;
import java.util.List;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.store.ListStore;
import com.gmvc.client.enums.RightGroup;
import com.gmvc.client.enums.TempRowStatus;
import com.gmvc.client.meta.EffectedDetail;
import com.gmvc.client.meta.FilterItem;
import com.gmvc.client.meta.Right;
import com.gmvc.client.meta.TempRow;
import com.gmvc.client.util.Utils;
import com.google.gwt.resources.client.ImageResource;


/**
 * MVC deki standart Controller den farkli bir yapidir; Kapsam olarak standart
 * MVC nin altinda yer alir. Gecici modeller ile calisir. MiniController
 * siniflari icin hazir yapi sunan soyut siniftir
 * 
 * @see IModel
 * @see AbstractController
 * 
 * @author mdpinar
 * 
 */
public abstract class AbstractMiniController<M extends IModel> extends AbstractController<M> {

	/**
	 * Bu Controller i kapsayan parent in referansi
	 * 
	 */
	private IController<?> parent;

	/**
	 * <strong><i>Orijinal</i></strong> model listesi
	 * 
	 */
	protected List<M> realModelList;

	/**
	 * Uzerinde calisilan <strong><i>Gecici</i></strong> model listesi
	 * 
	 */
	private List<M> tempModelList;

	/**
	 * Her satir icin, model ile beraber durumunu tutan Map
	 * 
	 * @see TempRow
	 */
	private List<TempRow<M>> tempRows;
	
	/**
	 * Modelin detaylarinda yapilan degisiklikleri tutan EffectedDetail siniflarinin,
	 * detay modelleri birbirinden ayirabilmeleri icin kullanilan isim degiskenidir
	 * 
	 * <p/>
	 * Acikcasi: Bir master da birbirinden farkli detay model listeleri olabilir
	 * herbir detay listesinin kendine has bir degisiklikler listesi olmasi gerekir.
	 * Bu degisken yardimi ile detaylari birbirinden ayirabiliyoruz.
	 * 
	 */
	private String miniName;
	
	/**
	 * Bagli olacagi parent kontroleri ile adi alinarak ayaga kalkmali
	 * 
	 * @param parent
	 * @param miniName
	 */
	public AbstractMiniController(IController<?> parent, String miniName) {
		this.parent = parent;
		this.miniName = miniName;
	}
	
	/**
	 * Bagli bulundugu parent controller i doner
	 */
	protected IController<?> getParent() {
		return parent;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Right getRight() {
		return new Right("Void", RightGroup.System);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageResource getIcon() {
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void add(M model) {
		tempModelList.add(model);
		
		TempRow<M> tempRow = null;
		if (model.getId() != null) tempRow = findTempRowByModel(model);
		
		if (tempRow != null) {
			if (model.getId() != null)
				tempRow.setStatus(TempRowStatus.UPDATE);
			else
				tempRow.setStatus(TempRowStatus.ADD);
			tempRows.set(tempRow.getIndex(), tempRow);
		} else {
			tempRow = new TempRow<M>(tempModelList.size() - 1, model, TempRowStatus.ADD);
			tempRows.add(tempRow);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void update(M model) {
		if (model.getId() != null) {
			TempRow<M> tempRow = findTempRowByModel(model);
			if (tempRow != null) {
				tempRow.setStatus(TempRowStatus.UPDATE);
				tempRows.set(tempRow.getIndex(), tempRow);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void remove(M model) {
		tempModelList.remove(model);
		
		if (model.getId() != null) {
			TempRow<M> tempRow = findTempRowByModel(model);
			if (tempRow != null) {
				tempRow.setStatus(TempRowStatus.REMOVE);
				tempRows.set(tempRow.getIndex(), tempRow);
			}
		}
		getBrowser().getGrid().getStore().remove(Utils.convertToBeanModel(model));
	}

	/**
	 * Ayni duzenlemeler tekrar edebilir. rempRows uzerinde bu karmasikliga sebep olur.
	 * Onlemek icin, degisiklikten once ilgili row (varsa) bulunur ve statusu update edilir.
	 * <p/>
	 * Bu metod, verilen model in tempRow unu doner.
	 */
	private TempRow<M> findTempRowByModel(M model) {
		for (int i=0; i<tempRows.size(); i++) {
			TempRow<M> tr = tempRows.get(i);
			if (tr.getModel().equals(model)) {
				tr.setIndex(i);
				return tr;
			}
		}
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setModelList(List<M> modelList) {
		if (modelList != null) {
			this.realModelList = modelList;
			int modelSize = this.realModelList.size();
			tempModelList = new ArrayList<M>(modelSize);
			tempRows = new ArrayList<TempRow<M>>(modelSize);
			
			for (int i = 0; i < modelSize; i++) {
				M model = this.realModelList.get(i);
				tempModelList.add(model);
				tempRows.add(new TempRow<M>(i, model, TempRowStatus.UNCHANGED));
			}
		} else {
			tempModelList = new ArrayList<M>();
			tempRows = new ArrayList<TempRow<M>>();
		}
		refresh(null);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void doUpdates() {
		List<IModel> addedList = new ArrayList<IModel>();
		List<IModel> updatedList = new ArrayList<IModel>();
		List<IModel> deletedList = new ArrayList<IModel>();
		
		realModelList.clear();
		for (TempRow<M> tempRow : tempRows) {
			
			if (! tempRow.getStatus().equals(TempRowStatus.REMOVE)) {
				realModelList.add(tempRow.getModel());
			}
			
			switch (tempRow.getStatus()) {
				case ADD: {
					addedList.add(tempRow.getModel());
					break;
				}
				case UPDATE: {
					updatedList.add(tempRow.getModel());
					break;
				}
				case REMOVE: {
					deletedList.add(tempRow.getModel());
					break;
				}
			}
		}
		
		EffectedDetail effectedDetail = new EffectedDetail(miniName);
		
		effectedDetail.setAddedList(addedList);
		effectedDetail.setUpdatedList(updatedList);
		effectedDetail.setDeletedList(deletedList);
		
		getParent().addEffectedDetail(effectedDetail);
	}
	
	/**
	 * Genel MVC yapisinda kullanacagi icin bu sinifi miras alan siniflar
	 * icin <strong>kullanilamayacak</strong> hale getiriliyor
	 */
	@Override
	void save(M model, boolean refresh, List<EffectedDetail> effectedDetailList) {
		;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void refresh(final FilterItem filter) {
		ListStore<BeanModel> store = new ListStore<BeanModel>();
		
		if (tempModelList != null) {
			for (M model: tempModelList) {
				store.add(Utils.convertToBeanModel(model));
			}
		}
		
		getBrowser().refresh(store);
	}

}
