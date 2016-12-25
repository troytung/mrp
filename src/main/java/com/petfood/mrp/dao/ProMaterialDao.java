package com.petfood.mrp.dao;

import java.util.List;

import com.petfood.mrp.model.ProMaterial;

public interface ProMaterialDao {

    void deleteByProCode(String pro_code);

    int insert(ProMaterial proMat);

    List<ProMaterial> getMatsByProCode(String pro_code);

}
