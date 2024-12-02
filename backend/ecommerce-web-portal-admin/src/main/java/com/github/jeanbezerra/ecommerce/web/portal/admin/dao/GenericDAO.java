package com.github.jeanbezerra.ecommerce.web.portal.admin.dao;

import java.util.List;

import jakarta.ejb.Stateless;

@Stateless
public interface GenericDAO<T> {

	public void persistObject(T t);

	public void updateObject(T t);

	public void removeObject(String id);

	public void removeObject(T t);

	public T readObject(String id);

	public List<T> listAll();

}