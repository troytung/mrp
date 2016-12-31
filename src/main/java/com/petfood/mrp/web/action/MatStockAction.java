package com.petfood.mrp.web.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.google.gson.Gson;
import com.petfood.mrp.model.Customer;
import com.petfood.mrp.model.Material;
import com.petfood.mrp.model.ProMaterial;
import com.petfood.mrp.model.Product;
import com.petfood.mrp.model.Supplier;
import com.petfood.mrp.web.manager.CustomerManager;
import com.petfood.mrp.web.manager.MaterialManager;
import com.petfood.mrp.web.manager.ProductManager;
import com.petfood.mrp.web.manager.SupplierManager;

@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@Controller("web.matStockAction")
public class MatStockAction extends AbstractAction {

    @Autowired
    private CustomerManager customerManager;

    @Autowired
    private MaterialManager materialManager;

    @Autowired
    private ProductManager productManager;

    @Autowired
    private SupplierManager supplierManager;

    private String suppCode;
    private List<Customer> customers;
    private List<Supplier> suppliers;
    private List<Material> materials;
    private Product product;
    private List<ProMaterial> proMats;
    private String pro_code;
    private String proMatsJson;
    private boolean viewOnly = false;
    private boolean forModify = false;

    @Override
    public String execute() {
        customers = customerManager.getAllCustomers();
        suppliers = supplierManager.getAllSuppliers();
        return SUCCESS;
    }

    public String ajaxMaterials() {
        materials = materialManager.getMaterialsBySuppCode(suppCode);
        return "json";
    }

    public String save() {
        product.setCreate_by(getLogin().getUserCode());
        productManager.insert(product, proMats);
        pro_code = product.getPro_code();
        return view();
    }

    private void preparePro() {
        product = productManager.getProductByProCode(pro_code);
        proMatsJson = new Gson().toJson(productManager.getMatsByProCode(pro_code));
    }

    public String view() {
        viewOnly = true;
        preparePro();
        return SUCCESS;
    }

    public String modify() {
        forModify = true;
        preparePro();
        suppliers = supplierManager.getAllSuppliers();
        return SUCCESS;
    }

    public String getSuppCode() {
        return suppCode;
    }

    public void setSuppCode(String suppCode) {
        this.suppCode = suppCode;
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public List<Material> getMaterials() {
        return materials;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public List<Supplier> getSuppliers() {
        return suppliers;
    }

    public List<ProMaterial> getProMats() {
        return proMats;
    }

    public void setProMats(List<ProMaterial> proMats) {
        this.proMats = proMats;
    }

    public String getPro_code() {
        return pro_code;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public boolean isViewOnly() {
        return viewOnly;
    }

    public boolean isForModify() {
        return forModify;
    }

    public String getProMatsJson() {
        return proMatsJson;
    }

}
