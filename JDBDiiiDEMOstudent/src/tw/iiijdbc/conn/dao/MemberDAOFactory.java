package tw.iiijdbc.conn.dao;

public class MemberDAOFactory {
	
	private static final MemberDAO memberDAO = createMemberDAO();
	
	private static MemberDAO createMemberDAO() { //static故不用每次都實體化一次
		MemberDAO mDAO = new MemberDAO();
		return mDAO;
	}	
	public static MemberDAO gatMemberDAO() {
		return memberDAO;
	}
	
}
