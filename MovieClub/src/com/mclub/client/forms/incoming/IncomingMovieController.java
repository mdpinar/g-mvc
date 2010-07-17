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
package com.mclub.client.forms.incoming;

import java.util.List;

import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.gmvc.client.base.AbstractBaseController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.Right;
import com.gmvc.client.service.ICRUDServiceAsync;
import com.gmvc.client.util.Tags;
import com.gmvc.client.util.Utils;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.mclub.client.app.Rights;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.image.Images;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.model.IncomingMovieDTO;
import com.mclub.client.model.IncomingMovieDetailDTO;
import com.mclub.client.service.IncomingMovieService;
import com.mclub.client.service.IncomingMovieServiceAsync;

/**
 * Gelen filmler, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class IncomingMovieController extends AbstractBaseController<IncomingMovieDTO> {

	/**
	 * Editor kismi
	 */
	private IncomingMovieEditor editor;
	
	/**
	 * Server tarafi iletisim servisi
	 */
	private IncomingMovieServiceAsync service = GWT.create(IncomingMovieService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IncomingMovieBrowser createBrowser() {
		return new IncomingMovieBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IncomingMovieEditor createEditor() {
		editor = new IncomingMovieEditor(this);
		return editor;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICRUDServiceAsync<IncomingMovieDTO> getService() {
		return this.service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void beforeEvent(Event event) {
		switch (event.getType()) {

			case SpecialEvent.SaveAndClose:
			case SpecialEvent.SaveAndContinue: {
				IncomingMovieDTO incomingMovie = (IncomingMovieDTO) event.getModel();
				if (incomingMovie.getDetails().size() < 1) {
					event.setCancel(true);
					Utils.showAlert(Tags.get("haveToAddMinDetail"));
					break;
				}
				break;
			}
		
			case SpecialEvent.Delete: {
				IncomingMovieDTO incomingMovie = (IncomingMovieDTO) event.getModel();
				for (IncomingMovieDetailDTO detail: incomingMovie.getDetails()) {
					if (! detail.getTransId().equals(detail.getMovie().getLastTransId())) {
						event.setCancel(true);
						Utils.showAlert(Tags.get("cantDeleteThisMovie"));
						break;
					}
				}
				
			}
			
		}
	}
	
	@Override
	public void holdSpecialEvent(final Event event) {
		switch (event.getType()) {
		
			case SpecialEvent.Overall: {
				BeanModel bm = Utils.convertToBeanModel(event.getModel());
				if (! editor.cbListView.getStore().contains(bm)) editor.cbListView.getStore().add(bm);
				break;
			}
		
			case SpecialEvent.LoadMovie: {
				
				RpcProxy<List<IncomingMovieDetailDTO>> proxy = new RpcProxy<List<IncomingMovieDetailDTO>>() {
					@Override
					public void load(Object loadConfig, AsyncCallback<List<IncomingMovieDetailDTO>> callback) {
						service.getMovieList((CustomerDTO) event.getModel(), callback);
					}
				};
				ListLoader<ListLoadResult<IncomingMovieDetailDTO>> loader = 
					new BaseListLoader<ListLoadResult<IncomingMovieDetailDTO>>(proxy, new BeanModelReader());
				ListStore<BeanModel> store = new ListStore<BeanModel>(loader);
				loader.load();
				
				editor.cbListView.setStore(store);
				break;
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Right getRight() {
		return Rights.IncomingMovie;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 670;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageResource getIcon() {
		return Images.Instance.repeat();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("incomingMovies");
	}

}
