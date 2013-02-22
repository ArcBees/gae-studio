package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;

public class GetEntityCountByKindAction extends GaeStudioActionImpl<GetEntityCountByKindResult> { 
  private String kind;

  protected GetEntityCountByKindAction() {
    // Possibly for serialization.
  }

  public GetEntityCountByKindAction(String kind) {
    this.kind = kind;
  }

  public String getKind(){
    return kind;
  }
}
