public abstract class User {
    protected String userId;
    protected String name;
    protected String role; // Admin, Pharmacist, or Customer
    
    public User(String userId, String name, String role) {
        this.userId = userId;
        this.name = name;
        this.role = role;
    }
    
    // Common methods for all users
    public void login() {
        System.out.println(name + " (" + role + ") logged in");
    }
    
    public void logout() {
        System.out.println(name + " logged out");
    }
}