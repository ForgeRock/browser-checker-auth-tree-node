/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2017-2018 ForgeRock AS.
 */
/*
 * simon.moffatt@forgerock.com
 *
 * Needed to register the node
 */

package org.forgerock.openam.auth.nodes;

import static java.util.Collections.singletonList;

import java.util.Map;

import javax.inject.Inject;

import org.forgerock.openam.auth.node.api.AbstractNodeAmPlugin;
import org.forgerock.openam.auth.node.api.Node;
import org.forgerock.openam.plugins.PluginException;
import org.forgerock.openam.plugins.StartupType;

import com.google.common.collect.ImmutableMap;

/**
 * Core nodes installed by default with no engine dependencies.
 */
public class BrowserCollectorNodePlugin extends AbstractNodeAmPlugin {

    /**
     * DI-enabled constructor.
     */
    @Inject
    public BrowserCollectorNodePlugin() {
    }

    @Override
    public String getPluginVersion() {
        return "1.0.0";
    }

    @Override
    public void onInstall() throws PluginException {
        for (String version : getNodesByVersion().keySet()) {
            for (Class<? extends Node> nodeClass : getNodesByVersion().get(version)) {
                pluginTools.installAuthNode(nodeClass);
            }
        }
    }

    @Override
    public void onStartup(StartupType startupType) throws PluginException {
        for (String version : getNodesByVersion().keySet()) {
            for (Class<? extends Node> nodeClass : getNodesByVersion().get(version)) {
                pluginTools.startAuthNode(nodeClass);
            }
        }
    }

    @Override
    protected Map<String, Iterable<? extends Class<? extends Node>>> getNodesByVersion() {
        return ImmutableMap.of("1.0.0", singletonList(BrowserCollectorNode.class));
    }
}
