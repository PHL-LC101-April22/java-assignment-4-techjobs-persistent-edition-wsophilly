package org.launchcode.techjobs.persistent.controllers;

import org.launchcode.techjobs.persistent.models.Employer;
import org.launchcode.techjobs.persistent.models.Job;
import org.launchcode.techjobs.persistent.models.Skill;
import org.launchcode.techjobs.persistent.models.data.EmployerRepository;
import org.launchcode.techjobs.persistent.models.data.JobRepository;
import org.launchcode.techjobs.persistent.models.data.SkillRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by LaunchCode
 */
@Controller
public class HomeController {

    @Autowired
    EmployerRepository employerRepository;

    @Autowired
    SkillRepository skillRepository;

    @Autowired
    JobRepository jobRepository;

    @RequestMapping("")
    public String index(Model model) {

        model.addAttribute("title", "My Jobs");

        return "index";
    }

    @GetMapping("add")
    public String displayAddJobForm(Model model) {
        model.addAttribute("title", "Add Job");
        model.addAttribute(new Job());
        model.addAttribute("employers", employerRepository.findAll());
        return "add";
    }


    @PostMapping("add")
    public String processAddJobForm(@ModelAttribute @Valid Job newJob,
                                       Errors errors, Model model,  @RequestParam
                                        List<Integer> skills, @RequestParam int employerID) {

        if (errors.hasErrors()) {
            model.addAttribute("title", "Add Job");
            return "add";
        }
        Optional<Employer> optEmployer = employerRepository.findById(employerID);

        if (optEmployer.isPresent()) {
           Employer emp = optEmployer.get();
           newJob.setEmployer(emp);
        }
        List<Skill> skillList = new ArrayList<>();
        for ( int skill : skills) {
        Optional<Skill>  optSkill = skillRepository.findById(skill);
        Skill jobSkill = optSkill.get();
            skillList.add(jobSkill);
        }
        newJob.setSkills(skillList);

        jobRepository.save(newJob);
        return "redirect:";
    }

    @GetMapping("view/{jobId}")
    public String displayViewJob(Model model, @PathVariable int jobId) {

        Optional optionalJob = jobRepository.findById(jobId);

        if (optionalJob.isPresent()) {
            Job job = (Job) optionalJob.get();
            model.addAttribute("Job", job);
            return "view/{jobId}";
        } else {
            return "redirect:../";
        }

    }


}
