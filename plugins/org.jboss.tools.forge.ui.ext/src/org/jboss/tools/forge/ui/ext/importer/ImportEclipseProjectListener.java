/*
 * Copyright 2013 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Eclipse Public License version 1.0, available at
 * http://www.eclipse.org/legal/epl-v10.html
 */

package org.jboss.tools.forge.ui.ext.importer;

import java.util.HashSet;
import java.util.Set;

import org.jboss.forge.projects.Project;
import org.jboss.forge.projects.ProjectListener;
import org.jboss.forge.resource.DirectoryResource;
import org.jboss.forge.ui.context.UIContext;
import org.jboss.tools.forge.ui.ext.wizards.WizardListener;

/**
 * A project listener that imports created projects into the Eclipse Workspace
 *
 * @author <a href="mailto:ggastald@redhat.com">George Gastaldi</a>
 *
 */
public enum ImportEclipseProjectListener implements ProjectListener,
		WizardListener {

	INSTANCE;

	private Set<Project> projects = new HashSet<Project>();

	@Override
	public void projectCreated(Project project) {
		projects.add(project);
	}

	public void doImport() {
		for (Project project : projects) {
			DirectoryResource projectRoot = project.getProjectRoot();
			String baseDirPath = projectRoot.getParent()
					.getFullyQualifiedName();
			String projectName = projectRoot.getName();
			ProjectImporter projectImporter = new ProjectImporter(baseDirPath,
					projectName);
			projectImporter.importProject();
		}
	}

	@Override
	public void onFinish(UIContext context) {
		doImport();
	}

	@Override
	public void dispose() {
		projects.clear();
	}

}
