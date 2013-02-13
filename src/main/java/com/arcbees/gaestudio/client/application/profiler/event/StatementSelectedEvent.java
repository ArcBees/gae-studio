package com.arcbees.gaestudio.client.application.profiler.event;

import com.arcbees.gaestudio.shared.dto.DbOperationRecordDto;
import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HasHandlers;
import com.google.web.bindery.event.shared.HandlerRegistration;

public class StatementSelectedEvent extends GwtEvent<StatementSelectedEvent.StatementSelectedHandler> {
    private DbOperationRecordDto record;

    protected StatementSelectedEvent() {
        // Possibly for serialization.
    }

    public StatementSelectedEvent(DbOperationRecordDto record) {
        this.record = record;
    }

    public static void fire(HasHandlers source, DbOperationRecordDto record) {
        StatementSelectedEvent eventInstance = new StatementSelectedEvent(record);
        source.fireEvent(eventInstance);
    }

    public static void fire(HasHandlers source, StatementSelectedEvent eventInstance) {
        source.fireEvent(eventInstance);
    }

    public interface HasStatementSelectedHandlers extends HasHandlers {
        HandlerRegistration addStatementSelectedHandler(StatementSelectedHandler handler);
    }

    public interface StatementSelectedHandler extends EventHandler {
        public void onStatementSelected(StatementSelectedEvent event);
    }

    private static final Type<StatementSelectedHandler> TYPE = new Type<StatementSelectedHandler>();

    public static Type<StatementSelectedHandler> getType() {
        return TYPE;
    }

    @Override
    public Type<StatementSelectedHandler> getAssociatedType() {
        return TYPE;
    }

    @Override
    protected void dispatch(StatementSelectedHandler handler) {
        handler.onStatementSelected(this);
    }

    public DbOperationRecordDto getRecord() {
        return record;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StatementSelectedEvent other = (StatementSelectedEvent) obj;
        if (record == null) {
            if (other.record != null)
                return false;
        } else if (!record.equals(other.record))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (record == null ? 1 : record.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "StatementSelectedEvent[" + record + "]";
    }
}
