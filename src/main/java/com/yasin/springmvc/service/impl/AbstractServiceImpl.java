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
    protected Type type;

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

        items = gson.fromJson(response.toString(), type);
        for (int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).toString());
        }
        return items;
    }
    public void deleteById(long id) {
        networkUtil.networkService(url + "/id=" + id ,"DELETE","");
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

    public T findById(long id)  {
        return new Gson().fromJson(networkUtil.networkService(url + "/id=" + id ,"GET",""), type);

    }
    public void deleteAll() {

    }
    public boolean isExist(T item) {
        return findByName(item.getName())!=null;
    }

}
