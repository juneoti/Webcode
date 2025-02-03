package edu.web.member;

public interface MemberDAO {
	int insert(MemberVO vo);
	MemberVO login(String username, String password);
	MemberVO select(String username);
	int update(MemberVO vo);
	int delete(String username);
}
