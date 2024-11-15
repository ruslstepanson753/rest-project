package com.javarush.lesson09.service;

import com.javarush.lesson09.model.MessageOwnClass;
import com.javarush.lesson09.repository.Repository;

import java.util.Map;

public class MessageService implements Rest<MessageOwnClass> {

    private final Repository<MessageOwnClass> repository;

    public MessageService(Repository<MessageOwnClass> repository) {
        this.repository = repository;
        this.repository.saveOrUpdate(new MessageOwnClass("Hello everybody!!!", "Ivan"));
        this.repository.saveOrUpdate(new MessageOwnClass("Hi!", "Joe"));
        this.repository.saveOrUpdate(new MessageOwnClass("How are you guys?", "Maria"));
        this.repository.saveOrUpdate(new MessageOwnClass("Thank you, we're all right", "Админ"));
    }


    @Override
    public Map<Long, MessageOwnClass> getAll() {
        return repository.getAll();
    }

    @Override
    public MessageOwnClass get(Long id) {
        return repository.findById(id);
    }

    @Override
    public boolean post(MessageOwnClass message) {
        message.setId(0L);
        MessageOwnClass update = repository.saveOrUpdate(message);
        return update != null;
    }

    @Override
    public boolean put(MessageOwnClass message) {
        MessageOwnClass update = repository.saveOrUpdate(message);
        return update != null;
    }

    @Override
    public boolean delete(MessageOwnClass message) {
        return repository.delete(message);
    }
}
