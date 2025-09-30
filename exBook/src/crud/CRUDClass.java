package crud;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import connect.DBConnection;

public class CRUDClass {

	public void booksAfter2020() {
        String sql = "SELECT * FROM Book WHERE PubYear >= 2020";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("1. 2020년 이후 출판된 도서 목록");
            while (rs.next()) {
                System.out.printf("%d | %s | %s | %s | %d원 | %d%n",
                        rs.getInt("BookID"),
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getInt("Price"),
                        rs.getInt("PubYear"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void booksBorrowedByHong() {
        String sql = "SELECT b.Title, b.Author, b.Publisher, b.Price, b.PubYear " +
                     "FROM Rental r " +
                     "JOIN Member m ON r.MemberID = m.MemberID " +
                     "JOIN Book b ON r.BookID = b.BookID " +
                     "WHERE m.Name = '홍길동'";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("2. 홍길동 회원이 대출한 도서 목록");
            while (rs.next()) {
                System.out.printf("%s | %s | %s | %d원 | %d%n",
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getInt("Price"),
                        rs.getInt("PubYear"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unreturnedBooks() {
        String sql = "SELECT b.Title, m.Name, r.RentDate " +
                     "FROM Rental r " +
                     "JOIN Book b ON r.BookID = b.BookID " +
                     "JOIN Member m ON r.MemberID = m.MemberID " +
                     "WHERE r.ReturnDate IS NULL";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("3. 반납하지 않은 도서");
            while (rs.next()) {
                System.out.printf("%s | %s | %s%n",
                        rs.getString("Title"),
                        rs.getString("Name"),
                        rs.getDate("RentDate"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void rentalCountByBook() {
        String sql = "SELECT b.Title, COUNT(r.RentalID) AS RentalCount " +
                     "FROM Book b " +
                     "LEFT JOIN Rental r ON b.BookID = r.BookID " +
                     "GROUP BY b.BookID, b.Title";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("4. 도서별 대출 횟수");
            while (rs.next()) {
                System.out.printf("%s | %d회%n",
                        rs.getString("Title"),
                        rs.getInt("RentalCount"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void mostExpensiveBook() {
        String sql = "SELECT * FROM Book WHERE Price = (SELECT MAX(Price) FROM Book)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            System.out.println("5. 가격이 가장 비싼 도서");
            while (rs.next()) {
                System.out.printf("%s | %s | %s | %d원 | %d%n",
                        rs.getString("Title"),
                        rs.getString("Author"),
                        rs.getString("Publisher"),
                        rs.getInt("Price"),
                        rs.getInt("PubYear"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}