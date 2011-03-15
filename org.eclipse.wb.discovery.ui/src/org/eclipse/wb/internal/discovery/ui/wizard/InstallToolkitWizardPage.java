/*******************************************************************************
 * Copyright (c) 2011 Google, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Google, Inc. - initial API and implementation
 *******************************************************************************/

package org.eclipse.wb.internal.discovery.ui.wizard;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.wb.internal.discovery.core.WBToolkit;
import org.eclipse.wb.internal.discovery.ui.util.BrowserHelper;

/**
 * The wizard page used by the InstallToolkitWizard wizard.
 */
class InstallToolkitWizardPage extends WizardPage {
  private WBToolkit toolkit;
  
  /**
   * Create a new instance of a InstallToolkitWizardPage.
   * @param toolkit the WindowBuilder toolkit to install
   */
  protected InstallToolkitWizardPage(WBToolkit toolkit) {
    super("Install Toolkit");
    
    this.toolkit = toolkit;
    
    setTitle(toolkit.getName());
    setMessage(toolkit.getWizardContributionDescription());
  }
  
  public void createControl(Composite parent) {
    Composite composite = new Composite(parent, SWT.NONE);
    
    composite.setLayout(new GridLayout(2, false));
    
    Label iconLabel = new Label(composite, SWT.NULL);
    GridDataFactory.swtDefaults().span(1, 2).align(SWT.CENTER, SWT.BEGINNING).applyTo(iconLabel);
    
    if (toolkit.getIconURL() != null) {
      ImageDescriptor iconDescriptor = ImageDescriptor.createFromURL(toolkit.getIconURL());
      
//      ImageDescriptor iconDescriptor = WBDiscoveryCorePlugin.imageDescriptorFromPlugin(
//          WBDiscoveryCorePlugin.PLUGIN_ID,
//          "resources/" + toolkit.getIconPath());
      
      if (iconDescriptor != null) {
        iconLabel.setImage(iconDescriptor.createImage());
      }
    }
    
    Label summaryLabel = new Label(composite, SWT.WRAP);
    String description = toolkit.getDescription();
    description = description.replaceAll("(\\r\\n)|\\n|\\r", " ");
    summaryLabel.setText(description);
    GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(summaryLabel);
    
    if (toolkit.getMoreInfoURL() != null) {
      Link moreInfoLink = new Link(composite, SWT.NONE);
      moreInfoLink.setText("<a>more info...</a>");
      moreInfoLink.addListener(SWT.Selection, new Listener() {
        public void handleEvent(Event event) {
          BrowserHelper.openUrl(getShell(), toolkit.getMoreInfoURL());
        }
      });
      GridDataFactory.fillDefaults().grab(true, false).hint(100, SWT.DEFAULT).applyTo(moreInfoLink);
    } else {
      // spacer
      new Label(composite, SWT.NONE);
    }
    
    Label separator = new Label(composite, SWT.SEPARATOR | SWT.HORIZONTAL);
    GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(100, SWT.DEFAULT).applyTo(separator);
    
    Label label = new Label(composite, SWT.NONE);
    label.setText("Click finish to install this toolkit.");
    GridDataFactory.fillDefaults().grab(true, false).span(2, 1).hint(100, SWT.DEFAULT).applyTo(label);
    
    setControl(composite);
  }

}