package com.mclub.client.forms.info;

import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.gmvc.client.base.AbstractEditor;
import com.gmvc.client.base.IController;
import com.gmvc.client.util.Tags;
import com.mclub.client.model.MovieGroupDTO;

/**
 * Film Gurubu Tanitimi, Editor sinifi
 * 
 * @see AbstractEditor
 * 
 * @author mdpinar
 * 
 */
public class MovieGroupEditor extends AbstractEditor<MovieGroupDTO> {

	/**
	 * Ad alani
	 * 
	 */
	private TextField<String> nameText;

	public MovieGroupEditor(IController<MovieGroupDTO> controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MovieGroupDTO getNewModel() {
		return new MovieGroupDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Size getSize() {
		return new Size(350, 110);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String isValid() {
		StringBuilder sb = new StringBuilder();
		if (nameText.getValue() == null || nameText.getValue().isEmpty())
			sb.append(Tags.get("nameCanNotBeEmpty"));
		return sb.toString();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("movieGroupTitle");
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
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void popFields() {
		nameText.setValue(model.getName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		nameText = new TextField<String>();
		nameText.setFieldLabel(Tags.get("name"));
		nameText.setName("name");

		addFieldToForm(nameText);
		addWidgetToPanel(getFormPanel(), new RowData(1, 1, new Margins(4)));
	}

}
