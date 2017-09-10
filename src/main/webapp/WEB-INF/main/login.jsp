<%@page pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>达内－NetCTOSS</title>
<link type="text/css" rel="stylesheet" media="all"
	href="styles/global.css" />
<link type="text/css" rel="stylesheet" media="all"
	href="styles/global_color.css" />
	 <script  type="text/javascript"   src="js/ajax.js">
</script>
	<script language="javascript"  type="text/javascript">
	
	</script>
</head>
<body class="index">
	<div class="login_box">
		<form  action="login.do"  method="post">
			<table>
				<tr>
					<td class="login_info">账号：</td>
					<td colspan="2"><input name="adminCode"  id="adminCode" type="text" class="width150"   value="${param.adminCode }"  onblur="f5();"/></td>
					<td class="login_error_info"><span class="required">30长度的字母、数字和下划线</span></td>
				</tr>
				<tr>
					<td class="login_info">密码：</td>
					<td colspan="2"><input name="password" type="password"
						class="width150"   value="${param.password }"/></td>
					<td><span class="required">30长度的字母、数字和下划线</span></td>
				</tr>
				<tr>
					<td class="login_info">验证码：</td>
					<td class="width70"><input name="code" type="text" class="width70" /></td>
					<td><img src="createimg.do" onclick="this.src='createimg.do?x='+Math.random();" alt="验证码" title="点击更换" /></td>
					<!-- 路径后加参数的目的，是为了将其伪装为动态路径，从而促使
					浏览器访问服务器 -->
					<!-- 'createimg.do?x='+Math.random() -->
					<td><span class="required"></span></td>
				</tr>
				<tr>
					<td></td>
					<td class="login_button" colspan="2">
					<!-- form有onsubmit事件,点击submit按钮时触发
					     form还有submit(),可以通过js调用
					     上述两种方式,都能提交表单中的数据 -->
					<a href="javascript:document.forms[0].submit()"><img
							src="images/login_btn.png" /></a></td>
					<td><span class="required"  id="msg"></span></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>
