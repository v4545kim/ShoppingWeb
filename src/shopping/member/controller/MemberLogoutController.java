package shopping.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.common.controller.SuperClass;

public class MemberLogoutController extends SuperClass {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);

		// 만약 세션 영역에 장바구니 정보가 들어있으면
		//	이 정보를 장바구니 임시 테이블에 저장합니다.
		
		
		// 나의 로그인 정보 및 관련 모든 것들을 비웁니다.
		super.session.invalidate();

		String gotopage = "/member/meLoginForm.jsp";
		super.GotoPage(gotopage);
	}
}