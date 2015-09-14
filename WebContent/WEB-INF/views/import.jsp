<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<div id="content">
	<div id="import_section">
		<c:choose>
			<c:when test="${empty error}">
				<h3>Import new contacts:</h3>
			</c:when>
			<c:otherwise>
				<h3 class="error">${error}</h3>
			</c:otherwise>
		</c:choose>
		<section>
			<form method="POST" onSubmit="localpath.value=filePath.value" action="action">
				<p>
					<input type="file" name="filePath" id="file">
					<input type="hidden" name="localpath">
				</p>
				<p>
					<button type="submit">Import</button>
					<button type="reset">Cancel</button>
				</p>
				<input type="hidden" name="command" id="command" value=/contact_import>
			</form>
		</section>
	</div>
</div>
