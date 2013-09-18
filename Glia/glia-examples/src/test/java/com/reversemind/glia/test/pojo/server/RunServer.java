package com.reversemind.glia.test.pojo.server;

import com.reversemind.glia.server.GliaPayloadProcessor;
import com.reversemind.glia.server.GliaServerFactory;
import com.reversemind.glia.server.IGliaServer;
import com.reversemind.glia.test.pojo.shared.ISimplePojo;

import java.io.Serializable;

/**
 * Date: 4/24/13
 * Time: 5:06 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class RunServer implements Serializable {

    public static void main(String... args) {

        GliaPayloadProcessor gliaPayloadProcessor = new GliaPayloadProcessor();
        gliaPayloadProcessor.registerPOJO(ISimplePojo.class, SimplePojo.class);

        IGliaServer server = GliaServerFactory.builder()
                .setPayloadWorker(gliaPayloadProcessor)
                .setPort(7012)
                .setAutoSelectPort(false)
                .setKeepClientAlive(false)
                .setUseMetrics(true)
                .build();

        System.out.println("Started on port:" + server.getPort());
        server.start();
    }

}
