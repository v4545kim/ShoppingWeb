package shopping.common.model ;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import shopping.comment.model.Comment;

public class CompositeDao extends SuperDao{

	public List<ShoppingInfo> ShowDetail(int oid) {
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = "select p.num pnum, p.name pname, od.qty, p.price, p.point, p.image " ; 
		sql += " from ( orders o inner join orderdetails od  ";
		sql += " on o.oid=od.oid ) inner join products p ";
		sql += " on od.pnum = p.num and o.oid = ? "; 
		sql += " order by od.odid desc "; 
		List<ShoppingInfo> lists = new ArrayList<ShoppingInfo>();
		try {
			if( conn == null ){ super.conn = super.getConnection() ; }
			pstmt = super.conn.prepareStatement(sql) ;			
			pstmt.setInt(1, oid); 
			rs = pstmt.executeQuery() ;
			
			while( rs.next() ){
				ShoppingInfo bean = new ShoppingInfo();
				bean.setImage( rs.getString("image") );
				bean.setPname( rs.getString("pname") );
				bean.setPnum( Integer.parseInt( rs.getString("pnum") ));
				bean.setPoint( Integer.parseInt( rs.getString("point") ));
				bean.setPrice( Integer.parseInt( rs.getString("price") ));
				bean.setQty( Integer.parseInt( rs.getString("qty") ));				 
				 
				lists.add( bean ) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if( rs != null ){ rs.close(); }
				if( pstmt != null ){ pstmt.close(); }
				super.closeConnection(); 
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}		
		return lists ;
	}
	
	public List<Postcode> SelectDataZipcode(String dong) {		
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;

		String sql = " select * from  postcodes " ;
		sql += " where emd_nm like '%" + dong + "%' or "  ;
		sql += " rd_nm like '%" + dong + "%' or "  ;
		sql += " search_word like '%" + dong + "%' "  ;
		sql += " order by si_nm, sgg_nm, rd_nm " ;
		
		System.out.println("dao---dong name : " + dong);
		
		List<Postcode> lists = new ArrayList<Postcode>();
		try {
			conn = getConnection() ;
			pstmt = conn.prepareStatement(sql) ;			
			 
			rs = pstmt.executeQuery() ;			
			while( rs.next() ){
				Postcode bean = new Postcode();
				//��ü�� �����Ѵ�.
				bean.setArea_cd( rs.getString( "area_cd" )) ; 
				bean.setBd_ma_sn( rs.getString( "bd_ma_sn" ));
				bean.setBd_sb_sn( rs.getString( "bd_sb_sn" ));
				bean.setDisplay_word( rs.getString( "display_word" ));
				bean.setDisplay_word_dtail( rs.getString( "display_word_dtail" ));
				bean.setEmd_nm( rs.getString( "emd_nm" ));
				bean.setLndn_ma_sn( rs.getString( "lndn_ma_sn" ));
				bean.setLndn_sb_sn( rs.getString( "lndn_sb_sn" ));
				bean.setMt_yn( rs.getString( "mt_yn" ));
				bean.setRd_nm( rs.getString( "rd_nm" ));
				bean.setRi_nm( rs.getString( "ri_nm" ));
				bean.setSearch_word( rs.getString( "search_word" ));
				bean.setSgg_nm( rs.getString( "sgg_nm" ));
				bean.setSi_nm( rs.getString( "si_nm" ));
				bean.setUdrgrnd_yn( rs.getString( "udrgrnd_yn" ));

				//System.out.println( bean.toString() );
				lists.add( bean ) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if( rs != null ){ rs.close(); }
				if( pstmt != null ){ pstmt.close(); }
				if( conn != null){ conn.close(); } 
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}
		
		return lists ;
	}

	public int DeleteCommentData(int cnum) {
		Connection conn = null ;
		String sql = " delete from comments where cnum = ? " ;
		PreparedStatement pstmt = null ;
		int cnt = -99999 ;
		try {
			conn = super.getConnection() ; 
			conn.setAutoCommit( false );
			pstmt = conn.prepareStatement(sql) ;
			pstmt.setInt(1, cnum);
			
			cnt = pstmt.executeUpdate() ; 
			conn.commit(); 
		} catch (Exception e) {
			SQLException err = (SQLException)e ;			
			cnt = - err.getErrorCode() ;			
			e.printStackTrace();
			try {
				conn.rollback(); 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if( pstmt != null ){ pstmt.close(); }
				if( conn != null ){ conn.close(); }
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt ;
	}

	public int InsertCommentData(Comment bean) {
		Connection conn = null ;
		System.out.println( bean.toString() ); 

		String sql = " insert into comments(cnum, no, writer, content, regdate) " ;
		sql += " values(seqcomm.nextval, ?, ?, ?, sysdate ) " ;

		PreparedStatement pstmt = null ;
		int cnt = -99999 ;
		try {
			conn = this.getConnection() ; 
			conn.setAutoCommit( false );
			pstmt = conn.prepareStatement(sql) ;
			
			pstmt.setInt(1, bean.getNo() );
			pstmt.setString(2, bean.getWriter() );
			pstmt.setString(3, bean.getContent() );
			
			cnt = pstmt.executeUpdate() ; 
			conn.commit(); 
		} catch (Exception e) {
			SQLException err = (SQLException)e ;			
			cnt = - err.getErrorCode() ;			
			e.printStackTrace();
			try {
				conn.rollback(); 
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		} finally{
			try {
				if( pstmt != null ){ pstmt.close(); }
				if( conn != null ){ conn.close(); }
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return cnt ;
	}

	public List<Comment> SelectCommentListByPk(int no) {
		Connection conn = null ;
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;

		String sql = " select * from comments " ;
		sql += " where no = ? order by cnum " ;
		
		List<Comment> lists = new ArrayList<Comment>();
		try {
			conn = getConnection() ;
			pstmt = conn.prepareStatement(sql) ;			
			pstmt.setInt(1, no);
		
			rs = pstmt.executeQuery() ;			
			while( rs.next() ){
				Comment bean = new Comment();
				//��ü�� �����Ѵ�.
				bean.setCnum( Integer.parseInt( rs.getString("cnum") ));
				bean.setContent( rs.getString("content") );
				bean.setNo( Integer.parseInt( rs.getString("no") ));
				bean.setRegdate( String.valueOf( rs.getDate("regdate") ));
				bean.setWriter( rs.getString("writer") );
		  
				lists.add( bean ) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if( rs != null ){ rs.close(); }
				if( pstmt != null ){ pstmt.close(); }
				if( conn != null){ conn.close(); } 
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}
		
		return lists ;
	}

	public List<Combo01> View01() {
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = " select m.name, b.subject, b.content, b.regdate " ; 
		sql += " from members m inner join boards b ";
		sql += " on m.id = b.writer ";
		
		
		List<Combo01> lists = new ArrayList<Combo01>();
		try {
			if( conn == null ){ super.conn = super.getConnection() ; }
			pstmt = super.conn.prepareStatement(sql) ;			
			rs = pstmt.executeQuery() ;
			
			while( rs.next() ){
				Combo01 bean = new Combo01();
				bean.setContent(rs.getString("content"));
				bean.setName(rs.getString("name"));
				bean.setRegdate(String.valueOf(rs.getDate("regdate")));
				bean.setSubject(rs.getString("subject"));
				 
				 
				lists.add( bean ) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if( rs != null ){ rs.close(); }
				if( pstmt != null ){ pstmt.close(); }
				super.closeConnection(); 
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}		
		return lists ;
	}

	public List<Combo02> View02() {
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = " select m.name, count(*) as cnt " ; 
		sql += " from members m inner join boards b ";
		sql += " on m.id = b.writer ";
		sql += " group by m.name ";
		sql += " order by m.name desc ";
		
		
		List<Combo02> lists = new ArrayList<Combo02>();
		try {
			if( conn == null ){ super.conn = super.getConnection() ; }
			pstmt = super.conn.prepareStatement(sql) ;			
			rs = pstmt.executeQuery() ;
			
			while( rs.next() ){
				Combo02 bean = new Combo02();
				
				bean.setName(rs.getString("name"));
				bean.setCnt(rs.getInt("cnt"));
				 
				lists.add( bean ) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if( rs != null ){ rs.close(); }
				if( pstmt != null ){ pstmt.close(); }
				super.closeConnection(); 
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}		
		return lists ;
	}

	public List<Combo03> View03() {
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = " select m.name mname, p.name pname, od.qty, p.price, p.price * od.qty as amount " ; 
		sql += " from ((members m inner join orders o ";
		sql += " on m.id = o.mid) inner join orderdetails od ";
		sql += " on o.oid = od.oid) inner join products p ";
		sql += " on od.pnum = p.num ";
		sql += " order by p.name desc, m.name asc ";
		
		
		List<Combo03> lists = new ArrayList<Combo03>();
		try {
			if( conn == null ){ super.conn = super.getConnection() ; }
			pstmt = super.conn.prepareStatement(sql) ;			
			rs = pstmt.executeQuery() ;
			
			while( rs.next() ){
				Combo03 bean = new Combo03();
				
				bean.setMname(rs.getString("mname"));
				bean.setPname(rs.getString("pname"));
				bean.setQty(rs.getInt("qty"));
				bean.setPrice(rs.getInt("price"));
				bean.setAmount(rs.getInt("amount"));
				 
				lists.add( bean ) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if( rs != null ){ rs.close(); }
				if( pstmt != null ){ pstmt.close(); }
				super.closeConnection(); 
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}		
		return lists ;
	}

	public List<Combo04> View04() {
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = " select m.id, sum(p.price * od.qty) as amount " ; 
		sql += " from ((members m inner join orders o ";
		sql += " on m.id = o.mid) inner join orderdetails od ";
		sql += " on o.oid = od.oid) inner join products p ";
		sql += " on od.pnum = p.num ";
		sql += " group by m.id ";
		
		
		List<Combo04> lists = new ArrayList<Combo04>();
		try {
			if( conn == null ){ super.conn = super.getConnection() ; }
			pstmt = super.conn.prepareStatement(sql) ;			
			rs = pstmt.executeQuery() ;
			
			while( rs.next() ){
				Combo04 bean = new Combo04();
				
				bean.setId(rs.getString("id"));
				bean.setAmount(rs.getInt("amount"));
				 
				lists.add( bean ) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if( rs != null ){ rs.close(); }
				if( pstmt != null ){ pstmt.close(); }
				super.closeConnection(); 
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}		
		return lists ;
	}

	public List<Combo05> View05() {
		PreparedStatement pstmt = null ;
		ResultSet rs = null ;
		String sql = " select m.id, count(mid) as cnt " ; 
		sql += " from members m left outer join orders o ";
		sql += " on m.id = o.mid ";
		sql += " group by m.id ";
		
		
		List<Combo05> lists = new ArrayList<Combo05>();
		try {
			if( conn == null ){ super.conn = super.getConnection() ; }
			pstmt = super.conn.prepareStatement(sql) ;			
			rs = pstmt.executeQuery() ;
			
			while( rs.next() ){
				Combo05 bean = new Combo05();
				
				bean.setId(rs.getString("id"));
				bean.setCnt(rs.getInt("cnt"));
				 
				lists.add( bean ) ;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try {
				if( rs != null ){ rs.close(); }
				if( pstmt != null ){ pstmt.close(); }
				super.closeConnection(); 
			} catch (Exception e2) {
				e2.printStackTrace(); 
			}
		}		
		return lists ;
	}
	

}
