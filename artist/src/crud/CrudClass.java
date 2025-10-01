package crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import conn.DBConnection;

public class CrudClass {

    // (0) 모든 데이터 삭제
    public void deleteAll() {
        String sql1 = "DELETE FROM album";
        String sql2 = "DELETE FROM artist";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            int rows1 = pstmt1.executeUpdate();
            int rows2 = pstmt2.executeUpdate();
            System.out.println("*** album 테이블: " + rows1 + "행 삭제 ***");
            System.out.println("*** artist 테이블: " + rows2 + "행 삭제 ***");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // (0)-2 AUTO_INCREMENT 초기화
    public void setAuto_Increment() {
        String sql1 = "ALTER TABLE album AUTO_INCREMENT = 1";
        String sql2 = "ALTER TABLE artist AUTO_INCREMENT = 1";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt1 = conn.prepareStatement(sql1);
             PreparedStatement pstmt2 = conn.prepareStatement(sql2)) {
            pstmt1.executeUpdate();
            pstmt2.executeUpdate();
            System.out.println("*** album/artist AUTO_INCREMENT 리셋 ***");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // (1) 아티스트 등록
    public void insertArtist(String name, String debut, String genre, String agency) {
        String sql = "INSERT INTO artist (ArtistName, DebutDate, Genre, Agency) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, debut);
            pstmt.setString(3, genre);
            pstmt.setString(4, agency);
            pstmt.executeUpdate();
            System.out.println("아티스트 등록 완료: " + name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public void selectArtistAll() {
		String sql = "SELECT ArtistNo, ArtistName, DebutDate, Genre, Agency FROM artist ORDER BY ArtistNo";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        System.out.println("*** 아티스트 전체 조회 ***");
	        while (rs.next()) {
	            System.out.printf(
	                "ArtistNo:%d | Name:%s | Debut:%s | Genre:%s | Agency:%s%n",
	                rs.getInt("ArtistNo"),
	                rs.getString("ArtistName"),
	                rs.getDate("DebutDate"),
	                rs.getString("Genre"),
	                rs.getString("Agency")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    // (1)-2 앨범 등록
    public void insertAlbum(int artistNo, String title, String release, int sales) {
        String sql = "INSERT INTO album (ArtistNo, AlbumTitle, ReleaseDate, Sales) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, artistNo);
            pstmt.setString(2, title);
            pstmt.setString(3, release);
            pstmt.setInt(4, sales);
            pstmt.executeUpdate();
            System.out.println("앨범 등록 완료: " + title);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
	public void selectAlbumAll() {
		String sql = "SELECT AlbumNo, ArtistNo, AlbumTitle, ReleaseDate, Sales FROM album ORDER BY AlbumNo";

	    try (Connection conn = DBConnection.getConnection();
	         PreparedStatement pstmt = conn.prepareStatement(sql);
	         ResultSet rs = pstmt.executeQuery()) {
	        System.out.println("*** 앨범 전체 조회 ***");
	        while (rs.next()) {
	            System.out.printf(
	                "AlbumNo:%d | ArtistNo:%d | Title:%s | Release:%s | Sales:%d%n",
	                rs.getInt("AlbumNo"),
	                rs.getInt("ArtistNo"),
	                rs.getString("AlbumTitle"),
	                rs.getDate("ReleaseDate"),
	                rs.getInt("Sales")
	            );
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

    // (전체 조회 - Artist + Album)
    public void selectAll() {
        String sql = "SELECT a.ArtistNo, a.ArtistName, a.DebutDate, a.Genre, a.Agency, "
                   + "al.AlbumNo, al.AlbumTitle, al.ReleaseDate, al.Sales "
                   + "FROM artist a LEFT JOIN album al ON a.ArtistNo = al.ArtistNo "
                   + "ORDER BY a.ArtistNo, al.AlbumNo";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("*** 전체 조회 ***");
            while (rs.next()) {
                System.out.printf("ArtistNo:%d | Name:%s | Debut:%s | Genre:%s | Agency:%s | "
                                + "AlbumNo:%d | Title:%s | Release:%s | Sales:%d%n",
                        rs.getInt("ArtistNo"),
                        rs.getString("ArtistName"),
                        rs.getDate("DebutDate"),
                        rs.getString("Genre"),
                        rs.getString("Agency"),
                        rs.getInt("AlbumNo"),
                        rs.getString("AlbumTitle"),
                        rs.getDate("ReleaseDate"),
                        rs.getInt("Sales"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (2) 장르별 조회
    public void selectGenre(String genre) {
        String sql = "SELECT ArtistName, Agency FROM artist WHERE Genre = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, genre);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("*** 장르가 " + genre + " 인 아티스트 ***");
            while (rs.next()) {
                System.out.printf("Name:%s | Agency:%s%n",
                        rs.getString("ArtistName"),
                        rs.getString("Agency"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (2)-2 판매량 조회
    public void selectSales(int minSales) {
        String sql = "SELECT AlbumTitle, Sales FROM album WHERE Sales >= ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, minSales);
            ResultSet rs = pstmt.executeQuery();
            System.out.println("*** 판매량 " + minSales + " 이상 앨범 ***");
            while (rs.next()) {
                System.out.printf("Title:%s | Sales:%d%n",
                        rs.getString("AlbumTitle"),
                        rs.getInt("Sales"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (2)-3 아티스트별 총 판매량
    public void selectSales() {
        String sql = "SELECT a.ArtistName, COALESCE(SUM(al.Sales),0) AS 총판매량 "
                   + "FROM artist a LEFT JOIN album al ON a.ArtistNo = al.ArtistNo "
                   + "GROUP BY a.ArtistNo, a.ArtistName";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            System.out.println("*** 아티스트별 총 판매량 ***");
            while (rs.next()) {
                System.out.printf("Artist:%s | TotalSales:%d%n",
                        rs.getString("ArtistName"),
                        rs.getInt("총판매량"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (3) 소속사 수정
    public void updatAgency(String name, String newAgency) {
        String sql = "UPDATE artist SET Agency = ? WHERE ArtistName = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, newAgency);
            pstmt.setString(2, name);
            int rows = pstmt.executeUpdate();
            System.out.println("소속사 수정 완료: " + name + " → " + newAgency + " (" + rows + "행 수정)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // (4) 앨범 삭제
    public void deleteAlbumNo(int albumNo) {
        String sql = "DELETE FROM album WHERE AlbumNo = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, albumNo);
            int rows = pstmt.executeUpdate();
            System.out.println("앨범 삭제 완료: AlbumNo=" + albumNo + " (" + rows + "행 삭제)");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
