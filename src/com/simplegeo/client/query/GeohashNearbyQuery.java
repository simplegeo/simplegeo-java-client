/**
 * Copyright (c) 2009-2010, SimpleGeo
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, 
 * this list of conditions and the following disclaimer. Redistributions 
 * in binary form must reproduce the above copyright notice, this list of
 * conditions and the following disclaimer in the documentation and/or 
 * other materials provided with the distribution.
 * 
 * Neither the name of the SimpleGeo nor the names of its contributors may
 * be used to endorse or promote products derived from this software 
 * without specific prior written permission.
 *  
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE 
 * GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER 
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, 
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.simplegeo.client.query;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.simplegeo.client.http.exceptions.ValidLayerException;

import ch.hsr.geohash.GeoHash;

/**
 * A nearby query that uses a geohash as its search parameters.
 * 
 * @see com.simplegeo.client.query.NearbyQuery
 * 
 * @author Derek Smith
 */
public class GeohashNearbyQuery extends NearbyQuery {

	private GeoHash geohash;
	
	/**
	 * @param geohash the area to search for records
	 * @param @see com.simplegeo.client.query.NearbyQuery#getLayer()
	 * @throws ValidLayerException
	 */
	public GeohashNearbyQuery(GeoHash geohash, String layer) throws ValidLayerException {
		this(geohash, layer, null, -1, null);
	}
	
	/**
	 * @param geohash the area to search for records
	 * @param @see com.simplegeo.client.query.NearbyQuery#getLayer()
	 * @param @see com.simplegeo.client.query.NearbyQuery#getTypes()
	 * @param @see com.simplegeo.client.query.NearbyQuery#getLimit()
	 * @param @see com.simplegeo.client.query.NearbyQuery#getCursor()
	 * @throws ValidLayerException
	 */
	public GeohashNearbyQuery(GeoHash geohash, String layer, List<String> types, int limit, String cursor) throws ValidLayerException {
		super(layer, types, limit, cursor);
		this.geohash = geohash;
	}
	
	public String getUri() throws UnsupportedEncodingException {
		return String.format("%s/%s.json", super.getUri(), this.geohash.toBase32());
	}

	/**
	 * @return the geohash
	 */
	public GeoHash getGeohash() {
		return geohash;
	}

	/**
	 * @param geohash the geohash to set
	 */
	public void setGeohash(GeoHash geohash) {
		this.geohash = geohash;
	}
}
