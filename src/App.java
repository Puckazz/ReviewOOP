import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class App {
    private static ProductManagement productManagement = new ProductManagement();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        boolean running = true;
        while (running) {
            System.out.println();
            System.out.println("\n--- Quan ly san pham ---");
            System.out.println("1. Them san pham moi");
            System.out.println("2. Cap nhat thong tin san pham");
            System.out.println("3. Hien thi danh sach san pham (theo gia hoac danh muc)");
            System.out.println("4. Tinh tong gia tri hang ton kho theo danh muc");
            System.out.println("5. Ap dung giam gia cho san pham");
            System.out.println("6. Dat hang va tinh tong tien");
            System.out.println("7. Thoat");
            System.out.print("Chon chuc nang: ");
            int choice;
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    addProduct();
                    break;
                case 2:
                    updateProduct();
                    break;
                case 3:
                    displayProducts();
                    break;
                case 4:
                    calculateInventoryValueByCategory();
                    break;
                case 5:
                    applyDiscount();
                    break;
                case 6:
                    placeOrder();
                    break;
                case 7:
                    running = false;
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        }
        scanner.close();
    }

    private static void addProduct() {
        System.out.print("ID: ");
        String id = scanner.nextLine();
        System.out.print("Ten: ");
        String name = scanner.nextLine();
        System.out.print("Gia: ");
        double price = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Mo ta: ");
        String description = scanner.nextLine();
        System.out.print("Danh muc: ");
        String category = scanner.nextLine();
        System.out.print("So luong: ");
        int quantity = scanner.nextInt();
        scanner.nextLine();

        productManagement.addProduct(new Product(id, name, price, description, category, quantity));
        System.out.println("Them san pham thanh cong!");
    }

    private static void updateProduct() {
        System.out.print("Nhap ID san pham can cap nhat: ");
        String id = scanner.nextLine();
        Product product = productManagement.findProductById(id);
        if (product != null) {
            System.out.println("Thông tin hiện tại: " + product);
            System.out.print("Ten moi: ");
            String newName = scanner.nextLine();
            System.out.print("Gia moi: ");
            double newPrice = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Mo ta moi:");
            String newDesc = scanner.nextLine();
            System.out.print("Danh muc moi:");
            String newCat = scanner.nextLine();
            System.out.print("So luong moi:");
            int newQty = scanner.nextInt();
            scanner.nextLine();

            productManagement.updateProduct(id, newName, newPrice, newDesc, newCat, newQty);
            System.out.println("Cập nhật thành công: " + productManagement.findProductById(id));
        } else {
            System.out.println("Khong tim thay san pham");
        }
    }

    private static void displayProducts() {
        System.out.println("1. Theo gia tang dan");
        System.out.println("2. Theo gia giam dan");
        System.out.println("3. Theo danh muc");
        System.out.print("Chon cach hien thi: ");
        int sortChoice;
        try {
            sortChoice = scanner.nextInt();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Lua chon khong hop le!");
            return;
        }

        List<Product> sortedProducts = productManagement.getProductsSortedBy(sortChoice);
        System.out.println("\nDanh sach san pham:");
        for (Product p : sortedProducts) {
            System.out.println(p);
        }
    }

    private static void calculateInventoryValueByCategory() {
        Map<String, Double> categoryValues = productManagement.calculateInventoryValueByCategory();
        System.out.println("\nTong gia tri hàng ton kho theo danh muc:");
        for (Map.Entry<String, Double> entry : categoryValues.entrySet()) {
            System.out.println("Danh muc: " + entry.getKey() + " - Tong gia tri: " + entry.getValue());
        }
    }

    private static void applyDiscount() {
        System.out.print("Nhap ID san pham can giam gia: ");
        String id = scanner.nextLine();
        System.out.print("Nhap ty le giam gia (vi du 0.1 cho 10%): ");
        double discountRate;
        try {
            discountRate = scanner.nextDouble();
            scanner.nextLine();
        } catch (Exception e) {
            scanner.nextLine();
            System.out.println("Ty le giam gia khong hop le!");
            return;
        }
        productManagement.applyDiscount(id, discountRate);
        System.out.println("Ap dung giam gia thanh cong: " + productManagement.findProductById(id));
    }

    private static void placeOrder() {
        List<String> productIds = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        boolean adding = true;

        while (adding) {
            System.out.print("Nhap ID san pham de dat hang (enter de ket thuc): ");
            String id = scanner.nextLine();
            if (id.isEmpty()) {
                adding = false;
                continue;
            }
            Product product = productManagement.findProductById(id);
            if (product != null) {
                System.out.print("So luong: ");
                int qty;
                    qty = scanner.nextInt();
                    scanner.nextLine();
                productIds.add(id);
                quantities.add(qty);
            } else {
                System.out.println("Khong tim thay san pham!");
            }
        }

        if (!productIds.isEmpty()) {
            double total = productManagement.placeOrder(productIds, quantities);
            System.out.println("\nDon hang:");
            for (int i = 0; i < productIds.size(); i++) {
                Product p = productManagement.findProductById(productIds.get(i));
                int qty = quantities.get(i);
                System.out.println(p.getName() + " x " + qty + " = " + (p.getDiscountedPrice() * qty));
            }
            System.out.println("Tong tien: " + total);
        } else {
            System.out.println("Khong co san pham nao duoc dat!");
        }
    }
}
