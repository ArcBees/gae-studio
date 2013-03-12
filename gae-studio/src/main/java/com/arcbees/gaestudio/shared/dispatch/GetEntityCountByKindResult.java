/**
 * Copyright 2013 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

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
