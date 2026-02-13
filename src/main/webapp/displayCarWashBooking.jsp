<%@ page import="com.wipro.carwash.bean.CarWashBookingBean" %>
<%
    CarWashBookingBean bean =
        (CarWashBookingBean) request.getAttribute("bean");
%>

<html>
<body>
<%
    if (bean == null) {
%>
    <h3>No matching records exists! Please try again!</h3>
<%
    } else {
%>
    <h3>Car Wash Booking Details</h3>
    Record ID: <%= bean.getRecordId() %><br>
    Customer Name: <%= bean.getCustomerName() %><br>
    Vehicle Number: <%= bean.getVehicleNumber() %><br>
    Wash Type: <%= bean.getWashType() %><br>
    Booking Date: <%= bean.getBookingDate() %><br>
    Time Slot: <%= bean.getTimeSlot() %><br>
    Remarks: <%= bean.getRemarks() %><br>
<%
    }
%>
</body>
</html>

