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

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.MessageBoxEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.RowData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.gmvc.client.base.AbstractBrowser;
import com.gmvc.client.base.IController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.util.ColumnFactory;
import com.gmvc.client.util.Tags;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.image.Images;
import com.mclub.client.model.MovieDTO;

/**
 * Film Tanitimi altindaki Kopya Filmler kismi, Browser sinifi
 * 
 * @see AbstractBrowser
 * 
 * @author mdpinar
 * 
 */
public class MovieMiniBrowser extends AbstractBrowser<MovieDTO> {

	public MovieMiniBrowser(IController<MovieDTO> controller) {
		super(controller);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void init() {
		addWidgetToPanel(getAddButton(), new RowData());
		addWidgetToPanel(getGrid(), new RowData(1, 1));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ToolBar getToolBar() {
		return null;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ColumnFactory getColumnFactory() {
		return
			new ColumnFactory()
				.addColumn("copyNo", Tags.get("copy"), 50, HorizontalAlignment.RIGHT)
				.addColumn("lastStatus", Tags.get("lastStatus"), 80)
				.addColumn("lastCustomer.name", Tags.get("customer"), 250)
				.addColumn("deleteButton", Tags.get("delete"), 35, 
						ColumnFactory.getRemoveButtonCellRenderer(getController()));
	}

	/**
	 * Yeni film kopyasi ekleme butonu 
	 * 
	 * <p/>
	 * <strong>
	 * Burada eklenen filmin kendisi degil kopyasidir.
	 * </strong>
	 */
	private Button getAddButton() {
		Button button = new Button(Tags.get("add"),
				AbstractImagePrototype.create(Images.Instance.add()),
				new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						final MessageBox box = MessageBox.prompt(
								Tags.get("copyCount"),
								Tags.get("copyCountMessage"));
						box.addCallback(new Listener<MessageBoxEvent>() {

							public void handleEvent(MessageBoxEvent be) {
								int count = 0;
								try {
									count = Integer.parseInt(be.getValue());
								} catch (Exception e) {

								}

								for (int i = 0; i < count; i++) {
									getController().fireEvent(new Event(SpecialEvent.AddNewModel, new MovieDTO()));
								}
								getController().fireEvent(new Event(SpecialEvent.Refresh));
							}

						});
					}

				});
		button.setWidth(60);
		return button;
	}

}
