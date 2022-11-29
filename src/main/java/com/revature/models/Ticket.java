package com.revature.models;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Objects;

public class Ticket {
	private int id;
	private double amount;
	private Timestamp submitted;
	private Timestamp resolved;
	private String description;
	private byte[] receipt; // byte[] receipt = {0, 1, 2, 3, 124, 125, 126, 127};
	private int author;
	private int resolver;
	private int status;
	private int type;

	public Ticket() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ticket(int id, double amount, Timestamp submitted, Timestamp resolved, String description, byte[] receipt,
			int author, int resolver, int status, int type) {
		super();
		this.id = id;
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public Ticket(double amount, Timestamp submitted, Timestamp resolved, String description, byte[] receipt,
			int author, int resolver, int status, int type) {
		super();
		this.amount = amount;
		this.submitted = submitted;
		this.resolved = resolved;
		this.description = description;
		this.receipt = receipt;
		this.author = author;
		this.resolver = resolver;
		this.status = status;
		this.type = type;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Timestamp getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Timestamp submitted) {
		this.submitted = submitted;
	}

	public Timestamp getResolved() {
		return resolved;
	}

	public void setResolved(Timestamp resolved) {
		this.resolved = resolved;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public byte[] getReceipt() {
		return receipt;
	}

	public void setReceipt(byte[] receipt) {
		this.receipt = receipt;
	}

	public int getAuthor() {
		return author;
	}

	public void setAuthor(int author) {
		this.author = author;
	}

	public int getResolver() {
		return resolver;
	}

	public void setResolver(int resolver) {
		this.resolver = resolver;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Arrays.hashCode(receipt);
		result = prime * result
				+ Objects.hash(amount, author, description, id, resolved, resolver, status, submitted, type);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Ticket other = (Ticket) obj;
		return Double.doubleToLongBits(amount) == Double.doubleToLongBits(other.amount) && author == other.author
				&& Objects.equals(description, other.description) && id == other.id
				&& Arrays.equals(receipt, other.receipt) && Objects.equals(resolved, other.resolved)
				&& resolver == other.resolver && status == other.status && Objects.equals(submitted, other.submitted)
				&& type == other.type;
	}

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", amount=" + amount + ", submitted=" + submitted + ", resolved=" + resolved
				+ ", description=" + description + ", receipt=" + Arrays.toString(receipt) + ", author=" + author
				+ ", resolver=" + resolver + ", status=" + status + ", type=" + type + "]";
	}

}