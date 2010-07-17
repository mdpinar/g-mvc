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
package com.mclub.client.forms.info;

import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.gmvc.client.base.AbstractEditor;
import com.gmvc.client.base.IController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.ImageItem;
import com.gmvc.client.util.Tags;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.enums.MediaType;
import com.mclub.client.model.MovieDTO;
import com.mclub.client.model.MovieGroupDTO;
import com.mclub.client.model.MovieInfoDTO;
import com.mclub.client.util.GUIHelper;

/**
 * Film Tanitimi, Editor sinifi
 * 
 * @see AbstractEditor
 * 
 * @author mdpinar
 * 
 */
public class MovieInfoEditor extends AbstractEditor<MovieInfoDTO> {

	/**
	 * Kopya filmleri yoneten mini kontrolor
	 * 
	 */
	private IController<MovieDTO> miniController;
	
	/**
	 * Film gurubu secim combosu, kontrolor den erisiliyor
	 * 
	 */
	protected SimpleComboBox<MovieGroupDTO> movieGroup;

	/**
	 * Isism alani
	 * 
	 */
	private TextField<String> nameText;
	
	/**
	 * Yonetmen alani
	 * 
	 */
	private TextField<String> directorText;
	
	/**
	 * Cekim yili alani
	 * 
	 */
	private NumberField prodYear;
	
	/**
	 * Oyuncular alani
	 * 
	 */
	private TextArea actors;
	
	/**
	 * Urun tipi alani (dvd, vcd, diger)
	 * 
	 */
	private ComboBox<ImageItem> mediaType;

	public MovieInfoEditor(IController<MovieInfoDTO> controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MovieInfoDTO getNewModel() {
		return new MovieInfoDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Size getSize() {
		return new Size(500, 500);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String isValid() {
		StringBuilder sb = new StringBuilder();
		
		if (nameText.getValue() == null || nameText.getValue().isEmpty())
			sb.append(Tags.get("nameCanNotBeEmpty"));
		
		if (miniController.getBrowser().getSelectedModel() == null)
			sb.append(Tags.get("haveToAddMinCopy"));
		
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("movieInfoTitle");
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<?> getFirstField() {
		return nameText;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pushFields() {
		model.setName(nameText.getValue());
		model.setDirector(directorText.getValue());
		model.setActors(actors.getValue());

		model.setProdYear(prodYear.getValue() != null ? prodYear.getValue().intValue() : 0);
		model.setMediaType(mediaType.getValue() != null 
				? (MediaType) mediaType.getValue().getEnumVal() : null);
		
		model.setMovieGroup(movieGroup.getValue() != null ? movieGroup.getSimpleValue() : null);

		miniController.fireEvent(new Event(SpecialEvent.MiniDoUpdates));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void popFields() {
		nameText.setValue(model.getName());
		directorText.setValue(model.getDirector());
		actors.setValue(model.getActors());
		prodYear.setValue(model.getProdYear());

		mediaType.setValue(model.getMediaType() != null ? new ImageItem(model.getMediaType()) : null);

		miniController.fireEvent(
			new Event(SpecialEvent.MiniSetModels)
				.addParam("modelList", model.getMovies())
		);
		
		getController().fireEvent(new Event(SpecialEvent.LoadMovieGroup, model));
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {

		miniController = new MovieMiniController(getController());

		nameText = new TextField<String>();
		nameText.setFieldLabel(Tags.get("name"));
		nameText.setName("name");
		addFieldToForm(nameText);

		directorText = new TextField<String>();
		directorText.setFieldLabel(Tags.get("director"));
		directorText.setName("name");
		addFieldToForm(directorText);

		prodYear = new NumberField();
		prodYear.setFieldLabel(Tags.get("prodYear"));
		prodYear.setName("prodYear");
		addFieldToForm(prodYear);

		mediaType = GUIHelper.getComboMediaType();
		addFieldToForm(mediaType);

		movieGroup = new SimpleComboBox<MovieGroupDTO>();
		movieGroup.setFieldLabel(Tags.get("group"));
		movieGroup.setName("movieGroup");
		movieGroup.setForceSelection(true);
		movieGroup.setTypeAhead(true);
		movieGroup.setTriggerAction(TriggerAction.ALL);
		addFieldToForm(movieGroup);

		actors = new TextArea();
		actors.setPreventScrollbars(true);
		actors.setFieldLabel(Tags.get("actors"));
		actors.setName("actors");
		addFieldToForm(actors);

		addWidgetToForm(miniController.getBrowser().popup());
		
		addWidgetToPanel(getFormPanel(), new RowData(1, 1));
	}

}
