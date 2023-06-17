package it.uniroma3.siw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;






@Controller
public class adminController {

    @GetMapping(value = "/admin/getAdminIndex")
        public String getAdminIndex() {
            return "admin/adminIndex.html";
    }
    
}
