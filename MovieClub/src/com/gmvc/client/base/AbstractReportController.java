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

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.gmvc.client.app.ContentController;
import com.gmvc.client.enums.RightGroup;
import com.gmvc.client.image.BaseImages;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.EventType;
import com.gmvc.client.meta.Right;
import com.google.gwt.resources.client.ImageResource;

/**
 * Rapor formlari icin mantiksal islemlerin yapildigi soyut Controller sinifidir
 * 
 * @see IReportController
 * 
 * @author mdpinar
 * 
 */
public abstract class AbstractReportController implements IReportController {

	/**
	 * Rapor servletinin bulunduğu modul
	 */
	private final String servletPrefix = "com.gmvc.GMVC/";
	
	/**
	 * Gosterecegi View
	 * 
	 * @see IReportView
	 */
	private IReportView view;

	/**
	 * Gosterilecek View'in yeni bir ornegini doner
	 * 
	 * @see IReportView
	 */
	public abstract IReportView createView();

	/**
	 * {@inheritDoc}
	 */
	@Override
	public IReportView getView() {
		if (view == null) {
			view = createView();
		}
		return view;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ContentPanel popup() {
		return getView().popup();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 200;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getHeight() {
		return 190;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageResource getIcon() {
		return BaseImages.Instance.report();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void fireEvent(Event event) {

		if (event != null) {
			switch (event.getType()) {

				case EventType.GetReport: {
					getReport();
					break;
				}
	
				default: {
					holdSpecialEvent(event);
				}

			}

		}

	}

	/**
	 * fireEvent metodundaki standart Event' larin disinda kontrol edilmek
	 * istenen ozel bir Event varsa, bu metod otomatik olarak cagrilir.
	 * 
	 * <p/>
	 * <strong>OZEL OLAYLAR</strong> denetlenecekse bu metod ezilebilir
	 * 
	 * @param Event
	 * 
	 * @see #fireEvent(Event)
	 */
	public void holdSpecialEvent(Event event) {
		System.out.println("AbstractReportController -> Special event trigged : " + event.getType());
	}

	/**
	 * Rapor ciktisini uretir
	 * 
	 * @see ContentController
	 */
	private void getReport() {
		final StringBuilder sb = new StringBuilder();
		sb.append("reportService?reportName=");
		sb.append(view.getReportName());
		sb.append("&reportUnit=");
		sb.append(view.getReportUnit());
		sb.append("&sqlHaving=");
		sb.append(view.getHavingString());
		sb.append("&sqlQuery=");
		sb.append(view.getQueryString());
		
		switch (view.getReportUnit()) {
		
			case Html: {
				ContentController.fireEvent(
					new Event(EventType.ShowContent)
						.addParam("popupable", new NewReport(sb))
				);
				break;
			}

			default: {
				sb.insert(0, servletPrefix);
				if (GXT.isIE) sb.insert(0, "../");
				
				showSaveAsDialog(sb.toString());
				break;
			}
			
		}
	}

	/**
	 * Html ciktisi disindaki raporlarin, Web Browser' daki Save As diyalogu ile
	 * dosya (pdf, xls, csv ...) olarak kayit edilmesini saglar.
	 * 
	 * <p/>
	 * Native js metoddur
	 * 
	 * @param url
	 */
	private native void showSaveAsDialog(String url) /*-{
		window.open(url);
	}-*/;
	
	/**
	 * Html raporlarin yeni bir tabitem acilarak, Web Browser uzerinde
	 * goruntulenmesini saglar Burada uretilen sinif ContentController a
	 * verilerek tabpanelde yeni bir sayfa acilmasi saglanir
	 * 
	 * @see ContentController
	 * @see AbstractReportController#getReport()
	 */
	private class NewReport implements Popupable {

		private StringBuilder url;

		NewReport(StringBuilder url) {
			this.url = url;
		}

		@Override
		public ContentPanel popup() {
			url.insert(0, servletPrefix);

			ContentPanel panel = new ContentPanel();
			panel.setHeaderVisible(false);
			panel.setUrl(url.toString());
			
			return panel;
		}

		@Override
		public Right getRight() {
			return new Right("Void", RightGroup.System);
		}

		@Override
		public int getHeight() {
			return 0;
		}

		@Override
		public int getWidth() {
			return 0;
		}

		@Override
		public ImageResource getIcon() {
			return BaseImages.Instance.report();
		}

		@Override
		public String getTitle() {
			return view.getReportName();
		}

	}

}
