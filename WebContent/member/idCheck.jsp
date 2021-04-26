<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
	
	<script type="text/javascript">
		function meClose(isCheck) {
			opener.myform.isCheck.value = isCheck;
			self.close();
		}
	</script>
</head>
<body>
	<h1></h1>
	<h1></h1>
	<h1></h1>
	<div class="container">
		<p align="center">${message}</p>
		<div class="row" align="center">
			<button class="btn btn-primary" type="button" 
				onclick="meClose(${isCheck});">닫기
			</button>
		</div>
	</div>
</body>
</html>