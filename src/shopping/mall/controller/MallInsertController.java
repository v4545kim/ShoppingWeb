package shopping.mall.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.common.controller.SuperClass;
import shopping.common.model.MyCartList;
import shopping.product.controller.ProductDetailViewController;
import shopping.product.controller.ProductListController;

public class MallInsertController extends SuperClass {
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
		
		if (super.session.getAttribute("loginfo") == null) {
			String message = "로그인이 필요합니다.";
			super.setErrorMessage(message);
			
			String gotopage = "/member/meLoginForm.jsp";
			super.GotoPage(gotopage);
		} else {
			int num = Integer.parseInt(request.getParameter("num"));
			int stock = Integer.parseInt(request.getParameter("stock"));
			int qty = Integer.parseInt(request.getParameter("qty"));
			
			if (stock < qty) {
				String message = "재고 수량이 부족합니다.";
				super.setErrorMessage(message);
				new ProductDetailViewController().doGet(request, response);
			} else {
				MyCartList mycart = (MyCartList) super.session.getAttribute("mycart");
				if(mycart == null) {
					mycart = new MyCartList();
				}
				mycart.AddOrder(num, qty); // put into mycart
				super.session.setAttribute("mycart", mycart);
				new MallListController().doGet(request, response);
			}
		}
	}
}














