package dev.jpestana.mifitanalyzer;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MiFitAnalyzerController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/data-import")
    public String dataImport() {
        return "import";
    }

}
