package shopping.mall.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import shopping.common.model.ShoppingInfo;
import shopping.common.model.SuperDao;
import shopping.member.model.Member;
import shopping.product.model.Product;
import shopping.product.model.ProductDao;

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

	public List<Order> OrderMall(String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " select * from orders";
		sql += " where mid = ? ";
		sql += " order by orderdate desc ";
		
		
		List<Order> lists = new ArrayList<Order>();
				
		try {
			if(this.conn == null) {this.conn = this.getConnection();}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Order bean = new Order();
				bean.setMid(rs.getString("mid"));
				bean.setOid(rs.getInt("oid"));
				bean.setOrderdate(String.valueOf(rs.getDate("orderdate")));
				bean.setRemark(rs.getString("remark"));
						
				lists.add(bean);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(pstmt!=null) {pstmt.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return lists;
	}

	public Order SelectDataByPk(int oid) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " select * from orders ";
		sql += " where oid = ? ";
		
		Order bean = null;
		
		try {
			if(this.conn == null) {this.conn = this.getConnection();}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, oid);
			
			rs = pstmt.executeQuery();
					
			if(rs.next()) {
				bean = new Order();
				
				bean.setMid(rs.getString("mid"));
				bean.setOid(rs.getInt("oid"));
				bean.setOrderdate(String.valueOf(rs.getDate("orderdate")));
				bean.setRemark(rs.getString("remark"));
						
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(pstmt!=null) {pstmt.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		
		
		
		
		return bean;
	}

	public void InsertCartData(Member mem, Map<Integer, Integer> maplist) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		int cnt = -9999;
		
		
		
		try {
			if(this.conn == null) {this.conn = this.getConnection();}
			conn.setAutoCommit(false);
			
			Set<Integer> keylist = maplist.keySet();
			System.out.println("InsertCartData keylist size : " + keylist.size());
			
			// 이전 내역을 삭제합니다.
			String sql = " delete from shoppinginfos ";
			sql += " where mid = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, mem.getId());
			
			cnt = pstmt.executeUpdate();
			if(pstmt != null) {pstmt.close();}
			
			
			// 반복문을 사용하여 현재 장바구니 정보를 저장하도록 합니다.
			sql = " insert into shoppinginfos(mid, pnum, pname, qty, price, image, point, inputdate)";
			sql += " values(?, ?, ?, ?, ?, ?, ?, default) ";
			
			for(Integer pnum : keylist) {
				pstmt = conn.prepareStatement(sql);
				
				ProductDao pdao = new ProductDao();
				Product bean = pdao.SelectDataByPk(pnum);
				
				int qty = maplist.get(pnum);
				
				pstmt.setString(1, mem.getId());
				pstmt.setInt(2, pnum);
				pstmt.setString(3, bean.getName());
				pstmt.setInt(4, qty);
				pstmt.setInt(5, bean.getPrice());
				pstmt.setString(6, bean.getImage());
				pstmt.setInt(7, bean.getPoint());
				
				cnt = pstmt.executeUpdate();
				
				if(pstmt != null) {pstmt.close();}
			}
			
			conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
			
			try {
				conn.rollback();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}finally {
			try {
				if(rs!=null) {rs.close();}
				if(pstmt!=null) {pstmt.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
	}

	public List<ShoppingInfo> GetShoppingInfo(String id) {
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = " select * from shoppinginfos ";
		sql += " where mid = ?";
		
		
		List<ShoppingInfo> lists = new ArrayList<ShoppingInfo>();
				
		try {
			if(this.conn == null) {this.conn = this.getConnection();}
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, id);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ShoppingInfo bean = new ShoppingInfo();
				bean.setImage(rs.getString("image"));
				bean.setPname(rs.getString("pname"));
				bean.setPnum(rs.getInt("pnum"));
				bean.setPoint(rs.getInt("point"));
				bean.setPrice(rs.getInt("price"));
				bean.setQty(rs.getInt("qty"));
				
						
				lists.add(bean);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(rs!=null) {rs.close();}
				if(pstmt!=null) {pstmt.close();}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		
		return lists;
	}
	
}

















