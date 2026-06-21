package com.typeahead.service;

import com.typeahead.trie.QueryMetadata;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CacheService {

    private final RedisTemplate<String, Object>
            redisTemplate;

    private static final Duration TTL =
            Duration.ofMinutes(5);

    @SuppressWarnings("unchecked")
    public List<QueryMetadata> getSuggestions(
            String prefix
    ) {

        try {

            Object value =
                    redisTemplate
                            .opsForValue()
                            .get(
                                    "suggest:" + prefix
                            );

            if(value == null) {
                return List.of();
            }

            return (List<QueryMetadata>) value;

        }
        catch (Exception ex) {

            System.out.println(
                    "Redis unavailable"
            );

            return List.of();
        }
    }

    public void putSuggestions(
            String prefix,
            List<QueryMetadata> suggestions
    ) {

        try {

            redisTemplate
                    .opsForValue()
                    .set(
                            "suggest:" + prefix,
                            suggestions,
                            TTL
                    );

        }
        catch (Exception ex) {

            System.out.println(
                    "Redis unavailable"
            );
        }
    }

    public boolean exists(
            String prefix
    ) {

        try {

            return Boolean.TRUE.equals(
                    redisTemplate.hasKey(
                            "suggest:" + prefix
                    )
            );

        }
        catch (Exception ex) {

            System.out.println(
                    "Redis unavailable"
            );

            return false;
        }
    }

    public void evict(
            String prefix
    ) {

        try {

            redisTemplate.delete(
                    "suggest:" + prefix
            );

        }
        catch (Exception ex) {

            System.out.println(
                    "Redis unavailable"
            );
        }
    }

    public void clearAll() {

        try {

            redisTemplate
                    .getConnectionFactory()
                    .getConnection()
                    .serverCommands()
                    .flushDb();

        }
        catch (Exception ex) {

            System.out.println(
                    "Redis unavailable"
            );
        }
    }
}