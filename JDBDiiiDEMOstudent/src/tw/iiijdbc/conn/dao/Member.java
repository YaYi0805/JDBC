package tw.iiijdbc.conn.dao;

import java.io.Serializable;

public class Member implements Serializable {
//實作 Serializable 介面 -> 序列化:物件存到資料庫或文件時使用
	
	private static final long serialVersionUID = 1L;
    
	//不可有public修飾子屬性變數
	private int memberId;
	private String memberName;
	private String memberAddress;
	private String phone;

	//一定要有不帶參數的建構子
	public Member() {
		
	}
	
	//提供public的getter&setter,讓其他物件存取
	public int getMemberId() {
		return memberId;
	}
	
	public void setMemberId(int id) {
		this.memberId = id;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberAddress() {
		return memberAddress;
	}

	public void setMemberAddress(String memberAddress) {
		this.memberAddress = memberAddress;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	

}
