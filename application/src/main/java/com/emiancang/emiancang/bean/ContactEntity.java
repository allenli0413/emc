package com.emiancang.emiancang.bean;

import static com.emiancang.emiancang.update.UpdateManager.url;

/**
 * 联系人实体类
 *
 */
public class ContactEntity {

	private String id;
	private String url;
	private String name;
	private String phone;

	public ContactEntity(){}

	public ContactEntity(String id, String url, String name, String phone) {
		this.id = id;
		this.url = url;
		this.name = name;
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
