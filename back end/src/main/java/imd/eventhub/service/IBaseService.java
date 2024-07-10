package imd.eventhub.service;

import java.util.List;
import java.util.Optional;

public interface IBaseService<T> {
    public T save(T object);
    public void delete(Integer id);
    public Optional<T> update(T object, Integer id);
    public Optional<T> getById(Integer id);
    public List<T> getList();
}
