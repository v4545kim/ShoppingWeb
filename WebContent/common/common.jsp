<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE>
<%
	int twelve = 12;
%>

<html>
<head>
<!-- 로그인 상태 정보 -->
	<c:set var="whologin" value="0"/>
	
	<c:if test="${empty sessionScope.loginfo}">
		<c:set var="whologin" value="0"/>
	</c:if>
	
	<c:if test="${not empty sessionScope.loginfo}">
		<c:if test="${sessionScope.loginfo.id == 'admin'}">
			<c:set var="whologin" value="2"/>
		</c:if>
		<c:if test="${sessionScope.loginfo.id != 'admin'}">
			<c:set var="whologin" value="1"/>
		</c:if>
	</c:if>
<%
	String contextPath = request.getContextPath();
	String mappingName = "/Shopping" ;
	
	// 폼 태그에서 사용할 변수
	String YesForm = contextPath + mappingName;
	
	// 폼이 아닌 다른 영역에서 사용할 변수
	String NoForm = contextPath + mappingName + "?command=";
	
	//out.print("contextPath=" + contextPath +"<br>");
	//out.print("mappingName=" + mappingName +"<br>");
	//out.print("NoForm=" + NoForm +"<br>");
%>	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	<style type="text/css">
		/* for form validation check */
		.err{font-size: 10pt; color: red; font-weight: bolder;}
	</style>
</head>
<body>
	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<a class="navbar-brand" href="#">미니 쇼핑몰</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li class="active">
						<a href="<%=NoForm%>main">Home</a>
					</li>
					<li>
						<a href="#" class="dropdown-toggle"> 
							<font color='white'>
								<c:if test="${whologin == 0}">
									미로그인
								</c:if>
								<c:if test="${whologin != 0}">
									${sessionScope.loginfo.name}(${sessionScope.loginfo.id})님
								</c:if>
							</font>
						</a>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">회원<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li>
								<c:if test="${whologin == 0}">
									<a href="<%=NoForm%>meLogin">로그인</a>
									<a href="<%=NoForm%>meInsert">회원 가입</a>	
								</c:if>
								<c:if test="${whologin != 0}">
									<a href="<%=NoForm%>meLogout">로그아웃</a>
									<a href="<%=NoForm%>meUpdate">회원 정보 수정</a>	
								</c:if>
								<c:if test="${whologin == 1}">
									<a href="<%=NoForm%>meDelete&id=${sessionScope.loginfo.id}">회원 탈퇴</a>
								</c:if>
								<c:if test="${whologin == 2}">
									<a href="<%=NoForm%>meList">회원 목록 보기</a>
								</c:if>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">게시물<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li>
								<c:if test="${whologin != 0}">
									<a href="<%=NoForm%>boInsert">게시물 등록</a>
									<a href="<%=NoForm%>boList">게시물 목록 보기</a>
								</c:if>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">상품<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li>
								<c:if test="${whologin == 2}">
									<a href="<%=NoForm%>prInsert">상품 등록</a>
								</c:if>
								<c:if test="${whologin != 0}">
									<a href="<%=NoForm%>prList">상품 목록 보기</a>
								</c:if>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">쇼핑몰<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li>
								<c:if test="${whologin != 0}">
									<a href="<%=NoForm%>mallOrder">나의 쇼핑 내역</a>
									<a href="<%=NoForm%>mallList">장바구니 보기</a>
								</c:if>
							</li>
						</ul>
					</li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown">데이터 보기<b class="caret"></b></a>
						<ul class="dropdown-menu">
							<li><a href="<%=NoForm%>viewExam01">회원과 게시물</a></li>
							<li><a href="<%=NoForm%>viewExam02">회원별 게시물 건수</a></li>
							<li><a href="<%=NoForm%>viewExam03">주문 정보</a></li>
							<li><a href="<%=NoForm%>viewExam04">고객별 매출 총액</a></li>
							<li><a href="<%=NoForm%>viewExam05">회원별 주문 건수</a></li>
						</ul>
					</li>					
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li>
						<c:if test="${whologin == 0}">
							<a href="<%=NoForm%>meLogin">
								<span class="glyphicon glyphicon-log-in"> 로그인</span>
							</a>
						</c:if>
						<c:if test="${whologin != 0}">
							<a href="<%=NoForm%>meLogout">
								<span class="glyphicon glyphicon-log-out"> 로그아웃</span>
							</a>
						</c:if>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<c:if test="${not empty requestScope.errmsg}">
		<div class="alert alert-danger alert-dismissible">
  			<a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
  			&nbsp;&nbsp;&nbsp;<strong>${requestScope.errmsg}</strong>
		</div>
	</c:if>
</body>
</html>

























