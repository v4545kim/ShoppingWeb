package shopping.mall.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.common.controller.SuperClass;
import shopping.common.model.MyCartList;

public class MallDeleteController extends SuperClass {
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		
		
		
		if (super.session.getAttribute("loginfo") == null) {
			String gotopage = "/member/meLoginForm.jsp" ;
			super.GotoPage(gotopage);
			
		} else {
			MyCartList mycart = (MyCartList)super.session.getAttribute("mycart");
			if (mycart == null) {
				mycart = new MyCartList();
			}
			
			int pnum = Integer.parseInt(request.getParameter("pnum"));
			mycart.DeleteOrder(pnum);
			super.session.setAttribute("mycart", mycart);
			new MallListController().doGet(request, response);
		}
		
	}	
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doPost(request, response);
	}
}