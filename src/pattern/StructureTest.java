package pattern;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

class StructureTest {

	@Test
	void testMovableAdapter() {
		System.out.println("*********MovableAdapter()**********************");
        Movable mcLaren = new McLaren();
        MovableAdapter mcLarenAdapter = new MovableAdapterImpl(mcLaren);
        System.out.println("McLaren F1 top speed is " + mcLarenAdapter.getSpeed() + " Kmph.");

        Movable astonMartin = new AstonMartin();
        MovableAdapter astonMartinAdapter = new MovableAdapterImpl(astonMartin);
        System.out.println("McLaren F1 top speed is " + astonMartinAdapter.getSpeed() + " Kmph.");
	}
	
	@Test
	void testCompositePattern() {
		System.out.println("*********CompositePattern()**********************");
        Department salesDepartment = new SalesDepartment(1, "Sales department");
        Department financialDepartment = new FinancialDepartment(2, "Financial department");

        HeadDepartment headDepartment = new HeadDepartment(3, "Head department");

        headDepartment.addDepartMent(salesDepartment);
        headDepartment.addDepartMent(financialDepartment);

        headDepartment.printDepartmentName();
	}
	
	
	@Test
	void testBridgePattern() {
		System.out.println("*********BridgePattern()**********************");
        //a square with red color
        Shape square = new Square(new Red());
        System.out.println(square.draw());
        
        //a circle with blue color
        Shape circle = new Circle(new Blue());
        System.out.println(circle.draw());
	}
	
	@Test
	void testDecoratorPattern() {
		System.out.println("*********DecoratorPattern()**********************");
	}
	
	@Test
	void testFacadePattern() {
		System.out.println("*********FacadePattern()**********************");
	}
	
	@Test
	void testProxyPattern() {
		System.out.println("*********ProxyPattern()**********************");
	}
		

}

// Adapter Pattern
interface Movable{
	//speed in MPH
	double getSpeed();
}
class AstonMartin implements Movable {
    @Override
    public double getSpeed() {
        return 220;
    }
}
class McLaren implements Movable {
    @Override
    public double getSpeed() {
        return 241;
    }
}
interface MovableAdapter {
    // returns speed in KMPH 
    double getSpeed();
}
class MovableAdapterImpl implements MovableAdapter {
    private Movable luxuryCars;
    
    public MovableAdapterImpl(Movable luxuryCars) {
        this.luxuryCars = luxuryCars;
    }

    @Override
    public double getSpeed() {
        double mph = luxuryCars.getSpeed();
        return convertMPHtoKMPH(mph);
    }
    
    private double convertMPHtoKMPH(double mph) {
        return mph * 1.60934;
    }
}



// Composite Pattern
interface Department {
    void printDepartmentName();
}

class FinancialDepartment implements Department {
    Integer id;
    String name;

    public FinancialDepartment(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void printDepartmentName() {
        System.out.println(getClass().getSimpleName());
    }
}

class SalesDepartment implements Department {
    Integer id;
    String name;

    public SalesDepartment(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public void printDepartmentName() {
        System.out.println(getClass().getSimpleName());
    }
}

class HeadDepartment implements Department {
    Integer id;
    String name;

    private List<Department> childDepartments;

    public HeadDepartment(Integer id, String name) {
        this.id = id;
        this.name = name;
        this.childDepartments = new ArrayList<Department>();
    }

    public void printDepartmentName() {
        childDepartments.forEach(Department::printDepartmentName);
    }

    public void addDepartMent(Department department) {
        childDepartments.add(department);
    }

    public void removeDepartment(Department department) {
        childDepartments.remove(department);
    }
}

// Bridge Pattern
interface Color {
    String fill();
}
class Blue implements Color {
    @Override
    public String fill() {
        return "Color is Blue";
    }
}
class Red implements Color {
    @Override
    public String fill() {
        return "Color is Red";
    }
}


abstract class Shape {
    protected Color color;
    
    public Shape(Color color) {
        this.color = color;
    }
    
    abstract public String draw();
}

class Square extends Shape {

    public Square(Color color) {
        super(color);
    }

    @Override
    public String draw() {
        return "Square drawn. " + color.fill();
    }
}

class Circle extends Shape {

    public Circle(Color color) {
        super(color);
    }

    @Override
    public String draw() {
        return "Circle drawn. "+ color.fill();
    }
}
