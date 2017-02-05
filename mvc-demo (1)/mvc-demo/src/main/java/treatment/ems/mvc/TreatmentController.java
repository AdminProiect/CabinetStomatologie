package treatment.ems.mvc;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import treatment.ems.domain.Treatment;
import treatment.ems.service.TreatmentService;
import treatment.ems.service.ValidationException;

@Controller
@RequestMapping("/treatment")
public class TreatmentController {

	@Autowired
	private TreatmentService treatmentService;

	@RequestMapping("add")
	public ModelAndView renderAdd() {
		ModelAndView modelAndView = new ModelAndView("treatment/add");
		modelAndView.addObject("treatment", new Treatment());
		return modelAndView;
	}

	@RequestMapping("edit")
	public ModelAndView renderEdit(int id) {
		ModelAndView modelAndView = new ModelAndView("treatment/add");
		modelAndView.addObject("treatment", treatmentService.get(id));
		return modelAndView;
	}

	@RequestMapping("save")
	public ModelAndView save(
			@Valid @ModelAttribute("treatment") Treatment treatment, int id, 
			BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		boolean hasErros = false;
		if (!bindingResult.hasErrors()) {
			try {
				treatmentService.save(treatment,id );

				modelAndView = new ModelAndView();
				modelAndView.setView(new RedirectView(""));
			} catch (ValidationException ex) {
				for (String msg : ex.getCauses()) {
					bindingResult.addError(new ObjectError("treatment", msg));
				}
				hasErros = true;
			}
		} else {
			hasErros = true;
		}

		if (hasErros) {
			modelAndView = new ModelAndView("treatment/add");
			modelAndView.addObject("treatment", treatment);
			modelAndView.addObject("errors", bindingResult.getAllErrors());
		}

		return modelAndView;
	}

	@RequestMapping("")
	public ModelAndView list() throws Exception {
		ModelAndView modelAndView = new ModelAndView("treatment/list");
		modelAndView.addObject("treatments", treatmentService.listAll());
		return modelAndView;
	}
}
