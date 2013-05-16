package com.reversemind.glia.server;

import java.io.Serializable;

/**
 * Date: 5/13/13
 * Time: 4:42 PM
 *
 * @author Eugene Kalinin
 * @since 1.4
 */
public class GliaServerFactory implements Serializable {

    private GliaServerFactory(){
    }

    /**
     * Return a new builder that builds a GliaServer
     *
     * @return new Builder();
     */
    public static Builder builder(Builder.Type type){
        return new Builder(type);
    }

    /**
     *
     */
    public static class Builder{

        private String name = null;
        private String instanceName = null;
        private int port = -1;
        private boolean dropClientConnection = false;
        private IGliaPayloadProcessor gliaPayloadWorker = null;

        private boolean autoSelectPort = true;
        private boolean keepClientAlive = false;

        private Type type = Type.SIMPLE;

        // for Zookeeper specific
        private String zookeeperConnectionString;
        private String serviceBasePath;

        private Builder(Type type){
            this.type = type;
        }

        public IGliaServer build(){
            if(this.gliaPayloadWorker == null){
                throw new RuntimeException("Assign a payloadWorker to server!");
            }
            switch(this.type){
                case SIMPLE: return new GliaServerSimple(this);
                case ZOOKEEPER_ADVERTISER: return new GliaServerAdvertiser(this);
                default: return new GliaServerSimple(this);
            }
        }

        public Builder payloadWorker(IGliaPayloadProcessor gliaPayloadWorker){
            this.gliaPayloadWorker = gliaPayloadWorker;
            return this;
        }

        /**
         * Server name should be without any space - if you will use zookeeper
         *
         * @param name
         * @return
         */
        public Builder name(String name) {
            // TODO check is the name contains any space
            this.name = name;
            return this;
        }

        /**
         * Instance name should be without any space - if you will use zookeeper
         * <p></p>
         * but prefer to use empty - constructor will assign a UUID for instance name
         *
         * @param instanceName
         * @return
         */
        public Builder instanceName(String instanceName) {
            this.instanceName = instanceName;
            return this;
        }

        /**
         * Right now (v1.4) only SIMPLE and ZOOKEEPER_ADVERTISER(server will share selft info end metrics inside zookeeper)
         *
         * @param type
         * @return
         */
        public Builder type(Type type) {
            this.type = type;
            return this;
        }

        /**
         * Example: localhost:2181
         *
         * @param zookeeperConnectionString
         * @return
         */
        public Builder zookeeperConnectionString(String zookeeperConnectionString) {
            this.zookeeperConnectionString = zookeeperConnectionString;
            return this;
        }

        /**
         * Base path relatively from this server will publish info
         * <p></p>
         * Example: /baloo/services
         *
         * @param serviceBasePath
         * @return
         */
        public Builder serviceBasePath(String serviceBasePath) {
            this.serviceBasePath = serviceBasePath;
            return this;
        }

        /**
         * if autoSelectPort is true than assigned port number will be ignored
         *
         * @param port - port number from 0-65k
         * @return
         */
        public Builder port(int port) {
            this.port = port;
            return this;
        }

        /**
         * Force to server select and assign any available port in OS
         *
         * @param autoSelectPort
         * @return
         */
        public Builder autoSelectPort(boolean autoSelectPort) {
            this.autoSelectPort = autoSelectPort;
            return this;
        }

        /**
         * Close or not after response a client connection
         *
         * @param keepClientAlive
         * @return
         */
        public Builder keepClientAlive(boolean keepClientAlive) {
            this.keepClientAlive = keepClientAlive;
            return this;
        }




        public boolean isKeepClientAlive() {
            return keepClientAlive;
        }

        public String getName() {
            return name;
        }

        public String getInstanceName() {
            return instanceName;
        }

        public int port() {
            return port;
        }

        public boolean isDropClientConnection() {
            return dropClientConnection;
        }

        public IGliaPayloadProcessor getGliaPayloadWorker() {
            return gliaPayloadWorker;
        }

        /**
         * Server type
         *
         * @return
         */
        public Type getType() {
            return type;
        }

        public String getServiceBasePath() {
            return this.serviceBasePath;
        }

        /**
         * If autoSelectPort is true - port number will be ignored
         *
         * @return
         */
        public boolean isAutoSelectPort() {
            return autoSelectPort;
        }

        public String getZookeeperConnectionString() {
            return this.zookeeperConnectionString;
        }

        public enum Type{
            SIMPLE, ZOOKEEPER_ADVERTISER
        }
    }


}