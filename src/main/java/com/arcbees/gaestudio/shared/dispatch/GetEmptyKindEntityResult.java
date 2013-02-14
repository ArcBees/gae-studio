package com.arcbees.gaestudio.shared.dispatch;

import com.arcbees.gaestudio.shared.dto.entity.EntityDto;
import com.gwtplatform.dispatch.shared.Result;

public class GetEmptyKindEntityResult implements Result { 
  private EntityDto entityDTO;

  protected GetEmptyKindEntityResult() {
    // Possibly for serialization.
  }

  public GetEmptyKindEntityResult(EntityDto entityDTO) {
    this.entityDTO = entityDTO;
  }

  public EntityDto getEntityDTO(){
    return entityDTO;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
        return true;
    if (obj == null)
        return false;
    if (getClass() != obj.getClass())
        return false;
    GetEmptyKindEntityResult other = (GetEmptyKindEntityResult) obj;
    if (entityDTO == null) {
      if (other.entityDTO != null)
        return false;
    } else if (!entityDTO.equals(other.entityDTO))
      return false;
    return true;
  }

  @Override
  public int hashCode() {
    int hashCode = 23;
    hashCode = (hashCode * 37) + (entityDTO == null ? 1 : entityDTO.hashCode());
    return hashCode;
  }

  @Override
  public String toString() {
    return "GetEmptyKindEntityResult["
                 + entityDTO
    + "]";
  }
}
