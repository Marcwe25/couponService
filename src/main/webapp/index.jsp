<!DOCTYPE html>
<html ng-app="couponAdmin">

<head>
    <meta charset="windows-1255">
    <title>LOGIN PAGE</title>
	<link href="https://fonts.googleapis.com/css?family=Karla" rel="stylesheet">
	<script src="common/js/pub/angular.js"></script>
	<script src="common/js/pub/angular-ui-router.js"></script>
    <script src="common/js/pub/angular-cookies.js"></script>
	<script src="common/js/angular-bootstrap.js"></script>
	<!-- <script src="common/js/login.js"></script></script> -->
	<script src="common/js/service/type.srvc.js"></script>
	<script src="common/js/controller/login.ctrl.js"></script>
	<script src="common/js/service/login.srvc.js"></script>
	<script src="common/js/beans/company.js"></script>
    <script src="common/js/beans/customer.js"></script>
    <script src="common/js/beans/coupon.js"></script>
	<link  href="common/css/login.css" rel="stylesheet">
	
</head>

<body style="background-color: #9e9e9e">
    <div 	ng-controller="loginCtrl as login"
    		class="login_form">
        <H1>USER LOGIN</H1><hr/>
        
        <form name="myForm" id="form_id" action = "http://localhost:8080/couponService/Login" method="post">

            <input	required type="text" 		id="username" name="username" placeholder="username"/>
            <input	required type="password" 	id="password" name="password" placeholder="password""/>
            <select required name="userType" id="userType"">
            
                <option value="" disable>choose user type	</option>
                <option value="admin"	>administrator		</option>
                <option value="company"	>company			</option>
                <option value="customer">customer			</option>
                
            </select>&nbsp
			<label>
				<input type="checkbox" name="remember"/>remember me</label><br/>
            <input type="submit" class="sButton">
            <div class="message">${message}</div>
            <p></p>

        </form>
    </div>

</body>

</html>