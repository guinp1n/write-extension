/*
 * Copyright 2018-present HiveMQ GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hivemq.extensions.helloworld;

import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.client.parameter.ConnectionAttributeStore;
import com.hivemq.extension.sdk.api.events.client.ClientLifecycleEventListener;
import com.hivemq.extension.sdk.api.events.client.parameters.AuthenticationSuccessfulInput;
import com.hivemq.extension.sdk.api.events.client.parameters.ConnectionStartInput;
import com.hivemq.extension.sdk.api.events.client.parameters.DisconnectEventInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

public class HelloWorldListener implements ClientLifecycleEventListener {

    private static final @NotNull Logger log = LoggerFactory.getLogger(HelloWorldListener.class);

    @Override
    public void onMqttConnectionStart(final @NotNull ConnectionStartInput connectionStartInput) {
        log.info("write-extension.ClientLifecycleEventListener.onMqttConnectionStart    – clientId: {}.",
                connectionStartInput.getClientInformation().getClientId());
        final ConnectionAttributeStore connectionAttributeStore = connectionStartInput.getConnectionInformation().getConnectionAttributeStore();
        connectionAttributeStore.putAsString("write-extension.ClientLifecycleEventListener.onMqttConnectionStart", ZonedDateTime.now().toString());
    }

    @Override
    public void onAuthenticationSuccessful(final @NotNull AuthenticationSuccessfulInput authenticationSuccessfulInput) {
        log.info("write-extension.ClientLifecycleEventListener.onAuthenticationSuccessful    – clientId: {}.",
                authenticationSuccessfulInput.getClientInformation().getClientId());
    }

    @Override
    public void onDisconnect(final @NotNull DisconnectEventInput disconnectEventInput) {
        log.info("write-extension.ClientLifecycleEventListener.onDisconnect    – clientId: {}.",
                disconnectEventInput.getClientInformation().getClientId());
    }
}