package com.arcbees.gaestudio.client.application.visualizer.event;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.web.bindery.event.shared.HandlerRegistration;

import com.google.gwt.event.shared.HasHandlers;

public class KindSelectedEvent extends GwtEvent<KindSelectedEvent.KindSelectedHandler> {
    private String kind;

    protected KindSelectedEvent() {
        // Possibly for serialization.
    }

    public KindSelectedEvent(String kind) {
        this.kind = kind;
    }

    public static void fire(HasHandlers source, String kind) {
        KindSelectedEvent eventInstance = new KindSelectedEvent(kind);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, KindSelectedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasKindSelectedHandlers extends HasHandlers {
        HandlerRegistration addKindSelectedHandler(KindSelectedHandler handler);
    }

    public interface KindSelectedHandler extends EventHandler {
        public void onKindSelected(KindSelectedEvent event);
    }

    private static final Type<KindSelectedHandler> TYPE = new Type<KindSelectedHandler>();

    public static Type<KindSelectedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<KindSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(KindSelectedHandler handler) {
        handler.onKindSelected(this);
    }

    public String getKind() {
        return kind;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        KindSelectedEvent other = (KindSelectedEvent) obj;
        if (kind == null) {
            if (other.kind != null)
                return false;
        } else if (!kind.equals(other.kind))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (kind == null ? 1 : kind.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "KindSelectedEvent[" + kind + "]";
    }
}
