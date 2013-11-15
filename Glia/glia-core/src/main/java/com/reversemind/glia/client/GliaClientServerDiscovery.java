package com.reversemind.glia.client;

import com.reversemind.glia.servicediscovery.ServiceDiscoverer;
import com.reversemind.glia.servicediscovery.serializer.ServerMetadata;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.Serializable;

/**
 * Date: 4/30/13
 * Time: 2:01 PM
 *
 * @author konilovsky
 * @since 1.0
 */
public class GliaClientServerDiscovery extends GliaClient implements IGliaClient, Serializable {

    private final static Logger LOG = LoggerFactory.getLogger(GliaClientServerDiscovery.class);

    private String zookeeperHosts;
    private String serviceBasePath;
    private String serviceName;
    private IServerSelectorStrategy serverSelectorStrategy;

    private ServiceDiscoverer serviceDiscoverer;

    /**
     * @param zookeeperHosts
     * @param serviceBasePath
     * @param serviceName
     * @param clientTimeOut
     * @param serverSelectorStrategy
     */
    public GliaClientServerDiscovery(String zookeeperHosts,
                                     String serviceBasePath,
                                     String serviceName,
                                     long clientTimeOut,
                                     IServerSelectorStrategy serverSelectorStrategy) {
        this.zookeeperHosts = zookeeperHosts;
        this.serviceBasePath = serviceBasePath;
        this.serviceName = serviceName;
        this.serverSelectorStrategy = serverSelectorStrategy;
        this.setClientTimeOut(clientTimeOut);
        this.serviceDiscoverer = new ServiceDiscoverer(this.zookeeperHosts, this.serviceBasePath);
    }

    @Override
    public void start() throws Exception {
        ServerMetadata serverMetadata = this.serverSelectorStrategy.selectServer(this.serviceDiscoverer.discover(this.serviceName));
        if (serverMetadata != null && !StringUtils.isEmpty(serverMetadata.getHost()) && serverMetadata.getPort() > 0) {
            LOG.info("found server:" + serverMetadata);
            this.port = serverMetadata.getPort();
            this.host = serverMetadata.getHost();
            super.start();
            return;
        }
        throw new Exception("Could not find any available server for the ServiceName:" + this.serviceName + " on path:" + this.serviceBasePath);
    }

    @Override
    public void shutdown() {
        super.shutdown();
        if (this.serviceDiscoverer != null) {
            try {
                this.serviceDiscoverer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void restart() throws Exception {
        this.shutdown();
//        this.zookeeperHosts = zookeeperHosts;
//        this.serviceBasePath = serviceBasePath;
//        this.serviceName = serviceName;
//        this.serverSelectorStrategy = serverSelectorStrategy;
        this.serviceDiscoverer = new ServiceDiscoverer(this.zookeeperHosts, this.serviceBasePath);
        this.start();
    }

    @Override
    public void restart(String serverHost, int serverPort, long clientTimeOut) throws Exception {
        this.shutdown();
        this.setClientTimeOut(clientTimeOut);
        this.serviceDiscoverer = new ServiceDiscoverer(this.zookeeperHosts, this.serviceBasePath);
        this.start();
    }

}
