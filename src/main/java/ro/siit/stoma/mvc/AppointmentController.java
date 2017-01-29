package ro.siit.stoma.appointment.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.RedirectView;

import ro.siit.stoma.appointment.domain.Appointment;
import ro.siit.stoma.appointment.service.AppointmentService;
import ro.siit.stoma.appointment.service.ValidationException;

@Controller
@RequestMapping("/appointment")
public class AppointmentController extends WebMvcConfigurerAdapter {

	@Autowired
	private AppointmentService appService;

	@RequestMapping("add")
	public ModelAndView renderAdd() {
		ModelAndView modelAndView = new ModelAndView("appointment/add");
		modelAndView.addObject("appointment", new Appointment());
		return modelAndView;
	}

	@RequestMapping("edit")
	public ModelAndView renderEdit(long id) {
		ModelAndView modelAndView = new ModelAndView("appointment/add");
		modelAndView.addObject("appointment", appService.get(id));
		return modelAndView;
	}

	@RequestMapping("save")
	public ModelAndView save(@Valid @ModelAttribute("appointment") Appointment app, int id, BindingResult bindingResult) {
		ModelAndView modelAndView = null;
		boolean hasErrors = false;
		if (!bindingResult.hasErrors()) {
			try {
				appService.saveAppointment(app, id);
				modelAndView = new ModelAndView();
				modelAndView.setView(new RedirectView("/appointment"));
				//System.out.println("saved");
			} catch (ValidationException ex) {
				for (String msg : ex.getCauses()) {
					bindingResult.addError(new ObjectError("appointment", msg));
				}
				hasErrors = true;
				//System.out.println("error");
			}
		} else {
			hasErrors = true;
			//System.out.println("error2");
		}

		if (hasErrors) {
			modelAndView = new ModelAndView("appointment/add");
			modelAndView.addObject("appointment", app);
			modelAndView.addObject("errors", bindingResult.getAllErrors());
			//System.out.println("error3");
		}
		//System.out.println("return model&view");
		//System.out.println(modelAndView.toString());
		return modelAndView;
	}
	
	@RequestMapping("")
	public ModelAndView list() throws Exception{
		ModelAndView model = new ModelAndView("appointment/list");
		model.addObject("appointments", appService.listAllPer7Days());
		model.addObject("width", 8);
		return model;
	}

	@RequestMapping("/")
	public String getLogin(Model model) {

		return "home";
	}
}
