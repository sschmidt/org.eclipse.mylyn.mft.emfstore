package org.eclipse.mylyn.mft.emfstore.core;

import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.emfstore.client.model.Workspace;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.mylyn.mft.emf.core.ecore.EcoreDomainBridge;

@SuppressWarnings("restriction")
public class EMFStoreDomainBridge extends EcoreDomainBridge {

	public static final String EMFSTORE_CONTENT_TYPE = "ecore"; //$NON-NLS-1$

	@Override
	public Object getObjectForHandle(String handle) {
		if (isDocument(handle)) {
			EObject managedEObject = findManagedEObject(handle);
			if (managedEObject != null) {
				return managedEObject;
			}
		}

		return super.getObjectForHandle(handle);
	}

	@Override
	public Object getDomainObject(Object object) {
		if (object == null) {
			return null;
		}

		if (object instanceof EObject) {
			if (isManagedByEMFStore((EObject) object)) {
				return object;
			}
		}

		return super.getDomainObject(object);
	}

	@Override
	public String getDomainHandleIdentifier(Object object) {
		if (object instanceof EObject) {
			EObject eObject = (EObject) object;
			if (EMFStoreDomainBridge.isManagedByEMFStore(eObject)) {
				return eObject.eResource().getURIFragment(eObject);
			}
		}
		return super.getDomainHandleIdentifier(object);
	}

	public static boolean isManagedByEMFStore(EObject object) {
		Workspace currentWorkspace = WorkspaceManager.getInstance().getCurrentWorkspace();
		for (Resource resource : currentWorkspace.getResourceSet().getResources()) {
			TreeIterator<EObject> allContents = resource.getAllContents();
			while (allContents.hasNext()) {
				EObject currentObject = allContents.next();
				if (object.equals(currentObject)) {
					return true;
				}
			}
		}

		return false;
	}

	private EObject findManagedEObject(String uriFragment) {
		Workspace currentWorkspace = WorkspaceManager.getInstance().getCurrentWorkspace();

		for (Resource resource : currentWorkspace.getResourceSet().getResources()) {
			TreeIterator<EObject> allContents = resource.getAllContents();
			while (allContents.hasNext()) {
				EObject currentObject = allContents.next();
				if (uriFragment.equals(currentObject.eResource().getURIFragment(currentObject))) {
					return currentObject;
				}
			}
		}

		return null;
	}
}
