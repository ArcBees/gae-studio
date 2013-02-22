package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;

public class GetEmptyKindEntityAction extends GaeStudioActionImpl<GetEmptyKindEntityResult> {
    private String kind;

    protected GetEmptyKindEntityAction() {
        // Possibly for serialization.
    }

    public GetEmptyKindEntityAction(String kind) {
        this.kind = kind;
    }
    
    public String getKind() {
      return kind;
    }
}
