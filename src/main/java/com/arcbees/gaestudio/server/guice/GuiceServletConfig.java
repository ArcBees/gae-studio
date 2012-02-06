package com.arcbees.gaestudio.server.guice;

import com.arcbees.gaestudio.server.recorder.ApiProxyHook;
import com.arcbees.gaestudio.server.recorder.DbOperationRecorderHookFactory;
import com.arcbees.gaestudio.server.recorder.DbOperationRecorderModule;
import com.google.apphosting.api.ApiProxy;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class GuiceServletConfig extends GuiceServletContextListener {

    @Override
    @SuppressWarnings("unchecked")
    protected Injector getInjector() {
        Injector injector = Guice.createInjector(new ServerModule(), new DispatchServletModule(),
                new DbOperationRecorderModule());

        ApiProxyHook hook = new ApiProxyHook(ApiProxy.getDelegate());
        hook.getHooks().put("datastore_v3",
                injector.getProvider(DbOperationRecorderHookFactory.class).get().create(hook.getBaseDelegate()));
        ApiProxy.setDelegate(hook);

        return injector;
    }

}
