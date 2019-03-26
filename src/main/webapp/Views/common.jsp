<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ tag description="Overall Page template" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title><template:get name='title' /></title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet">
</head>
<body>
    <template:get name='content' />
</body>
</html>