/**
 * Copyright (c) 2014 by ArcBees Inc., All rights reserved.
 * This source code, and resulting software, is the confidential and proprietary information
 * ("Proprietary Information") and is the intellectual property ("Intellectual Property")
 * of ArcBees Inc. ("The Company"). You shall not disclose such Proprietary Information and
 * shall use it only in accordance with the terms and conditions of any and all license
 * agreements you have entered into with The Company.
 */

package com.arcbees.gaestudio.server.util;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JsonToCsvConverterTest {
    @Test
    public void convertJsonToCsv() throws JSONException {
        String jsonData = getJsonData();

        String result = JsonToCsvConverter.convert(jsonData);

        assertEquals(getCsvData(), result);
    }

    private String getCsvData() {
        return "id, model, manufacturer, carProperties\n" +
                "23, Civic, Manufacturer(17), CarProperties(19)\n" +
                "24, Accord, Manufacturer(17), CarProperties(20)\n";
    }

    private String getJsonData() {
        return "[\n" +
                "  {\n" +
                "    \"key\": {\n" +
                "      \"parentKey\": null,\n" +
                "      \"kind\": \"Car\",\n" +
                "      \"appId\": null,\n" +
                "      \"id\": 23,\n" +
                "      \"name\": null,\n" +
                "      \"appIdNamespace\": {\n" +
                "        \"appId\": \"s~gaestudio-staging\",\n" +
                "        \"namespace\": \"\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"propertyMap\": {\n" +
                "      \"model\": {\n" +
                "        \"value\": \"Civic\",\n" +
                "        \"__gaePropertyType\": \"STRING\"\n" +
                "      },\n" +
                "      \"manufacturer\": {\n" +
                "        \"value\": {\n" +
                "          \"parentKey\": null,\n" +
                "          \"kind\": \"Manufacturer\",\n" +
                "          \"appId\": null,\n" +
                "          \"id\": 17,\n" +
                "          \"name\": null,\n" +
                "          \"appIdNamespace\": {\n" +
                "            \"appId\": \"gwtp-carstore\",\n" +
                "            \"namespace\": \"\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"__gaePropertyType\": \"KEY\"\n" +
                "      },\n" +
                "      \"carProperties\": {\n" +
                "        \"value\": {\n" +
                "          \"parentKey\": null,\n" +
                "          \"kind\": \"CarProperties\",\n" +
                "          \"appId\": null,\n" +
                "          \"id\": 19,\n" +
                "          \"name\": null,\n" +
                "          \"appIdNamespace\": {\n" +
                "            \"appId\": \"gwtp-carstore\",\n" +
                "            \"namespace\": \"\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"__gaePropertyType\": \"KEY\"\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  {\n" +
                "    \"key\": {\n" +
                "      \"parentKey\": null,\n" +
                "      \"kind\": \"Car\",\n" +
                "      \"appId\": null,\n" +
                "      \"id\": 24,\n" +
                "      \"name\": null,\n" +
                "      \"appIdNamespace\": {\n" +
                "        \"appId\": \"s~gaestudio-staging\",\n" +
                "        \"namespace\": \"\"\n" +
                "      }\n" +
                "    },\n" +
                "    \"propertyMap\": {\n" +
                "      \"model\": {\n" +
                "        \"value\": \"Accord\",\n" +
                "        \"__gaePropertyType\": \"STRING\"\n" +
                "      },\n" +
                "      \"manufacturer\": {\n" +
                "        \"value\": {\n" +
                "          \"parentKey\": null,\n" +
                "          \"kind\": \"Manufacturer\",\n" +
                "          \"appId\": null,\n" +
                "          \"id\": 17,\n" +
                "          \"name\": null,\n" +
                "          \"appIdNamespace\": {\n" +
                "            \"appId\": \"gwtp-carstore\",\n" +
                "            \"namespace\": \"\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"__gaePropertyType\": \"KEY\"\n" +
                "      },\n" +
                "      \"carProperties\": {\n" +
                "        \"value\": {\n" +
                "          \"parentKey\": null,\n" +
                "          \"kind\": \"CarProperties\",\n" +
                "          \"appId\": null,\n" +
                "          \"id\": 20,\n" +
                "          \"name\": null,\n" +
                "          \"appIdNamespace\": {\n" +
                "            \"appId\": \"gwtp-carstore\",\n" +
                "            \"namespace\": \"\"\n" +
                "          }\n" +
                "        },\n" +
                "        \"__gaePropertyType\": \"KEY\"\n" +
                "      }\n" +
                "    }\n" +
                "  }]";
    }
}
