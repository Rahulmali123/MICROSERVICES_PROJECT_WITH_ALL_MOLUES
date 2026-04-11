package com.embarx.companyms.impl;

import java.util.List;
import java.util.Optional;

import com.embarx.companyms.company.Company;
import com.embarx.companyms.company.CompanyRepo;
import com.embarx.companyms.company.CompanyService;
import org.springframework.stereotype.Service;

@Service
public class ComapnyServiceimpl implements CompanyService
{

	CompanyRepo companyRepo;

	public ComapnyServiceimpl(CompanyRepo companyRepo) {
		super();
		this.companyRepo = companyRepo;
	}

	@Override
	public void createCompanies(Company company)
	{
		companyRepo.save(company);

	}

	@Override
	public boolean updtaeCompanies(Company company, Long id)
	{
		Optional<Company> exstingCompany = companyRepo.findById(id);

		if (exstingCompany.isPresent())
		{
			Company company2=exstingCompany.get();

			company2.setName(company.getName());
			company2.setDescription(company.getDescription());
//			company2.setJobs(company.getJobs());
			companyRepo.save(company);
			return true;
		}
		return false;
	}


	@Override
	public List<Company> getAllCompanies()
	{
		List<Company> findAllCompanies = this.companyRepo.findAll();

		return findAllCompanies;
	}

	@Override
	public boolean deleteCompanies(Long id)
	{
		Optional<Company> exstingCompany = companyRepo.findById(id);

		if (exstingCompany.isPresent())
		{
			companyRepo.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public Company getByCompaniesId(Long id)
	{
		Company company = companyRepo.findById(id).orElse(null);

		return company;
	}



}

