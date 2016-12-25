package com.petfood.mrp.web.manager;

import java.util.Map;

import org.joda.time.DateTime;

import com.petfood.mrp.model.site.Site;

public interface QueryManager {

//    List<XYData<DateTime, Long>> queryForChart(SolrQuery query, DateTime beginDate, DateTime endDate);
//
//    PageContainer<Article> getArticles(SolrQuery query, DateTime beginDate, DateTime endDate, int page, int rowsPerPage);

    Map<DateTime, Map<Site, Integer>> getPostCnt(DateTime beginDate, DateTime endDate);

//    SolrQuery formatQuery(Category category, String inputQuery, LogicOp defaultOp, String excludeQuery);
//    
//    List<XYData<Site, Long>> queryForSiteForAll(SolrQuery query);
//    
//    List<XYData<Site, Long>> queryForSiteForReply(SolrQuery query, boolean isReply);
}
