package by.innowise.tasks.util.mapperForDto;

public interface MapperForDtoInterface<T, R> {

    R getMapper(T something);

    String getType();
}
