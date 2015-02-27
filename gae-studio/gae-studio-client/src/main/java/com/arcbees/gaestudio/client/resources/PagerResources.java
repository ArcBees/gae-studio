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

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.SimplePager;

public interface PagerResources extends SimplePager.Resources {
    public interface PageStyles extends SimplePager.Style {
        @Override
        String pageDetails();
    }

    @Override
    @Source("css/simplePagerStyle.gss")
    PageStyles simplePagerStyle();

    @Override
    @Source("images/pager/next_up.png")
    ImageResource simplePagerPreviousPage();

    @Override
    @Source("images/pager/next_dis.png")
    ImageResource simplePagerPreviousPageDisabled();

    @Override
    @Source("images/pager/next_dis.png")
    ImageResource simplePagerNextPageDisabled();

    @Override
    @Source("images/pager/next_up.png")
    ImageResource simplePagerNextPage();

    @Override
    @Source("images/pager/end_dis.png")
    ImageResource simplePagerLastPageDisabled();

    @Override
    @Source("images/pager/end_up.png")
    ImageResource simplePagerLastPage();

    @Override
    @Source("images/pager/end_dis.png")
    ImageResource simplePagerFirstPageDisabled();

    @Override
    @Source("images/pager/end_up.png")
    ImageResource simplePagerFirstPage();
}
