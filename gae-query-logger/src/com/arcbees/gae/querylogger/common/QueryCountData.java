/*
 * Copyright 2011 ArcBees Inc.
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

package com.arcbees.gae.querylogger.common;

import java.io.Serializable;
import java.util.Set;
import java.util.TreeSet;

public class QueryCountData implements Serializable {

    private static final long serialVersionUID = -182068789701427739L;

    private Integer count;

    private Set<String> locations;

    public QueryCountData() {
        this.count = 0;
        this.locations = new TreeSet<String>();
    }

    public Integer getCount() {
        return count;
    }
    
    protected void setCount(Integer count) {
        this.count = count;
    }

    public void incrementCount() {
        this.count++;
    }

    public Set<String> getLocations() {
        return locations;
    }

    protected void setLocations(Set<String> locations) {
        this.locations = locations;
    }
    
    public void addLocation(String location) {
        this.locations.add(location);
    }

}
