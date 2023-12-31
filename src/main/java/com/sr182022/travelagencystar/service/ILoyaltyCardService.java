package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.LoyaltyCard;

import java.util.List;

public interface ILoyaltyCardService {
    List<LoyaltyCard> findAll();
    LoyaltyCard findOne(int id);
    int save(int points, int userId, boolean activated);
    int update(int loyaltyCardId, int points);
    int delete(int loyaltyCardId);
}