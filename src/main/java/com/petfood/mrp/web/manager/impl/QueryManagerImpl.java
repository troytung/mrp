
package com.petfood.mrp.web.manager.impl;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.petfood.mrp.dao.JdbcDao;
import com.petfood.mrp.model.PostDateCnt;
import com.petfood.mrp.model.XYData;
import com.petfood.mrp.model.site.Site;
import com.petfood.mrp.web.manager.QueryManager;

@Service
public class QueryManagerImpl implements QueryManager {

    private static final Logger log = LoggerFactory.getLogger(QueryManagerImpl.class);
    //private static final DateTimeFormatter ISO_DATETIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").withLocale(Locale.US);
    
    @Autowired
    private JdbcDao jdbcDao;
//    @Autowired
//    private SolrDao solrDao;
    
//    @Override
//    public PageContainer<Article> getArticles(final SolrQuery query, final DateTime beginDate, final DateTime endDate, final int page, final int rowsPerPage) {
//
//        log.debug("querying articles with query: {}, page: {}, rows: {}", query.getQuery(), page, rowsPerPage);
//        query.setStart((page - 1) * rowsPerPage);
//        query.setRows(rowsPerPage);
//        query.setFields("id", "positive", "negative");
//        addTimeQuiteria(query, beginDate, endDate);
//
//        final QueryResponse response = solrDao.query(query);
//
//        final SolrDocumentList list = response.getResults();
//
//        // final List<String> hashes = new ArrayList<>(list.size());
//        final LinkedHashMap<String, Article> amap = new LinkedHashMap<>(list.size());
//        Article a;
//        String hashId;
//        for (final SolrDocument doc : list) {
//
//            hashId = (String) doc.getFieldValue("id");
//            // hashes.add(hashId);
//            a = new Article();
//            a.setPositive(((Number) doc.getFieldValue("positive")).doubleValue());
//            a.setNegative(((Number) doc.getFieldValue("negative")).doubleValue());
//            amap.put(hashId, a);
//        }
//
//        final long numFound = list.getNumFound();
//        final List<Article> articles = jdbcDao.getArticles(amap);
//
//        final PageContainer<Article> pc = new PageContainer<>();
//        pc.setRows(articles);
//        pc.setPage(page);
//        pc.setTotalRows(numFound);
//        long tp = numFound / (long) rowsPerPage;
//        if (numFound % (long) rowsPerPage > 0) {
//            ++tp;
//        }
//        pc.setTotalPages(tp);
//        log.debug("return {} articles, with {} found", articles.size(), numFound);
//        return pc;
//
//    }
    
//    @Override
//    public List<XYData<Site, Long>> queryForSiteForReply(SolrQuery query, boolean isReply) {
//        return solrDao.queryForSiteForReply(query, isReply);
//    }
//    
//    @Override
//    public List<XYData<Site, Long>> queryForSiteForAll(SolrQuery query) {
//        return solrDao.queryForSiteForAll(query);
//    }
//
//    @Override
//    public List<XYData<DateTime, Long>> queryForChart(final SolrQuery query, final DateTime beginDate, DateTime endDate) {
//        
//        addTimeQuiteria(query, beginDate, endDate);
//        List<XYData<DateTime, Long>> data = solrDao.queryForChart(query);
//        //endDate = endDate.minusDays(1);
//        data = filter(data, beginDate, endDate);
//        Collections.sort(data, new XYComparator());
//        XYData<DateTime, Long> curData;
//        DateTime nextDate;
//        
//        final List<XYData<DateTime, Long>> finalData = new ArrayList<>(data.size());
//        final int size = data.size();
//        for(int i = 0; i < size; i++) {
//            
//            curData = data.get(i);
//            nextDate = i + 1 < size ? data.get(i + 1).getX() : null;
//            fillDates(finalData, curData, nextDate);
//            
//        }
//        return finalData;
//    }
    
//    private List<XYData<DateTime, Long>> filter(List<XYData<DateTime, Long>> data, DateTime beginDate, DateTime endDate) {
//        
//        final List<XYData<DateTime, Long>> filtered = new ArrayList<>(data.size());
//        boolean containsBegin = false;
//        boolean containsEnd = false;
//        DateTime dt;
//        for(final XYData<DateTime, Long> dd : data) {
//            dt = dd.getX();
//            if(dt.isAfter(beginDate) && dt.isBefore(endDate)) {
//                filtered.add(dd);
//            }
//            else if(dt.isEqual(beginDate)) {
//                filtered.add(dd);
//                containsBegin = true;
//            }
//            else if(dt.isEqual(endDate)) {
//                filtered.add(dd);
//                containsEnd = true;
//            }
//        }
//        
//        if(!containsBegin) {
//            filtered.add(new XYData<DateTime, Long>(beginDate, NumberUtils.LONG_ZERO));
//        }
//        
//        if(!containsEnd) {
//            filtered.add(new XYData<DateTime, Long>(endDate, NumberUtils.LONG_ZERO));
//        }
//        return filtered;
//    }
    
//    private void fillDates(List<XYData<DateTime, Long>> data, XYData<DateTime, Long> currentData, DateTime toDate) {
//        
//        data.add(currentData);
//        if(toDate != null) {
//        
//            DateTime cd = currentData.getX().plusDays(1);
//            while (cd.isBefore(toDate)) {
//                
//                data.add(new XYData<DateTime, Long>(cd, NumberUtils.LONG_ZERO));
//                cd = cd.plusDays(1);
//            }
//        }
//        
//    }
    
//    protected void addTimeQuiteria(SolrQuery query, DateTime beginDate, DateTime endDate) {
//
//        if(beginDate != null && endDate != null) {
//            //final String fqFromTo = beginDate.toString(ISO_DATETIME_FORMAT) + " TO " + endDate.toString(ISO_DATETIME_FORMAT);
//        
//            final String fqFromTo = beginDate.getMillis() + " TO " + endDate.getMillis();
//            query.addFilterQuery("post_date_long:[" + fqFromTo + "]");
//        }
//        
//    }
    
    private class XYComparator implements Comparator<XYData<DateTime, Long>> {

        @Override
        public int compare(XYData<DateTime, Long> o1, XYData<DateTime, Long> o2) {
            return o1.getX().compareTo(o2.getX());
        }

        
    }

    @Override
    public Map<DateTime, Map<Site, Integer>> getPostCnt(DateTime beginDate, DateTime endDate) {
        
        final List<PostDateCnt> postDateCnts = jdbcDao.getPostCnt(beginDate, endDate);
        TreeMap<DateTime, Map<Site, Integer>> m = new TreeMap<DateTime, Map<Site, Integer>>();
        for (PostDateCnt p : postDateCnts) {
//            if (!m.containsKey(p.getPost_date())) {
//                Map<Site, Integer> tmpMap = new HashMap<Site, Integer>();
//                tmpMap.put(p.getSite(), p.getPosts());
//                m.put(p.getPost_date(), tmpMap);
//            } else {
//                Map<Site, Integer> tmpMap = m.get(p.getPost_date());
//                tmpMap.put(p.getSite(), p.getPosts());
//            }
            
            Map<Site, Integer> tmpMap = m.get(p.getPost_date());
            if (tmpMap == null) {
                tmpMap = new HashMap<Site, Integer>();
            }
            tmpMap.put(p.getSite(), p.getPosts());
            m.put(p.getPost_date(), tmpMap);
        }
        return m.descendingMap();
    }

//    @Override
//    public SolrQuery formatQuery(Category category, String inputQuery, LogicOp defaultOp, String excludeQuery) {
//        
//        
//        return null;
//    }

}
