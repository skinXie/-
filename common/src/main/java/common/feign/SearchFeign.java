package common.feign;

import common.search.QuestionSearch;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "search-service", url = "http://127.0.0.1:8083")
public interface SearchFeign {
    //搜索
    @GetMapping("/api/search")
    public List<QuestionSearch> searchQuestion(
            @RequestParam("keywords") String keyWords,
            @RequestParam(value = "page", defaultValue = "1", required = false) int page,
            @RequestParam(value = "count", defaultValue = "5", required = false) int count);
}
