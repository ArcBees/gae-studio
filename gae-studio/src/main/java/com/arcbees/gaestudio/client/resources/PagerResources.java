package com.arcbees.gaestudio.client.resources;

import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.cellview.client.SimplePager;

public interface PagerResources extends SimplePager.Resources {
    public interface PageStyles extends SimplePager.Style {
        @Override
        String pageDetails();
    }

    @Override
    PageStyles simplePagerStyle();

    @Override
    ImageResource simplePagerFastForward();

    @Override
    ImageResource simplePagerPreviousPage();

    @Override
    ImageResource simplePagerPreviousPageDisabled();

    @Override
    ImageResource simplePagerNextPageDisabled();

    @Override
    ImageResource simplePagerNextPage();

    @Override
    ImageResource simplePagerLastPageDisabled();

    @Override
    ImageResource simplePagerLastPage();

    @Override
    ImageResource simplePagerFirstPageDisabled();

    @Override
    ImageResource simplePagerFirstPage();

    @Override
    ImageResource simplePagerFastForwardDisabled();
}
