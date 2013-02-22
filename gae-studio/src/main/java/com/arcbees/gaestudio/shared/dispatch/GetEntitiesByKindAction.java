package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dispatch.util.GaeStudioActionImpl;

public class GetEntitiesByKindAction extends GaeStudioActionImpl<GetEntitiesByKindResult> { 
  private String kind;
  private Integer offset;
  private Integer limit;

  public static class Builder { 
    // Required parameters
    private final String kind;

    // Optional parameters - initialized to default values
    private Integer offset;
    private Integer limit;

    public Builder(String kind) {
      this.kind = kind;
    }

    public Builder offset(Integer offset) {
      this.offset = offset;
      return this;
    }

    public Builder limit(Integer limit) {
      this.limit = limit;
      return this;
    }

    public GetEntitiesByKindAction build() {
      return new GetEntitiesByKindAction(this);
    }
  }

  protected GetEntitiesByKindAction() {
    // Possibly for serialization.
  }

  public GetEntitiesByKindAction(String kind) {
    this.kind = kind;
  }

  private GetEntitiesByKindAction(Builder builder) {
    this.kind = builder.kind;
    this.offset = builder.offset;
    this.limit = builder.limit;
  }

  public String getKind(){
    return kind;
  }

  public Integer getOffset(){
    return offset;
  }

  public Integer getLimit(){
    return limit;
  }
}
