<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Коментарі</title>
    <style>
        body { font-family: sans-serif; margin: 20px; }
        .comment-item { margin-bottom: 15px; padding: 10px; border-bottom: 1px solid #eee; }
        .form-section { background: #f9f9f9; padding: 15px; border-radius: 5px; margin-bottom: 20px; }
    </style>
</head>
<body>
<a href="${pageContext.request.contextPath}/books">← Назад до каталогу</a>

<h2>Коментарі до книги</h2>

<div class="form-section">
    <h4>Залишити відгук:</h4>
    <form method="post" action="${pageContext.request.contextPath}/comments">
        <input type="hidden" name="bookId" value="${param.bookId}">

        <p>Ваше ім'я:<br>
            <input type="text" name="author" required></p>

        <p>Текст повідомлення:<br>
            <textarea name="text" rows="4" cols="40" required></textarea></p>

        <button type="submit">Опублікувати</button>
    </form>
</div>

<hr>

<h3>Список відгуків:</h3>
<c:if test="${empty comments}">
    <p>Для цієї книги ще немає жодного коментаря. Будьте першим!</p>
</c:if>

<ul>
    <c:forEach var="c" items="${comments}">
        <li class="comment-item">
            <b>${c.author}</b> <small>(${c.createdAt})</small><br>
            <span>${c.text}</span>
            <br>

            <form method="post" action="${pageContext.request.contextPath}/comments" style="display:inline">
                <input type="hidden" name="bookId" value="${param.bookId}">
                <input type="hidden" name="commentId" value="${c.id}">
                <input type="hidden" name="_method" value="delete">
                <button type="submit" onclick="return confirm('Видалити цей коментар?')">❌ видалити</button>
            </form>
        </li>
    </c:forEach>
</ul>

</body>
</html>