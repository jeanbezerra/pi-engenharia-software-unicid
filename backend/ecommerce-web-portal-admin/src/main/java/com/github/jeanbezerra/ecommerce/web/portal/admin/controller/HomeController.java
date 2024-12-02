package com.github.jeanbezerra.ecommerce.web.portal.admin.controller;

import java.io.Serializable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class HomeController implements Serializable {

	private static final Logger LOGGER = LogManager.getLogger(HomeController.class);
	private static final long serialVersionUID = 2354574612441633043L;

	public HomeController() {
		// TODO Auto-generated constructor stub
	}

	@PostConstruct
	public void init() {

	}

}