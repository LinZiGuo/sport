<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
<html>
<head>
<title>查询产品</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="/css/vip.css" type="text/css">
<SCRIPT language=JavaScript src="/js/FoshanRen.js"></SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<form action="/control/product/list" method="post">
<input name="typeid" type="hidden" />
<input name="query" value="true" type="hidden" />
  <table width="98%" border="0" cellspacing="1" cellpadding="3" align="center">
    <tr bgcolor="6f8ac4"> 
      <td colspan="2" ><font color="#FFFFFF">查询产品：</font></td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品名称  ：</div></td>
      <td width="75%">
      <input name="name" type="text" size="50" maxlength="40"/>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">产品类别  ：</div></td>
      <td width="75%"> <input type="text" name="v_type_name" disabled="true" size="30" value="${typename}"/> 
        <input type="button" name="select" value="选择..." onClick="javaScript:winOpen('/control/product/manage/selectUI','列表',600,400)">
      </td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">底(采购)价(元) ：</div></td>
      <td width="75%"> 
在<input name="startbaseprice" type="text" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>
      与<input name="endbaseprice" type="text" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>之间
</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">销售价(元) ：</div></td>
      <td width="75%"> 在<input name="startsellprice" type="text" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>
      与<input name="endsellprice" type="text" size="10" maxlength="10" onkeypress="javascript:InputLongNumberCheck()"/>之间
      </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">货号 ：</div></td>
      <td width="75%">
      <input name="code" type="text" size="20" maxlength="30" />(注:供货商提供的便于产品查找的编号)</td>
    </tr>
	<tr bgcolor="f5f5f5"> 
      <td width="25%"> <div align="right">品牌 ：</div></td>
      <td width="75%">
      	<s:select name="brandid" list="brands" listKey="code" listValue="name" headerKey="" headerValue="--请选择--">
      	</s:select>
      </td>
    </tr>
    <tr bgcolor="f5f5f5"> 
      <td colspan="2"> <div align="center"> 
          <input type="submit" name="edit" value=" 确 认 " class="frm_btn">
          &nbsp;&nbsp;<input type="button" name="Button" value=" 返 回 " class="frm_btn" onclick="javascript:history.back()">
        </div></td>
    </tr>
  </table>
</form>
<br>
</body>
</html>