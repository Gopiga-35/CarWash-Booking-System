package com.wipro.carwash.dao;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.wipro.carwash.bean.CarWashBookingBean;
import com.wipro.carwash.util.DBUtil;

public class CarWashBookingDAO {

   
    public String createRecord(CarWashBookingBean bean) {

        String query = "INSERT INTO CARWASH_TB "
                + "(RECORDID, CUSTOMERNAME, VEHICLENUMBER, WASHTYPE, BOOKING_DATE, TIMESLOT, REMARKS) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, bean.getRecordId());
            ps.setString(2, bean.getCustomerName());
            ps.setString(3, bean.getVehicleNumber());
            ps.setString(4, bean.getWashType());
            ps.setDate(5, new java.sql.Date(bean.getBookingDate().getTime()));
            ps.setString(6, bean.getTimeSlot());
            ps.setString(7, bean.getRemarks());

            int rows = ps.executeUpdate();
            if (rows > 0) return bean.getRecordId();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "FAIL";
    }

   
    public String generateRecordID(String vehicleNumber, Date bookingDate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            String datePart = sdf.format(bookingDate); 

            String vehiclePart = vehicleNumber.length() >= 2
                    ? vehicleNumber.substring(0, 2).toUpperCase()
                    : (vehicleNumber + "X").toUpperCase();

            Connection connection = DBUtil.getDBConnection();
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT CARWASH_SEQ.NEXTVAL FROM DUAL");

            if (rs.next()) {
                int seq = rs.getInt(1);
                String seqPart = String.format("%02d", seq); 
                return datePart + vehiclePart + seqPart; 
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean recordExists(String vehicleNumber, Date bookingDate) {

        String query = "SELECT 1 FROM CARWASH_TB "
                     + "WHERE UPPER(TRIM(VEHICLENUMBER)) = ? "
                     + "AND (BOOKING_DATE) = ?";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, vehicleNumber.trim().toUpperCase());

           
            ps.setDate(2, new java.sql.Date(bookingDate.getTime()));

            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public CarWashBookingBean fetchRecord(String vehicleNumber, Date bookingDate) {

        String query = "SELECT * FROM CARWASH_TB "
                     + "WHERE UPPER(TRIM(VEHICLENUMBER)) = ? "
                     + "AND (BOOKING_DATE) = ?";

        try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, vehicleNumber.trim().toUpperCase());
            ps.setDate(2, new java.sql.Date(bookingDate.getTime()));

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    CarWashBookingBean bean = new CarWashBookingBean();
                    bean.setRecordId(rs.getString("RECORDID"));
                    bean.setCustomerName(rs.getString("CUSTOMERNAME"));
                    bean.setVehicleNumber(rs.getString("VEHICLENUMBER"));
                    bean.setWashType(rs.getString("WASHTYPE"));
                    bean.setBookingDate(rs.getDate("BOOKING_DATE"));
                    bean.setTimeSlot(rs.getString("TIMESLOT"));
                    bean.setRemarks(rs.getString("REMARKS"));
                    return bean;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public List<CarWashBookingBean> fetchAllRecords() {
    	
        List<CarWashBookingBean> list = new ArrayList<>();
        String query = "SELECT * FROM CARWASH_TB ORDER BY BOOKING_DATE DESC";

       try (Connection connection = DBUtil.getDBConnection();
             PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                CarWashBookingBean bean = new CarWashBookingBean();
                bean.setRecordId(rs.getString("RECORDID"));
                bean.setCustomerName(rs.getString("CUSTOMERNAME"));
                bean.setVehicleNumber(rs.getString("VEHICLENUMBER"));
                bean.setWashType(rs.getString("WASHTYPE"));
                bean.setBookingDate(rs.getDate("BOOKING_DATE"));
                bean.setTimeSlot(rs.getString("TIMESLOT"));
                bean.setRemarks(rs.getString("REMARKS"));
                list.add(bean);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
