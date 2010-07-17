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
import com.mclub.client.model.MovieGroupDTO;
import com.mclub.client.service.MovieGroupService;
import com.mclub.server.model.MovieGroup;

/**
 * Film Gurubu Tanitimi servisi
 * 
 * @see CRUDImpl
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings("serial")
public class MovieGroupServiceImpl
	extends CRUDImpl<MovieGroup, MovieGroupDTO> implements MovieGroupService {

	public MovieGroupServiceImpl() {
		super(MovieGroup.class, MovieGroupDTO.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxResultSize() {
		return 0; //tum guruplar donulecek
	}

}
