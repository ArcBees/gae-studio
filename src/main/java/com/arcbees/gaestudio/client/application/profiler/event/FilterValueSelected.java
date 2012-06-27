package com.arcbees.gaestudio.client.application.profiler.event;

import com.arcbees.gaestudio.client.application.profiler.widget.filter.FilterValue;
import com.gwtplatform.dispatch.annotation.GenEvent;

@GenEvent
public class FilterValueSelected {
    
    FilterValue filterValue;
    
}
