package com.social.searchservice.controller;

import com.social.searchservice.service.SearchService;
import common.search.QuestionSearch;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
public class SearchController {
    @Autowired
    SearchService searchService;

    //删除数据
    @GetMapping("/api/delete")
    public String deleteData() throws IOException, SolrServerException {
        return searchService.deleteAllData();
    }

    //导入数据
    @GetMapping("/api/import")
    public String importData() throws Exception {
        return searchService.importAllData();
    }

    //搜索
    @GetMapping("/api/search")
    public List<QuestionSearch> searchQuestion(
            @RequestParam("keywords") String keyWords,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "count", defaultValue = "5", required = false) int count) throws SolrServerException {
        return searchService.searchQuestion(keyWords, page, count);
    }

    //更新数据
    @GetMapping("/api/update")
    public String update(@RequestParam("QuestionSearch") QuestionSearch q) throws Exception {
        return searchService.updateData(q);

    }
}
