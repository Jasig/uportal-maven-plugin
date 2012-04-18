/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
/**
 * 
 */
package org.jasig.portal.web.skin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.Validate;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;


/**
 * Java bean to represent the "css" type in the 
 * "http://www.jasig.org/uportal/web/skin" schema.
 * 
 * @author Nicholas Blair, npblair@wisc.edu
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "value"
})
@XmlRootElement(name = "css", namespace = "http://www.jasig.org/uportal/web/skin")
public class Css {

    @XmlValue
    private String value;
    @XmlAttribute
    private String conditional;
    @XmlAttribute
    private String media;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the conditional property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConditional() {
        return conditional;
    }

    /**
     * Sets the value of the conditional property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConditional(String value) {
        this.conditional = value;
    }
    /**
     * Gets the value of the media property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMedia() {
        return media;
    }

    /**
     * Sets the value of the media property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMedia(String value) {
        this.media = value;
    }

    /**
     * 
     * @return true if and only if the value property begins with a "/"
     */
    public boolean isAbsolute() {
    	if(null == value) { return false; }
    	return value.startsWith("/");
    }  
    
    /**
     * 
     * @return true if the "conditional" property of this instance is not blank.
     */
    public boolean isConditional() {
    	return StringUtils.isNotBlank(this.conditional);
    }

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		HashCodeBuilder builder = new HashCodeBuilder();
		builder.append(this.value);
		builder.append(this.conditional);
		builder.append(this.media);
		return builder.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(!(obj instanceof Css)) {
			return false;
		}
		Css rhs = (Css) obj;
		
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.value, rhs.value);
		builder.append(this.conditional, rhs.conditional);
		builder.append(this.media, rhs.media);
		return builder.isEquals();
	}
    
	/**
	 * Similar to the {@link #equals(Object)} method, this will return
	 * true if this object and the argument are "aggregatable".
	 * 
	 * 2 {@link Css} objects are aggregatable if and only if:
	 * <ol>
	 * <li>Neither object returns true for {@link #isAbsolute()}</li>
	 * <li>The values of their "conditional" properties are equivalent</li>
	 * <li>The values of their "media" properties are equivalent</li>
	 * <li>The "paths" of their values are equivalent</li>
	 * </ol>
	 * 
	 * The last rule mentioned above uses {@link FilenameUtils#getFullPath(String)}
	 * to compare each object's value. In short, the final file name in the value's path
	 * need not be equal, but the rest of the path in the value must be equal.
	 * 
	 * @param other
	 * @return
	 */
	public boolean willAggregateWith(final Css other) {
		Validate.notNull(other, "Css argument cannot be null");
		// never can aggregate absolute Css values
		if(this.isAbsolute() || other.isAbsolute()) {
			return false;
		}
		EqualsBuilder builder = new EqualsBuilder();
		builder.append(this.conditional, other.conditional);
		builder.append(this.media, other.media);
		String thisFullPath = FilenameUtils.getFullPath(this.value);
		String otherFullPath = FilenameUtils.getFullPath(other.value);
		builder.append(thisFullPath, otherFullPath);
		return builder.isEquals();
	}
	
    
}
