package com.petfood.mrp.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.petfood.mrp.dao.JdbcDao;
import com.petfood.mrp.model.Article;
import com.petfood.mrp.model.Post;
import com.petfood.mrp.model.PostDateCnt;
import com.petfood.mrp.model.Role;
import com.petfood.mrp.model.Topic;
import com.petfood.mrp.model.User;
import com.petfood.mrp.model.site.Site;

@Repository
@Profile("!test")
public class JdbcDaoImpl extends JdbcDaoSupport implements JdbcDao {

    private static final Logger log = LoggerFactory.getLogger(JdbcDaoImpl.class);
    private final SimpleJdbcInsert topicInsert;
    private final SimpleJdbcInsert postInsert;
    private final SimpleJdbcInsert userInsert;
    private final UserRowMapper urm;
    private final ResultSetExtractor<User> userRse;

    @Autowired
    public JdbcDaoImpl(DataSource dataSource) {
        setDataSource(dataSource);
        topicInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("TOPIC");
        postInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("POST");
        userInsert = new SimpleJdbcInsert(getJdbcTemplate()).withTableName("USER");
        urm = new UserRowMapper();
        userRse = new ResultSetExtractor<User>() {
            @Override
            public User extractData(ResultSet rst) throws SQLException, DataAccessException {
                if (rst.next()) {
                    return urm.mapRow(rst, 1);
                }
                else {
                    return null;
                }
            }
        };
    }

    @Override
    public void savePosts(final List<Post> posts) {

        int cnt;
        Map<String, Object> m;
        Date postDate;
        for (final Post p : posts) {
            try {
                Post.setHashId(p);
                postDate = new Date(p.getPostDate().getMillis());
                cnt = getJdbcTemplate().update(
                        "UPDATE POST SET SITE=?, TOPIC_ID=?, POST_ID=?, REPLY=?, POST_NUMBER=?, TEXT=?, POSTER=?, POSTER_ID=?, POST_DATE=?, POST_DATE_LONG=? WHERE HASH_ID=?",
                        p.getSite().toString(), p.getTopicId(), p.getPostId(), p.isReply(), p.getPostNumber(), p.getText(), p.getPoster(),
                        p.getPosterId(), postDate, p.getPostDateLong(), p.getHashId());

                if (cnt == 0) {
                    // getJdbcTemplate()
                    // .update("INSERT INTO POST(SITE, TOPIC_ID, POST_ID, REPLY, POST_NUMBER, TEXT, POSTER, POSTER_ID, POST_DATE) VALUES(?, ?, ?, ?,
                    // ?, ?, ?, ?, ?)",
                    // p.getSite(), p.getTopicId(), p.getPostId(), p.isReply(),
                    // p.getPostNumber(),
                    // p.getText(), p.getPoster(), p.getPosterId(),
                    // p.getPostDate());
                    m = new HashMap<>();
                    m.put("HASH_ID", p.getHashId());
                    m.put("SITE", p.getSite().toString());
                    m.put("TOPIC_ID", p.getTopicId());
                    m.put("POST_ID", p.getPostId());
                    m.put("REPLY", p.isReply());
                    m.put("POST_NUMBER", p.getPostNumber());
                    m.put("TEXT", p.getText());
                    m.put("POSTER", p.getPoster());
                    m.put("POSTER_ID", p.getPosterId());
                    m.put("POST_DATE", postDate);
                    m.put("POST_DATE_LONG", p.getPostDateLong());
                    postInsert.execute(m);
                }
            }
            catch (DataAccessException e) {
                log.warn("error inserting post: " + p.toString(), e);
            }
        }
    }

    @Override
    public void saveTopic(final Topic t) {

        String brand = t.getBrand() == null ? null : t.getBrand().toString();
        Date modifyDate = new Date(t.getModifyDate().getMillis());
        int rst = getJdbcTemplate().update(
                "UPDATE TOPIC SET BRAND=?, MODEL=?, TOPIC=?, LASTEST_POSTER=?, REPLY_CNT=?, VIEW_CNT=?, MODIFY_DT=? WHERE SITE=? AND FORUM_ID=? AND TOPIC_ID=?",
                brand, t.getModel(), t.getTopic(), t.getLastestPoster(), t.getReplyCnt(), t.getViewCnt(), modifyDate, t.getSite().toString(),
                t.getForumId(), t.getTopicId());

        if (rst == 0) {
            // getJdbcTemplate()
            // .update("INSERT INTO TOPIC(SITE, BRAND, MODEL, TOPIC, TOPIC_ID, LASTEST_POSTER, REPLY_CNT, VIEW_CNT, MODIFY_DT) VALUES(?, ?, ?, ?, ?,
            // ?, ?, ?, ?)",
            // t.getSite(), brand, t.getModel(), t.getTopic(), t.getTopicId(),
            // t.getLastestPoster(), t.getReplyCnt(), t.getViewCnt(),
            // t.getModifyDate());
            Map<String, Object> m = new HashMap<String, Object>();
            m.put("SITE", t.getSite().toString());
            m.put("FORUM_ID", t.getForumId());
            m.put("BRAND", t.getBrand());
            m.put("MODEL", t.getModel());
            m.put("TOPIC", t.getTopic());
            m.put("TOPIC_ID", t.getTopicId());
            m.put("LASTEST_POSTER", t.getLastestPoster());
            m.put("REPLY_CNT", t.getReplyCnt());
            m.put("VIEW_CNT", t.getViewCnt());
            m.put("MODIFY_DT", modifyDate);
            topicInsert.execute(m);
        }
    }

    @Override
    public List<Post> getPosts(List<String> hashIds) {

        if (hashIds == null || hashIds.isEmpty()) {
            return null;
        }
        String sql = "SELECT * FROM POST WHERE HASH_ID IN (?{0})";
        return getJdbcTemplate().query(formatSql(sql, hashIds), hashIds.toArray(), new BeanPropertyRowMapper<Post>(Post.class));
    }

    @Override
    public List<Article> getArticles(List<String> hashIds) {

        if (hashIds == null || hashIds.isEmpty()) {
            return new ArrayList<>(0);
        }

        RowMapper<Article> rm = new RowMapper<Article>() {

            @Override
            public Article mapRow(ResultSet rst, int arg1) throws SQLException {

                Article a = new Article();
                a.setHashId(rst.getString("hash_id"));
                a.setTopic(rst.getString("topic"));
                a.setReply(rst.getBoolean("reply"));
                a.setPostNumber(rst.getInt("post_number"));
                a.setText(rst.getString("text"));
                a.setReplyCnt(rst.getInt("reply_cnt"));
                a.setPoster(rst.getString("poster"));
                Timestamp ts = rst.getTimestamp("post_date");
                if (ts != null) {
                    a.setPostDate(new DateTime(ts.getTime()));
                }
                a.setViewCnt(rst.getInt("view_cnt"));
                a.setSite(Site.valueOf(rst.getString("site")));
                a.setForumId(rst.getString("forum_id"));
                a.setTopicId(rst.getString("topic_id"));
                a.setPostId(rst.getString("post_id"));
                return a;
            }

        };
        String sql = "SELECT A.HASH_ID, B.TOPIC, A.REPLY, A.POST_NUMBER, A.TEXT, B.REPLY_CNT, A.POSTER, A.POST_DATE, B.SITE, B.FORUM_ID, B.VIEW_CNT, B.TOPIC_ID, A.POST_ID "
                + "FROM POST A INNER JOIN TOPIC B ON A.SITE=B.SITE AND A.TOPIC_ID=B.TOPIC_ID WHERE A.HASH_ID IN (?{0})";
        List<Article> tempArticles = getJdbcTemplate().query(formatSql(sql, hashIds), hashIds.toArray(), rm);
        List<Article> sortedArticles = new ArrayList<Article>(tempArticles.size());
        for (String hashId : hashIds) {
            for (Article a : tempArticles) {
                if (hashId.equals(a.getHashId())) {
                    sortedArticles.add(a);
                    break;
                }
            }
        }
        return sortedArticles;
    }

    private String formatSql(String sql, Collection<String> hashIds) {

        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < hashIds.size(); i++) {
            sb.append(", ?");
        }
        return MessageFormat.format(sql, sb.toString());
    }

    @Override
    public DateTime getMaxModifyDate(Site site, String forumId) {

        String sql = "SELECT MAX(MODIFY_DT) FROM TOPIC WHERE SITE=? AND FORUM_ID=?";
        Date maxModifyDate = getJdbcTemplate().queryForObject(sql, Date.class, site.toString(), forumId);
        if (maxModifyDate == null) {
            return null;
        }
        else {
            return new DateTime(maxModifyDate.getTime());
        }
    }

    @Override
    public Integer getMaxPostNumber(Site site, String topicId) {

        String sql = "SELECT MAX(POST_NUMBER) FROM POST WHERE SITE=? AND TOPIC_ID=?";
        Integer maxPostNumber = getJdbcTemplate().queryForObject(sql, Integer.class, site.toString(), topicId);
        return maxPostNumber;
    }

    @Override
    public List<Article> getArticles(final LinkedHashMap<String, Article> hashIdMap) {

        if (hashIdMap == null || hashIdMap.isEmpty()) {
            return new ArrayList<>(0);
        }

        RowMapper<Article> rm = new RowMapper<Article>() {

            @Override
            public Article mapRow(ResultSet rst, int arg1) throws SQLException {

                final String hash = rst.getString("hash_id");
                Article a = hashIdMap.get(hash);
                if (a == null) {
                    log.warn("hash id : {} is not found in original map, record is null", hash);
                }
                else {
                    a.setHashId(hash);
                    a.setTopic(rst.getString("topic"));
                    a.setReply(rst.getBoolean("reply"));
                    a.setPostNumber(rst.getInt("post_number"));
                    a.setText(rst.getString("text"));
                    a.setReplyCnt(rst.getInt("reply_cnt"));
                    a.setPoster(rst.getString("poster"));
                    Timestamp ts = rst.getTimestamp("post_date");
                    if (ts != null) {
                        a.setPostDate(new DateTime(ts.getTime()));
                    }
                    a.setViewCnt(rst.getInt("view_cnt"));
                    a.setSite(Site.valueOf(rst.getString("site")));
                    a.setForumId(rst.getString("forum_id"));
                    a.setTopicId(rst.getString("topic_id"));
                    a.setPostId(rst.getString("post_id"));
                }
                return a;
            }

        };

        final Set<String> hashIds = hashIdMap.keySet();
        final String sql = "SELECT P.HASH_ID, T.TOPIC, P.REPLY, P.POST_NUMBER, P.TEXT, T.REPLY_CNT, P.POSTER, P.POST_DATE, T.SITE, T.FORUM_ID, T.VIEW_CNT, "
                + "T.TOPIC_ID, P.POST_ID "
                + "FROM POST P INNER JOIN TOPIC T ON P.SITE = T.SITE AND P.TOPIC_ID = T.TOPIC_ID WHERE P.HASH_ID IN (?{0})";
        final List<Article> tempArticles = getJdbcTemplate().query(formatSql(sql, hashIds), hashIds.toArray(new String[hashIds.size()]), rm);
        final List<Article> sortedArticles = new ArrayList<Article>(tempArticles.size());
        for (final Map.Entry<String, Article> entry : hashIdMap.entrySet()) {
            sortedArticles.add(entry.getValue());
        }
        return sortedArticles;
    }

    @Override
    public List<PostDateCnt> getPostCnt(DateTime beginDate, DateTime endDate) {

        RowMapper<PostDateCnt> rm = new RowMapper<PostDateCnt>() {
            @Override
            public PostDateCnt mapRow(ResultSet rst, int arg1) throws SQLException {
                PostDateCnt p = new PostDateCnt();
                p.setSite(Site.valueOf(rst.getString("site")));
                p.setPost_date(new DateTime(rst.getLong("post_date_long")));
                p.setPosts(rst.getInt("posts"));
                return p;
            }
        };

        // final String sql =
        // "SELECT SITE, POST_DATE_LONG, COUNT(1) POSTS FROM POST WHERE POST_DATE_LONG >= ? AND POST_DATE_LONG <= ? GROUP BY SITE, POST_DATE_LONG
        // ORDER BY POST_DATE_LONG";
        final String sql = "SELECT SITE, POST_DATE_LONG, COUNT(1) POSTS FROM POST WHERE POST_DATE_LONG >= ? AND POST_DATE_LONG <= ? GROUP BY SITE, POST_DATE_LONG";
        final List<PostDateCnt> cnts = getJdbcTemplate().query(sql, new Object[] {beginDate.getMillis(), endDate.getMillis()}, rm);
        return cnts;
    }

    @Override
    public void insertUser(User user) {

        // Map<String, Object> m = new HashMap<String, Object>();
        // m.put("USER_CODE", user.getUserCode());
        // m.put("USER_NAME", user.getUserName());
        // m.put("EMAIL", user.getEmail());
        // m.put("SALT", user.getSalt());
        // m.put("PASSWORD", user.getPassword());
        // m.put("ADMIN", user.isAdmin());
        // m.put("ACTIVE", user.isActive());
        // m.put("CREATE_DATE", new Date());
        // m.put("RECEIVE_EMAIL", user.isReceiveEmail());
        // // m.put("MODIFY_DT", user.getModifyDate());
        // userInsert.execute(m);

        List<Object> params = new ArrayList<Object>();
        params.add(user.getUserCode());
        params.add(user.getUserName());
        params.add(user.getRoleCode());
        params.add(user.getEmail());
        params.add(user.getSalt());
        params.add(user.getPassword());
        params.add(user.isAdmin());
        params.add(user.isActive());
        params.add("admin");
        params.add(new Date());
        String sql = "INSERT INTO \"USER\" (USER_CODE, USER_NAME, ROLE_CODE, EMAIL, SALT, PASSWORD, ADMIN, ACTIVE, CREATE_BY, CREATE_DT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        getJdbcTemplate().update(sql, params.toArray());
    }

    private class UserRowMapper implements RowMapper<User> {

        @Override
        public User mapRow(ResultSet rst, int row) throws SQLException {
            final User u = new User();
            u.setUserCode(rst.getString("USER_CODE"));
            u.setUserName(rst.getString("USER_NAME"));
            String roleCode = rst.getString("ROLE_CODE");
            u.setRoleCode(roleCode);
            u.setRole(Role.valueOf(roleCode));
            u.setEmail(rst.getString("EMAIL"));
            u.setSalt(rst.getString("SALT"));
            u.setPassword(rst.getString("PASSWORD"));
            u.setAdmin(rst.getBoolean("ADMIN"));
            u.setActive(rst.getBoolean("ACTIVE"));
            u.setCreateBy(rst.getString("CREATE_BY"));
            u.setCreateDt(new DateTime(rst.getDate("CREATE_DT").getTime()));
            Date modifyDate = rst.getDate("MODIFY_DT");
            if (modifyDate != null) {
                u.setModifyDt(new DateTime(modifyDate.getTime()));
            }
            // u.setReceiveEmail(rst.getBoolean("RECEIVE_EMAIL"));
            return u;
        }
    }

    @Override
    public User getUser(String userId) {
        return getJdbcTemplate().query("SELECT * FROM \"USER\" WHERE USER_CODE = ? ", userRse, userId);
    }

    @Override
    public List<User> getAllUsers() {
        return getJdbcTemplate().query("SELECT * FROM \"USER\" ORDER BY USER_CODE", urm);
    }

    @Override
    public void updateUser(User u) {
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE \"USER\" SET USER_NAME = ?, EMAIL = ?, ADMIN = ?, ACTIVE = ?, MODIFY_DT = ? ");
        List<Object> params = new ArrayList<Object>(9);
        params.add(u.getUserName());
        params.add(u.getEmail());
        params.add(u.isAdmin());
        params.add(u.isActive());
        params.add(new Date());
        if (StringUtils.isNotEmpty(u.getPassword())) {
            sql.append(", SALT = ?, PASSWORD = ? ");
            params.add(u.getSalt());
            params.add(u.getPassword());
        }
        sql.append("WHERE USER_CODE = ? ");
        params.add(u.getUserCode());
        getJdbcTemplate().update(sql.toString(), params.toArray());
    }

    // @Override
    // public List<User> findByReceiveEmail(boolean receiveEmail) {
    // return getJdbcTemplate().query("SELECT * FROM \"USER\" WHERE RECEIVE_EMAIL = ? ", new Object[] { receiveEmail },
    // urm);
    // }

}
