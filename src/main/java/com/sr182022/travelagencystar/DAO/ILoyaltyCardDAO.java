package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.LoyaltyCard;

import java.util.List;

public interface ILoyaltyCardDAO {
    List<LoyaltyCard> findAll();
    LoyaltyCard findOne(int id);
    int save(int points, int userId, boolean activated);
    int update(int loyaltyCardId, int points);
    int delete(int loyaltyCardId);
    void deleteJunction(int loyaltyCardId);
    int takePointsFromJunction(int loyaltyCardId);
}
