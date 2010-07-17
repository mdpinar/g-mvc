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
import com.gmvc.client.meta.Right;
import com.gmvc.client.util.Tags;
import com.mclub.client.app.Rights;

/**
 * Film Hareket raporunun Controller'i
 * 
 * @see AbstractReportController
 * 
 * @author mdpinar
 * 
 */
public class MovieTransReportController extends AbstractReportController {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public MovieTransReportView createView() {
		return new MovieTransReportView(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Right getRight() {
		return Rights.MovieTransReport;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("movieTransReport");
	}

}
