package com.typeahead.cache;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.SortedMap;
import java.util.TreeMap;

@Component
public class ConsistentHashRing {

    private final TreeMap<Long, String> ring =
            new TreeMap<>();

    private static final int VIRTUAL_NODES = 50;

    public ConsistentHashRing() {

        addNode("NodeA");
        addNode("NodeB");
        addNode("NodeC");
    }

    private void addNode(String nodeName) {

        for(int i = 0; i < VIRTUAL_NODES; i++) {

            ring.put(
                    hash(nodeName + "#" + i),
                    nodeName
            );
        }
    }

    public String getNode(String key) {

        long hash = hash(key);

        SortedMap<Long,String> tail =
                ring.tailMap(hash);

        if(tail.isEmpty()) {
            return ring.firstEntry().getValue();
        }

        return tail.get(tail.firstKey());
    }

    private long hash(String key) {

        try {

            MessageDigest md =
                    MessageDigest.getInstance("SHA-256");

            byte[] digest =
                    md.digest(
                            key.getBytes(
                                    StandardCharsets.UTF_8
                            )
                    );

            long value = 0;

            for(int i = 0; i < 8; i++) {

                value =
                        (value << 8)
                        | (digest[i] & 0xff);
            }

            return Math.abs(value);

        } catch (Exception ex) {

            throw new RuntimeException(ex);
        }
    }
}