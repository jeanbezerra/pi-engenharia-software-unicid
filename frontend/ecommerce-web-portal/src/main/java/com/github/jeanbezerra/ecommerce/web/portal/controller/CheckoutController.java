package com.github.jeanbezerra.ecommerce.web.portal.controller;

import java.io.Serializable;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;

@Named
@SessionScoped
public class CheckoutController implements Serializable {

	private static final long serialVersionUID = -8682527724475032437L;

	private String firstName;
	private String lastName;
	private String email;
	private String address;
	private String country;
	private String state;
	private String zip;
	private String paymentMethod;

	public CheckoutController() {
		// TODO Auto-generated constructor stub
	}

	public void completePurchase() {
		// Lógica de finalização da compra
		System.out.println("Compra finalizada com sucesso!");
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

}