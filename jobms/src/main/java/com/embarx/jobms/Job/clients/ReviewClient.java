package com.embarx.jobms.Job.clients;

import com.embarx.jobms.Job.external.Review;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "reviewms")
public interface ReviewClient
{

    @GetMapping("/reviews")
    List<Review> getReviews(@RequestParam Long companyId);
}
