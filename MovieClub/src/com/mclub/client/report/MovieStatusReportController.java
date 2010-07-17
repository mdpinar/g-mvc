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

import com.gmvc.client.base.AbstractReportController;
import com.gmvc.client.meta.Event;
import com.gmvc.client.meta.Right;
import com.gmvc.client.util.Tags;
import com.mclub.client.app.Rights;
import com.mclub.client.app.SpecialEvent;
import com.mclub.client.util.Loaders;

/**
 * Film Durum raporunun Controller'i
 * 
 * @see AbstractReportController
 * 
 * @author mdpinar
 * 
 */
public class MovieStatusReportController extends AbstractReportController {

	private MovieStatusReportView view;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MovieStatusReportView createView() {
		view = new MovieStatusReportView(this);
		return view;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void holdSpecialEvent(Event event) {
		if (event != null) {
			switch (event.getType()) {

				case SpecialEvent.LoadMovieGroup: {
					Loaders.loadMovieGroups(view.movieGroup);
					break;
				}
				
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Right getRight() {
		return Rights.MovieStatusReport;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("movieStatusReport");
	}

}
