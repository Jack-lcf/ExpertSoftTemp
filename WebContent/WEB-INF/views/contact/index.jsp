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
                        <th>ID</th>
                        <th>Name</th> 
                        <th>Surname</th> 
                        <th>Login</th>
                        <th>Email</th>
                        <th>Phone</th>
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
            </form>
        </section>
    </div>
</div>
