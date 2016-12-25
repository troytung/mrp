package com.petfood.mrp.web.manager.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petfood.mrp.dao.MaterialDao;
import com.petfood.mrp.model.Material;
import com.petfood.mrp.web.manager.MaterialManager;

@Service
public class MaterialManagerImpl implements MaterialManager {

    private static final Logger log = LoggerFactory.getLogger(MaterialManagerImpl.class);

    @Autowired
    private MaterialDao materialDao;

    @Override
    public List<Material> getMaterialsBySuppCode(String suppCode) {
        return materialDao.getMaterialsBySuppCode(suppCode);
    }

}
