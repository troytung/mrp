package com.petfood.mrp.web.manager;

import java.sql.Date;
import java.util.List;

import com.petfood.mrp.model.DailySchedule;

public interface DailyScheduleManager {

    void save(Date schedule_dt, List<DailySchedule> scheduleLst);

    List<DailySchedule> getByScheduleDt(Date schedule_dt);

}
