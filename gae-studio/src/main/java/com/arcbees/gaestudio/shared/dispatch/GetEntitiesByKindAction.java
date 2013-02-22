package com.arcbees.gaestudio.shared.dispatch;

import com.gwtplatform.dispatch.shared.Action;

public class GetEntitiesByKindAction implements Action<GetEntitiesByKindResult> { 
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

  @Override
  public String getServiceName() {
    return Action.DEFAULT_SERVICE_NAME + "GetEntitiesByKind";
  }

  @Override
  public boolean isSecured() {
    return false;
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

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetEntitiesByKindAction other = (GetEntitiesByKindAction) obj;
    if (kind == null) {
      if (other.kind != null)
        return false;
    } else if (!kind.equals(other.kind))
      return false;
    if (offset == null) {
      if (other.offset != null)
        return false;
    } else if (!offset.equals(other.offset))
      return false;
    if (limit == null) {
      if (other.limit != null)
        return false;
    } else if (!limit.equals(other.limit))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (kind == null ? 1 : kind.hashCode());
    hashCode = (hashCode * 37) + (offset == null ? 1 : offset.hashCode());
    hashCode = (hashCode * 37) + (limit == null ? 1 : limit.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetEntitiesByKindAction["
                 + kind
                 + ","
                 + offset
                 + ","
                 + limit
    + "]";
  }
}
