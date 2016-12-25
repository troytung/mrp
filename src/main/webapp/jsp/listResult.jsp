<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="show">
    <section id="accordion" class="pageContent">
    <div class="topicBg">
    <h6 class="item listData">
    <article class="totleNum">
                  共<s:property value="articles.totalRows"/>筆 
     <select name="page" id="pageSel">
        <s:iterator begin="1" end="articles.totalPages">
          <option value="<s:property/>" <s:if test="top == page">selected</s:if>><s:property /></option>
        </s:iterator>
     </select>
    </article>
    搜尋結果</h6>
    </div>
    <div class="searchListBox">
    <s:iterator value="articles">
      <article class="docuBox">
            <div class="moregood">
                <span class="upNum"><s:property value="formatNumber(positive)"/></span>
                <span class="downNum"><s:property value="formatNumber(negative)"/></span>
            </div>
            <h3 class="ui-accordion-header ui-helper-reset ui-state-default ui-accordion-icons ui-accordion-header-active ui-state-active ui-corner-top"><a href="<s:property value="getTopicUrl(top)"/>" target="_blank"><s:property value="topic"/></a>
                <u><s:property value="%{reply? '回文' : '主文'}"/> <a href="<s:property value="getPostUrl(top)"/>" target="_blank">#<s:property value="postNumber"/></a></u>
            </h3>
            <h6><s:property value="getSiteForumName(top)"/></h6>
            <div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active">
                <article></article>
                <p class="text-content"><s:property value="text" escapeHtml="false"/></p>
                <ul>
                    <li>回文數：<s:property value="replyCnt"/></li>
                    <li><s:property value="poster"/></li>
                    <li><s:property value="formatDateTime(postDate)"/></li>
                    <li>點閱數：<s:property value="viewCnt"/></li>
                </ul>
            </div>
      </article>
      </s:iterator>
        <%--
        <article class="docuBox">
            <div class="morebad">
                <span class="upNum">0.99%</span>
                <span class="downNum">0.19%</span>
            </div>
            <h3 class="ui-accordion-header ui-helper-reset ui-state-default ui-accordion-icons ui-accordion-header-active ui-state-active ui-corner-top">
                <a href="http://mobile.discuss.com.hk/viewthread.php?tid=22425112" target="_blank">立志等iphone7屍</a>
                <u>回文<a href="http://mobile.discuss.com.hk/viewthread.php?tid=22425112&page=1#pid371448642" target="_blank">#14</a>
                </u>
            </h3>
            <h6>discuss &gt; iPhone 技術,Q&amp;A及綜合討論區</h6>
            <div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active">
                <article></article>
                <p>我自己 Apple，Sony，HTC，Samsung手機都用過，自問對Samsung最冇感情同brand loyalty。慶幸今年Sony同HTC都有突破，俾返多啲選擇我！</p>
                <ul>
                    <li>回文數：20</li>
                    <li>lafy</li>
                    <li>2013-09-12 00:13</li>
                    <li>點閱數：497</li>
                </ul>
            </div>
      </article>
        
    
        <article class="docuBox">
      <div class="moregood">
                <span class="upNum">0.99%</span>
                <span class="downNum">0.19%</span>
            </div>    
          <h3 class="ui-accordion-header ui-helper-reset ui-state-default ui-accordion-icons ui-accordion-header-active ui-state-active ui-corner-top">
              <a href="http://mobile.discuss.com.hk/viewthread.php?tid=22422941" target="_blank">Recover Deleted Messages, Contacts, Photos &amp; Video on Samsung Galaxy Ace</a>
              <u>主文 <a href="http://mobile.discuss.com.hk/viewthread.php?tid=22422941&page=1#pid371401723" target="_blank">#1</a></u>
            </h3>
            <h6>discuss &gt; Samsung - Android 機種及技術討論區</h6>
          <div class="ui-accordion-content ui-helper-reset ui-widget-content ui-corner-bottom ui-accordion-content-active">
                <article></article>
                <p>
                Accidentally deleted messages or contacts on your Samsung GALAXY Ace? Or lost photos and video on SD card from Samsung? That's unbelievable!! Don't panic when you're encountering this. Dr.Fone for Android is capable of recovering messages, contacts, photos, video, audio and documents on/from Samsung Galaxy Ace if you take care. Watch Video: Note: Don't write anything to your phone after the loss, or you risk losing them forever. Lost data is not gone immediately after deleting or formatting, but it'll gone permanently if there are new data overwritting it. Directly scan and recover SMS, contacts, photos, video and more * Automatically scan your device after connected to the computer * Recover sent &amp; received messages, and export both in HTML and XML formats to PC for easy reading, printing and importing to your device * Retrieve deleted contacts, including names, numbers, Email &amp; addresses, and export in HTML, vCard and CSV to PC * Regain photos, video, audio and documents from SD cards inside Android devices to a computer Support multiple Android devices &amp; Android OS * Available for hot Android phones and tablets from Samsung, HTC, LG, Sony, Motorola, ZET, Huawei, etc * Support lots of Android OS versions * All rooted Samsung devices are supported, regardless of Android OS Transfer Data between Android Phones &amp; Other Phones:</p>
                <ul>
                    <li>回文數：0</li>
                    <li>terrence25</li>
                    <li>2013-09-11 11:19</li>
                    <li>點閱數：39</li>
                </ul>
          </div>
      </article>
    </div>
    
        --%>
      </div>
    </section>
</s:if>