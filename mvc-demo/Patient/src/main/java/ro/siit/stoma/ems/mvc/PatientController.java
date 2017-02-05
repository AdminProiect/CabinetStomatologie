package patient.ems.mvc;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import patient.ems.domain.Patient;
import patient.ems.service.PatientService;
import patient.ems.service.ValidationException;

@Controller
@RequestMapping("/patient")
public class PatientController {

	@Autowired
	private PatientService patientService;

	@RequestMapping("add")
	public ModelAndView renderAdd() {
		ModelAndView modelAndView = new ModelAndView("patient/add");
		modelAndView.addObject("patient", new Patient());
		return modelAndView;
	}

	@RequestMapping("edit")
	public ModelAndView renderEdit(int id) {
		ModelAndView modelAndView = new ModelAndView("patient/add");
		modelAndView.addObject("patient", patientService.get(id));
		return modelAndView;
	}

	@RequestMapping("delete")
	public ModelAndView delete(int id) throws Exception {
		patientService.delete(patientService.get(id));
		ModelAndView modelAndView = new ModelAndView("patient/list");
		modelAndView.addObject("patients", patientService.listAll());
		return modelAndView;
	}

	@RequestMapping("save")
	public ModelAndView save(@Valid @ModelAttribute("patient") Patient patient, int id, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		boolean hasErros = false;
		if (!bindingResult.hasErrors()) {
			try {
				patientService.save(patient, id);

				modelAndView = new ModelAndView();
				modelAndView.setView(new RedirectView(""));
			} catch (ValidationException ex) {
				for (String msg : ex.getCauses()) {
					bindingResult.addError(new ObjectError("patient", msg));
				}
				hasErros = true;
			}
		} else {
			hasErros = true;
		}

		if (hasErros) {
			modelAndView = new ModelAndView("patient/add");
			modelAndView.addObject("patient", patient);
			modelAndView.addObject("errors", bindingResult.getAllErrors());
		}

		return modelAndView;
	}

	@RequestMapping("")
	public ModelAndView list() throws Exception {
		ModelAndView modelAndView = new ModelAndView("patient/list");
		modelAndView.addObject("patients", patientService.listAll());
		return modelAndView;
	}

}
