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

import com.hivemq.extension.sdk.api.ExtensionMain;
import com.hivemq.extension.sdk.api.annotations.NotNull;
import com.hivemq.extension.sdk.api.auth.SimpleAuthenticator;
import com.hivemq.extension.sdk.api.events.EventRegistry;
import com.hivemq.extension.sdk.api.parameter.*;
import com.hivemq.extension.sdk.api.services.Services;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloWorldMain implements ExtensionMain {

    private static final @NotNull Logger log = LoggerFactory.getLogger(HelloWorldMain.class);
    private static final @NotNull SimpleAuthenticator helloWriteAuthenticator = new HelloWorldAuthenticator();

    @Override
    public void extensionStart(
            final @NotNull ExtensionStartInput extensionStartInput,
            final @NotNull ExtensionStartOutput extensionStartOutput) {

        try {
            addConnectInboundInterceptor();
            addClientLifecycleEventListener();
            Services.securityRegistry().setAuthenticatorProvider(authenticatorProviderInput -> helloWriteAuthenticator);

            final ExtensionInformation extensionInformation = extensionStartInput.getExtensionInformation();
            log.info("Hello, I have started! {}:{}", extensionInformation.getName(), extensionInformation.getVersion());

        } catch (final Exception e) {
            log.error("Exception thrown at extension start: ", e);
        }
    }

    @Override
    public void extensionStop(
            final @NotNull ExtensionStopInput extensionStopInput,
            final @NotNull ExtensionStopOutput extensionStopOutput) {

        final ExtensionInformation extensionInformation = extensionStopInput.getExtensionInformation();
        log.info("Stopped " + extensionInformation.getName() + ":" + extensionInformation.getVersion());
    }

    private void addClientLifecycleEventListener() {
        final EventRegistry eventRegistry = Services.eventRegistry();

        final HelloWorldListener helloWorldListener = new HelloWorldListener();

        eventRegistry.setClientLifecycleEventListener(input -> helloWorldListener);
    }

    private void addConnectInboundInterceptor() {
        final HelloWorldInterceptor helloWorldInterceptor = new HelloWorldInterceptor();

        Services.interceptorRegistry().setConnectInboundInterceptorProvider(input -> helloWorldInterceptor);
    }
}