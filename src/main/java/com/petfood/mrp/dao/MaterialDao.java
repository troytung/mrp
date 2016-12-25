package com.petfood.mrp.dao;

import java.util.List;

import com.petfood.mrp.model.Material;

public interface MaterialDao {

    List<Material> getMaterialsBySuppCode(String suppCode);

}
