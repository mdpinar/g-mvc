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
package com.mclub.client.forms.given;

import java.util.Arrays;

import com.extjs.gxt.ui.client.Style.Orientation;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.layout.RowLayout;
import com.gmvc.client.base.AbstractEditor;
import com.gmvc.client.base.IController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.ImageItem;
import com.gmvc.client.util.Tags;
import com.gmvc.client.util.Utils;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.enums.FilmStatus;
import com.mclub.client.model.CustomerDTO;
import com.mclub.client.model.GivenMovieDTO;
import com.mclub.client.model.GivenMovieDetailDTO;
import com.mclub.client.model.MovieDTO;
import com.mclub.client.util.GUIHelper;

/**
 * Film verme kismi, Editor sinifi
 * 
 * @see AbstractEditor
 * 
 * @author mdpinar
 * 
 */
public class GivenMovieEditor extends AbstractEditor<GivenMovieDTO> {

	/**
	 * Bu formda istenmeyen filmler
	 */
	private FilmStatus[] unwantedStatus;
	
	/**
	 * Eklenecek filmler icin mini kontrolor
	 */
	private IController<GivenMovieDetailDTO> miniController;
	
	/**
	 * Filmi verecegimiz musteri, 
	 * kontrolorden erisilip yukleme yapilabilsin diye protected yapildi
	 * 
	 */
	protected ComboBox<BeanModel> customerCombo;
	
	/**
	 * Aciklama alani
	 * 
	 */
	private TextField<String> descText;
	
	/**
	 * Film secim combosu
	 * 
	 */
	private ComboBox<BeanModel> movieCombo;
	
	/**
	 * Durum secim combosu
	 * 
	 */
	private ComboBox<ImageItem> statusCombo;
	
	public GivenMovieEditor(IController<GivenMovieDTO> controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public GivenMovieDTO getNewModel() {
		return new GivenMovieDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Size getSize() {
		return new Size(450, 420);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String isValid() {
		StringBuilder sb = new StringBuilder();
		if (customerCombo.getValue() == null)
			sb.append(Tags.get("customerCanNotBeEmpty"));
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("givenMovies");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<?> getFirstField() {
		return customerCombo;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pushFields() {
		model.setCustomer((CustomerDTO) customerCombo.getValue().getBean());
		model.setDescription(descText.getValue());
		
		miniController.fireEvent(new Event(SpecialEvent.MiniDoUpdates));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void popFields() {
		customerCombo.setEditable(model.getId() == null);
		
		if (model.getCustomer() != null) {
			customerCombo.setValue(Utils.convertToBeanModel(model.getCustomer()));
		} else {
			customerCombo.setValue(null);
		}

		descText.setValue(model.getDescription());
		
		miniController.fireEvent(
			new Event(SpecialEvent.MiniSetModels)
				.addParam("modelList", model.getDetails())
		);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		
		miniController = new GivenMovieMiniController(getController());
		
		customerCombo = GUIHelper.getCustomerSuggestCombo();
		addFieldToForm(customerCombo);
		
		descText = new TextField<String>();
		descText.setFieldLabel(Tags.get("description"));
		descText.setName("desc");
		addFieldToForm(descText);

		getMainPanel().setLayout(new RowLayout(Orientation.VERTICAL));
		
		addWidgetToPanel(getFormPanel(), new RowData(1, -1, new Margins(4)));
		addWidgetToPanel(buildAddMoviePanel(), new RowData(1, 1, new Margins(0, 4, 0, 4)));
	}

	/**
	 * Film ekleme panelini bina eder
	 * 
	 */
	private FormPanel buildAddMoviePanel() {
		FormData formData = new FormData("-10");
		FormPanel moviePanel = new FormPanel();
		moviePanel.setHeaderVisible(false);
		moviePanel.setLabelAlign(LabelAlign.RIGHT);
		moviePanel.setLabelWidth(100);
		moviePanel.setFrame(true);
		moviePanel.setHeight(200);
		moviePanel.setHeaderVisible(false);

		unwantedStatus = new FilmStatus[] {FilmStatus.IN_STORE};
		
		statusCombo = GUIHelper.getComboFilmStatus(Arrays.asList(unwantedStatus));
		moviePanel.add(statusCombo, formData);
		
		movieCombo = GUIHelper.getMovieSuggestCombo(unwantedStatus);
		movieCombo.addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
				if (se != null && se.getSelectedItem() != null) {
					FilmStatus status = null;
					MovieDTO movie = se.getSelectedItem().getBean();

					if (statusCombo.getValue() != null) status = (FilmStatus) statusCombo.getValue().getEnumVal();
					
					if (movie != null && status != null) {
						GivenMovieDetailDTO givenMovie = new GivenMovieDetailDTO();
						givenMovie.setStatus(status);
						givenMovie.setMovie(movie);
						miniController.fireEvent(new Event(SpecialEvent.AddNewModel, givenMovie));
						miniController.fireEvent(new Event(SpecialEvent.Refresh));
						
						movieCombo.setValue(null);
					}
				}
			}
			
		});
		moviePanel.add(movieCombo, formData);
 		
		miniController.getBrowser().getGrid().setHeight(170);
		moviePanel.add(miniController.getBrowser().getGrid());
		
		return moviePanel;
	}
	
}
