/*
 * Copyright (C) 2018 Facebook, Inc.
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
package io.airlift.drift.transport.netty.client;

import io.airlift.units.Duration;

import java.util.concurrent.atomic.AtomicLong;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class ThriftRequestTiming
{
    private static final Duration ZERO_DURATION = new Duration(0, MILLISECONDS);

    private static final AtomicLong NEXT_ID = new AtomicLong();

    private final String name;
    private final long invocationId = NEXT_ID.incrementAndGet();

    private final long startTime = System.nanoTime();
    private final AtomicLong connectedTime = new AtomicLong();
    private final AtomicLong requestEncodedTime = new AtomicLong();
    private final AtomicLong requestSentTime = new AtomicLong();
    private final AtomicLong requestSentClock = new AtomicLong();
    private final AtomicLong responseReceivedTime = new AtomicLong();
    private final AtomicLong responseDecodedTime = new AtomicLong();
    private final AtomicLong endTime = new AtomicLong();

    public ThriftRequestTiming(String name)
    {
        this.name = name;
    }

    public void connected()
    {
        connectedTime.compareAndSet(0, System.nanoTime());
    }

    public void requestEncoded()
    {
        requestEncodedTime.compareAndSet(0, System.nanoTime());
    }

    public void requestSent()
    {
        requestSentTime.compareAndSet(0, System.nanoTime());
        requestSentClock.compareAndSet(0, System.currentTimeMillis());
    }

    public void responseReceived()
    {
        responseReceivedTime.compareAndSet(0, System.nanoTime());
    }

    public void responseDecoded()
    {
        responseDecodedTime.compareAndSet(0, System.nanoTime());
    }

    public boolean done()
    {
        return endTime.compareAndSet(0, System.nanoTime());
    }

    public String toTimingsLine(boolean success)
    {
        long now = System.nanoTime();
        Duration connect = computeDuration(startTime, connectedTime.get(), now);
        Duration encode = computeDuration(connectedTime.get(), requestEncodedTime.get(), now);
        Duration send = computeDuration(requestEncodedTime.get(), requestSentTime.get(), now);
        Duration receive = computeDuration(requestSentTime.get(), responseReceivedTime.get(), now);
        Duration decode = computeDuration(responseReceivedTime.get(), responseDecodedTime.get(), now);
        Duration finish = computeDuration(responseDecodedTime.get(), endTime.get(), now);
        Duration elapsed = computeDuration(startTime, endTime.get(), now);

        return String.format(
                "TIMELINE: Method %s :: %s :: %s :: elapsed %7s :: connect %7s :: encode %7s :: send %7s :: receive %7s :: decode %7s :: finish %7s :: sentTime %s",
                name,
                invocationId,
                success ? "success" : "failed",
                elapsed,
                connect,
                encode,
                send,
                receive,
                decode,
                finish,
                requestSentClock.get());
    }

    private static Duration computeDuration(long startTime, long endTime, long now)
    {
        if (startTime == 0) {
            startTime = now;
        }
        if (endTime == 0) {
            endTime = now;
        }
        if (endTime <= startTime) {
            return ZERO_DURATION;
        }
        return new Duration(endTime - startTime, NANOSECONDS).convertTo(MILLISECONDS);
    }
}
