<%@ page language="java" %>
<html>
<head>
    <title>View Car Wash Booking</title>
</head>
<body>
<form action="MainServlet" method="post">
    <input type="hidden" name="operation" value="viewRecord">

    Vehicle Number: <input type="text" name="vehicleNumber"><br><br>
    Booking Date: <input type="date" name="bookingDate"><br><br>

    <input type="submit" value="View Booking">
</form>
</body>
</html>
