package org.eclipse.mylyn.mft.ecp.ui;

import java.util.Arrays;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecp.editor.MEEditor;
import org.eclipse.mylyn.context.core.AbstractContextStructureBridge;
import org.eclipse.mylyn.context.core.ContextCore;
import org.eclipse.mylyn.context.core.IInteractionContext;
import org.eclipse.mylyn.context.core.IInteractionElement;
import org.eclipse.mylyn.context.ui.AbstractContextUiBridge;
import org.eclipse.mylyn.context.ui.ContextUi;
import org.eclipse.mylyn.internal.context.core.ContextCorePlugin;
import org.eclipse.mylyn.mft.emfstore.core.EMFStoreDomainBridge;
import org.eclipse.mylyn.monitor.core.InteractionEvent;
import org.eclipse.mylyn.monitor.ui.AbstractEditorTracker;
import org.eclipse.ui.IEditorPart;

public class ECPEditorListener extends AbstractEditorTracker {

	@Override
	protected void editorOpened(IEditorPart part) {
		// ignore
	}

	@Override
	protected void editorClosed(IEditorPart part) {
		if (!(part instanceof MEEditor)) {
			return;
		}
		AbstractContextUiBridge uiBridge = ContextUi.getUiBridgeForEditor(part);
		IInteractionElement element = uiBridge.getElement(part.getEditorInput());
		if (element != null) {
			ContextCore.getContextManager().deleteElements(Arrays.asList(new IInteractionElement[] { element }));
			/**
			 * FIXME !!!!!!!!!!!!!!!!!!!!!!!!
			 * 
			 * If we delete the last context element, the context won't be
			 * saved. See InteractionContextExternalizer#writeContextToXml(...)
			 * In this case, we have to make sure that we delete the context
			 * (using restricted API).
			 */
			IInteractionContext activeContext = ContextCore.getContextManager().getActiveContext();
			if (activeContext.getInteractionHistory().size() == 0) {
				ContextCorePlugin.getContextStore().deleteContext(activeContext.getHandleIdentifier());
			}
		}
	}

	@Override
	protected void editorBroughtToTop(IEditorPart part) {
		if (!(part instanceof MEEditor)) {
			return;
		}
		Object object = part.getEditorInput().getAdapter(EObject.class);
		if (object instanceof EObject) {
			EObject resource = (EObject) object;
			AbstractContextStructureBridge bridge = ContextCore
					.getStructureBridge(EMFStoreDomainBridge.EMFSTORE_CONTENT_TYPE);
			String handleIdentifier = bridge.getHandleIdentifier(resource);
			if (handleIdentifier != null && !"/".equals(handleIdentifier)) {
				InteractionEvent selectionEvent = new InteractionEvent(InteractionEvent.Kind.SELECTION,
						bridge.getContentType(), handleIdentifier, part.getSite().getId());
				ContextCore.getContextManager().processInteractionEvent(selectionEvent);
			}
		}
	}
}
