package shopping.mall.controller;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.common.controller.SuperClass;
import shopping.common.model.MyCartList;
import shopping.mall.model.MallDao;
import shopping.member.model.Member;

public class MallCalculateController extends SuperClass {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		System.out.println("장바구니 내역을 이용하여 계산을 합니다.");
		
		MyCartList mycart = (MyCartList) super.session.getAttribute("mycart");
		MallDao dao = new MallDao();
		
		if (mycart != null) {
			Map<Integer, Integer> maplists = mycart.GetAllOrderList();
			System.out.println("shopping lists count" + maplists.size());
			
			int totalPoint = (int)super.session.getAttribute("totalPoint");
			
			Member mem = (Member)super.session.getAttribute("loginfo");
			
			System.out.println("call dao.Calculate()");
			dao.Calculate(mem, maplists, totalPoint);
			
			System.out.println("remove attribute in session");
			super.session.removeAttribute("totalPoint");
			super.session.removeAttribute("totalAmount");
			super.session.removeAttribute("mycart");
			super.session.removeAttribute("shoplists");
			
			String message = "결제를 완료하였습니다.";
			super.session.setAttribute("message", message);
		}
		
		new MallOrderController().doGet(request, response);
		

	}	
}