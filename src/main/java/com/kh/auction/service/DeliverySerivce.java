package com.kh.auction.service;


import com.kh.auction.domain.Delivery;
import com.kh.auction.repo.DeliveryDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeliverySerivce {


    @Autowired
    private DeliveryDAO deliveryDAO;

    public List<Delivery> showAll(){
        return deliveryDAO.findAll();
    }

    public Delivery show(int id){
        return deliveryDAO.findById(id).orElse(null);
    }

    public Delivery create(Delivery details){
        return deliveryDAO.save(details);
    }
    public Delivery update(Delivery delivery) {
        Delivery target = deliveryDAO.findById(delivery.getDeliveryNo()).orElse(null);
        if(target!=null) {
            return deliveryDAO.save(delivery);
        }
        return null;
    }

    public Delivery delete(int id){
        Delivery data = deliveryDAO.findById(id).orElse(null);
        deliveryDAO.delete(data);
        return data;
    }
}
