package com.social.portals.controller;

import common.feign.SearchFeign;
import common.portals.Holder;
import common.search.QuestionSearch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
public class SearchController {
    @Autowired
    Holder holder;
    @Autowired
    SearchFeign searchFeign;

    @GetMapping("/search")
    public String search(Model model, @RequestParam("keywords") String keywords,
                         @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                         @RequestParam(value = "count", required = false, defaultValue = "5") int count) {
        List<QuestionSearch> questionSearches = searchFeign.searchQuestion(keywords, page, count);
        model.addAttribute("questionSearches", Arrays.asList(questionSearches));
        model.addAttribute("holder", holder.getUser());
        model.addAttribute("keywords", keywords);
        return "search";
    }
}
