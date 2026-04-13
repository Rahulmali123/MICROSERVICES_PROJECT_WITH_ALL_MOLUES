package com.embarx.jobms.Job.clients;

import com.embarx.jobms.Job.external.Company;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "companyms")
public interface CompanyClients {

    @GetMapping("/companies/getById/{id}")
    Company getCompany(@PathVariable Long id);
}