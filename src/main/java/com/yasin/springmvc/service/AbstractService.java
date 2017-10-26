package com.yasin.springmvc.service;

import com.yasin.model.User;
import org.springframework.stereotype.Service;

import java.util.List;
public interface AbstractService<T> {
    public void setUrl(String url);
    public List<T> findAll();
    public boolean isUserExist(T item);
    public void deleteById(long id);
    public void update(T item);
    public void save(T item);
    public T findByName(String name);
    public T findById(long id);
    public void deleteAll();
    public boolean isExist(T item);
}
