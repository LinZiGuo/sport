<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>修改订单的配送信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
<script language="JavaScript">
function checkfm(form){
	return true;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/order/manage/modifyDeliverInfo" method="post" onsubmit="return checkfm(this)">
<input type="hidden" name="orderid">
<input type="hidden" name="deliverid" value="${param.deliverid }">
  <table width="90%" border="0" cellspacing="2" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"><td colspan="2"  > <font color="#FFFFFF">修改订单的配送信息：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">收件人名称：</div></td>
      <td width="78%"><input type="text" name="recipients" value="${recipients }" size="20" maxlength="30"/>
        <input type="radio" name="gender" value="MAN" <c:if test="${gender=='MAN' }">checked='checked'</c:if>>先生
        <input type="radio" name="gender" value="WOMEN" <c:if test="${gender=='WOMEN' }">checked='checked'</c:if>>女士
        <font color="#FF0000">*</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">收货人地址：</div></td>
      <td width="78%"> <input type="text" name="address" value="${address }" size="50" maxlength="100"/>
        <font color="#FF0000">*</font></td>
    </tr>
        <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">邮编：</div></td>
      <td width="78%"> <input type="text" name="postalcode" value="${postalcode }" size="8" maxlength="6"/></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">电子邮箱：</div></td>
      <td width="78%"> <input type="text" name="email" value="${email }" size="20" maxlength="50"/></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">座机：</div></td>
      <td width="78%"> <input type="text" name="tel" value="${tel }" size="20" maxlength="20"/></td>
    </tr>
        <tr bgcolor="f5f5f5"> 
      <td width="22%" > <div align="right">手机：</div></td>
      <td width="78%"> <input type="text" name="mobile" value="${mobile }" size="20" maxlength="11"/></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="submit" name="SYS_SET" value=" 确 定 " class="frm_btn">
        </div></td>
    </tr>
  </table>
</form>
<br>
</body>
</html>