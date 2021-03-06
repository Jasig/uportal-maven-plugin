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
package org.jasig.portal.plugin.mojo;

import java.io.File;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.Component;
import org.apache.maven.plugins.annotations.Parameter;
import org.jasig.portal.plugin.deployer.DeployerConfig;
import org.jasig.portal.plugin.deployer.EarDeployer;

/**
 * Base class for ear deployment mojos 
 * 
 * @author Eric Dalquist
 */
public abstract class AbstractDeployEarMojo extends AbstractMojo {
    /**
     * Component to deploy ear files
     */
    @Component(role = EarDeployer.class)
    private Map<String, EarDeployer> earDeployers;
    
    /**
     * Role hint of the {@link org.jasig.portal.plugin.deployer.EarDeployer} implementation to use.
     */
    @Parameter(defaultValue = "tomcat", property = "earDeployerId")
    private String earDeployerId;
    
    /**
     * Destination for EAR deployment
     */
    @Parameter(property="deployDestination", required = true)
    private File deployDestination;
    
    /**
     * If the war files within the ear should be extracted during deploy
     */
    @Parameter(property="extractWars", defaultValue = "true")
    private boolean extractWars = true;
    
    /**
     * If true remove conflicting webapp directories before deploying instead of deploying on top of them 
     */
    @Parameter(property="removeExistingWebappDirectories", defaultValue = "true")
    private boolean removeExistingWebappDirectories = false;
    
    /**
     * If the shared library directory should have all existing files removed before deploying 
     */
    @Parameter(property="cleanSharedDirectory", defaultValue = "true")
    private boolean cleanSharedDirectory = true;
    
    /**
     * Arbitrary deployer parameters, see the implementation of {@link EarDeployer} you are using for a parameter
     * reference. 
     */
    @Parameter
    private Map<String, String> deployerParameters;

    protected final EarDeployer getEarDeployer() throws MojoExecutionException {
        final EarDeployer earDeployer = this.earDeployers.get(this.earDeployerId);
        if (earDeployer == null) {
            throw new MojoExecutionException("No " + EarDeployer.class.getSimpleName() + " found for 'earDeployerId'=" + this.earDeployerId);
        }
        return earDeployer;
    }

    protected final DeployerConfig getDeployerConfig() {
        final DeployerConfig deployerConfig = new DeployerConfig();
        deployerConfig.setCleanSharedDirectory(cleanSharedDirectory);
        deployerConfig.setDeployDestination(deployDestination);
        deployerConfig.setDeployerParameters(deployerParameters);
        deployerConfig.setExtractWars(extractWars);
        deployerConfig.setRemoveExistingWebappDirectories(removeExistingWebappDirectories);
        return deployerConfig;
    }
}