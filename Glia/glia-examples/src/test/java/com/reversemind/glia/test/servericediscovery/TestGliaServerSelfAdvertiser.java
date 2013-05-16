package com.reversemind.glia.test.servericediscovery;

import com.reversemind.glia.server.*;
import org.junit.Ignore;
import org.junit.Test;

import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 11:45 AM
 *
 * @author konilovsky
 * @since 1.0
 */
public class TestGliaServerSelfAdvertiser implements Serializable {

    @Ignore
    @Test
    public void testGliaServerSelfAdvertiser() throws InterruptedException {

        final String ZOOKEEPER_CONNECTION = "localhost:2181";
        final String SERVICE_BASE_PATH = "/baloo/services";
        final String SERVICE_NAME = "ADDRESS";

        IGliaPayloadProcessor gliaPayloadProcessor = new GliaPayloadProcessor();

        IGliaServer server01 = GliaServerFactory.builder()
                .setPayloadWorker(gliaPayloadProcessor)
                .setName(SERVICE_NAME)
                .setInstanceName("INSTANCE.001")
                .setZookeeperConnectionString(ZOOKEEPER_CONNECTION)
                .setServiceBasePath(SERVICE_BASE_PATH)
                .setAutoSelectPort(true)
                .setKeepClientAlive(false)
                .build();

        IGliaServer server02 = GliaServerFactory.builder()
                .setPayloadWorker(gliaPayloadProcessor)
                .setName(SERVICE_NAME)
                .setInstanceName("INSTANCE.002")
                .setZookeeperConnectionString(ZOOKEEPER_CONNECTION)
                .setServiceBasePath(SERVICE_BASE_PATH)
                .setAutoSelectPort(true)
                .setKeepClientAlive(false)
                .build();

        server01.start();
        server02.start();

        for(int i=0;i<100;i++){
            Thread.sleep(1000);
            //server01.updateMetrics();
            server01.getMetrics().addRequest(10);
        }

        Thread.sleep(1000 * 60 * 1000);

        server01.shutdown();
        server02.shutdown();
    }

}
