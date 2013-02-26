package org.eclipse.mylyn.mft.emfstore.core;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class EMFStoreContextPlugin extends Plugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.mylyn.mft.emfstore.core"; //$NON-NLS-1$

	// The shared instance
	private static EMFStoreContextPlugin plugin;

	/**
	 * The constructor
	 */
	public EMFStoreContextPlugin() {
	}

	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static EMFStoreContextPlugin getDefault() {
		return plugin;
	}

}
