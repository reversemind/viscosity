/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.test.netty.object.kryoobject;

import org.jboss.netty.channel.*;

import java.util.concurrent.atomic.AtomicLong;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Handles both client-side and server-side handler depending on which
 * constructor was called.
 */
public class ObjectKryoServerHandler extends SimpleChannelUpstreamHandler {

    private static final Logger logger = Logger.getLogger(
            ObjectKryoServerHandler.class.getName());

    private final AtomicLong transferredMessages = new AtomicLong();

    public long getTransferredMessages() {
        return transferredMessages.get();
    }

    @Override
    public void handleUpstream(
            ChannelHandlerContext ctx, ChannelEvent e) throws Exception {
        if (e instanceof ChannelStateEvent &&
            ((ChannelStateEvent) e).getState() != ChannelState.INTEREST_OPS) {
            logger.info(e.toString());
        }
        super.handleUpstream(ctx, e);
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {
        // Echo back the received object to the client.
        transferredMessages.incrementAndGet();
        ChannelFuture channelFuture = e.getChannel().write(e.getMessage());

        // see here - http://netty.io/3.6/guide/
        // Close connection right after sending
        channelFuture.addListener(new ChannelFutureListener() {
            public void operationComplete(ChannelFuture future) {
                Channel ch = future.getChannel();
                ch.close();
                }
        });

    }

    @Override
    public void exceptionCaught(
            ChannelHandlerContext ctx, ExceptionEvent e) {
        logger.log(
                Level.WARNING,
                "Unexpected exception from downstream.",
                e.getCause());
        e.getChannel().close();
    }
}
