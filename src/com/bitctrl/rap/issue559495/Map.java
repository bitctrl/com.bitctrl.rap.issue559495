package com.bitctrl.rap.issue559495;

import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.remote.Connection;
import org.eclipse.rap.rwt.remote.RemoteObject;
import org.eclipse.rap.rwt.widgets.WidgetUtil;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.widgets.Composite;

@SuppressWarnings("serial")
public class Map extends Composite {

	private static final String TYPE = "BitCtrl.Leaflet.Map";

	private final RemoteObject remoteObject;

	public Map(Composite parent, int style) {
		super(parent, style);

		Resources.initialize();

		Connection connection = RWT.getUISession().getConnection();
		remoteObject = connection.createRemoteObject(TYPE);
		remoteObject.set("parent", WidgetUtil.getId(this));

		parent.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				System.out.println("Resize: " + e + " (Bounds: " + parent.getBounds() + ")");
			}
		});
	}

	public final void setView(double lat, double lon, int zoom) {
		checkWidget();

		JsonObject parameters = new JsonObject();
		parameters.set("center", new JsonArray().add(lat).add(lon));
		parameters.set("zoom", zoom);
		remoteObject.call("setView", parameters);
	}

	@Override
	public void dispose() {
		remoteObject.destroy();
		super.dispose();
	}

}
