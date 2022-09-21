package com.mengtu.netty.protocol;

import java.util.concurrent.atomic.AtomicInteger;

public class SequenceIdGenerator {
    private static final AtomicInteger id = new AtomicInteger();

    public static int nextId() { return id.incrementAndGet();}
}
