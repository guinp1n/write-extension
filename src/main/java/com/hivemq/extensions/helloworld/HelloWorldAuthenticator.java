package com.hivemq.extensions.helloworld;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.auth.SimpleAuthenticator;
import com.hivemq.extension.sdk.api.auth.parameter.SimpleAuthInput;
import com.hivemq.extension.sdk.api.auth.parameter.SimpleAuthOutput;
import com.hivemq.extension.sdk.api.client.parameter.ConnectionAttributeStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

public class HelloWorldAuthenticator implements SimpleAuthenticator {
    private static final @NotNull Logger log = LoggerFactory.getLogger(HelloWorldAuthenticator.class);

    @Override
    public void onConnect(final @NotNull SimpleAuthInput simpleAuthInput, final @NotNull SimpleAuthOutput simpleAuthOutput) {
        final String clientId = simpleAuthInput.getClientInformation().getClientId();

        log.info("write-extension.SimpleAuthenticator.onConnect – clientId {}.", clientId);

        final ConnectionAttributeStore connectionAttributeStore = simpleAuthInput
                .getConnectionInformation()
                .getConnectionAttributeStore();
        connectionAttributeStore.putAsString("write-extension.SimpleAuthenticator.onConnect", ZonedDateTime.now().toString());

        simpleAuthOutput.nextExtensionOrDefault();
    }
}