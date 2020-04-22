package es.uji.ei1027.elderlypeople.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.elderlypeople.dao.InvoiceDao;
import es.uji.ei1027.elderlypeople.model.Invoice;

@Controller
@RequestMapping("/invoice")
public class InvoiceController {

	private InvoiceDao invoiceDao;

	@Autowired
	public void setInvoiceDao(InvoiceDao invoiceDao) {
		this.invoiceDao = invoiceDao;
	}

	@RequestMapping("/list")
	public String listInvoices(Model model) {
		model.addAttribute("invoices", invoiceDao.getInvoices());
		return "invoice/list";
	}

	@RequestMapping(value = "/add")
	public String addInvoice(Model model) {
		model.addAttribute("invoice", new Invoice());
		return "invoice/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("invoice") Invoice invoice, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "invoice/add";
		invoiceDao.addInvoice(invoice);
		return "redirect:list";
	}

	@RequestMapping(value = "/update/{invoiceCode}", method = RequestMethod.GET)
	public String editInvoice(Model model, @PathVariable Integer invoiceCode) {
		model.addAttribute("invoice", invoiceDao.getInvoice(invoiceCode));
		return "invoice/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("invoice") Invoice invoice, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "invoice/update";
		invoiceDao.updateInvoice(invoice);
		return "redirect:list";
	}

	@RequestMapping(value = "/delete/{invoiceCode}")
	public String processDelete(@PathVariable Integer invoiceCode) {
		invoiceDao.deleteInvoice(invoiceCode);
		return "redirect:../list";
	}
}

