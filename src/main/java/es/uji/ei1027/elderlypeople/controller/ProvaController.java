package es.uji.ei1027.elderlypeople.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import es.uji.ei1027.elderlypeople.categoria.Categoria;
import es.uji.ei1027.elderlypeople.dao.NadadorDao;
import es.uji.ei1027.elderlypeople.dao.ProvaDao;
import es.uji.ei1027.elderlypeople.model.Nadador;

@Controller    
public class ProvaController {
    @RequestMapping("/prova") 
    public String provaWeb(Model model ) {
        String message = "Provant la Web del Club Esportiu";
        model.addAttribute("message", message); 
        return "prova"; 
    }
    
    @Autowired
    NadadorDao nadadorDao;
    
    @Autowired
    ProvaDao provaDao;

    @RequestMapping("/provaNadador/{nom}") 
    public String provaUnNadador(Model model, @PathVariable String nom) { 
       Nadador nadador = nadadorDao.getNadador(nom); 
       model.addAttribute("nadador", nadador);
       model.addAttribute("categoria", categoria.getCategoria(nadador.getEdat()));
       return "prova_nadador"; 
    }


    
    private Categoria categoria; 
    @Autowired
    public void setCategoria(Categoria categoria) {
               this.categoria = categoria;
    }

    @RequestMapping("/provaCategoria")
    public String provaCategoria(Model model) { 
             model.addAttribute("message",  
                        "El nadador té la categoria " 
                        + categoria.getCategoria(19));
          return "prova";
    }
    
    @RequestMapping("/prova/proves")
    public String listProves(Model model) {
        model.addAttribute("proves", provaDao.getProves());
        return "prova/list";
     }


}