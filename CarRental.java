import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Car {
    private int Carid;
    private String Brand;
    private String Model;
    private double Priceperday;
    private boolean isAvailable;

    public Car(int carid, String Brand, String Model, double Priceperday, boolean isAvailable) {
        this.Carid = carid;
        this.Brand = Brand;
        this.Model = Model;
        this.Priceperday = Priceperday;
        this.isAvailable = isAvailable;
    }

    public int getCarid() {
        return Carid;
    }

    public String getBrand() {
        return Brand;
    }

    public String getModel() {
        return Model;
    }

    public double calculateprice(int rentaldays) {
        return Priceperday * rentaldays;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void rent() {
        isAvailable = false;
    }

    public void returncar() {
        isAvailable = true;
    }
}

class Customer {
    private int customerid;
    private String name;

    public Customer(int customerid, String name) {
        this.customerid = customerid;
        this.name = name;
    }

    public int getCustomerid() {
        return customerid;
    }

    public String getName() {
        return name;
    }
}

class Rental {
    private Car car;
    private Customer customer;
    private int days;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
    }

    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }

    public int getDays() {
        return days;
    }
}

class CarRentalsys {
    private List<Car> cars;
    private List<Customer> customers;
    private List<Rental> rentals;

    public CarRentalsys() {
        cars = new ArrayList<>();
        customers = new ArrayList<>();
        rentals = new ArrayList<>();
    }

    public void addCar(Car car) {
        cars.add(car);
    }

    public void addCustomers(Customer customer) {
        customers.add(customer);
    }

    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            rentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Car is not available for rent");
        }
    }

    public void returnCar(Car car) {
        car.returncar();
        Rental rentalToRemove = null;
        for (Rental rental : rentals) {
            if (rental.getCar() == car) {
                rentalToRemove = rental;
                break;
            }
        }
        if (rentalToRemove != null) {
            rentals.remove(rentalToRemove);
        } else {
            System.out.println("Car was not rented");
        }
    }

    public void menu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("====Car Rental System====");
            System.out.println("1. Rent a Car");
            System.out.println("2. Return a Car");
            System.out.println("3. Exit");
            System.out.println("Enter your choice:");

            int choice = scanner.nextInt();
            scanner.nextLine();

            if (choice == 1) {
                System.out.println("Rent a Car");
                System.out.println("Enter your name:");
                String customerName = scanner.nextLine();

                System.out.println("Available Cars:");
                for (Car car : cars) {
                    if (car.isAvailable()) {
                        System.out.println(car.getCarid() + "  " + car.getBrand() + "  " + car.getModel());
                    }
                }

                System.out.println("Enter the car ID you want to rent:");
                int carid = scanner.nextInt();
                scanner.nextLine();

                System.out.println("Enter the number of days for rental:");
                int rentalDays = scanner.nextInt();
                scanner.nextLine();


                int newCustomerId = customers.size() + 1;
                Customer newCustomer = new Customer(newCustomerId, customerName);
                addCustomers(newCustomer);

                Car selectedCar = null;
                for (Car car : cars) {
                    if (car.getCarid() == carid) {
                        selectedCar = car;
                        break;
                    }
                }

                if (selectedCar != null) {
                    double totalprice = selectedCar.calculateprice(rentalDays);
                    System.out.println("\n== Rental Information ==");
                    System.out.println("Customer ID: " + newCustomer.getCustomerid());
                    System.out.println("Customer Name: " + newCustomer.getName());
                    System.out.println("Car: " + selectedCar.getBrand() + " " + selectedCar.getModel());
                    System.out.println("Rental Days: " + rentalDays);
                    System.out.printf("Total Price: $%.2f%n", totalprice);

                    System.out.println("\nConfirm rental (Y/N):");
                    String confirm = scanner.nextLine();

                    if (confirm.equalsIgnoreCase("y")) {
                        rentCar(selectedCar, newCustomer, rentalDays);
                        System.out.println("\nCar rented successfully");
                    } else {
                        System.out.println("Rental canceled");
                    }
                } else {
                    System.out.println("Car with the given ID is not available.");
                }
            } else if (choice == 2) {
                System.out.println("=== Return a Car ===");
                System.out.println("Enter the car ID you want to return:");
                int carid = scanner.nextInt();
                scanner.nextLine();

                Car cartoreturn = null;
                for (Car car : cars) {
                    if (car.getCarid() == carid && !car.isAvailable()) {
                        cartoreturn = car;
                        break;
                    }
                }

                if (cartoreturn != null) {
                    Customer customer = null;
                    for (Rental rental : rentals) {
                        if (rental.getCar() == cartoreturn) {
                            customer = rental.getCustomer();
                            break;
                        }
                    }
                    if (customer != null) {
                        returnCar(cartoreturn);
                        System.out.println("Car returned successfully by " + customer.getName());
                    } else {
                        System.out.println("Car was not rented or rental information is missing");
                    }
                } else {
                    System.out.println("Car was not found or is already available");
                }
            } else if (choice == 3) {
                break;
            } else {
                System.out.println("Invalid choice. Enter a valid option.");
            }
        }
        System.out.println("Thank you for using the car rental system.");
    }
}

public class CarRental {
    public static void main(String[] args) {
        CarRentalsys rentalsys = new CarRentalsys();

        Car car1 = new Car(1, "Toyota", "Camry", 60.0, true);
        Car car2 = new Car(2, "Honda", "Civic", 50.0, true);
        Car car3 = new Car(3, "Ford", "Mustang", 70.0, true);

        rentalsys.addCar(car1);
        rentalsys.addCar(car2);
        rentalsys.addCar(car3);

        rentalsys.menu();
    }
}
