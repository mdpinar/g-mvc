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
package com.mclub.client.forms.changeStatus;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.gmvc.client.base.AbstractEditor;
import com.gmvc.client.base.IController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.ImageItem;
import com.gmvc.client.util.Tags;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.enums.FilmStatus;
import com.mclub.client.model.ChangeStatusDTO;
import com.mclub.client.model.ChangeStatusDetailDTO;
import com.mclub.client.model.MovieDTO;
import com.mclub.client.util.GUIHelper;

/**
 * Durumu degisen filmler, Editor sinifi
 * 
 * @see AbstractEditor
 * 
 * @author mdpinar
 * 
 */
public class ChangeStatusEditor extends AbstractEditor<ChangeStatusDTO> {

	/**
	 * Eklenmis filmleri duzenleyen mini kontrolor
	 * 
	 */
	private IController<ChangeStatusDetailDTO> miniController;
	
	/**
	 * Aciklama alani
	 * 
	 */
	private TextField<String> descText;
	
	/**
	 * Film secme combosu
	 * 
	 */
	private ComboBox<BeanModel> movieCombo;
	
	/**
	 * Yeni durum combosu
	 * 
	 */
	private ComboBox<ImageItem> statusCombo;
	
	public ChangeStatusEditor(IController<ChangeStatusDTO> controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ChangeStatusDTO getNewModel() {
		return new ChangeStatusDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Size getSize() {
		return new Size(450, 350);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String isValid() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("changeStatus");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<?> getFirstField() {
		return descText;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pushFields() {
		model.setDescription(descText.getValue());
		miniController.fireEvent(new Event(SpecialEvent.MiniDoUpdates));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void popFields() {
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
		miniController = new ChangeStatusMiniController(getController());
		addWidgetToPanel(buildAddMoviePanel(), new RowData(1, 1));
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

		descText = new TextField<String>();
		descText.setFieldLabel(Tags.get("description"));
		descText.setName("desc");
		moviePanel.add(descText, formData);
		
		statusCombo = GUIHelper.getComboFilmStatus(null);
		moviePanel.add(statusCombo, formData);
		
		movieCombo = GUIHelper.getMovieSuggestCombo(null);
		movieCombo.addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
				if (se != null && se.getSelectedItem() != null) {
					FilmStatus status = null;
					MovieDTO movie = se.getSelectedItem().getBean();

					if (statusCombo.getValue() != null) status = (FilmStatus) statusCombo.getValue().getEnumVal();
					
					if (movie != null && status != null) {
						ChangeStatusDetailDTO detail = new ChangeStatusDetailDTO();
						detail.setStatus(status);
						detail.setMovie(movie);
						miniController.fireEvent(new Event(SpecialEvent.AddNewModel, detail));
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
