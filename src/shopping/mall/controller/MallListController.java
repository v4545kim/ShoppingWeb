package shopping.mall.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import shopping.common.controller.SuperClass;
import shopping.common.model.MyCartList;
import shopping.common.model.ShoppingInfo;
import shopping.member.controller.MemberLoginController;
import shopping.product.controller.ProductListController;
import shopping.product.model.Product;
import shopping.product.model.ProductDao;

public class MallListController extends SuperClass {
   @Override
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      super.doGet(request, response);
      
      if(super.session.getAttribute("loginfo") == null)
      {
         new MemberLoginController().doGet(request, response);
      }
      else
      {
         MyCartList mycart = (MyCartList)super.session.getAttribute("mycart");
         if(mycart == null)
         {
            String message = "쇼핑 목록이 없어서 상품 목록 페이지로 이동합니다.";
            super.setErrorMessage(message);
            
            new ProductListController().doGet(request, response);
         }
         else
         {
            Map<Integer, Integer> maplists = mycart.GetAllOrderList();
            System.out.println("cart item size : " + maplists.size());
            
            
            Set<Integer> keylist = maplists.keySet();
            
            
            
            List<ShoppingInfo> shoplists = new ArrayList<ShoppingInfo>();
            
            int totalAmount = 0; // 총 금액
            int totalPoint = 0; // 총 누적 포인트
            
            for(Integer pnum : keylist)
            {
               //pnum는 상품 번호
               
               int qty = maplists.get(pnum);
               
               ProductDao dao = new ProductDao();
               Product bean = dao.SelectDataByPk(pnum);
               
               //shopinfo : 상품 1개에 대한 정보를 저장하기 위한 클래스
               ShoppingInfo shopInfo = new ShoppingInfo();
               
               int point = bean.getPoint();
               int price = bean.getPrice();
                  
               totalAmount += qty * price;
               totalPoint += qty * point;
               
               shopInfo.setImage(bean.getImage());
               shopInfo.setPname(bean.getName());
               shopInfo.setPnum(pnum);
               shopInfo.setPoint(point);
               shopInfo.setPrice(price);
               shopInfo.setQty(qty);
               
               
               shoplists.add(shopInfo);
            }
            
            super.session.setAttribute("totalAmount", totalAmount);
            super.session.setAttribute("totalPoint", totalPoint);
            super.session.setAttribute("shoplists", shoplists);
            
            
            String gotopage = "/mall/mallList.jsp";
            super.GotoPage(gotopage);
            
            
         }
      }
      

   }   
   
}