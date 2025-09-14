import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductManagement {
  private List<Product> products;

  public ProductManagement() {
    this.products = new ArrayList<>();
  }

  public void addProduct(Product product) {
    products.add(product);
  }

  public Product findProductById(String id) {
    for (Product p : products) {
      if (p.getId().equals(id)) {
        return p;
      }
    }
    return null;
  }

  public void updateProduct(String id, String newName, double newPrice, String newDescription, String newCategory,
      int newQuantity) {
    Product product = findProductById(id);
    if (product != null) {
      if (newName.isEmpty()) {
        product.setName(newName);
      }
      product.setPrice(newPrice);
      if (newDescription.isEmpty()) {
        product.setDescription(newDescription);
      }
      if (newCategory.isEmpty()) {
        product.setCategory(newCategory);
      }
      product.setQuantity(newQuantity);
    }
  }

  public List<Product> getProductsSortedBy(int sortChoice) {
    List<Product> sortedProducts = new ArrayList<>(products);
    if (sortChoice == 1) {
      sortedProducts.sort(Comparator.comparingDouble(Product::getPrice));
    } else if (sortChoice == 2) {
      sortedProducts.sort(Comparator.comparingDouble(Product::getPrice).reversed());
    } else if (sortChoice == 3) {
      sortedProducts.sort(Comparator.comparing(Product::getCategory));
    }
    return sortedProducts;
  }

  public Map<String, Double> calculateInventoryValueByCategory() {
    Map<String, Double> categoryValues = new HashMap<>();
    for (Product p : products) {
      String cat = p.getCategory();
      double value = p.getPrice() * p.getQuantity();
      categoryValues.put(cat, categoryValues.getOrDefault(cat, 0.0) + value);
    }
    return categoryValues;
  }

  public void applyDiscount(String id, double discountRate) {
    Product product = findProductById(id);
    if (product != null) {
      product.setDiscountRate(discountRate);
    }
  }

  public double placeOrder(List<String> productIds, List<Integer> quantities) {
    double total = 0.0;
    if (productIds.size() == quantities.size()) {
      for (int i = 0; i < productIds.size(); i++) {
        Product product = findProductById(productIds.get(i));
        if (product != null) {
          int qty = quantities.get(i);
          product.updateQuantity(qty);
          total += product.getDiscountedPrice() * qty;
        }
      }
    }
    return total;
  }

  public List<Product> getProducts() {
    return new ArrayList<>(products);
  }
}
