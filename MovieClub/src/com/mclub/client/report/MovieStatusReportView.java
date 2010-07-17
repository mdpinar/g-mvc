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
package com.mclub.client.report;

import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.gmvc.client.base.AbstractReportView;
import com.gmvc.client.base.IReportController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.ImageItem;
import com.gmvc.client.util.Tags;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.enums.MediaType;
import com.mclub.client.model.MovieGroupDTO;
import com.mclub.client.util.GUIHelper;

/**
 * Film Durum raporunun View'i
 * 
 * @see AbstractReportView
 * 
 * @author mdpinar
 * 
 */
public class MovieStatusReportView extends AbstractReportView {

	/**
	 * Musteri gurubu secim combosu
	 * 
	 */
	protected SimpleComboBox<MovieGroupDTO> movieGroup;
	
	/**
	 * Isim
	 */
	private TextField<String> name;
	
	/**
	 * Yonetmen
	 * 
	 */
	private TextField<String> director;
	
	/**
	 * Cekim yili
	 * 
	 */
	private NumberField prodYear;
	
	/**
	 * Urun tipi
	 * 
	 */
	private ComboBox<ImageItem> mediaType;
	
	/**
	 * Son Durumu
	 * 
	 */
	private ComboBox<ImageItem> statusCombo;

	public MovieStatusReportView(IReportController controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getReportName() {
		return "MovieStatusReport";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void buildPanel() {
		name = new TextField<String>();
		name.setFieldLabel(Tags.get("name"));
		name.setName("name");
		addWidget(name);

		statusCombo = GUIHelper.getComboFilmStatus(null);
		statusCombo.setValue(null);
		statusCombo.setForceSelection(false);
		addWidget(statusCombo);

		director = new TextField<String>();
		director.setFieldLabel(Tags.get("director"));
		director.setName("name");
		addWidget(director);

		prodYear = new NumberField();
		prodYear.setFieldLabel(Tags.get("prodYear"));
		prodYear.setName("prodYear");
		addWidget(prodYear);

		mediaType = GUIHelper.getComboMediaType();
		mediaType.setValue(new ImageItem(MediaType.DVD));
		addWidget(mediaType);

		movieGroup = new SimpleComboBox<MovieGroupDTO>();
		movieGroup.setFieldLabel(Tags.get("group"));
		movieGroup.setName("movieGroup");
		movieGroup.setForceSelection(true);
		movieGroup.setTypeAhead(true);
		movieGroup.setTriggerAction(TriggerAction.ALL);
		addWidget(movieGroup);
		
		getController().fireEvent(new Event(SpecialEvent.LoadMovieGroup));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getQueryString() {
		StringBuilder queryBuilder = new StringBuilder("");

		if (name.getValue() != null && ! name.getValue().equals("")) {
			queryBuilder.append(" and mi.name >= '");
			queryBuilder.append(name.getValue());
			queryBuilder.append("'");
		}

		
		if (statusCombo.getValue() != null) {
			queryBuilder.append(" and m.lastStatus = '");
			queryBuilder.append(statusCombo.getValue().getEnumVal().name());
			queryBuilder.append("'");
		}

		if (director.getValue() != null) {
			queryBuilder.append(" and mi.director like '%");
			queryBuilder.append(director.getValue());
			queryBuilder.append("%'");
		}

		if (prodYear.getValue() != null) {
			queryBuilder.append(" and mi.prodYear = ");
			queryBuilder.append(prodYear.getValue());
		}

		if (mediaType.getValue() != null) {
			queryBuilder.append(" and mi.mediaType = '");
			queryBuilder.append(mediaType.getValue().getEnumVal().toString());
			queryBuilder.append("'");
		}

		if (movieGroup.getValue() != null && movieGroup.getSimpleValue() != null) {
			queryBuilder.append(" and mi.movieGroup_id = ");
			queryBuilder.append(movieGroup.getSimpleValue().getId());
		}

		return queryBuilder.toString();
	}

}
