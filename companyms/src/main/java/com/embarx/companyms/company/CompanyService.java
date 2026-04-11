package com.embarx.companyms.company;

import java.util.List;

public interface CompanyService 
{
	void  createCompanies(Company company);
	
	boolean  updtaeCompanies(Company company,Long id);
	
	public List<Company>  getAllCompanies();
	
	boolean  deleteCompanies(Long id);
	
	Company  getByCompaniesId(Long id);

}
