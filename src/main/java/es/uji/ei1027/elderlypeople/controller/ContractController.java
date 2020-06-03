package es.uji.ei1027.elderlypeople.controller;


import javax.servlet.http.HttpSession;

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

import es.uji.ei1027.elderlypeople.dao.ContractDao;
import es.uji.ei1027.elderlypeople.model.Contract;

@Controller
@RequestMapping("/contract")
public class ContractController {

	private ContractDao contractDao;

	@Autowired
	public void setContractDao(ContractDao contractDao) {
		this.contractDao = contractDao;
	}

	@RequestMapping("/list")
	public String listContracts(Model model) {
		model.addAttribute("contracts", contractDao.getContracts());
		return "contract/list";
	}
	
	@RequestMapping(value = "/listUser/{fiscalNumber}")
	public String listContractsUser(Model model, @PathVariable String fiscalNumber) {
		model.addAttribute("fiscalNumber", fiscalNumber);
		model.addAttribute("contracts", contractDao.getContractsUser(fiscalNumber));
		return "contract/list";
	}

	@RequestMapping(value = "/add")
	public String addContract(Model model) {
		model.addAttribute("contract", new Contract());
		return "contract/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("contract") Contract contract, BindingResult bindingResult) {
		ContractValidator contractValidator = new ContractValidator();
		contractValidator.validate(contract, bindingResult);
		if (bindingResult.hasErrors())
			return "contract/add";
		try {
			contractDao.addContract(contract);
		} catch (DuplicateKeyException e) {
			throw new ElderlyPeopleException("Error add contract controller", "validator");
		} catch (DataAccessException e) {
			throw new ElderlyPeopleException("Error en l'accés a la base de dades", "ErrorAccedintDades");
		}

		return "redirect:list";
	}
	
	@RequestMapping(value = "/addUser/{fnCompany}")
	public String addContractUser(Model model, @PathVariable String fnCompany, HttpSession session) {
		Contract contract = new Contract();
		contract.setFnCompany(fnCompany);
		model.addAttribute("contract", contract);
		try {
			contractDao.allowCreateContract(fnCompany);
		} catch (IllegalArgumentException e) {
			session.setAttribute("message", "You cannot do a new contract for this company because one of the contracts is currently active");
			session.setAttribute("reference", "/contract/listUser/" + fnCompany);
			return "/notification";
		}
		return "contract/add";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String processAddSubmitUser(@ModelAttribute("contract") Contract contract, BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors())
			return "contract/addUser";
		try {
			contractDao.addContractUser(contract);
			session.setAttribute("message", "Contract has been created correctly");
			session.setAttribute("reference", "/contract/listUser/" + contract.getFnCompany());
			return "/notification";
		} catch (DataAccessException e) {
			throw new ElderlyPeopleException("Error en l'accés a la base de dades", "ErrorAccedintDades");
		} catch (ArithmeticException e) {
			session.setAttribute("message", "Hours isn't correct. You must correct it");
			session.setAttribute("reference", "/contract/listUser/" + contract.getFnCompany());
			return "/notification";
		}
		
	}

	@RequestMapping(value = "/update/{idContract}", method = RequestMethod.GET)
	public String editContract(Model model, @PathVariable int idContract) {
		model.addAttribute("contract", contractDao.getContract(idContract));
		return "contract/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("contract") Contract contract, BindingResult bindingResult, HttpSession session) {
		if (bindingResult.hasErrors())
			return "contract/update";
		contractDao.updateContract(contract);
		session.setAttribute("message", "Contract has been updated correctly");
		session.setAttribute("reference", "/contract/listUser/" + contract.getFnCompany());
		return "/notification";
	}

	@RequestMapping(value = "/delete/{idContract}")
	public String processDelete(@PathVariable int idContract, HttpSession session) {
		String fnCompany = contractDao.getContract(idContract).getFnCompany();
		try {
			contractDao.deleteContract(idContract);
			session.setAttribute("message", "Contract has been deleted correctly");
			session.setAttribute("reference", "/contract/listUser/" + fnCompany);
			return "/notification";
		} catch (IllegalArgumentException e) {
			session.setAttribute("message", "This contract is currently assigned to other users and can't be deleted");
			session.setAttribute("reference", "/contract/listUser/" + fnCompany);
			return "/notification";
		}
	}

}