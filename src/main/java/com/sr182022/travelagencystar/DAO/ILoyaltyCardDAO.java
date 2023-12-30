package com.sr182022.travelagencystar.DAO;

import com.sr182022.travelagencystar.model.LoyaltyCard;

public interface ILoyaltyCardDAO {
    LoyaltyCard findOne(int id);
    int save(int points, int userId, boolean activated);
}
