<%@ page import="java.util.List" %>
<%@ page import="com.wipro.carwash.bean.CarWashBookingBean" %>

<%
    List<CarWashBookingBean> list =
        (List<CarWashBookingBean>) request.getAttribute("list");
%>

<html>
<body>
<%
    if (list == null || list.isEmpty()) {
%>
    <h3>No records available!</h3>
<%
    } else {
%>
    <h3>All Car Wash Bookings</h3>
    <%
        for (CarWashBookingBean bean : list) {
    %>
        <hr>
        Record ID: <%= bean.getRecordId() %><br>
        Customer Name: <%= bean.getCustomerName() %><br>
        Vehicle Number: <%= bean.getVehicleNumber() %><br>
        Wash Type: <%= bean.getWashType() %><br>
        Booking Date: <%= bean.getBookingDate() %><br>
        Time Slot: <%= bean.getTimeSlot() %><br>
        Remarks: <%= bean.getRemarks() %><br>
    <%
        }
    }
%>
</body>
</html>
