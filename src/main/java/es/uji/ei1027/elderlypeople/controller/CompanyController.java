package es.uji.ei1027.elderlypeople.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.elderlypeople.dao.CompanyDao;
import es.uji.ei1027.elderlypeople.model.Company;


@Controller
@RequestMapping("/company")
public class CompanyController {

	private CompanyDao companyDao;

	@Autowired
	public void setCompanyDao(CompanyDao companyDao) {
		this.companyDao = companyDao;
	}

	@RequestMapping("/list")
	public String listCompanies(Model model) {
		model.addAttribute("companies", companyDao.getCompanies());
		return "company/list";
	}

	@RequestMapping(value = "/add")
	public String addCompany(Model model) {
		model.addAttribute("company", new Company());
		return "company/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("company") Company company, BindingResult bindingResult) {
		CompanyValidator companyValidator = new CompanyValidator();
		companyValidator.validate(company, bindingResult);
		if (bindingResult.hasErrors())
			return "company/add";
		try {
			companyDao.addCompany(company);
		} catch (DuplicateKeyException e) {
			throw new ElderlyPeopleException(
					"Ja existeix una companyia amb el mateix numero fiscal: " + company.getFiscalNumber(),
					"CPduplicada");
		} catch (DataAccessException e) {
			throw new ElderlyPeopleException("Error en l'accés a la base de dades", "ErrorAccedintDades");
		}

		return "redirect:list";
	}

	@RequestMapping(value = "/update/{fiscalNumber}", method = RequestMethod.GET)
	public String editCompany(Model model, @PathVariable String fiscalNumber) {
		model.addAttribute("company", companyDao.getCompany(fiscalNumber));
		return "company/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("company") Company company, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "company/update";
		companyDao.updateCompany(company);
		return "redirect:list";
	}

	@RequestMapping(value = "/delete/{fiscalNumber}")
	public String processDelete(@PathVariable String fiscalNumber) {
		companyDao.deleteCompany(fiscalNumber);
		return "redirect:../list";
	}

}