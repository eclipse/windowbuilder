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
package org.eclipse.wb.internal.core.editor.palette.dialogs;

import org.eclipse.wb.internal.core.editor.palette.PluginPalettePreferences;
import org.eclipse.wb.internal.core.utils.dialogfields.BooleanDialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.DialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.DialogFieldUtils;
import org.eclipse.wb.internal.core.utils.dialogfields.FontDialogField;
import org.eclipse.wb.internal.core.utils.dialogfields.SelectionButtonDialogFieldGroup;
import org.eclipse.wb.internal.core.utils.ui.GridLayoutFactory;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;

/**
 * Dialog for modifying palette settings.
 * 
 * @author scheglov_ke
 * @coverage core.editor.palette.ui
 */
public final class PalettePreferencesDialog extends AbstractPaletteDialog {
  private final PluginPalettePreferences m_preferences;

  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public PalettePreferencesDialog(Shell parentShell, PluginPalettePreferences preferences) {
    super(parentShell,
        "Palette settings",
        "Palette settings",
        null,
        "Configure settings for displaying palette.");
    m_preferences = preferences;
    setShellStyle(SWT.APPLICATION_MODAL | SWT.DIALOG_TRIM);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Access
  //
  ////////////////////////////////////////////////////////////////////////////
  /**
   * Commits settings from dialog to the preferences.
   */
  public void commit() {
    m_preferences.setOnlyIcons(m_onlyIconsField.getSelection());
    m_preferences.setMinColumns(1 + m_minColumnsField.getSelection()[0]);
    m_preferences.setCategoryFont(m_categoryFontField.getFontDataArray());
    m_preferences.setEntryFont(m_entryFontField.getFontDataArray());
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // GUI
  //
  ////////////////////////////////////////////////////////////////////////////
  private BooleanDialogField m_onlyIconsField;
  private SelectionButtonDialogFieldGroup m_minColumnsField;
  private FontDialogField m_categoryFontField;
  private FontDialogField m_entryFontField;

  @Override
  protected void createControls(Composite container) {
    m_fieldsContainer = container;
    GridLayoutFactory.create(container).columns(2);
    // only icons
    {
      m_onlyIconsField = new BooleanDialogField();
      doCreateField(m_onlyIconsField, "Show only icons for entries");
    }
    // min columns
    {
      m_minColumnsField =
          new SelectionButtonDialogFieldGroup(SWT.RADIO, new String[]{
              "1 (one)",
              "2 (two)",
              "3 (three)",
              "4 (four)",
              "5 (five)"}, 5, SWT.SHADOW_ETCHED_IN);
      doCreateField(m_minColumnsField, "Minimal number of columns");
    }
    // category font
    {
      m_categoryFontField = new FontDialogField();
      doCreateField(m_categoryFontField, "Category font");
      m_categoryFontField.setChooseButtonText("&Choose...");
      m_categoryFontField.setDefaultButtonText("Use System Font");
    }
    // entry font
    {
      m_entryFontField = new FontDialogField();
      doCreateField(m_entryFontField, "Entry font");
      m_entryFontField.setChooseButtonText("&Choose...");
      m_entryFontField.setDefaultButtonText("Use &System Font");
    }
    // initialize fields
    m_onlyIconsField.setSelection(m_preferences.isOnlyIcons());
    m_minColumnsField.setSelection(new int[]{m_preferences.getMinColumns() - 1});
    m_categoryFontField.setFontDataArray(m_preferences.getCategoryFont().getFontData());
    m_entryFontField.setFontDataArray(m_preferences.getEntryFont().getFontData());
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Utils
  //
  ////////////////////////////////////////////////////////////////////////////
  private Composite m_fieldsContainer;

  /**
   * Configures given {@link DialogField} for specific of this dialog.
   */
  protected final void doCreateField(DialogField dialogField, String labelText) {
    dialogField.setLabelText(labelText);
    dialogField.setDialogFieldListener(m_validateListener);
    DialogFieldUtils.fillControls(m_fieldsContainer, dialogField, 2, 60);
  }
}