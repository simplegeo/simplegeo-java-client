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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.simplegeo.client.http.exceptions.ValidLayerException;
import com.simplegeo.client.utilities.SimpleGeoUtilities;

/**
 * An abstracted class that defines the some of the common parameters
 * between a Geohash and LatLon nearby query.
 * 
 * @author Derek Smith
 */
public abstract class NearbyQuery implements IQuery {
	
	private String cursor;
	private String layer;
	private List<String> types;
	private String tag;
	private int limit;
	private double start;
	private double end;

	/**
	 * @param layer @see com.simplegeo.client.query.NearbyQuery#getLayer()
	 * @param types @see com.simplegeo.client.query.NearbyQuery#getTypes()
	 * @param limit @see com.simplegeo.client.query.NearbyQuery#getLimit()
	 * @param cursor @see com.simplegeo.client.query.IQuery#getCursor()
	 * @throws ValidLayerException
	 */
	public NearbyQuery(String layer, List<String> types, int limit, String cursor) throws ValidLayerException {
		this(layer, types, limit, cursor, -1, -1, null);
	}
	/**
	 * @param layer @see com.simplegeo.client.query.NearbyQuery#getLayer()
	 * @param types @see com.simplegeo.client.query.NearbyQuery#getTypes()
	 * @param limit @see com.simplegeo.client.query.NearbyQuery#getLimit()
	 * @param cursor @see com.simplegeo.client.query.IQuery#getCursor()
	 * @param limit @see com.simplegeo.client.query.NearbyQuery#getTag()
	 * @throws ValidLayerException
	 */
	public NearbyQuery(String layer, List<String> types, int limit, String cursor, String tag) throws ValidLayerException {
		this(layer, types, limit, cursor, -1, -1, tag);
	}
	
	public NearbyQuery(String layer, List<String> types, int limit, String cursor, double start, double end) 
			throws ValidLayerException {

		this(layer, types, limit, cursor, start, end, null);
	}

	public NearbyQuery(String layer, List<String> types, int limit, String cursor, double start, double end,
			String tag)	throws ValidLayerException {

		this.types = types;
		this.cursor = cursor;
		this.limit = limit;
		this.tag = tag;

		if (layer == null || layer.equals(""))
			throw new ValidLayerException("");

		this.layer = layer;

		this.start = start;
		this.end = end;
	}
	
	/**
	 * @see com.simplegeo.client.query.IQuery#getParams()
	 */
	public Map<String, String> getParams() {
		Map<String, String> params = new HashMap<String, String>();
		if(limit > 0)
			params.put("limit", Integer.toString(limit));
		
		if(types != null && !types.isEmpty())			
			params.put("types", SimpleGeoUtilities.commaSeparatedString(types));
		
		if(cursor != null)
			params.put("cursor", cursor);

		if(tag != null)
			params.put("tag", tag);
		
		if(start > 0 && end > 0) {
			params.put("start", Double.toString(start));
			params.put("end", Double.toString(end));
		}

		return params;
	}
	
	/**
	 * @see com.simplegeo.client.query.IQuery#getUri()
	 */
	public String getUri() {
		return String.format("/records/%s/nearby", layer);
	}
	
	/**
	 * @see com.simplegeo.client.query.IQuery#getCursor()
	 */
	public String getCursor() {
		return cursor;
	}

	/**
	 * @see com.simplegeo.client.query.IQuery#setCursor()
	 */
	public void setCursor(String cursor) {
		this.cursor = cursor;
	}

	/**
	 * @return the layer
	 */
	public String getLayer() {
		return layer;
	}

	/**
	 * @param layer the layer to search in
	 */
	public void setLayer(String layer) {
		this.layer = layer;
	}

	/**
	 * @return the tag
	 */
	public String getTag() {
		return tag;
	}

	/**
	 * @param tag the tag to search in
	 */
	public void setTag(String tag) {
		this.tag = tag;
	}

	/**
	 * If this value is null, ALL types will be searched.
	 * 
	 * @return the types to look for
	 */
	public List<String> getTypes() {
		return types;
	}

	/**
	 * @param types the types to set
	 */
	public void setTypes(List<String> types) {
		this.types = types;
	}

	/**
	 * The default limit is 25.
	 * 
	 * @return the limit
	 */
	public int getLimit() {
		return limit;
	}

	/**
	 * @param limit the limit to set
	 */
	public void setLimit(int limit) {
		this.limit = limit;
	}
}
