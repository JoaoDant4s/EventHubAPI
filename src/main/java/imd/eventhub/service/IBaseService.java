package imd.eventhub.service;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T> {
    public T save(T object);
    public void delete(T object);
    public void update(T object);
    public Optional<T> getById(Integer id);
    public List<T> getList();
}
