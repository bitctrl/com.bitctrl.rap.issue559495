package com.bitctrl.rap.issue559495;

import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("serial")
public class BasicEntryPoint extends AbstractEntryPoint {

    @Override
    protected void createContents(Composite parent) {
        parent.setLayout(new FillLayout());
        Map map = new Map(parent, SWT.NONE);
        map.setView(51.4920026, -0.1952084, 17);
    }

}
