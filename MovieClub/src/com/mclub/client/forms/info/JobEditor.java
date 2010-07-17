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

import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.util.Size;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.gmvc.client.base.AbstractEditor;
import com.gmvc.client.base.IController;
import com.gmvc.client.util.Tags;
import com.mclub.client.model.JobDTO;

/**
 * Meslek Tanitimi, Editor sinifi
 * 
 * @see AbstractEditor
 * 
 * @author mdpinar
 * 
 */
public class JobEditor extends AbstractEditor<JobDTO> {

	/**
	 * Ad alani
	 */
	private TextField<String> nameText;

	public JobEditor(IController<JobDTO> controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobDTO getNewModel() {
		return new JobDTO();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Size getSize() {
		return new Size(300, 110);
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
		return Tags.get("jobTitle");
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
