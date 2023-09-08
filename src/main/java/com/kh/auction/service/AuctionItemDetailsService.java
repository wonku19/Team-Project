package com.kh.auction.service;


import com.kh.auction.domain.AuctionItemDetails;
import com.kh.auction.repo.DetailsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuctionItemDetailsService {


    @Autowired
    private DetailsDAO detailsDAO;

    public List<AuctionItemDetails> showAll(){
        return detailsDAO.findAll();
    }

    public AuctionItemDetails show(int id){
        return detailsDAO.findById(id).orElse(null);
    }

    public AuctionItemDetails create(AuctionItemDetails details){
        return detailsDAO.save(details);
    }

    public AuctionItemDetails update(AuctionItemDetails auctionItemDetails) {
        AuctionItemDetails target = detailsDAO.findById(auctionItemDetails.getItemNo()).orElse(null);
        if(target!=null) {
            return detailsDAO.save(auctionItemDetails);
        }
        return null;
    }

    public AuctionItemDetails delete(int id){
        AuctionItemDetails data = detailsDAO.findById(id).orElse(null);
        detailsDAO.delete(data);
        return data;
    }
}
