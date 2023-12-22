package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.model.WishlistItem;
import com.sr182022.travelagencystar.service.IWishlistService;
import com.sr182022.travelagencystar.DAO.IWishlistDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WishlistService implements IWishlistService {

    private final IWishlistDAO wishlistDAO;

    public WishlistService(IWishlistDAO wishlistDAO) {
        this.wishlistDAO = wishlistDAO;
    }

    @Override
    public List<WishlistItem> findAll(int userId) {
        return wishlistDAO.findAll(userId);
    }

    @Override
    public void save(int userId, int travelId) {
        wishlistDAO.save(userId, travelId);
    }

    @Override
    public void delete(int userId, int travelId) {
        wishlistDAO.delete(userId, travelId);
    }

    @Override
    public boolean checkExistence(int userId, int travelId) {
        List<WishlistItem> wItems = findAll(userId);
        if(wItems == null) {
            return false;
        }
        for(WishlistItem item : wItems) {
           if(item.getUser().getId() == userId && item.getTravel().getId() == travelId) {
               return true;
           }
        }
        // if doesnt exist, return false.
        return false;
    }
}
