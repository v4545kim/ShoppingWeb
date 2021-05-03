package shopping.member.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.common.controller.MainController;
import shopping.common.controller.SuperClass;
import shopping.common.model.MyCartList;
import shopping.common.model.ShoppingInfo;
import shopping.mall.model.MallDao;
import shopping.member.model.Member;
import shopping.member.model.MemberDao;

public class MemberLoginController extends SuperClass {
	private String id ;
	private String password ;
	
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		String gotopage = "/member/meLoginForm.jsp" ;
		super.GotoPage(gotopage);
	}	
	
	@Override
	public boolean validate(HttpServletRequest request) {
		boolean isCheck = true; // true면 유효성 검사에 문제가 없습니다.
		
		if (this.id.length() < 4 || this.id.length() > 10) {
			request.setAttribute(super.PREFIX + "id", "아이디는 4자리 이상 10자리 이하이어야 합니다.");
			isCheck = false;
		}
		
		if (this.password.length() < 4 || this.password.length() > 10) {
			request.setAttribute(super.PREFIX + "password", "비밀 번호는 4자리 이상 10자리 이하이어야 합니다.");
			isCheck = false;
		}
		
		return isCheck;
	}
	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		
		this.id = request.getParameter("id") ;
		this.password = request.getParameter("password") ;
		System.out.println(id + "/" + password);
		
		String gotopage = "";
		
		if (this.validate(request) == true) {
			System.out.println("유효성 검사에 문제 없음");
			
			MemberDao dao = new MemberDao();
			Member bean = dao.SelectData(id,password) ;
			
			if (bean == null) {
				System.out.println("로그인 실패!");
				String message = "아이디나 비밀 번호가 잘못되었습니다.";
				super.setErrorMessage(message);
				gotopage = "/member/meLoginForm.jsp";
				super.GotoPage(gotopage);
			} else {
				System.out.println("로그인 성공!");
				// 로그인 정보를 세션에 바인딩
				super.session.setAttribute("loginfo", bean);
				
				// 장바구니 테이블에서 들어 있는 나의 쇼핑 정보가 있으면
				//	session 영역에 mycart라는 이름으로 바인딩합니다.
				MallDao mdao = new MallDao();
				
				List<ShoppingInfo> lists = mdao.GetShoppingInfo(bean.getId());
				
				if (lists.size() > 0) {
					MyCartList mycart = new MyCartList();
					
					// insert mycart for statement
					for(ShoppingInfo shop : lists) {
						mycart.AddOrder(shop.getPnum(), shop.getQty());
					}
					
					super.session.setAttribute("mycart", mycart);
				}
				
				// 메인 페이지로 이동합니다.
				new MainController().doGet(request, response);
			}
			
			
		} else { // 문제가 있음
			// 이전에 입력했던 정보를 다시 바인딩해줍니다.
			request.setAttribute("id", this.id);
			request.setAttribute("password", this.password);
			
			gotopage = "/member/meLoginForm.jsp";
			super.GotoPage(gotopage);
		}
	}
}







