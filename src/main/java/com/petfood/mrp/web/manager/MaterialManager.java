package com.petfood.mrp.web.manager;

import java.util.List;

import com.petfood.mrp.model.Material;

public interface MaterialManager {

    List<Material> getMaterialsBySuppCode(String suppCode);

}
