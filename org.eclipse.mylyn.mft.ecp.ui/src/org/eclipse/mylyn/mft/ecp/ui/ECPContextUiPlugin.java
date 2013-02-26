package org.eclipse.mylyn.mft.ecp.ui;

import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class ECPContextUiPlugin extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.mylyn.mft.ecp.ui"; //$NON-NLS-1$

	// The shared instance
	private static ECPContextUiPlugin plugin;

	private ECPEditorListener ecpEditorListener;

	/**
	 * The constructor
	 */
	public ECPContextUiPlugin() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext
	 * )
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		ecpEditorListener = new ECPEditorListener();
		ecpEditorListener.install(PlatformUI.getWorkbench());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext
	 * )
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		ecpEditorListener.dispose(PlatformUI.getWorkbench());
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static ECPContextUiPlugin getDefault() {
		return plugin;
	}

}
