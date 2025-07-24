package it.uniroma3.siw.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import it.uniroma3.siw.model.Discoteca;
import it.uniroma3.siw.service.DiscotecaService;

@Controller
@RequestMapping("/admin/discoteca")
@PreAuthorize("hasRole('ADMIN')")
public class DiscotecaAdminController {

    @Autowired
    private DiscotecaService discotecaService;

    // Visualizza tutte le discoteche (admin dashboard)
    @GetMapping("/lista")
    public String listaDiscoteche(Model model) {
        model.addAttribute("discoteche", discotecaService.getAllDiscoteche());
        return "admin/listaDiscoteche";
    }

    // Form per creare una nuova discoteca
    @GetMapping("/nuova")
    public String nuovaDiscotecaForm(Model model) {
        model.addAttribute("discoteca", new Discoteca());
        return "admin/formDiscoteca";
    }

    // Salvataggio nuova discoteca
    @PostMapping("/salva")
    public String salvaDiscoteca(@ModelAttribute Discoteca discoteca) {
        discotecaService.salvaDiscoteca(discoteca);
        return "redirect:/admin/discoteca/lista";
    }

    // Form per modificare discoteca
    @GetMapping("/{id}/modifica")
    public String modificaForm(@PathVariable Long id, Model model) {
        model.addAttribute("discoteca", discotecaService.getDiscoteca(id));
        return "admin/formDiscoteca";
    }

    // Conferma modifica discoteca
    @PostMapping("/{id}/modifica")
    public String confermaModifica(@PathVariable Long id, @ModelAttribute Discoteca discoteca) {
        discotecaService.modificaDiscoteca(id, discoteca);
        return "redirect:/admin/discoteca/lista";
    }

    // Eliminazione discoteca
    @PostMapping("/{id}/elimina")
    public String eliminaDiscoteca(@PathVariable Long id) {
        discotecaService.cancellaDiscoteca(id);
        return "redirect:/admin/discoteca/lista";
    }
}