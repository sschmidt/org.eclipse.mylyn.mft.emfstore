package org.eclipse.mylyn.mft.emfstore.core;

import java.io.File;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.emfstore.client.model.WorkspaceManager;
import org.eclipse.mylyn.mft.sdk.util.AbstractModelingContextTest;

@SuppressWarnings("restriction")
public class EMFStoreDomainBridgeTest extends AbstractModelingContextTest {

	private EObject managedObject = EcoreFactory.eINSTANCE.createEObject();

	private EObject unmanagedObject = EcoreFactory.eINSTANCE.createEObject();

	private EMFStoreDomainBridge objectUnderTest = new EMFStoreDomainBridge();

	private URI tmpResourceUri = URI.createFileURI("tmp.xmi");

	@Override
	protected void setUp() throws Exception {
		super.setUp();

		EList<Resource> resources = WorkspaceManager.getInstance().getCurrentWorkspace().getResourceSet()
				.getResources();
		ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap()
				.put(Resource.Factory.Registry.DEFAULT_EXTENSION, new XMIResourceFactoryImpl());
		Resource createResource = resourceSet.createResource(tmpResourceUri);
		createResource.getContents().add(managedObject);
		resources.add(createResource);
	}

	public void testManagedObject() {
		// empty id => ws root
		assertEquals("/", objectUnderTest.getDomainHandleIdentifier(managedObject));
	}

	public void testUnmanagedObject() {
		// unmanaged object defaults to EcoreBridge
		assertEquals("#//", objectUnderTest.getDomainHandleIdentifier(unmanagedObject));
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		File tmpFile = new File(tmpResourceUri.toFileString());
		if (tmpFile.exists()) {
			tmpFile.delete();
		}
	}
}
