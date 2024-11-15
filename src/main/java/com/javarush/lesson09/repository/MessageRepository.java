package com.javarush.lesson09.repository;

import com.javarush.lesson09.model.MessageOwnClass;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class MessageRepository implements Repository<MessageOwnClass> {

    private final Map<Long, MessageOwnClass> map = new HashMap<>();
    public static final AtomicLong id = new AtomicLong();

    @Override
    public MessageOwnClass findById(Long id) {
        return map.get(id);
    }

    @Override
    public MessageOwnClass saveOrUpdate(MessageOwnClass message) {
        if (!map.containsKey(message.getId())) {
            message.setId(id.incrementAndGet());
        }
        map.put(message.getId(), message);
        return message;
    }

    @Override
    public boolean delete(MessageOwnClass message) {
        return map.remove(message.getId()) != null;
    }

    @Override
    public Map<Long, MessageOwnClass> getAll() {
        return map;
    }
}
