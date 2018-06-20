<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/page/share/taglib.jsp" %>
	<c:forEach items="${viewHistory}" var="viewproduct" varStatus="statu">
		<c:if test="${viewproduct != 'getViewHistory' }">
			<li class="bj_blue"><a href="/product/view?productid=${viewproduct.id}" target="_blank" title="${viewproduct.name}">${viewproduct.name}</a></li>
		</c:if>
	</c:forEach>