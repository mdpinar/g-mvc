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

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.gmvc.client.enums.ReportUnit;
import com.gmvc.client.image.BaseImages;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.EventType;
import com.gmvc.client.meta.ImageItem;
import com.gmvc.client.util.InstantComponent;
import com.gmvc.client.util.Tags;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Widget;

/**
 * Rapor formlari icin hazir yapi sunan soyut siniftir
 * 
 * @see IReportView
 * 
 * @author mdpinar
 * 
 */
public abstract class AbstractReportView implements IReportView {

	/**
	 * Bagli bulundugu Controller
	 * 
	 * @see IReportController
	 */
	private IReportController controller;

	/**
	 * Kisitlarin yer aldigi ana panel ve yerlesim verisi
	 * 
	 */
	private FormPanel panel;
	private FormData formData;

	/**
	 * Rapor cikis uniteleri (Html, pdf, xls, csv...)
	 * 
	 * @see ReportUnit
	 */
	private ComboBox<ImageItem> reportUnit = InstantComponent.getComboReportUnit();

	/**
	 * Form bilesenlerinin insaa edildigi metod
	 * 
	 */
	public abstract void buildPanel();

	/**
	 * Bagli bulunacagi Controlleri alarak ayaga kalkar
	 * 
	 * @param IReportController
	 */
	public AbstractReportView(IReportController controller) {
		this.controller = controller;
		
		controller.fireEvent(new Event(EventType.InitEditor));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentPanel popup() {
		return getPanel();
	}

	/**
	 * Panele yeni bir kisit sahasi ekler
	 * 
	 * @param widget
	 * @see #getQueryString()
	 */
	protected void addWidget(Widget widget) {
		panel.add(widget, formData);
	}

	/**
	 * Bagli bulundugu Controlleri doner
	 * 
	 * @return ReportController
	 */
	protected IReportController getController() {
		return this.controller;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public FormPanel getPanel() {
		if (this.panel == null) {
			formData = new FormData("-10");
			panel = new FormPanel();
			panel.setHeaderVisible(false);
			panel.setLabelAlign(LabelAlign.RIGHT);
			panel.setLabelWidth(100);
			panel.setFrame(true);
			panel.setWidth(320);
			panel.setHeaderVisible(false);

			buildPanel();

			reportUnit.setValue(reportUnit.getStore().findModel("title", ReportUnit.Html.toString()));
			addWidget(reportUnit);

			panel.add(getButtonPanel());
		}
		return this.panel;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getHavingString() {
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ReportUnit getReportUnit() {
		return (ReportUnit) reportUnit.getValue().getEnumVal();
	}

	/**
	 * Sadece Rapor butonunun bulundugu paneli doner
	 * 
	 * @see InstantComponent#buildCenterPanel(Widget, int)
	 * @return ContentPanel
	 */
	private ContentPanel getButtonPanel() {
		Button button = new Button(Tags.get("report"),
			AbstractImagePrototype.create(BaseImages.Instance.report()),
			new SelectionListener<ButtonEvent>() {

				@Override
				public void componentSelected(ButtonEvent ce) {
					controller.fireEvent(new Event(EventType.GetReport));
				}
				
		});

		return InstantComponent.buildCenterPanel(button, 30);
	}

}
