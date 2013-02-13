package com.arcbees.gaestudio.shared.dispatch;

import com.gwtplatform.dispatch.shared.Result;

public class GetEntityCountByKindResult implements Result { 
  private Integer count;

  protected GetEntityCountByKindResult() {
    // Possibly for serialization.
  }

  public GetEntityCountByKindResult(Integer count) {
    this.count = count;
  }

  public Integer getCount(){
    return count;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetEntityCountByKindResult other = (GetEntityCountByKindResult) obj;
    if (count == null) {
      if (other.count != null)
        return false;
    } else if (!count.equals(other.count))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (count == null ? 1 : count.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetEntityCountByKindResult["
                 + count
    + "]";
  }
}
