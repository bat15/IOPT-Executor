<%-- 
    Document   : json_write
    Created on : Mar 4, 2017, 10:49:31 AM
    Author     : Павел
--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<body>
<form name="frm" method="post" action="../server-side/json_write_process.jsp">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="12%">&nbsp;</td>
    <td width="78%">Max length - 500 000 symbols</td>
    </tr>
  <tr>
    <td><h1>Test model</h1></td>
    <td>
        <!--<input type="text" name="test_model">-->
        <textarea id="test_model" palceholder="Input to write to file for response" name="test_model" rows="50" cols="120"></textarea>
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td><input type="submit" name="submit" value="Перезаписать json"></td>
    </tr>
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    </tr>
</table>
</form>
</body>
</html>
