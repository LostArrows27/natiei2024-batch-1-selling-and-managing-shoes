package sun.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sun.intern.model.Product;
import sun.intern.service.ProductService;

import java.util.List;

@Controller
public class SearchController {

    private final ProductService productService;

    @Autowired
    public SearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search-results")
    public String searchPage(
        @RequestParam(name = "key", required = false, defaultValue = "") String query,
        @RequestParam(name = "page", required = false, defaultValue = "1") int page,
        Model model
    ) {
        int size = 10; // Number of items per page

        Page<Product> productPage = productService.getProductsByName(query, page, size);

        Product[] productsNew = productPage.getContent().toArray(new Product[0]);


        model.addAttribute("query", query);
        model.addAttribute("page", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("products", productsNew);

        return "screens/products/search";
    }
}
