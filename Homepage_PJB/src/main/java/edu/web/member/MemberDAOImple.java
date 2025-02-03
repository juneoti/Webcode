package edu.web.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.driver.OracleDriver;

public class MemberDAOImple implements MemberDAO, DBConnection{
	private static MemberDAOImple instance = null;
	
	private MemberDAOImple() {}
	
	public static MemberDAOImple getInstance() {
		if(instance == null) {
			instance = new MemberDAOImple();
		}
		return instance;
	}
	@Override
	public int insert(MemberVO vo) {
		int result = 0;
		Connection conn = null;
		PreparedStatement pstmt = null;
		
		try {
			DriverManager.registerDriver(new OracleDriver());
			conn = DriverManager.getConnection(URL, USER, PASSWORD);
			System.out.println("DB 연결 성공");
			pstmt = conn.prepareStatement(SQL_INSERT);
			pstmt.setString(1, vo.getUserid());
			pstmt.setString(2, vo.getPassword());
			pstmt.setString(3, vo.getEmail());
			pstmt.setString(4, vo.getEmailAgree());
			pstmt.setString(5, vo.getInterestJoin());
			pstmt.setString(6, vo.getPhone());
			pstmt.setString(7, vo.getIntroduce());
			
			result = pstmt.executeUpdate();
			System.out.println(result + "행 등록");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return result;
	}
	
	@Override
    public MemberVO login(String username, String password) {
        MemberVO member = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            DriverManager.registerDriver(new OracleDriver());
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("DB 연결 성공");
            pstmt = conn.prepareStatement(SQL_LOGIN);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                member = new MemberVO(
                    rs.getString(COL_USERID),
                    rs.getString(COL_PASSWORD),
                    rs.getString(COL_EMAIL),
                    rs.getString(COL_EMAIL_AGREE),
                    rs.getString(COL_INTEREST).split(","),
                    rs.getString("PHONE"),
                    rs.getString(COL_INTRODUCE)
                );
                System.out.println("[MemberDAOImple] 로그인 성공: " + member);
            } else {
                System.out.println("[MemberDAOImple] 로그인 실패: 사용자 정보를 찾을 수 없습니다.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        
        return member;
    }
	
	@Override
    public MemberVO select(String username) {
	       MemberVO member = null;
	        Connection conn = null;
	        PreparedStatement pstmt = null;
	        ResultSet rs = null;
	        
	        try {
	            DriverManager.registerDriver(new OracleDriver());
	            conn = DriverManager.getConnection(URL, USER, PASSWORD);
	            System.out.println("DB 연결 성공");
	            pstmt = conn.prepareStatement(SQL_SELECT);
	            pstmt.setString(1, username);
	            rs = pstmt.executeQuery();
	            
	            if (rs.next()) {
	                member = new MemberVO(
	                    rs.getString(COL_USERID),
	                    rs.getString(COL_PASSWORD),
	                    rs.getString(COL_EMAIL),
	                    rs.getString(COL_EMAIL_AGREE),
	                    rs.getString(COL_INTEREST).split(","),
	                    rs.getString("PHONE"),
	                    rs.getString(COL_INTRODUCE)
	                );
	                System.out.println("[MemberDAOImple] " + member.getUserid() + "님의 정보" + member);
	            } else {
	                System.out.println("[MemberDAOImple] 사용자 정보를 찾을 수 없습니다.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (rs != null) rs.close();
	                if (pstmt != null) pstmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }
	        
	        return member;
	}
	
	   @Override
	    public int update(MemberVO vo) {
	        int result = 0;
	        Connection conn = null;
	        PreparedStatement pstmt = null;

	        try {
	            DriverManager.registerDriver(new OracleDriver());
	            conn = DriverManager.getConnection(URL, USER, PASSWORD);
	            System.out.println("DB 연결 성공");
	            pstmt = conn.prepareStatement(SQL_UPDATE);
	            pstmt.setString(1, vo.getPassword());
	            pstmt.setString(2, vo.getEmail());
	            pstmt.setString(3, vo.getEmailAgree());
	            pstmt.setString(4, vo.getInterestJoin());
	            pstmt.setString(5, vo.getPhone());
	            pstmt.setString(6, vo.getIntroduce());
	            pstmt.setString(7, vo.getUserid());

	            result = pstmt.executeUpdate();
	            System.out.println(result + "행 업데이트");
	        } catch (SQLException e) {
	            e.printStackTrace();
	        } finally {
	            try {
	                if (pstmt != null) pstmt.close();
	                if (conn != null) conn.close();
	            } catch (SQLException e) {
	                e.printStackTrace();
	            }
	        }

	        return result;
	    }
	   
	   @Override
	   public int delete(String username) {
	       int result = 0;
	       Connection conn = null;
	       PreparedStatement pstmt = null;

	       try {
	           DriverManager.registerDriver(new OracleDriver());
	           conn = DriverManager.getConnection(URL, USER, PASSWORD);
	           System.out.println("DB 연결 성공");

	           pstmt = conn.prepareStatement(SQL_DELETE);
	           pstmt.setString(1, username);

	           result = pstmt.executeUpdate();
	           System.out.println(result + "행 삭제");
	       } catch (SQLException e) {
	           e.printStackTrace();
	       } finally {
	           try {
	               if (pstmt != null) pstmt.close();
	               if (conn != null) conn.close();
	           } catch (SQLException e) {
	               e.printStackTrace();
	           }
	       }

	       return result;
	   }
}
/*
package edu.web.member;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import oracle.jdbc.OracleDriver;

public class MemberDAOImple implements MemberDAO, DBConnection {
   private static MemberDAOImple instance = null;
   
   private MemberDAOImple() {}
   
   public static MemberDAOImple getInstance() {
      if(instance == null) {
         instance = new MemberDAOImple();
      }
      return instance;
   }
   
   // conn, pstmt 리소스 해제 함수
   private void closeResource(Connection conn, PreparedStatement pstmt) {
      try {
         pstmt.close();
         conn.close();
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
   }
   
   private void closeResource(Connection conn, PreparedStatement pstmt, ResultSet rs) {
      try {
         rs.close();
         pstmt.close();
         conn.close();
      } catch (SQLException e) {
         e.printStackTrace();
      }
   }
   
   @Override
   public int insert(MemberVO vo) {
      int result = 0;
      Connection conn = null;
      PreparedStatement pstmt = null;
      
      try {
         DriverManager.registerDriver(new OracleDriver());
         conn = DriverManager.getConnection(URL, USER, PASSWORD);
         System.out.println("DB 연결 성공");
         pstmt = conn.prepareStatement(SQL_INSERT);
         pstmt.setString(1, vo.getUserid());
         pstmt.setString(2, vo.getPassword());
         pstmt.setString(3, vo.getEmail());
         pstmt.setString(4, vo.getEmailAgree());
         pstmt.setString(5, vo.getInterestJoin());
         pstmt.setString(6, vo.getPhone());
         pstmt.setString(7, vo.getIntroduce());
         
         result = pstmt.executeUpdate();
         System.out.println(result + "행 등록");
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResource(conn, pstmt);
      }
      
      return result;
   }

   @Override
   public int select(String userid, String password) {
      int result = 0;
      
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      try {
         DriverManager.registerDriver(new OracleDriver());
         conn = DriverManager.getConnection(URL, USER, PASSWORD);
         pstmt = conn.prepareStatement(SQL_SELECT_BY_USERID_PASSWORD);
         pstmt.setString(1, userid);
         pstmt.setString(2, password);
         
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            result = rs.getInt(1);
         }
      } catch (SQLException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } finally {
         closeResource(conn, pstmt, rs);
      }
      
      return result;
   }

   @Override
   public MemberVO select(String userid) {
      MemberVO vo = null;
      Connection conn = null;
      PreparedStatement pstmt = null;
      ResultSet rs = null;
      
      try {
         DriverManager.registerDriver(new OracleDriver());
         conn = DriverManager.getConnection(URL, USER, PASSWORD);
         System.out.println("오라클 연결 성공");
         
         pstmt = conn.prepareStatement(SQL_SELECT_BY_USERID);
         pstmt.setString(1, userid);
         rs = pstmt.executeQuery();
         
         if(rs.next()) {
            userid = rs.getString(COL_USERID);
            String password = rs.getString(COL_PASSWORD);
            String email = rs.getString(COL_EMAIL);
            String emailAgree = rs.getString(COL_EMAIL_AGREE);
            String[] interest = rs.getString(COL_INTEREST).split(",");
            String phone = rs.getString(COL_PHONE);
            String introduce = rs.getString(COL_INTRODUCE);
            vo = new MemberVO(userid, password, email, emailAgree, interest, phone, introduce);
         }
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResource(conn, pstmt, rs);
      }
      
      return vo;
   }

   @Override
   public int update(MemberVO vo) {
      int result = 0;
      Connection conn = null;
      PreparedStatement pstmt = null;
      
      try {
         DriverManager.registerDriver(new OracleDriver());
         conn = DriverManager.getConnection(URL, USER, PASSWORD);
         pstmt = conn.prepareStatement(SQL_UPDATE);
         
         pstmt.setString(1, vo.getPassword());
         pstmt.setString(2, vo.getEmail());
         pstmt.setString(3, vo.getEmailAgree());
         pstmt.setString(4, vo.getInterestJoin());
         pstmt.setString(5, vo.getPhone());
         pstmt.setString(6, vo.getIntroduce());
         pstmt.setString(7, vo.getUserid());
         
         result = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResource(conn, pstmt);
      }
      
      return result;
   }

   @Override
   public int delete(String userid) {
      int result = 0;
      Connection conn = null;
      PreparedStatement pstmt = null;
      
      try {
         DriverManager.registerDriver(new OracleDriver());
         conn = DriverManager.getConnection(URL, USER, PASSWORD);
         pstmt = conn.prepareStatement(SQL_DELETE);
         pstmt.setString(1, userid);
         
         result = pstmt.executeUpdate();
      } catch (SQLException e) {
         e.printStackTrace();
      } finally {
         closeResource(conn, pstmt);
      }
      
      return result;
   }
   
}
 */