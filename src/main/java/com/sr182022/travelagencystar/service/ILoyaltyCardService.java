package com.sr182022.travelagencystar.service;

import com.sr182022.travelagencystar.model.LoyaltyCard;

public interface ILoyaltyCardService {
    LoyaltyCard findOne(int id);
    int save(int points, int userId, boolean activated);
}
