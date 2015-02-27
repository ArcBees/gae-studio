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

package com.arcbees.gaestudio.server.util;

import org.jukito.All;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.arcbees.gaestudio.testutil.GaeTestBase;
import com.google.appengine.api.datastore.Query;
import com.google.common.collect.Lists;

import static org.assertj.core.api.BDDAssertions.then;

import static com.google.appengine.api.datastore.Query.FilterOperator.EQUAL;
import static com.google.appengine.api.datastore.Query.FilterOperator.GREATER_THAN;
import static com.google.appengine.api.datastore.Query.FilterOperator.GREATER_THAN_OR_EQUAL;
import static com.google.appengine.api.datastore.Query.FilterOperator.IN;
import static com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN;
import static com.google.appengine.api.datastore.Query.FilterOperator.LESS_THAN_OR_EQUAL;
import static com.google.appengine.api.datastore.Query.FilterOperator.NOT_EQUAL;

@RunWith(JukitoRunner.class)
public class DatastoreHelperTest extends GaeTestBase {
    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            bindManyNamedInstances(FilterOperation.class, NOT_INEQUALITY,
                    new FilterOperation(EQUAL, ""),
                    new FilterOperation(IN, Lists.newArrayList("")),
                    new FilterOperation(NOT_EQUAL, ""));

            bindManyNamedInstances(FilterOperation.class, INEQUALITY,
                    new FilterOperation(GREATER_THAN, ""),
                    new FilterOperation(GREATER_THAN_OR_EQUAL, ""),
                    new FilterOperation(LESS_THAN, ""),
                    new FilterOperation(LESS_THAN_OR_EQUAL, ""));
        }
    }

    private static class FilterOperation {
        private final Query.FilterOperator operator;
        private final Object value;

        FilterOperation(Query.FilterOperator operator, Object value) {
            this.operator = operator;
            this.value = value;
        }
    }

    private static final String A_KIND = "A_KIND";
    private static final String A_PROPERTY = "A_PROPERTY";
    private static final String NOT_INEQUALITY = "NOT_INEQUALITY";
    private static final String INEQUALITY = "INEQUALITY";

    private DatastoreHelper datastoreHelper;

    @Before
    public void setUp() {
        super.setUp();

        datastoreHelper = new DatastoreHelper();
    }

    @Test
    public void preFilterGaeKinds_validQuery_addsFilter() throws Exception {
        // given
        Query query = new Query(A_KIND);

        // when
        datastoreHelper.preFilterGaeKinds(query);

        // then
        then(query.getFilter()).isInstanceOf(Query.FilterPredicate.class);
        then(query.getFilter()).isNotNull();
    }

    @Test
    public void preFilterGaeKinds_withFilter_addsFilter(@All(NOT_INEQUALITY) FilterOperation operation) {
        // given
        Query query = new Query(A_KIND);
        query.setFilter(new Query.FilterPredicate(A_PROPERTY, operation.operator, operation.value));

        // when
        datastoreHelper.preFilterGaeKinds(query);

        // then
        then(query.getFilter()).isInstanceOf(Query.CompositeFilter.class);
    }

    @Test(expected = IllegalArgumentException.class)
    public void preFilterGaeKinds_withInequalityFilter_throwsIllegalArgumentException(
            @All(INEQUALITY) FilterOperation operation) throws Exception {
        // given
        Query query = new Query(A_KIND);
        query.setFilter(new Query.FilterPredicate(A_PROPERTY, operation.operator, operation.value));

        // when
        datastoreHelper.preFilterGaeKinds(query);
    }

    @Test
    public void canPreFilterGaeKinds_withFilter_returnsTrue(@All(NOT_INEQUALITY) FilterOperation operation) {
        // given
        Query query = new Query(A_KIND);
        query.setFilter(new Query.FilterPredicate(A_PROPERTY, operation.operator, operation.value));

        // when
        boolean canPreFilter = datastoreHelper.canPreFilterGaeKinds(query);

        // then
        then(canPreFilter).isTrue();
    }

    @Test
    public void canPreFilterGaeKinds_withFilter_returnsFalse(@All(INEQUALITY) FilterOperation operation) {
        // given
        Query query = new Query(A_KIND);
        query.setFilter(new Query.FilterPredicate(A_PROPERTY, operation.operator, operation.value));

        // when
        boolean canPreFilter = datastoreHelper.canPreFilterGaeKinds(query);

        // then
        then(canPreFilter).isFalse();
    }
}
