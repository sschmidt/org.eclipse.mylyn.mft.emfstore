package org.eclipse.mylyn.mft.ecp.ui;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.common.utilities.ActionHelper;
import org.eclipse.emf.ecp.editor.MEEditor;
import org.eclipse.jface.text.TextSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.mylyn.context.core.AbstractContextStructureBridge;
import org.eclipse.mylyn.context.core.ContextCore;
import org.eclipse.mylyn.context.core.IInteractionElement;
import org.eclipse.mylyn.context.ui.AbstractContextUiBridge;
import org.eclipse.mylyn.mft.emfstore.core.EMFStoreDomainBridge;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;

public class ECPEditorContextUiBridge extends AbstractContextUiBridge {

	@Override
	public void open(IInteractionElement element) {
		Object objectForHandle = ContextCore.getStructureBridge(EMFStoreDomainBridge.EMFSTORE_CONTENT_TYPE)
				.getObjectForHandle(element.getHandleIdentifier());

		if (objectForHandle instanceof EObject) {
			EObject object = (EObject) objectForHandle;
			ActionHelper.openModelElement(object, "org.eclipse.emf.ecp.navigator.TreeView"); //$NON-NLS-1$
		}
	}

	@Override
	public void close(IInteractionElement element) {
		// ignore
	}

	@Override
	public boolean acceptsEditor(IEditorPart editorPart) {
		return editorPart instanceof MEEditor;
	}

	@Override
	public IInteractionElement getElement(IEditorInput input) {
		Object object = input.getAdapter(EObject.class);
		if (object != null && object instanceof EObject) {
			AbstractContextStructureBridge bridge = ContextCore
					.getStructureBridge(EMFStoreDomainBridge.EMFSTORE_CONTENT_TYPE);
			return ContextCore.getContextManager().getElement(bridge.getHandleIdentifier(object));
		}

		return null;
	}

	@Override
	public List<TreeViewer> getContentOutlineViewers(IEditorPart editorPart) {
		// TODO: what does it do?
		return null;
	}

	@Override
	public Object getObjectForTextSelection(TextSelection selection, IEditorPart editor) {
		return editor.getEditorInput().getAdapter(EObject.class);
	}

	@Override
	public String getContentType() {
		return EMFStoreDomainBridge.EMFSTORE_CONTENT_TYPE;
	}
}
