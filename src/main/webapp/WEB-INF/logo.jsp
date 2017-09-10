<%@  page pageEncoding="utf-8"%>
<!-- EL默认的取值范围：
pageContext,request,session,application
若想从cookie中取值。语法：
cookie.NAME.value -->
<img src="images/logo.png" alt="logo" class="left" />
<%-- <span>${cookie.user.value }</span> --%>
<span>${user }</span>
<a href="/NETCTOSS/tologout.do">[退出]</a>
