package com.embarx.jobms.Job.mapper;

import com.embarx.jobms.Job.Job;
import com.embarx.jobms.Job.dto.JobDTO;
import com.embarx.jobms.Job.external.Company;
import com.embarx.jobms.Job.external.Review;

import java.util.List;


public class JobMapper {

    public static JobDTO mapToJobWithCompanyDto(Job job, Company company, List<Review> reviews) {

        JobDTO dto = new JobDTO();

        dto.setJob(job);          // ✅ correct
        dto.setCompany(company);  // ✅ correct
        dto.setReviews(reviews);

        return dto;
    }
}