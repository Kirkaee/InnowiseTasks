package by.innowise.tasks.util.service;

public interface ServiceInterface<T, R> {

    R getService(T something);

    String getType();
}
