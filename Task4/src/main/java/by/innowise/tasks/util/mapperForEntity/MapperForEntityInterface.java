package by.innowise.tasks.util.mapperForEntity;

public interface MapperForEntityInterface<T, R>{

    R getMapper(T something);

    String getType();
}
