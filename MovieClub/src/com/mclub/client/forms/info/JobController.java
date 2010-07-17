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

import com.gmvc.client.base.AbstractBaseController;
import com.gmvc.client.meta.Right;
import com.gmvc.client.service.ICRUDServiceAsync;
import com.gmvc.client.util.Tags;
import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ImageResource;
import com.mclub.client.app.Rights;
import com.mclub.client.image.Images;
import com.mclub.client.model.JobDTO;
import com.mclub.client.service.JobService;
import com.mclub.client.service.JobServiceAsync;

/**
 * Meslek Tanitimi, Controller sinifi
 * 
 * @see AbstractBaseController
 * 
 * @author mdpinar
 * 
 */
public class JobController extends AbstractBaseController<JobDTO> {

	/**
	 * Server tarafi iletisim servisi
	 * 
	 */
	private JobServiceAsync service = GWT.create(JobService.class);

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobBrowser createBrowser() {
		return new JobBrowser(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public JobEditor createEditor() {
		return new JobEditor(this);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ICRUDServiceAsync<JobDTO> getService() {
		return this.service;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Right getRight() {
		return Rights.Job;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getWidth() {
		return 450;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public ImageResource getIcon() {
		return Images.Instance.building();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getTitle() {
		return Tags.get("jobTitle");
	}

}
