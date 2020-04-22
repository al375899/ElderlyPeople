package es.uji.ei1027.elderlypeople.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import es.uji.ei1027.elderlypeople.dao.LineDao;
import es.uji.ei1027.elderlypeople.model.Line;

@Controller
@RequestMapping("/line")
public class LineController {

	private LineDao lineDao;

	@Autowired
	public void setLineDao(LineDao lineDao) {
		this.lineDao = lineDao;
	}

	@RequestMapping("/list")
	public String listLines(Model model) {
		model.addAttribute("lines", lineDao.getLines());
		return "line/list";
	}

	@RequestMapping(value = "/add")
	public String addLine(Model model) {
		model.addAttribute("line", new Line());
		return "line/add";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddSubmit(@ModelAttribute("line") Line line, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "line/add";
		lineDao.addLine(line);
		return "redirect:list";
	}

	@RequestMapping(value = "/update/{invoiceCode}/{idRequest}", method = RequestMethod.GET)
	public String editInvoice(Model model, @PathVariable Integer invoiceCode, @PathVariable Integer idRequest) {
		model.addAttribute("line", lineDao.getLine(invoiceCode, idRequest));
		return "line/update";
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String processUpdateSubmit(@ModelAttribute("line") Line line, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "list/update";
		lineDao.updateLine(line);
		return "redirect:list";
	}

	@RequestMapping(value = "/delete/{invoiceCode}/{idRequest}")
	public String processDelete(@PathVariable Integer invoiceCode, @PathVariable Integer idRequest) {
		lineDao.deleteLine(invoiceCode, idRequest);
		return "redirect:../list";
	}
}
