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
import com.mclub.client.model.LocationDTO;
import com.mclub.client.service.LocationService;
import com.mclub.server.model.Location;

/**
 * Lokasyon/Bolge Tanitimi servisi
 * 
 * @see CRUDImpl
 * 
 * @author mdpinar
 * 
 */
@SuppressWarnings("serial")
public class LocationServiceImpl
	extends CRUDImpl<Location, LocationDTO> implements LocationService {

	public LocationServiceImpl() {
		super(Location.class, LocationDTO.class);
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getMaxResultSize() {
		return 0; //tum bolgeler donulecek
	}

}
