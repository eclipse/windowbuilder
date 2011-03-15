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
package org.eclipse.wb.tests.designer.rcp.model.jface;

import org.eclipse.wb.internal.core.model.property.Property;
import org.eclipse.wb.internal.core.model.property.category.PropertyCategory;
import org.eclipse.wb.internal.swt.model.jface.viewer.ViewerInfo;
import org.eclipse.wb.internal.swt.model.widgets.CompositeInfo;
import org.eclipse.wb.internal.swt.model.widgets.ControlInfo;
import org.eclipse.wb.tests.designer.rcp.RcpModelTest;

import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.widgets.Composite;

/**
 * Test for {@link TableViewer}.
 * 
 * @author scheglov_ke
 */
public class TableViewerTest extends RcpModelTest {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Exit zone :-) XXX
  //
  ////////////////////////////////////////////////////////////////////////////
  public void _test_exit() throws Exception {
    System.exit(0);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Tests
  //
  ////////////////////////////////////////////////////////////////////////////
  public void test_properties() throws Exception {
    CompositeInfo shell =
        parseComposite(
            "public class Test extends Shell {",
            "  public Test() {",
            "    TableViewer tableViewer = new TableViewer(this, SWT.NONE);",
            "  }",
            "}");
    shell.refresh();
    ControlInfo table = shell.getChildrenControls().get(0);
    ViewerInfo viewer = (ViewerInfo) table.getChildren().get(0);
    // setBusy() is protected, but for some reason it filters into
    assertNull(viewer.getPropertyByTitle("busy"));
    // "itemCount" is advanced
    {
      Property itemCountProperty = viewer.getPropertyByTitle("itemCount");
      assertSame(PropertyCategory.ADVANCED, itemCountProperty.getCategory());
    }
    // "Style" property should exists, not in "table", but in "viewer"
    assertNotNull(viewer.getPropertyByTitle("Style"));
  }

  /**
   * Test for {@link CheckboxTableViewer#newCheckList(Composite, int)} support.
   */
  public void test_CheckboxTableViewer() throws Exception {
    CompositeInfo shell =
        parseComposite(
            "public class Test extends Shell {",
            "  public Test() {",
            "    setLayout(new FillLayout());",
            "    TableViewer tableViewer = CheckboxTableViewer.newCheckList(this, SWT.NONE);",
            "  }",
            "}");
    assertHierarchy(
        "{this: org.eclipse.swt.widgets.Shell} {this} {/setLayout(new FillLayout())/ /CheckboxTableViewer.newCheckList(this, SWT.NONE)/}",
        "  {new: org.eclipse.swt.layout.FillLayout} {empty} {/setLayout(new FillLayout())/}",
        "  {viewer: public org.eclipse.swt.widgets.Table org.eclipse.jface.viewers.TableViewer.getTable()} {viewer} {}",
        "    {static factory: org.eclipse.jface.viewers.CheckboxTableViewer newCheckList(org.eclipse.swt.widgets.Composite,int)} {local-unique: tableViewer} {/CheckboxTableViewer.newCheckList(this, SWT.NONE)/}");
    // refresh()
    shell.refresh();
    assertNoErrors(shell);
  }
}