package com.petfood.mrp.model.site;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.petfood.mrp.model.ForumInfo;

public enum Site {

    PHONE_HK("PhoneHK", "http://board.phonehk.com/viewthread.php?tid={0}",
            "http://board.phonehk.com/viewthread.php?tid={0}&page={1}#pid{2}", 20, ForumList.getPhoneHKFBM()),
    UWANTS("Uwants", "http://mobile.uwants.com/viewthread.php?tid={0}",
            "http://mobile.uwants.com/viewthread.php?tid={0}&page={1}#pid{2}", 15, ForumList.getUwantsFBM()),
    DISCUSS("discuss", "http://mobile.discuss.com.hk/viewthread.php?tid={0}",
            "http://mobile.discuss.com.hk/viewthread.php?tid={0}&page={1}#pid{2}", 15, ForumList.getDiscussFBM()),
    UNWIRE("unwire", "http://forum.unwire.hk/forum.php?mod=viewthread&tid={0}",
            "http://forum.unwire.hk/forum.php?mod=viewthread&tid={0}&page={1}#pid{2}", 10, ForumList.getUnwireFBM()),
    EPRICE("ePrice", "http://www.eprice.com.hk/mobile/talk/{0}/{1}/1/1/0/",
            "http://www.eprice.com.hk/mobile/talk/{0}/{1}/{2}/1/0/", 10, ForumList.getEpriceFBM()),
    DCFEVER("DCFever", "http://www.dcfever.com/forum/read.php?f={0}&i={1}&t={2}",
            "http://www.dcfever.com/forum/read.php?f={0}&i={1}&t={2}&page={3}", 15, ForumList.getDcfeverFBM());

    private final String siteName;
    private final String topicUrlFormat;
    private final String postUrlFormat;
    private final int postPerPage;
    private final Map<String, ForumInfo> fim;

    private Site(String siteName, String topicUrlFormat, String postUrlFormat, int postPerPage, ForumInfo... fis) {
        this.siteName = siteName;
        this.topicUrlFormat = topicUrlFormat;
        this.postUrlFormat = postUrlFormat;
        this.postPerPage = postPerPage;
        Map<String, ForumInfo> m = new HashMap<>(fis.length);
        for (final ForumInfo fi : fis) {
            m.put(fi.getForumId(), fi);
        }
        this.fim = Collections.unmodifiableMap(m);
    }

    public Map<String, ForumInfo> getForumInfoMap() {
        return fim;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getTopicUrl(String forumId, String topicId) {
        if (this == Site.EPRICE) {
            return MessageFormat.format(topicUrlFormat, forumId, topicId);
        } else if (this == Site.DCFEVER) {
            return MessageFormat.format(topicUrlFormat, forumId, topicId, topicId);
        } else {
            return MessageFormat.format(topicUrlFormat, topicId);
        }
    }

    public String getPostUrl(String forumId, String topicId, Integer postNumber, String postId) {
        int p = postNumber / postPerPage;
        if (postNumber % postPerPage > 0) {
            ++p;
        }
        if (this == Site.EPRICE) {
            return MessageFormat.format(postUrlFormat, forumId, topicId, String.valueOf(p));
        } else if (this == Site.DCFEVER) {
            return MessageFormat.format(postUrlFormat, forumId, topicId, topicId, String.valueOf(p));
        } else {
            return MessageFormat.format(postUrlFormat, topicId, String.valueOf(p), postId);
        }
    }

}
