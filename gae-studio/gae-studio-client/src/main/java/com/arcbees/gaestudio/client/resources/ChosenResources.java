/**
 * Copyright 2015 ArcBees Inc.
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

package com.arcbees.gaestudio.client.resources;

import com.arcbees.chosen.client.resources.ChozenCss;
import com.arcbees.chosen.client.resources.Resources;
import com.google.gwt.resources.client.ImageResource;

public interface ChosenResources extends Resources {
    public interface CustomChosenCss extends ChozenCss {
    }

    @Source("images/dropdown/dropDownArrowUp.png")
    ImageResource chosenArrow();

    @Override
    @Source({"com/arcbees/gsss/mixin/client/mixins.gss", "com/arcbees/chosen/client/resources/colors.gss",
            "com/arcbees/chosen/client/resources/icons/icons.gss", "com/arcbees/chosen/client/resources/chozen.gss",
            "css/chozen.gss"})
    CustomChosenCss css();
}
