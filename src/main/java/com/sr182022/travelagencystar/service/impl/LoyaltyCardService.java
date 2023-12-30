package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.impl.DatabaseLoyaltyCardDAO;
import com.sr182022.travelagencystar.model.LoyaltyCard;
import com.sr182022.travelagencystar.service.ILoyaltyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoyaltyCardService implements ILoyaltyCardService {

    private final DatabaseLoyaltyCardDAO lcDao;

    @Autowired
    public LoyaltyCardService(DatabaseLoyaltyCardDAO lcDao) {
        this.lcDao = lcDao;
    }

    @Override
    public LoyaltyCard findOne(int id) {
        return lcDao.findOne(id);
    }

    @Override
    public void save(int points, int userId, boolean activated) {
        lcDao.save(points, userId, activated);
    }
}
