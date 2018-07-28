<%--
  Created by IntelliJ IDEA.
  User: Gunee
  Date: 06-07-2018
  Time: 17:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
  <link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
  <link rel="stylesheet" href="style.css" >
  <script src="http://code.jquery.com/jquery-2.1.1-rc2.min.js" ></script>
  <script src="http://malsup.github.com/jquery.form.js"></script>
  <script src="script.js"></script>
</head>
<body>
<div id="abc">
<div id="over">
  <div class="mine">
    <div id="invisible">
        <img src="https://thumbs.gfycat.com/EasygoingWastefulIridescentshark-size_restricted.gif"
        style="height: 20px; width: 20px">
    jhfbakhflhfvbalisuafhlisfuhlzkc
    </div>
  </div>
<div class="container-main">
  <form action="upload.jsp" method="post" id="myForm" style="padding:20px;"
        enctype="multipart/form-data">
    <label for="file">Filename:</label>
    <input type="file" name="file" id="file"><br>
    <input type="submit" name="submit" class="btn btn-success" value="Upload Image">
  </form>
  <div class="progress progress-striped active">
    <div class="progress-bar"  role="progressbar" aria-valuenow="0" aria-valuemin="0"
         aria-valuemax="100" style="width: 0%">
      <span class="sr-only">0% Complete</span>
    </div>
  </div>
  <div class="image"></div>
</div>
</div>
</div>
</body>
</html>
