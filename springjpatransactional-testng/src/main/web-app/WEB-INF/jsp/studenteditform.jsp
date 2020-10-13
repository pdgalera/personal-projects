<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<h1>Edit Student</h1>
<form:form method="post" action="/save" commandName="command">
  <table >
    <tr>
      <td> </td>
      <td><form:hidden  path="id" /></td>
    </tr>
    <tr>
      <td>Name : </td>
      <td><form:input path="name"  /></td>
    </tr>
    <tr>
      <td>Age :</td>
      <td><form:input path="age" /></td>
    </tr>
    <tr>
      <td> </td>
      <td><input type="submit" value="Edit Save" /></td>
    </tr>
  </table>
</form:form>    