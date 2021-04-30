package shopping.mall.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

import shopping.common.model.SuperDao;
import shopping.member.model.Member;

public class MallDao extends SuperDao{

	public void Calculate(Member mem, Map<Integer, Integer> maplists, int totalPoint) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "";
		int cnt = -9999;
		int invoice = -9999;
		
		try {
			if(this.conn == null) {this.conn = this.getConnection();}
			this.conn.setAutoCommit(false);
			
			// step01 : orders table into insert data
			sql = " insert into orders(oid, mid, orderdate)";
			sql += " values(seqoid.nextval, ?, sysdate)";
			
			pstmt = this.conn.prepareStatement(sql);
			
			pstmt.setString(1, mem.getId());
			
			cnt = pstmt.executeUpdate();
			if(pstmt != null) {pstmt.close();}
			
			// step02 : get max invoice number from table
			sql = " select max(oid) as invoice from orders";
			
			pstmt = this.conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			if (rs.next()) {
				invoice = rs.getInt("invoice");
			}
			
			if(pstmt != null) {pstmt.close();}
			System.out.println("max invoice number : " + invoice);
			
			Set<Integer> keylist = maplists.keySet();
			System.out.println("shopping item size : " + keylist.size());
			
			for(Integer pnum : keylist) {
				// step 03 : orderdetails table into insert data
				sql = " insert into orderdetails";
				sql += " (odid, oid, pnum, qty)";
				sql += " values(seqodid.nextval, ?, ?, ?)";
				
				pstmt = this.conn.prepareStatement(sql);
				
				int qty = maplists.get(pnum);
				
				pstmt.setInt(1, invoice);
				pstmt.setInt(2, pnum);
				pstmt.setInt(3, qty);
				
				cnt = pstmt.executeUpdate();
				
				if(pstmt != null) {pstmt.close();}
				
				// step 04 : decreasing product stock
				sql = " update products set stock = stock - ?";
				sql += " where num = ?";
				
				pstmt = this.conn.prepareStatement(sql);
				
				pstmt.setInt(1, qty);
				pstmt.setInt(2, pnum);
				
				cnt = pstmt.executeUpdate();
				
				if(pstmt != null) {pstmt.close();}
			}
			
			// step05 : update member point
			sql = " update members set mpoint = mpoint + ?";
			sql += " where id = ?";
			
			pstmt = this.conn.prepareStatement(sql);
			
			pstmt.setInt(1, totalPoint);
			pstmt.setString(2, mem.getId());
			
			cnt = pstmt.executeUpdate();
			
			if(pstmt != null) {pstmt.close();}
			
			conn.commit();
			System.out.println("Calculate finished");
		} catch (Exception e) {
			e.printStackTrace();
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(pstmt!=null) {pstmt.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}
	
}
