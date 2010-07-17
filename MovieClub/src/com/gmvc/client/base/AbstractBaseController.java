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

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Info;
import com.gmvc.client.meta.EffectedDetail;
import com.gmvc.client.meta.FilterItem;
import com.gmvc.client.meta.RetVal;
import com.gmvc.client.service.ICRUDServiceAsync;
import com.gmvc.client.util.Tags;
import com.gmvc.client.util.Utils;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Model-View-Controller uclusundeki standart Controller siniflari icin hazir
 * yapi sunan soyut siniftir
 * 
 * <p/>
 * Model-View yapilari arasindaki mantiksal islemlerin yapildigi siniflardir.
 * Bir nevi orkestra sefi denilebilir. Kod tekrarlarinin onune gecebilmek icin
 * kendisini miras alacak olan siniflar adina ortak olan metodlari
 * gerceklestirir.
 * 
 * @see IModel
 * @see AbstractController
 * 
 * @author mdpinar
 * 
 */
public abstract class AbstractBaseController<M extends IModel> extends AbstractController<M> {

	/**
	 * Veri katmanina bu metoddan gelen servis ile erisilir. Model e dair
	 * yapilacak islemlerinin server tarafindaki asenkron kullanilan servisidir.
	 * 
	 * <p/>
	 * Bu tip servisler genel olarak CRUD (Create-Read-Update-Delete)
	 * islemlerini yapar.
	 * 
	 * @see ICRUDServiceAsync asenkron CRUD servisi
	 */
	public abstract ICRUDServiceAsync<M> getService();
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void save(M model, final boolean refresh, List<EffectedDetail> effectedDetailList) {
		getService().save(model, effectedDetailList, new AsyncCallback<RetVal>() {
			@Override
			public void onFailure(Throwable caught) {
				Utils.showError(caught);
			}

			@Override
			public void onSuccess(RetVal arg0) {
				if (arg0.getEffectedRows() > 0) {
					Info.display(Tags.get("info"), Tags.get("successSaveMessage"));
					if (refresh) {
						if (getEditor() != null) getEditor().cancel();
						refresh(null);
					} else {
						if (getEditor() != null) getEditor().popup(null);
					}
				} else {
					if (arg0.getEffectedRows() == 0)
						Utils.showAlert(arg0.getMessage());
					else
						Utils.showAlert(Tags.get(arg0.getMessage()));
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void remove(final M model) {
		getService().remove(model, new AsyncCallback<RetVal>() {
			@Override
			public void onFailure(Throwable caught) {
				Utils.showError(caught);
			}

			@Override
			public void onSuccess(RetVal arg0) {
				if (arg0.getEffectedRows() > 0) { 
					Info.display(Tags.get("info"), Tags.get("successRemoveMessage"));
					getBrowser().getGrid().getStore().remove(Utils.convertToBeanModel(model));
				} else {
					if (arg0.getEffectedRows() == 0)
						Utils.showAlert(arg0.getMessage());
					else
						Utils.showAlert(Tags.get(arg0.getMessage()));
				}
			}
		});
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void refresh(final FilterItem filter) {
		RpcProxy<List<M>> proxy = new RpcProxy<List<M>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<List<M>> callback) {
				getService().getModelList(getBrowser().getMasterFilterItem(), filter, callback);
			}
		};
		ListLoader<ListLoadResult<M>> loader = new BaseListLoader<ListLoadResult<M>>(proxy, new BeanModelReader());
		ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
		loader.load();

		getBrowser().refresh(store);
	}

	/**
	 * Sadece MiniMVC yapisinda kullanacagi icin bu sinifi miras alan siniflar
	 * icin <strong>kullanilamayacak</strong> hale getiriliyor
	 */
	@Override
	void add(M model) {
		;
	}

	/**
	 * Sadece MiniMVC yapisinda kullanacagi icin bu sinifi miras alan siniflar
	 * icin <strong>kullanilamayacak</strong> hale getiriliyor
	 */
	@Override
	void update(M model) {
		;
	}

	/**
	 * Sadece MiniMVC yapisinda kullanacagi icin bu sinifi miras alan siniflar
	 * icin <strong>kullanilamayacak</strong> hale getiriliyor
	 */
	@Override
	void doUpdates() {
		;
	}

	/**
	 * Sadece MiniMVC yapisinda kullanacagi icin bu sinifi miras alan siniflar
	 * icin <strong>kullanilamayacak</strong> hale getiriliyor
	 */
	@Override
	void setModelList(List<M> modelList) {
		;
	}

}
