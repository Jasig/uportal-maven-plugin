/**
 * Copyright (c) 2000-2009, Jasig, Inc.
 * See license distributed with this file and available online at
 * https://www.ja-sig.org/svn/jasig-parent/tags/rel-10/license-header.txt
 */
package org.jasig.portal.plugin.deployer;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Entity resolver that uses the file name of the systemId URL to locate the
 * classpath resource to resolve.
 * 
 * @author Eric Dalquist
 * @version $Revision$
 */
public class ClasspathEntityResolver implements EntityResolver {
    protected final Log logger = LogFactory.getLog(this.getClass());
    
    /* (non-Javadoc)
     * @see org.xml.sax.EntityResolver#resolveEntity(java.lang.String, java.lang.String)
     */
    public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        final String entityName = getEntityName(systemId);
        final InputStream entityStream = getClass().getResourceAsStream(entityName);
        
        if (this.logger.isDebugEnabled()) {
            this.logger.debug((entityStream == null ? "Failed to resolve" : "Resolved") + " Entity for publicId: '" + publicId + "', systemId: '" + systemId + "'");
        }
        
        if (entityStream != null) {
            return new InputSource(entityStream);
        }
        
        return null;
    }

    /**
     * @param systemId The ID to parse
     * @return The path and name of the entity to return.
     */
    protected String getEntityName(String systemId) {
        final int lastSlashIndex = systemId.lastIndexOf('/');
        
        if (lastSlashIndex < 0) {
            return systemId;
        }
        
        final String entityName = systemId.substring(lastSlashIndex);
        return entityName;
    }
}
