package tw.iiijdbc.conn.dao;

import java.sql.SQLException;

public class MemberDAOAction {

	public static void main(String[] args) throws SQLException {
		
		MemberDAO mDAO = MemberDAOFactory.gatMemberDAO();
		mDAO.createConn();
		/*
		//加入兩個會員
		Member m1 = new Member();
		m1.setMemberId(1001);
		m1.setMemberName("Alber");
		m1.setMemberAddress("Taiwan");
		m1.setPhone("No");
		mDAO.addMember(m1);
		
		Member m2 = new Member();
		m2.setMemberId(1002);
		m2.setMemberName("John");
		m2.setMemberAddress("Paris");
		m2.setPhone("No");
		mDAO.addMember(m2);
		
		mDAO.closeConn();
		*/
		
		
		//根據id刪除
		//mDAO.deleteMemberById(1002);
		
		/*
		//修改
		Member m3 = new Member();
		m3.setMemberId(1001);
		m3.setMemberName("Alber");
		m3.setMemberAddress("Paris");
		m3.setPhone("1234567890");
		mDAO.updateByIdAndName(m3);
		*/
		
		
	    //用 id 找一筆資料
		Member m4= mDAO.findById(1001);
		System.out.println("MemberId : " + m4.getMemberId());
		System.out.println("MemberName :" + m4.getMemberName());
		System.out.println("Phone : " + m4.getPhone());
		System.out.println("Address : " + m4.getMemberAddress());
			
		mDAO.closeConn();

	}

}
