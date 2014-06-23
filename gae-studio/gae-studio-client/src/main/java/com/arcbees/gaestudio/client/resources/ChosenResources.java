package com.arcbees.gaestudio.client.resources;

import com.arcbees.chosen.client.resources.ChozenCss;
import com.arcbees.chosen.client.resources.Resources;
import com.google.gwt.resources.client.ImageResource;

public interface ChosenResources extends Resources {
    public interface CustomChosenCss extends ChozenCss {
    }

    @Source("images/dropdown/dropDownArrowRl.png")
    ImageResource chosenArrow();

    @Override
    @Source({"com/arcbees/chosen/client/resources/chozen.css", "css/colors.css", "css/mixins.css", "css/chozen.css"})
    CustomChosenCss css();
}