package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conn.DBConnection;

public class CRUDClass {

    // (0) 모든 데이터 삭제
    public void deleteAll() {
        String sql1 = "DELETE FROM sale";
        String sql2 = "DELETE FROM shopmember";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            int rows1 = pstmt1.executeUpdate();
            int rows2 = pstmt2.executeUpdate();
            System.out.println("*** Sale 테이블: " + rows1 + "행 삭제 ***");
            System.out.println("*** ShopMember 테이블: " + rows2 + "행 삭제 ***");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setAuto_Increment() {
        String sql1 = "ALTER TABLE ShopMember AUTO_INCREMENT = 1";
        String sql2 = "ALTER TABLE Sale AUTO_INCREMENT = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.executeUpdate();
            pstmt2.executeUpdate();
            System.out.println("*** ShopMember/Sale AUTO_INCREMENT 리셋 ***");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // (1) 회원 등록
    public void ShopMember(String CustName, String Phone, String Address, String JoinDate,
                           String Grade, String City) {
        String sql = "INSERT INTO shopmember (CustName, Phone, Address, JoinDate, Grade, City) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, CustName);
            pstmt.setString(2, Phone);
            pstmt.setString(3, Address);
            pstmt.setString(4, JoinDate);
            pstmt.setString(5, Grade);
            pstmt.setString(6, City);
            pstmt.executeUpdate();
            System.out.println("회원 등록 완료: " + CustName);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	public void selectMemberAll() {
		// TODO Auto-generated method stub
		    String sql = "SELECT CustNo, CustName, Phone, Address, JoinDate, Grade, City FROM shopmember";

		    try (Connection conn = DBConnection.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql);
		         ResultSet rs = pstmt.executeQuery()) {
		        System.out.println("*** 전체 회원 조회 ***");
		        while (rs.next()) {
		            System.out.printf(
		                "CustNo:%d | CustName:%s | Phone:%s | Address:%s | JoinDate:%s | Grade:%s | City:%s%n",
		                rs.getInt("CustNo"),
		                rs.getString("CustName"),
		                rs.getString("Phone"),
		                rs.getString("Address"),
		                rs.getString("JoinDate"),
		                rs.getString("Grade"),
		                rs.getString("City")
		            );
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}


    // (2) 전체 조회 (회원 + 판매 JOIN)
    public void selectAll() {
        String sql = "SELECT m.CustNo, m.CustName, m.Phone, m.Address, m.JoinDate, m.Grade, m.City, " +
                "s.SaleNo, s.PCost, s.Amount, s.Price, s.PCode " +
                "FROM shopmember m LEFT JOIN sale s ON m.CustNo = s.CustNo";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("*** 전체 조회 ***");
            while (rs.next()) {
                System.out.printf("CustNo:%d | CustName:%s | Phone:%s | Address:%s | JoinDate:%s | Grade:%s | City:%s | " +
                                "SaleNo:%s | PCost:%s | Amount:%s | Price:%s | PCode:%s%n",
                        rs.getInt("CustNo"),
                        rs.getString("CustName"),
                        rs.getString("Phone"),
                        rs.getString("Address"),
                        rs.getString("JoinDate"),
                        rs.getString("Grade"),
                        rs.getString("City"),
                        rs.getString("SaleNo"),
                        rs.getString("PCost"),
                        rs.getString("Amount"),
                        rs.getString("Price"),
                        rs.getString("PCode"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (3) 등급별 조회
    public void selectGrade(String grade) {
        String sql = "SELECT CustName, Phone, JoinDate FROM shopmember WHERE Grade = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, grade);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("*** " + grade + "등급 회원 조회 ***");
            while (rs.next()) {
                System.out.printf("CustName:%s | Phone:%s | JoinDate:%s%n",
                        rs.getString("CustName"),
                        rs.getString("Phone"),
                        rs.getString("JoinDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (4) 2020년 이후 가입 회원 조회
    public void selec2020Member(int year) {
        String sql = "SELECT CustName, Phone, JoinDate FROM shopmember WHERE YEAR(JoinDate) >= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, year);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("*** " + year + "년 이후 가입 회원 조회 ***");
            while (rs.next()) {
                System.out.printf("CustName:%s | Phone:%s | JoinDate:%s%n",
                        rs.getString("CustName"),
                        rs.getString("Phone"),
                        rs.getString("JoinDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (5) 판매 등록
    public void Sale(int CustNo, int PCost, int Amount, int Price, String PCode) {
        String sql = "INSERT INTO sale (CustNo, PCost, Amount, Price, PCode) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, CustNo);
            pstmt.setInt(2, PCost);
            pstmt.setInt(3, Amount);
            pstmt.setInt(4, Price);
            pstmt.setString(5, PCode);
            pstmt.executeUpdate();
            System.out.println("판매 등록 완료: CustNo=" + CustNo + ", PCode=" + PCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
	public void selectSaleAll() {
		// TODO Auto-generated method stub
		    String sql = "SELECT SaleNo, CustNo, PCost, Amount, Price, PCode FROM sale";

		    try (Connection conn = DBConnection.getConnection();
		         PreparedStatement pstmt = conn.prepareStatement(sql);
		         ResultSet rs = pstmt.executeQuery()) {
		        System.out.println("*** 전체 판매 조회 ***");
		        while (rs.next()) {
		            System.out.printf(
		                "SaleNo:%d | CustNo:%d | PCost:%d | Amount:%d | Price:%d | PCode:%s%n",
		                rs.getInt("SaleNo"),
		                rs.getInt("CustNo"),
		                rs.getInt("PCost"),
		                rs.getInt("Amount"),
		                rs.getInt("Price"),
		                rs.getString("PCode")
		            );
		        }
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		}

    // (6) 회원별 총 구매금액
    public void selectSales() {
        String sql = "SELECT m.CustName, SUM(s.Price) AS TotalPrice " +
                "FROM shopmember m LEFT JOIN sale s ON m.CustNo = s.CustNo " +
                "GROUP BY m.CustName";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("*** 회원별 총 구매금액 ***");
            while (rs.next()) {
                System.out.printf("CustName:%s | TotalPrice:%d%n",
                        rs.getString("CustName"),
                        rs.getInt("TotalPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (7) 구매금액이 높은 회원 조회
    public void selectPrice() {
        String sql = "SELECT m.CustName, SUM(s.Price) AS TotalPrice " +
                "FROM shopmember m LEFT JOIN sale s ON m.CustNo = s.CustNo " +
                "GROUP BY m.CustName ORDER BY TotalPrice DESC LIMIT 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("*** 구매금액이 높은 회원 조회 ***");
            if (rs.next()) {
                System.out.printf("CustName:%s | TotalPrice:%d%n",
                        rs.getString("CustName"),
                        rs.getInt("TotalPrice"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (8) 회원 등급 수정
    public void updateGrade(String custName, String grade) {
        String sql = "UPDATE shopmember SET Grade = ? WHERE CustName = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, grade);
            pstmt.setString(2, custName);
            int rows = pstmt.executeUpdate();
            System.out.println("등급 수정 완료: " + custName + " → " + grade + " (" + rows + "행 수정)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (9) 회원 삭제
    public void deleteMember(int custNo) {
        String sql1 = "DELETE FROM sale WHERE CustNo = ?";
        String sql2 = "DELETE FROM shopmember WHERE CustNo = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.setInt(1, custNo);
            int rows1 = pstmt1.executeUpdate();
            pstmt2.setInt(1, custNo);
            int rows2 = pstmt2.executeUpdate();
            System.out.println("회원 삭제 완료: CustNo=" + custNo + " (" + rows1 + "판매 삭제, " + rows2 + "회원 삭제)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
