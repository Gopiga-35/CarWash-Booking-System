package com.wipro.carwash.service;

import java.util.Date;
import java.util.List;

import com.wipro.carwash.bean.CarWashBookingBean;
import com.wipro.carwash.dao.CarWashBookingDAO;
import com.wipro.carwash.util.InvalidInputException;

public class Administrator {

    private CarWashBookingDAO dao = new CarWashBookingDAO();

    public String addRecord(CarWashBookingBean bean) throws InvalidInputException {

        
    
        if (bean == null || bean.getCustomerName() == null || bean.getBookingDate() == null) {
            throw new InvalidInputException();
        }

        String vehicleNumber = bean.getVehicleNumber();
        if (vehicleNumber == null || vehicleNumber.trim().isEmpty() || vehicleNumber.trim().length() < 5) {
            return "INVALID VEHICLE NUMBER";
        }
        vehicleNumber = vehicleNumber.trim().toUpperCase();
        bean.setVehicleNumber(vehicleNumber);

        
        if (bean.getWashType() == null || bean.getWashType().trim().isEmpty()) {
            return "INVALID WASH TYPE";
        }

        
        if (dao.recordExists(vehicleNumber, bean.getBookingDate())) {
            return "ALREADY EXISTS";
            
        }

        String recordId = dao.generateRecordID(vehicleNumber, bean.getBookingDate());
        if (recordId == null) {
            return "FAIL";
        }
        bean.setRecordId(recordId);

        
        String result = dao.createRecord(bean);
       
        if (result != null) {
            return "SUCCESS";
        } else {
            return "ERROR";
        }
    }

    public CarWashBookingBean viewRecord(String vehicleNumber, Date bookingDate) {
        if (vehicleNumber == null || bookingDate == null) {
            return null;
        }
        return dao.fetchRecord(vehicleNumber.trim().toUpperCase(), bookingDate);
    }

    public List<CarWashBookingBean> viewAllRecords() {
        return dao.fetchAllRecords();
    }
}
