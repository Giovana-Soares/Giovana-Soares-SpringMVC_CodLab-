package br.com.ifsp.regescweb.controllers;

import br.com.ifsp.regescweb.dto.RequisicaoNovoProfessor;
import br.com.ifsp.regescweb.models.Professor;
import br.com.ifsp.regescweb.models.StatusProfessor;
import br.com.ifsp.regescweb.repositories.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

import javax.validation.Valid;

@Controller
public class ProfessorController {
    @Autowired
    private ProfessorRepository professorRepository;

    @GetMapping("/professores")
    public ModelAndView index(){

        List<Professor> professores = this.professorRepository.findAll();

        ModelAndView mv = new ModelAndView("professores/index");
        mv.addObject("professores", professores);
        return mv;
    }

    @GetMapping("/professores/new")
    public ModelAndView nnew(RequisicaoNovoProfessor requisicao) {
        ModelAndView mv = new ModelAndView("professores/new");
        mv.addObject("listaStatusProfessor", StatusProfessor.values());

        return mv;
    }

    @PostMapping("/professores")
    public ModelAndView create(@Valid RequisicaoNovoProfessor requisicao, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            System.out.println("\n***** TEM ERROS *****\n");
            
            ModelAndView mv = new ModelAndView("professores/new");
            mv.addObject("listaStatusProfessor", StatusProfessor.values());
            return mv;
        }
        else {
            Professor professor = requisicao.toProfessor();
            this.professorRepository.save(professor);

            return new ModelAndView( "redirect:/professores") ;
        }
    }
}