package com.wipro.carwash.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wipro.carwash.bean.CarWashBookingBean;
import com.wipro.carwash.service.Administrator;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String operation = request.getParameter("operation");

        if ("newRecord".equals(operation)) {
            String result = addRecord(request);

            if (result.equals("SUCCESS")) {
                response.sendRedirect("success.html");
            } 
            else if (result.equals("ALREADY EXISTS") ||
                     result.equals("INVALID VEHICLE NUMBER") ||
                     result.equals("INVALID WASH TYPE")) {

                response.setContentType("text/html");
                response.getWriter().println("<h2>" + result + "</h2>");
            } 
            else {
                response.sendRedirect("error.html");
            }
        }

        else if ("viewRecord".equals(operation)) {
            CarWashBookingBean bean = viewRecord(request);

            if (bean == null) {
                request.setAttribute("message", "No matching records exists! Please try again!");
            } else {
                request.setAttribute("bean", bean);
            }

            RequestDispatcher rd = request.getRequestDispatcher("displayCarWashBooking.jsp");
            rd.forward(request, response);
        }

        else if ("viewAllRecords".equals(operation)) {
            List<CarWashBookingBean> list = viewAllRecords();

            if (list == null || list.isEmpty()) {
                request.setAttribute("message", "No records available!");
            } else {
                request.setAttribute("list", list);
            }

            RequestDispatcher rd = request.getRequestDispatcher("displayAllCarWashBookings.jsp");
            rd.forward(request, response);
        }
    }

    public String addRecord(HttpServletRequest request) {
        try {

            String customerName = request.getParameter("customerName");
            String vehicleNumber = request.getParameter("vehicleNumber").trim().toUpperCase();
            String washType = request.getParameter("washType");
            String timeSlot = request.getParameter("timeSlot");
            String remarks = request.getParameter("remarks");
            
            String bookingDateStr = request.getParameter("bookingDate");
           
            if (customerName == null || customerName.trim().isEmpty() ||
                bookingDateStr == null || bookingDateStr.trim().isEmpty()) {

                return "FAIL";
            }
            
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bookingDate = sdf.parse(bookingDateStr);

            
            CarWashBookingBean bean = new CarWashBookingBean();
            bean.setCustomerName(customerName);
            bean.setVehicleNumber(vehicleNumber);
            bean.setWashType(washType);
            bean.setTimeSlot(timeSlot);
            bean.setRemarks(remarks);
            bean.setBookingDate(bookingDate);

            return new Administrator().addRecord(bean);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "FAIL";
    }

    public CarWashBookingBean viewRecord(HttpServletRequest request) {
    	
    	String vehicleNumber = request.getParameter("vehicleNumber").trim().toUpperCase();
    	String bookingDateStr = request.getParameter("bookingDate");
    	
        try {
 
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date bookingDate = sdf.parse(bookingDateStr);

            return new Administrator().viewRecord(vehicleNumber, bookingDate);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<CarWashBookingBean> viewAllRecords() {
        return new Administrator().viewAllRecords();
    }
}
