<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
</div>
<div id="content">
    <div id="center_section">
    	<section>
     		<c:forEach var="error" items="${error}">
				<h3 class="error" align="center">${error}</h3>
			</c:forEach>
		</section>
    </div>
</div>