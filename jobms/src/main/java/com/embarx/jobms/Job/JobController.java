package com.embarx.jobms.Job;

import java.util.List;

import com.embarx.jobms.Job.dto.JobWithCompanyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jobs")
public class JobController {

	private JobService jobService;

	public JobController(JobService jobService) {
		super();
		this.jobService = jobService;
	}

	@PostMapping("/create")
	public ResponseEntity<String> createJob(@RequestBody Job job) {
		jobService.createJob(job);
		return new ResponseEntity<>("Job added successfully", HttpStatus.CREATED);
	}
	
	@PutMapping("/update/{id}")
//	@RequestMapping(value = "/update/{id}",method = RequestMethod.PUT)
	public ResponseEntity<String> updateJob(@RequestBody Job job,@PathVariable Long id) {
		 boolean updateJob= jobService.updateJob(job,id);
		 if (updateJob)
		 {
			 return new ResponseEntity<>("Job update successfully", HttpStatus.CREATED);	
		}
		 return new ResponseEntity<>( HttpStatus.NOT_FOUND);

	}

	@GetMapping("/")
	public ResponseEntity<List<JobWithCompanyDTO>> findAll() {

		return  ResponseEntity.ok(jobService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Job> getById(@PathVariable Long id) 
	{
		Job job = jobService.findByJobId(id);
		if (job != null) 
		{
			// return job;
			return new ResponseEntity<Job>(job, HttpStatus.OK);
		}
		return new ResponseEntity<Job>(HttpStatus.NOT_FOUND);
		// return new Job(1L,"TestJob","TestJob","2000","2000","location");
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id) 
	{
		
		 boolean deleteById = jobService.deleteById(id);
				
		if (deleteById) 
		{
			return new ResponseEntity<>("Job delete Succesffuly", HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	

}
