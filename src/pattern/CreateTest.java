package pattern;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import pattern.BankAccount.BankAccountBuilder;

class CreateTest {

	@Test
	void testSingleton() {
		System.out.println("*********Singleton()**********************");
		Singleton singleton = Singleton.getInstance(); 
	}

	@Test
	void testPolygonFactoryMethod() {
		System.out.println("*********PolygonFactoryMethod()**********************");
        Polygon p;
        PolygonFactory factory = new PolygonFactory();
        
        //get the shape which has 4 sides
        p = factory.getPolygon(4);
        System.out.println("The shape with 4 sides is a " + p.getType());
        
        //get the shape which has 3 sides
        p = factory.getPolygon(3);
        System.out.println("The shape with 8 sides is a " + p.getType());
	}

	@Test
	void testBankAccountBuilder() {
	    System.out.println("*********BankAccountBuilder()**********************");
	    
	    BankAccountBuilder builder = new BankAccountBuilder("Jon", "22738022275");
	    builder.withEmail("jon@example.com")
        .wantNewsletter(true);

        BankAccount newAccount = new BankAccount(builder);

        System.out.println("Name: " + newAccount.getName());
        System.out.println("AccountNumber:" + newAccount.getAccountNumber());
        System.out.println("Email: " + newAccount.getEmail());
        System.out.println("Want News letter?: " + newAccount.isNewsletter());
	}
}

// Singleton pattern
class Singleton {
	private static Singleton singleton;
	private static boolean initialized = false;
	private Singleton() {}
	private void init() {}
	
	public static Singleton getInstance() {
		if (initialized) return singleton;
		singleton = new Singleton();
		singleton.init();
		initialized = true;
		return singleton;
	}
}

// Factory method
// Abstract Factory:create families of related or dependent objects. 
// It's also sometimes called a factory of factories. 
class PolygonFactory {
	public Polygon getPolygon (int sides) {
		if (sides == 3) {
			return new Triangle();
		}
		if (sides == 4) {
			return new Rectangle();
		}
		
		return null;
	}
}
interface Polygon{
	String getType();
}
class Triangle implements Polygon {
    @Override
    public String getType() {
        return "Triangle";
    }
}
class Rectangle implements Polygon {
    @Override
    public String getType() {
        return "Rectangle";
    }
}


class BankAccount {
    private String name;
    private String accountNumber;
    private String email;
    private boolean newsletter;

    //The constructor that takes a builder from which it will create object
    //the access to this is only provided to builder
    public BankAccount(BankAccountBuilder builder) {
        this.name = builder.name;
        this.accountNumber = builder.accountNumber;
        this.email = builder.email;
        this.newsletter = builder.newsletter;
    }
    
    public static class BankAccountBuilder {
        private String name;
        private String accountNumber;
        private String email;
        private boolean newsletter;
        
        //All Mandatory parameters goes with this constructor
        public BankAccountBuilder(String name, String accountNumber) {
            this.name = name;
            this.accountNumber = accountNumber;
        }

        //setters for optional parameters which returns this same builder
        //to support fluent design
        public BankAccountBuilder withEmail(String email) {
            this.email = email;
            return this;
        }

        public BankAccountBuilder wantNewsletter(boolean newsletter) {
            this.newsletter = newsletter;
            return this;
        }
        
        //the actual build method that prepares and returns a BankAccount object
        public BankAccount build() {
            return new BankAccount(this);
        }
    }

    //getters
    public String getName() {
        return name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public boolean isNewsletter() {
        return newsletter;
    }
}