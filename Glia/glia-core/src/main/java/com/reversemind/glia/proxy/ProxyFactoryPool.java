package com.reversemind.glia.proxy;

import com.reversemind.glia.client.ClientPool;
import com.reversemind.glia.client.IGliaClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Proxy;

/**
 *
 */
public class ProxyFactoryPool {

    private final static Logger LOG = LoggerFactory.getLogger(ProxyFactoryPool.class);
    private static final ProxyFactoryPool proxyFactoryPool = new ProxyFactoryPool();

    private ProxyFactoryPool() {
    }

    public static ProxyFactoryPool getInstance() {
        return proxyFactoryPool;
    }

    public Object newProxyInstance(ClientPool clientPool, Class interfaceClass) {
        // TODO make map for classLoader - key is a interfaceClass.name
        ClassLoader classLoader = interfaceClass.getClassLoader();
        LOG.debug("GLIA PROXY FACTORY clientPool" + clientPool);
        LOG.debug("GLIA PROXY FACTORY classLoader:" + classLoader);
        return Proxy.newProxyInstance(classLoader, new Class[]{interfaceClass}, new ProxyHandlerPool(clientPool, interfaceClass));
    }

}
