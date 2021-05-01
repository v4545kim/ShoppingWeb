<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<%@include file="./../common/common.jsp"%>
<%
	int offset = 2; //오프 셋 
	int content = twelve - 2 * offset; //12 - 2 * 오프셋
%>
<html>
<head>
</head>
<body>
	<div class="container col-md-offset-<%=offset%> col-md-<%=content%>">
		<h1>${sessionScope.loginfo.name}님의 주문 상세 내역</h1>
		<hr>
		<p>${sessionScope.loginfo.name}고객님이<br>
			<strong>${order.orderdate}</strong>에 구매하신 상품에 대한 상세 결제 내역입니다.
		</p>
		<hr>

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">주문 내역</h3>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-condensed">
						<thead>
							<tr>
								<th class="text-center">제품명</th>
								<th class="text-center">이미지</th>								
								<th class="text-center">단가</th>
								<th class="text-center">수량</th>
								<th class="text-center">금액</th>
							</tr>
						</thead>
						<tbody>
							<c:set var="totalAmount" value="0" />
							<c:forEach var="shopinfo" items="${requestScope.lists}">
								<tr>
									<td class="text-center">${shopinfo.pname}</td>
									<td class="text-center">
										<img alt="${shopinfo.image}" width="45" height="45"
										src="${applicationScope.uploadedPath}/${shopinfo.image}">
									</td>
									<td class="text-center">
										<fmt:formatNumber value="${shopinfo.price}" pattern="###,###"/>원
									</td>
									<td class="text-center">
										<fmt:formatNumber value="${shopinfo.qty}" pattern="###,###"/>개
									</td>
									<td class="text-center">
										<c:set var="amount" value="${shopinfo.qty * shopinfo.price}" />
										<c:set var="totalAmount" value="${totalAmount + amount}" />
										<fmt:formatNumber value="${amount}" pattern="###,###"/>원
									</td>
								</tr>
							</c:forEach>
							<tr>
								<td class="thick-line"></td>
								<td class="thick-line"></td>
								<td class="thick-line"></td>
								<td class="thick-line text-center">
									<strong>합계</strong>
								</td>
								<td class="thick-line text-right">
									<fmt:formatNumber value="${totalAmount}" pattern="###,###"/>원
								</td>
							</tr>
							<%-- 요금에 따른 운송비 구하기 --%>
								<c:set var="shipExpense" value="0"/>
								<c:choose>
									<c:when test="${totalAmount >= 100000}">
										<c:set var="shipExpense" value="0"/>	
									</c:when>
									<c:when test="${totalAmount >= 50000}">
										<c:set var="shipExpense" value="2000"/>	
									</c:when>
									<c:otherwise>
										<c:set var="shipExpense" value="4000"/>	
									</c:otherwise>
								</c:choose>
							<tr>
								<td class="no-line"></td>
								<td class="no-line"></td>
								<td class="no-line"></td>
								<td class="no-line text-center">
									<strong>운송비</strong>
								</td>
								<td class="no-line text-right">
									<fmt:formatNumber value="${shipExpense}" pattern="###,###"/>원
								</td>
							</tr>
							<tr>
								<td class="no-line"></td>
								<td class="no-line"></td>
								<td class="no-line"></td>
								<td class="no-line text-center">
									<strong>최종 금액</strong>
								</td>
								<td class="no-line text-right">
									<c:set var="finalAmount" value="${totalAmount + shipExpense}" />
									<fmt:formatNumber value="${finalAmount}" pattern="###,###"/>원
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">결제 정보</h3>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td class="text-center gr">주문 번호</td>
								<td>${order.oid}</td>
								<td class="text-center gr">주문 날짜</td>
								<td>${order.orderdate}</td>
							</tr>
							<tr>
								<td class="text-center gr">주문 총액</td>
								<td>
									<fmt:formatNumber value="${finalAmount}" pattern="###,###"/>원
								</td>
								<td class="text-center gr">할인 금액</td>
								<td>
									<c:set var="discount" value="100"/>	
									${discount}원
								</td>
							</tr>
							<tr>
								<td class="text-center gr">결제 금액</td>
								<td>
									<fmt:formatNumber value="${finalAmount - discount}" pattern="###,###"/>원
								</td>
								<td class="text-center gr">결제 상태</td>
								<td>결제 완료</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>

		<div class="panel panel-primary">
			<div class="panel-heading">
				<h3 class="panel-title">배송 정보</h3>
			</div>
			<div class="panel-body">
				<div class="table-responsive">
					<table class="table table-bordered">
						<tbody>
							<tr>
								<td class="text-center gr"><strong>받으시는 분</strong></td>
								<td>${sessionScope.loginfo.name}(${sessionScope.loginfo.id}) 님</td>
							</tr>
							<tr>
								<td class="text-center gr"><strong></strong>우편 번호</td>
								<td>
									${sessionScope.loginfo.zipcode}
								</td>
							</tr>
							<tr>
								<td class="text-center gr"><strong>주소</strong></td>
								<td>
									${sessionScope.loginfo.address1}
									&nbsp;
									${sessionScope.loginfo.address2}
								</td>
							</tr>
							<tr>
								<td class="text-center gr"><strong>적릭 포인트</strong></td>
								<td>
									<fmt:formatNumber value="${sessionScope.loginfo.mpoint}" pattern="###,###"/>점
								</td>
							</tr>
							<tr>
								<td class="text-center gr"><strong>배송 방법</strong></td>
								<td></td>
							</tr>							
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>























