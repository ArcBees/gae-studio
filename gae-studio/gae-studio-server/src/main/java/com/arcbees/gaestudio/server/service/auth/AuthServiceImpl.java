package com.arcbees.gaestudio.server.service.auth;

import javax.inject.Inject;

import com.arcbees.oauth.client.OAuthClient;
import com.arcbees.oauth.client.UserClient;
import com.arcbees.oauth.client.domain.Token;
import com.arcbees.oauth.client.domain.User;
import com.google.appengine.api.NamespaceManager;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Query;
import com.google.common.base.Strings;

import static com.arcbees.gaestudio.server.GaeStudioConstants.GAE_NAMESPACE;
import static com.arcbees.gaestudio.server.GaeStudioConstants.GAE_USER_KIND;
import static com.arcbees.gaestudio.server.GaeStudioConstants.GA_CLIENT_KIND;

public class AuthServiceImpl implements AuthService {
    public static final String API_TOKEN = "ljhs98234h24o8dsyfjehrljqh01923874j2hj";

    private static final String TOKEN = "token";

    private final OAuthClient oAuthClient;
    private final UserClient userClient;

    @Inject
    AuthServiceImpl(OAuthClient oAuthClient,
                    UserClient userClient) {
        this.oAuthClient = oAuthClient;
        this.userClient = userClient;
    }

    @Override
    public User register(String email, String password, String firstName, String lastName) {
        Token bearerToken = getBearerToken();

        return userClient.register(bearerToken, email, password, firstName, lastName);
    }

    @Override
    public void requestResetToken(String email) {
        Token bearerToken = getBearerToken();

        userClient.requestResetPassword(bearerToken, email);
    }

    @Override
    public User checkLogin() {
        String authToken = getSavedAuthToken();

        User user = null;
        if (!Strings.isNullOrEmpty(authToken)) {
            user = oAuthClient.checkLogin(authToken);
        }

        return user;
    }

    @Override
    public Token login(String email, String password) {
        Token token = oAuthClient.login(email, password);

        saveAuthToken(token);

        return token;
    }

    private String getSavedAuthToken() {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(GAE_NAMESPACE);

        Query query = new Query(GAE_USER_KIND);
        Entity entity = datastoreService.prepare(query).asSingleEntity();

        NamespaceManager.set(defaultNamespace);

        return entity == null ? null : entity.getProperty(TOKEN).toString();
    }

    private void saveAuthToken(Token token) {
        DatastoreService datastoreService = DatastoreServiceFactory.getDatastoreService();

        String defaultNamespace = NamespaceManager.get();
        NamespaceManager.set(GAE_NAMESPACE);

        Query query = new Query(GAE_USER_KIND);
        Entity entity = datastoreService.prepare(query).asSingleEntity();

        if (entity == null) {
            entity = new Entity(GAE_USER_KIND);
        }

        entity.setProperty(TOKEN, token.getToken());
        datastoreService.put(entity);

        NamespaceManager.set(defaultNamespace);
    }

    private Token getBearerToken() {
        return oAuthClient.getBearerToken(API_TOKEN);
    }
}
