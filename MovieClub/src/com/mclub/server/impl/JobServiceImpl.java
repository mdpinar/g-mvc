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
package com.mclub.server.impl;

import com.gmvc.server.impl.CRUDImpl;
import com.mclub.client.model.JobDTO;
import com.mclub.client.service.JobService;
import com.mclub.server.model.Job;

/**
 * Meslek Tanitimi servisi
 * 
 * @see CRUDImpl
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings("serial")
public class JobServiceImpl 
	extends CRUDImpl<Job, JobDTO> implements JobService {

	public JobServiceImpl() {
		super(Job.class, JobDTO.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxResultSize() {
		return 0; //tum meslekler donulecek
	}

}
