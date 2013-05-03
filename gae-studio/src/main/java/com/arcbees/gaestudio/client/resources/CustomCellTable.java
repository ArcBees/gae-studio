package com.arcbees.gaestudio.client.resources;

import com.google.gwt.user.cellview.client.CellTable;

public interface CustomCellTable extends CellTable.Resources {
    interface TableStyle extends CellTable.Style {
    }

    @Override
    @Source({ CellTable.Style.DEFAULT_CSS, "com/arcbees/gaestudio/client/resources/cellTableStyles.css" })
    TableStyle cellTableStyle();
}
