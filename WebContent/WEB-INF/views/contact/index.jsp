<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="content">
	<div id="content_section">
		<c:choose>
			<c:when test="${empty error}">
				<h3>Contact list:</h3>
			</c:when>
			<c:otherwise>
				<h3 class="error">${error}</h3>
			</c:otherwise>
		</c:choose>
		<section>
			<form>
				<table border="1">
					<tr>
						<th><a href="contacts?method=getId&page=${currentPage}">ID</a></th>
						<th><a href="contacts?method=getName&page=${currentPage}">Name</a></th>
						<th><a href="contacts?method=getSurname&page=${currentPage}">Surname</a></th>
						<th><a href="contacts?method=getLogin&page=${currentPage}">Login</a></th>
						<th><a href="contacts?method=getEmail&page=${currentPage}">Email</a></th>
						<th><a href="contacts?method=getPhone&page=${currentPage}">Phone</a></th>
					</tr>
					<c:forEach var="contacts" items="${contacts}">
						<tr>
							<td><label>${contacts.id}</label></td>
							<td><label>${contacts.name}</label></td>
							<td><label>${contacts.surname}</label></td>
							<td><label>${contacts.login}</label></td>
							<td><label>${contacts.email}</label></td>
							<td><label>${contacts.phone}</label></td>
						</tr>
					</c:forEach>
				</table>
				<%--For displaying Page numbers--%>
				<table>
					<tr>
						<c:forEach begin="1" end="${numberOfPages}" var="i">
							<c:choose>
								<c:when test="${currentPage eq i}">
									<td>${i}</td>
								</c:when>
								<c:otherwise>
									<td><a href="contacts?method=${method}&page=${i}">${i}</a></td>
								</c:otherwise>
							</c:choose>
						</c:forEach>
					</tr>
				</table>
			</form>
		</section>
	</div>
</div>
