<%@ page language="java" %>
<html>
<head>
    <title>Add Car Wash Booking</title>
</head>
<body>
<form action="MainServlet" method="post">
    <input type="hidden" name="operation" value="newRecord">

    Customer Name: <input type="text" name="customerName"><br><br>
    Vehicle Number: <input type="text" name="vehicleNumber"><br><br>
    Wash Type: <input type="text" name="washType"><br><br>
    Booking Date: <input type="date" name="bookingDate"><br><br>
    Time Slot: <input type="text" name="timeSlot"><br><br>
    Remarks: <input type="text" name="remarks"><br><br>

    <input type="submit" value="Add Booking">
</form>
</body>
</html>

