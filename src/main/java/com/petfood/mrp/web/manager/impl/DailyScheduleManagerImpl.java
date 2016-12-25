package com.petfood.mrp.web.manager.impl;

import java.sql.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petfood.mrp.dao.DailyScheduleDao;
import com.petfood.mrp.model.DailySchedule;
import com.petfood.mrp.web.manager.DailyScheduleManager;

@Service
public class DailyScheduleManagerImpl implements DailyScheduleManager {

    private static final Logger log = LoggerFactory.getLogger(DailyScheduleManagerImpl.class);

    @Autowired
    private DailyScheduleDao dailyScheduleDao;

    @Override
    public void save(Date schedule_dt, List<DailySchedule> scheduleLst) {
        dailyScheduleDao.deleteByScheduleDt(schedule_dt);
        for (DailySchedule ds : scheduleLst) {
            dailyScheduleDao.insert(ds);
        }
    }

    @Override
    public List<DailySchedule> getByScheduleDt(Date schedule_dt) {
        return dailyScheduleDao.getByScheduleDt(schedule_dt);
    }

}
