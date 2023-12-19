package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.WishlistItem;

import java.util.List;

public interface IWishlistService {
    List<WishlistItem> findAll(int userId);
    void save(int userId, int travelId);
    void delete(int userId, int travelId);
}
