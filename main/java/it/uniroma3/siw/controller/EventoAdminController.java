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
import it.uniroma3.siw.model.Evento;
import it.uniroma3.siw.service.DiscotecaService;
import it.uniroma3.siw.service.EventoService;

@Controller
@RequestMapping("/admin/evento")
@PreAuthorize("hasRole('ADMIN')")
public class EventoAdminController {

    @Autowired
    private EventoService eventoService;

    @Autowired
    private DiscotecaService discotecaService;

    // Lista eventi di una specifica discoteca
    @GetMapping("/discoteca/{id}")
    public String eventiPerDiscoteca(@PathVariable Long id, Model model) {
        Discoteca discoteca = discotecaService.getDiscoteca(id);
        model.addAttribute("discoteca", discoteca);
        model.addAttribute("eventi", eventoService.getEventiPerDiscoteca(discoteca));
        return "admin/listaEventi";
    }

    // Form per creare un evento
    @GetMapping("/nuovo/{idDiscoteca}")
    public String nuovoEventoForm(@PathVariable Long idDiscoteca, Model model) {
        Evento evento = new Evento();
        evento.setDiscoteca(discotecaService.getDiscoteca(idDiscoteca));
        model.addAttribute("evento", evento);
        return "admin/formEvento";
    }

    // Salva nuovo evento
    @PostMapping("/salva")
    public String salvaEvento(@ModelAttribute Evento evento) {
        eventoService.salvaEvento(evento);
        return "redirect:/admin/evento/discoteca/" + evento.getDiscoteca().getId();
    }

    // Modifica evento
    @GetMapping("/{id}/modifica")
    public String modificaForm(@PathVariable Long id, Model model) {
        Evento evento = eventoService.getEvento(id);
        model.addAttribute("evento", evento);
        return "admin/formEvento";
    }

    @PostMapping("/{id}/modifica")
    public String confermaModifica(@PathVariable Long id, @ModelAttribute Evento evento) {
        eventoService.modificaEvento(id, evento);
        return "redirect:/admin/evento/discoteca/" + evento.getDiscoteca().getId();
    }

    // Elimina evento
    @PostMapping("/{id}/elimina")
    public String eliminaEvento(@PathVariable Long id) {
        Evento evento = eventoService.getEvento(id);
        Long discotecaId = evento.getDiscoteca().getId();
        eventoService.cancellaEvento(id);
        return "redirect:/admin/evento/discoteca/" + discotecaId;
    }
}
