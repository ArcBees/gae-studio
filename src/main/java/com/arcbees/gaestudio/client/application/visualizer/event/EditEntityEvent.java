package com.arcbees.gaestudio.client.application.visualizer.event;

import com.arcbees.gaestudio.client.application.visualizer.ParsedEntity;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class EditEntityEvent extends GwtEvent<EditEntityEvent.EditEntityHandler> {
    private ParsedEntity parsedEntity;

    protected EditEntityEvent() {
        // Possibly for serialization.
    }

    public EditEntityEvent(ParsedEntity parsedEntity) {
        this.parsedEntity = parsedEntity;
    }

    public static void fire(HasHandlers source, ParsedEntity parsedEntity) {
        EditEntityEvent eventInstance = new EditEntityEvent(parsedEntity);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, EditEntityEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasEditEntityHandlers extends HasHandlers {
        HandlerRegistration addEditEntityHandler(EditEntityHandler handler);
    }

    public interface EditEntityHandler extends EventHandler {
        public void onEditEntity(EditEntityEvent event);
    }

    private static final Type<EditEntityHandler> TYPE = new Type<EditEntityHandler>();

    public static Type<EditEntityHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<EditEntityHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(EditEntityHandler handler) {
        handler.onEditEntity(this);
    }

    public ParsedEntity getParsedEntity() {
        return parsedEntity;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        EditEntityEvent other = (EditEntityEvent) obj;
        if (parsedEntity == null) {
            if (other.parsedEntity != null)
                return false;
        } else if (!parsedEntity.equals(other.parsedEntity))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (parsedEntity == null ? 1 : parsedEntity.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "EditEntityEvent[" + parsedEntity + "]";
    }
}
