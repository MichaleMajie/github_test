<%@page pageEncoding="utf-8"%>
<!-- 此jsp被其他jsp复用,是其他jsp 的一部分
所以浏览器在 任何功能中,都可以看到并点击这些
链接,因此无法确定请求的来源路径,故此处合适写绝对路径 -->
<ul id="menu">
      <li><a href="/NETCTOSS/toindex.do" class="index_off"></a></li>
      <li><a href="" class="role_off"></a></li>
      <li><a href="" class="admin_off"></a></li>
      <li><a href="/NETCTOSS/findcost.do" class="fee_off"></a></li>
      <li><a href="" class="account_off"></a></li>
      <li><a href="" class="service_off"></a></li>
      <li><a href="" class="bill_off"></a></li>
      <li><a href="" class="report_off"></a></li>
      <li><a href="" class="information_off"></a></li>
      <li><a href="" class="password_off"></a></li>
</ul>