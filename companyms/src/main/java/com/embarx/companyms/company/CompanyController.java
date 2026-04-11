package com.embarx.companyms.company;

import java.util.List;

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
@RequestMapping("/companies")
public class CompanyController {

	private CompanyService companyService;

	public CompanyController(CompanyService companyService) {
		super();
		this.companyService = companyService;
	}
	
	@PostMapping("/createCompanies")
	public ResponseEntity<String> createCompanies(@RequestBody Company company)
	{
		 this.companyService.createCompanies(company);

		return new ResponseEntity<>("companies data add successfully", HttpStatus.CREATED);
	}

	@PutMapping("/updateCompanies/{id}")
	public ResponseEntity<String> updtaeCompanies(@RequestBody Company company, @PathVariable Long id)
	{
		boolean updtaeCompanies = this.companyService.updtaeCompanies(company, id);
		
		if (updtaeCompanies)
		{
			return new ResponseEntity<>("companies data update successfully", HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@GetMapping("/")
	public ResponseEntity<List<Company>> findAllCompaniesName() {
		List<Company> allCompanies = this.companyService.getAllCompanies();

		return new ResponseEntity<List<Company>>(allCompanies, HttpStatus.OK);
	}
	
	@DeleteMapping("/deleteCompanies/{id}")
	public ResponseEntity<String> deleteById(@PathVariable Long id)
	{
		boolean deleteCompanies = this.companyService.deleteCompanies(id);
		
		if (deleteCompanies)
		{
			return new ResponseEntity<>("companies delete successfully", HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
	
	@GetMapping("/getById/{id}")
	public ResponseEntity<Company> getById(@PathVariable Long id)
	{
		 Company byCompaniesId = this.companyService.getByCompaniesId(id);
		
		if (byCompaniesId != null)
		{
			return new ResponseEntity<>(byCompaniesId, HttpStatus.OK);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
