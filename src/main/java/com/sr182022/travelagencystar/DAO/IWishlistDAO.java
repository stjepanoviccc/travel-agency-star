package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.WishlistItem;

import java.util.List;

public interface IWishlistDAO {
    List<WishlistItem> findAll(int userId);
    void save(int userId, int travelId);
    void delete(int userId, int travelId);
}
