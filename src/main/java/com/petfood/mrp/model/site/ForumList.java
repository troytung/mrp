package com.petfood.mrp.model.site;

import com.petfood.mrp.model.Brand;
import com.petfood.mrp.model.ForumInfo;

final class ForumList {

    private ForumList() {
    }

    static ForumInfo[] getPhoneHKFBM() {
        return new ForumInfo[] {
                new ForumInfo("260", Brand.HTC, "HTC (Hot!!)"),
                new ForumInfo("296", Brand.HTC, "**HTC One 系列 專區**"),
                new ForumInfo("302", Brand.HTC, "**HTC J / HTC J Butterfly 專區**"),
                new ForumInfo("303", Brand.HTC, "**HTC Desire X 專區**"),
                new ForumInfo("305", Brand.HTC, "**HTC Windows Phone 8X 專區**"),
                new ForumInfo("293", Brand.HTC, "HTC Explorer 專區"),
                new ForumInfo("286", Brand.HTC, "HTC ChaCha 專區"),
                new ForumInfo("285", Brand.HTC, "HTC EVO 3D 專區"),
                new ForumInfo("264", Brand.HTC, "HTC Desire 專區"),
                new ForumInfo("304", Brand.HTC, "HTC Desire C 專區"),
                new ForumInfo("265", Brand.HTC, "HTC Desire HD 專區"),
                new ForumInfo("266", Brand.HTC, "HTC Desire S 專區"),
                new ForumInfo("279", Brand.HTC, "HTC Flyer 專區"),
                new ForumInfo("263", Brand.HTC, "HTC Incredible S 專區"),
                new ForumInfo("288", Brand.HTC, "HTC Radar 專區"),
                new ForumInfo("287", Brand.HTC, "HTC Rhyme 專區"),
                new ForumInfo("284", Brand.HTC, "HTC Salsa 專區"),
                new ForumInfo("270", Brand.HTC, "HTC Sensation 專區"),
                new ForumInfo("291", Brand.HTC, "HTC Sensation XE 專區"),
                new ForumInfo("292", Brand.HTC, "HTC Sensation XL 專區"),
                new ForumInfo("294", Brand.HTC, "HTC Titan 專區"),
                new ForumInfo("295", Brand.HTC, "HTC Velocity 4G"),
                new ForumInfo("269", Brand.HTC, "HTC Wildfire S 專區"),
                new ForumInfo("267", Brand.HTC, "HTC 用家意見區 (其它熱門手機)"),
                new ForumInfo("15", Brand.SAMSUNG, "Samsung (Hot!!)"),
                new ForumInfo("313", Brand.SAMSUNG, "Samsung Apps 交流區"),
                new ForumInfo("315", Brand.SAMSUNG, "Samsung Galaxy S 系列專區"),
                new ForumInfo("301", Brand.SAMSUNG, "Samsung Galaxy Note 專區"),
                new ForumInfo("17", Brand.SONY, "Sony (Hot!!)"),
                new ForumInfo("203", Brand.SONY, "Sony 用家發表意見"),
                new ForumInfo("204", Brand.SONY, "Sony 買機討論區"),
                new ForumInfo("205", Brand.SONY, "Sony 報價區"),
                new ForumInfo("206", Brand.SONY, "Sony 隨身拍分享"),
                new ForumInfo("207", Brand.SONY, "Sony 投票區"),
                new ForumInfo("251", Brand.SONY, "Sony Xperia X10 專區"),
                new ForumInfo("36", Brand.LG, "LG (Hot!!)"),
                new ForumInfo("163", Brand.IPHONE, "Apple (Hot!!)")
        };
    }
    static ForumInfo[] getUwantsFBM() {
        return new ForumInfo[] {
                new ForumInfo("1520", null, "Android"),
                new ForumInfo("1206", Brand.IPHONE, "iPhone")
        };
    }
    static ForumInfo[] getDiscussFBM() {
        return new ForumInfo[] {
                new ForumInfo("1031", Brand.HTC, "HTC - Android 機種及技術討論區"),
                new ForumInfo("1032", Brand.SAMSUNG, "Samsung - Android 機種及技術討論區"),
                new ForumInfo("1061", Brand.SONY, "Sony - Android 機種及技術討論區"),
                new ForumInfo("1033", null, "Motorola, LG, Google - Android 機種及技術討論區"),
                new ForumInfo("605", Brand.IPHONE, "iPhone 技術,Q&A及綜合討論區")
        };
    }
    static ForumInfo[] getUnwireFBM() {
        return new ForumInfo[] {
                new ForumInfo("37", null, "討論區 > 流動產品 > 手提電話")
        };
    }
    static ForumInfo[] getEpriceFBM() {
        return new ForumInfo[] {
                new ForumInfo("4542", Brand.HTC, "手機館討論區 > HTC"),
                new ForumInfo("4523", Brand.SAMSUNG, "手機館討論區 > Samsung"),
                new ForumInfo("4551", Brand.SONY, "手機館討論區 > Sony"),
                new ForumInfo("4544", Brand.IPHONE, "手機館討論區 > Apple"),
                new ForumInfo("4531", Brand.LG, "手機館討論區 > LG")
        };
    }
    static ForumInfo[] getDcfeverFBM() {
        return new ForumInfo[] {
                new ForumInfo("18", null, "討論區 > 手機討論")
        };
    }
    
}
