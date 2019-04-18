package com.social.searchservice.service;

import com.social.searchservice.dao.SearchDao;
import common.search.QuestionSearch;
import common.search.UserSearch;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


@Service
public class SearchService {
    @Autowired
    SearchDao searchDao;

    //搜索问题
    public List<QuestionSearch> searchQuestion(String keyWords, int page, int count) throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8983/solr/mycollection");
        //设置查询参数
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setFilterQueries("category:问题");
        solrQuery.setQuery(keyWords);
        solrQuery.setStart((page - 1) * count);
        solrQuery.setRows(count);
        solrQuery.set("df", "question_title");
        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.set("hl.fl", "question_title");
        //执行查询
        QueryResponse response = solrServer.query(solrQuery);
        //获取查询结果
        SolrDocumentList solrDocuments = response.getResults();
        List<QuestionSearch> result = new ArrayList<>();
        for (SolrDocument document : solrDocuments) {
            //创建QuestionSearch对象
            QuestionSearch q = new QuestionSearch();
            q.setAnswerTime((Integer) document.get("answer_time"));
            q.setQuestionDate((Date) document.get("question_date"));
            q.setHeadUrl((String) document.get("head_url"));
            q.setQuestionId((Integer) document.get("question_id"));
            q.setUserId((Integer) document.get("user_id"));
            q.setUserName((String) document.get("user_name"));
            q.setVisitTime((Integer) document.get("visit_time"));
            //获取高亮设置.
            List<String> title = response.getHighlighting().get(document.get("id")).get("question_title");
            if (title != null && title.size() > 0)
                q.setQuestionTitle(title.get(0));
            else
                q.setQuestionTitle((String) document.get("question_title"));
            result.add(q);
        }
        return result;
    }

    //搜索用户
    public List<UserSearch> searchUser(String keyWords, int page, int count) throws SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8983/solr/mycollection");
        //设置查询参数
        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setFilterQueries("category:用户");
        solrQuery.setQuery(keyWords);
        solrQuery.setStart((page - 1) * count);
        solrQuery.setRows(count);
        solrQuery.set("df", "user_name");
        //设置高亮
        solrQuery.setHighlight(true);
        solrQuery.setHighlightSimplePre("<em>");
        solrQuery.setHighlightSimplePost("</em>");
        solrQuery.set("hl.fl", "user_name");
        //执行查询
        QueryResponse response = solrServer.query(solrQuery);
        //获取查询结果
        SolrDocumentList solrDocuments = response.getResults();
        List<UserSearch> result = new ArrayList<>();
        for (SolrDocument solrDocument : solrDocuments) {
            UserSearch u = new UserSearch();
            u.setHeadUrl((String) solrDocument.get("head_url"));
            u.setUserId((Integer) solrDocument.get("user_id"));
            //获取高亮设置.
            List<String> title = response.getHighlighting().get(solrDocument.get("id")).get("user_name");
            if (title != null && title.size() > 0)
                u.setUserName(title.get(0));
            else
                u.setUserName((String) solrDocument.get("user_name"));
            result.add(u);
        }
        return result;
    }

    //添加全部索引
    public String importAllData() throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8983/solr/mycollection");
        //获取数据
        List<QuestionSearch> questionSearches = searchDao.selectQuestionSearch();
        List<UserSearch> userSearches = searchDao.selectUserSearch();
        //导入问题数据
        for (QuestionSearch q : questionSearches) {
            //创建文档
            SolrInputDocument document = new SolrInputDocument();
            document.addField("question_id", q.getQuestionId());
            document.addField("answer_time", q.getAnswerTime());
            document.addField("question_date", q.getQuestionDate());
            document.addField("head_url", q.getHeadUrl());
            document.addField("question_title", q.getQuestionTitle());
            document.addField("user_id", q.getUserId());
            document.addField("user_name", q.getUserName());
            document.addField("visit_time", q.getVisitTime());
            document.addField("category", q.getCategory());
            solrServer.add(document);
        }
        //导入用户数据
        for (UserSearch u : userSearches) {
            SolrInputDocument document = new SolrInputDocument();
            document.addField("user_id", u.getUserId());
            document.addField("user_name", u.getUserName());
            document.addField("head_url", u.getHeadUrl());
            document.addField("category", u.getCategory());
            solrServer.add(document);
        }
        //提交
        solrServer.commit();
        return "索引数据提交成功";
    }

    //删除全部索引数据
    public String deleteAllData() throws IOException, SolrServerException {
        SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8983/solr/mycollection");
        solrServer.deleteByQuery("*:*");
        solrServer.commit();
        return "删除索引数据成功";
    }

    //更新单条索引
    public String updateData(QuestionSearch q) throws Exception {
        SolrServer solrServer = new HttpSolrServer("http://127.0.0.1:8983/solr/mycollection");
        SolrInputDocument document = new SolrInputDocument();
        document.addField("question_id", q.getQuestionId());
        document.addField("answer_time", q.getAnswerTime());
        document.addField("question_date", q.getQuestionDate());
        document.addField("head_url", q.getHeadUrl());
        document.addField("question_title", q.getQuestionTitle());
        document.addField("user_id", q.getUserId());
        document.addField("user_name", q.getUserName());
        document.addField("visit_time", q.getVisitTime());
        document.addField("category", q.getCategory());
        solrServer.add(document);
        solrServer.commit();
        return "索引数据提交成功";
    }

}