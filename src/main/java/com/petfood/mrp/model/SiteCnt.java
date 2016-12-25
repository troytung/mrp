package com.petfood.mrp.model;

import com.petfood.mrp.model.site.Site;

public class SiteCnt implements Comparable<SiteCnt> {

    private Site site;
    private Long totalCnt;
    private Long mainCnt;
    private Long replyCnt;

    public SiteCnt(Site site, Long totalCnt, Long mainCnt, Long replyCnt) {
        this.site = site;
        this.totalCnt = totalCnt;
        this.mainCnt = mainCnt;
        this.replyCnt = replyCnt;
    }

    @Override
    public int compareTo(SiteCnt obj) {
        if (this.totalCnt > obj.totalCnt) {
            return 1;
        }
        else if (this.totalCnt < obj.totalCnt) {
            return -1;
        }
        else {
            return 0;
        }
    }

    public Site getSite() {
        return site;
    }

    public Long getMainCnt() {
        return mainCnt;
    }

    public Long getReplyCnt() {
        return replyCnt;
    }

    public Long getTotalCnt() {
        return totalCnt;
    }

}
