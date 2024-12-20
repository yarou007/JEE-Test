package com.example.personnes.controller;


import com.example.personnes.Repository.PersonneRepository;
import com.example.personnes.models.Personne;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller("/")

public class PersonneController {
    
    
    @Autowired
    PersonneRepository PersonneRepo ;


    @GetMapping("/")
    public String home() {

        return "redirect:/ListePersonnes";
    }
    @GetMapping("/ListePersonnes")
    public String index(ModelMap modelMap,
                        @RequestParam (name="page", defaultValue = "0") int page ,
                        @RequestParam (name="size", defaultValue = "3")  int size,
                        @RequestParam(name="keyword", defaultValue = "") String kw) {


       // Page<Personne> pagePersonnes= PersonneRepo.findByNomContains(kw, PageRequest.of(page, size) );// went to repo and findAll
//        model.addAttribute("ListPersonnes",PersonneRepo.findAll()); // stock el list fi model sous le nom de ListPersonne
//        model.addAttribute("currentPage",page);
//        model.addAttribute("keyword",kw);


        Page<Personne> personnes = PersonneRepo.findAll(PageRequest.of(page, size));
       // Page<Personne> personnes= PersonneRepo.findByNom(kw, PageRequest.of(page, size) );
        modelMap.addAttribute("ListPersonnes",personnes);
        modelMap.addAttribute("pages",new int[personnes.getTotalPages()]);
        modelMap.addAttribute("currentPage",page);
        return "Personnes";
    }
    @GetMapping("/admin/delete")

    public String delete(int id, String keyword, int page) {
        PersonneRepo.deleteById(id);
        return "redirect:/ListePersonnes?page="+page+"&keyword="+keyword;
    }
    @GetMapping("/formPersonne")
    public String formPersonnes(Model model) {
        model.addAttribute("Personne",new Personne());

        return "formPersonne";
    }
    @PostMapping ("/savepersonne")
    public String savePersonne(@Validated Personne Personne, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {return "formPersonne";}
        PersonneRepo.save(Personne);
        return "redirect:/ListePersonnes?keyword="+Personne.getNom();
    }
    @GetMapping("/editPer")
    public String editPersonne(@RequestParam("id") int id ,Model model) {
        Personne Per= PersonneRepo.findById(id).orElse(null);
        model.addAttribute("Personne",Per);
        return "formEditPer";
    }
    @PostMapping("/editDbPer")
    public String editDbPersonne(@Validated Personne Personne, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {return "formEditPer";}
        else PersonneRepo.save(Personne);
        return "redirect:/ListePersonnes?keyword="+Personne.getNom();

    }

}
