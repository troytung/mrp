package com.petfood.mrp.dao;

import java.sql.Date;
import java.util.List;

import com.petfood.mrp.model.DailySchedule;

public interface DailyScheduleDao {

    int insert(DailySchedule ds);

    int deleteByScheduleDt(Date schedule_dt);

    List<DailySchedule> getByScheduleDt(Date schedule_dt);

}
