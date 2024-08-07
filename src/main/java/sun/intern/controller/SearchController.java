package sun.intern.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
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
    public ModelAndView searchPage(
        @RequestParam(name = "key", required = false, defaultValue = "") String query,
        @RequestParam(name = "page", required = false, defaultValue = "1") int page
    ) {
        ModelAndView modelAndView = new ModelAndView("screens/products/search");

        int size = 10; // Number of items per page

        Page<Product> productPage = productService.getProductsByName(query, page, size);

        Product[] productsNew = productPage.getContent().toArray(new Product[0]);

        for(Product product : productsNew) {
            System.out.print(product.getName() + " ");
        }

        modelAndView.addObject("query", query);
        modelAndView.addObject("page", page);
        modelAndView.addObject("totalPages", productPage.getTotalPages());
        modelAndView.addObject("products", productsNew);

        return modelAndView;
    }
}
