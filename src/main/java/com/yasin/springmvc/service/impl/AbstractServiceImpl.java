package com.yasin.springmvc.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yasin.model.AbsClass;
import com.yasin.model.Roles;
import com.yasin.model.User;
import com.yasin.springmvc.service.AbstractService;
import com.yasin.springmvc.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractServiceImpl<T extends AbsClass> implements AbstractService<T>{


    private String url;
    private List<T> items;
    private String accessToken ;
    private NetworkUtil networkUtil = new NetworkUtil();
    public void setUrl(String url) {
        this.url = url;
    }
    public List<T> findAll(){
        StringBuffer response = new StringBuffer(networkUtil.networkService(url,"GET", ""));
        Gson gson = new Gson();
        System.out.println(response.toString());
        items = gson.fromJson(response.toString(), new TypeToken<ArrayList<User>>() {}.getType());
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
