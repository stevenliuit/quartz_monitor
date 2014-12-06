<%--
  Created by IntelliJ IDEA.
  User: yamorn
  Date: 2014/12/6
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset=”utf-8”>
    <title></title>
  <script type="text/javascript" src="<c:url value="/resources/jquery/jquery-1.8.3.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/jquery/plugins/jquery-validation/jquery.validate.min.js"/>"></script>
  <script type="text/javascript" src="<c:url value="/resources/jquery/plugins/jquery-validation/localization/messages_zh.js"/>"></script>
</head>
<body>
  <form:form method="post" commandName="user" action="/registerAction">
    <table>
      <tr>
        <td>用户账户</td>
        <td><form:input path="account" cssClass="required"/></td>
        <td></td>
      </tr>
      <tr>
        <td>企业名称</td>
        <td><form:input path="enterprise.name" cssClass="required"/></td>
        <td> </td>
      </tr>
      <tr>
        <td>用户名</td>
        <td><form:input path="username" cssClass="required"/></td>
        <td> </td>
      </tr>
      <tr>
        <td>注册邮箱</td>
        <td><form:input path="email"/> </td>
        <td></td>
      </tr>
      <tr>
        <td>企业地址</td>
        <td><form:input path="enterprise.address"/></td>
        <td></td>
      </tr>
      <tr>
        <td>组织机构代码</td>
        <td><form:input path="enterprise.orgCode"/></td>
        <td></td>
      </tr>
      <tr>
        <td>工商登记注册号</td>
        <td><form:input path="enterprise.regNum"/></td>
        <td></td>
      </tr>
      <tr>
        <td>登陆密码</td>
        <td><form:input path="password" cssClass="required"/></td>
        <td></td>
      </tr>
      <tr>
        <td>确认密码</td>
        <td><input type="text" title="" cssClass="required"/></td>
        <td></td>
      </tr>
      <tr>
        <td><input type="submit" value="保存"/></td>
        <td><input type="reset" value="重置"/></td>
        <td><form:hidden path="roles[0].role" value="ROLE_ENTERPRISE"/></td>
      </tr>
    </table>

  </form:form>
</body>
<script>
  $(function(){
    $('#user').validate({
      submitHandler: function(form) {
        form.submit();
      }
    });
  });
</script>
</html>
