package com.sr182022.travelagencystar.service.impl;

import com.sr182022.travelagencystar.DAO.impl.DatabaseLoyaltyCardDAO;
import com.sr182022.travelagencystar.model.LoyaltyCard;
import com.sr182022.travelagencystar.service.ILoyaltyCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoyaltyCardService implements ILoyaltyCardService {

    private final DatabaseLoyaltyCardDAO lcDao;

    @Autowired
    public LoyaltyCardService(DatabaseLoyaltyCardDAO lcDao) {
        this.lcDao = lcDao;
    }

    @Override
    public List<LoyaltyCard> findAll() {
        return lcDao.findAll();
    }

    @Override
    public LoyaltyCard findOne(int id) {
        return lcDao.findOne(id);
    }

    @Override
    public int save(int points, int userId, boolean activated) {
        return lcDao.save(points, userId, activated);
    }

    @Override
    public int update(int loyaltyCardId, int points) {
        return lcDao.update(loyaltyCardId, points);
    }

    @Override
    public int delete(int loyaltyCardId) {
        return lcDao.delete(loyaltyCardId);
    }
}
