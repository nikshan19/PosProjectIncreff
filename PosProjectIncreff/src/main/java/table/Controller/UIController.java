package table.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import table.Model.ErrorData;
import table.Model.InfoData;

@Controller
public class UIController {

	@Value("${app.baseUrl}")
	private String baseUrl;

	@RequestMapping(value = "")
	public String index() {
		return "index.html";
	}

	@RequestMapping(value = "/ui/home")
	public ModelAndView home() {
		return mav("home.html");
	}

	@RequestMapping(value = "/ui/products")
	public ModelAndView features() {
		return mav("products.html");
	}

	@RequestMapping(value = "/ui/order2")
	public ModelAndView order() {
		return mav("order2.html");
	}

	@RequestMapping(value = "/ui/placeorder")
	public ModelAndView placeorder() {
		return mav("placeorder.html");
	}

	@RequestMapping(value = "/ui/inventory")
	public ModelAndView inventory() {
		return mav("inventory.html");
	}

	@RequestMapping(value = "/ui/editorder/{id}")
	public ModelAndView EditOrder() {
		return mav("editorder.html");
	}

	@RequestMapping(value = "/ui/brandreport")
	public ModelAndView BrandReport() {
		return mav("brandreport.html");
	}

	@RequestMapping(value = "/ui/inventoryreport")
	public ModelAndView InventoryReport() {
		return mav("inventoryreport.html");
	}

	@RequestMapping(value = "/ui/salesreport")
	public ModelAndView SalesReport() {
		return mav("salesreport.html");
	}

	@RequestMapping(value = "/ui/cart")
	public ModelAndView Cart() {
		return mav("cart.html");
	}

	@RequestMapping(value = "/ui/invoice")
	public ModelAndView Invoice() {
		return mav("invoice.html");
	}

	protected ModelAndView mav(String page) {

		ModelAndView mav = new ModelAndView(page);
		mav.addObject("info", new InfoData());
		mav.addObject("baseUrl", baseUrl);
		mav.addObject("apierror", new ErrorData());
		return mav;
	}

}