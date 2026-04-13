package com.embarx.jobms.Job.impl;

import com.embarx.jobms.Job.Job;
import com.embarx.jobms.Job.JobRepo;
import com.embarx.jobms.Job.JobService;
import com.embarx.jobms.Job.clients.CompanyClients;
import com.embarx.jobms.Job.clients.ReviewClient;
import com.embarx.jobms.Job.dto.JobDTO;
import com.embarx.jobms.Job.external.Company;
import com.embarx.jobms.Job.external.Review;
import com.embarx.jobms.Job.mapper.JobMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JobServiceImpl implements JobService {

	private static final Logger log = LoggerFactory.getLogger(JobServiceImpl.class);

	private final JobRepo jobRepo;
	private final CompanyClients companyClients;
	private final ReviewClient reviewClient;

	public JobServiceImpl(JobRepo jobRepo,
						  CompanyClients companyClients,
						  ReviewClient reviewClient) {
		this.jobRepo = jobRepo;
		this.companyClients = companyClients;
		this.reviewClient = reviewClient;
	}

	private JobDTO convertToDto(Job job) {

		Company company;
		List<Review> reviews;

		try {
			// ✅ Feign call - Company
			company = companyClients.getCompany(job.getCompanyId());

			// ✅ Feign call - Review
			reviews = reviewClient.getReviews(job.getCompanyId());

			if (reviews == null) {
				reviews = List.of();
			}

		} catch (Exception e) {

			log.error("Error while calling external services", e);

			// fallback
			company = new Company();
			company.setId(job.getCompanyId());
			company.setName("Company not available");

			reviews = List.of();
		}

		return JobMapper.mapToJobWithCompanyDto(job, company, reviews);
	}

	@Override
	public void createJob(Job job) {
		jobRepo.save(job);
	}

	@Override
	public boolean updateJob(Job job, Long id) {
		Optional<Job> jobOptional = jobRepo.findById(id);

		if (jobOptional.isPresent()) {
			Job existingJob = jobOptional.get();

			existingJob.setTitle(job.getTitle());
			existingJob.setDescription(job.getDescription());
			existingJob.setMinSalary(job.getMinSalary());
			existingJob.setMaxSalary(job.getMaxSalary());
			existingJob.setLocation(job.getLocation());

			jobRepo.save(existingJob);
			return true;
		}

		return false;
	}

	@Override
	public List<JobDTO> findAll() {
		return jobRepo.findAll()
				.stream()
				.map(this::convertToDto)
				.toList();
	}

	@Override
	public JobDTO findJobWithCompanyById(Long id) {

		Job job = jobRepo.findById(id).orElse(null);

		if (job == null) {
			return null;
		}

		return convertToDto(job);
	}

	@Override
	public boolean deleteById(Long id) {
		try {
			jobRepo.deleteById(id);
			return true;
		} catch (Exception e) {
			log.error("Error deleting job", e);
			return false;
		}
	}
}