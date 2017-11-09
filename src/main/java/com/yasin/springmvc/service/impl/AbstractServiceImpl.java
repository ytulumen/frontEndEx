package com.yasin.springmvc.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasin.model.AbsClass;
import com.yasin.model.Roles;
import com.yasin.model.User;
import com.yasin.model.UserRoles;
import com.yasin.springmvc.service.AbstractService;
import com.yasin.springmvc.util.NetworkUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractServiceImpl<T extends AbsClass> implements AbstractService<T>{

    @Autowired
    NetworkUtil networkUtil;
    private String url;
    private List<T> items;
    private Class clazz;
    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public void setClazz(Class clazz) {
        this.clazz = clazz;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public List<T> findAll(){
        StringBuffer response = new StringBuffer(networkUtil.networkService(url,"GET", ""));
        Gson gson = new Gson();
        System.out.println(response.toString());
        Type type;
        if(clazz == Roles.class)
            type = new TypeToken<ArrayList<Roles>>() {}.getType();
        else if(clazz == User.class)
            type = new TypeToken<ArrayList<User>>() {}.getType();
        else
            type = new TypeToken<ArrayList<UserRoles>>() {}.getType();
        items = gson.fromJson(response.toString(), type);
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).toString());
        }
        return items;
    }
    public boolean isUserExist(T item) {
        return findByName(item.getName())!=null;
    }
    public void deleteById(long id) {
        url += "/id="+id;
        networkUtil.networkService(url ,"DELETE","");
    }
    public void update(T item) {
        Gson gson = new Gson();
        String send = gson.toJson(item);
        networkUtil.networkService( url,"PUT", send);
    }
    public void save(T item) {
        Gson gson = new Gson();
        String send = gson.toJson(item);
        networkUtil.networkService(url,"POST", send);
    }
    public T findByName(String name) {
        findAll();
        for(T item : items){
            if(item.getName().equalsIgnoreCase(name)){
                return item;
            }
        }
        return null;
    }
    public T findById(long id)  {
        findAll();
        for (T item: items) {
            if(item.getId()==id)
                return item;
        }
        return null;
    }
    public void deleteAll() {

    }
    public boolean isExist(T item) {
        return false;
    }

}
