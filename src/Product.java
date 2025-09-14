public class Product {
  private String id;
  private String name;
  private double price;
  private String description;
  private String category;
  private int quantity;
  private double discountRate;

  public Product(String id, String name, double price, String description, String category, int quantity) {
    this.id = id;
    this.name = name;
    this.price = price;
    this.description = description;
    this.category = category;
    this.quantity = quantity;
    this.discountRate = 0.0;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public double getPrice() {
    return price;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public int getQuantity() {
    return quantity;
  }

  public void setQuantity(int quantity) {
    this.quantity = quantity;
  }

  public double getDiscountRate() {
    return discountRate;
  }

  public void setDiscountRate(double discountRate) {
    this.discountRate = discountRate;
  }

  public double getDiscountedPrice() {
    return price * (1 - discountRate);
  }

  public void updateQuantity(int sold) {
    if (sold <= quantity) {
      quantity -= sold;
    } else {
      System.out.println("Không đủ hàng tồn kho!");
    }
  }

  @Override
  public String toString() {
    return "Product [ID=" + id + ", Name=" + name + ", Price=" + price + ", Discount=" + (discountRate * 100) + "%, "
        + "Discounted Price=" + getDiscountedPrice() + ", Description=" + description + ", Category=" + category
        + ", Quantity=" + quantity + "]";
  }
}
