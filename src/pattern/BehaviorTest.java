package pattern;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;


class BehaviorTest {

	@Test
	void testObserver() {
		System.out.println("*********Observer()**********************");

	}
	
	@Test
	void testChainOfResponsibility() {
		System.out.println("*********ChainOfResponsibility()**********************");

	}
	
	@Test
	void testCommand() {
		System.out.println("*********Command()**********************");

	}
	
	@Test
	void testVisitor() {
		System.out.println("*********Visitor()**********************");

	}
	
	@Test
	void testTemplateMethod() {
		System.out.println("*********TemplateMethod()**********************");

	}
	
	
	@Test
	void testMediator() {
		System.out.println("*********Mediator()**********************");

	}
	
	@Test
	void testInterpreter() {
		System.out.println("*********Interpreter()**********************");
        Expression query = new Select("name", new From("people"));
        Context ctx = new Context();
        List<String> result = query.interpret(ctx);
        System.out.println(result);

        Expression query2 = new Select("*", new From("people"));
        List<String> result2 = query2.interpret(ctx);
        System.out.println(result2);

        Expression query3 = new Select("name", new From("people", new Where(name -> name.toLowerCase().startsWith("d"))));
        List<String> result3 = query3.interpret(ctx);
        System.out.println(result3);
	}
}





// Observer
interface Channel {
    public void update(Object o);
}

class NewsAgency {
    private String news;
    private List<Channel> channels = new ArrayList<>();

    public void addObserver(Channel channel) {
        this.channels.add(channel);
    }

    public void removeObserver(Channel channel) {
        this.channels.remove(channel);
    }

    public void setNews(String news) {
        this.news = news;
        for (Channel channel : this.channels) {
            channel.update(this.news);
        }
    }
}
class NewsChannel implements Channel {

    private String news;

    @Override
    public void update(Object news) {
        this.setNews((String) news);
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}

class ONewsAgency extends Observable {
    private String news;

    public void setNews(String news) {
        this.news = news;
        setChanged();
        notifyObservers(news);
    }
}
class ONewsChannel implements Observer {

    private String news;

    @Override
    public void update(Observable o, Object news) {
        this.setNews((String) news);
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}

class PCLNewsAgency {
    private String news;

    private PropertyChangeSupport support;

    public PCLNewsAgency() {
        support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        support.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        support.removePropertyChangeListener(pcl);
    }

    public void setNews(String value) {
        support.firePropertyChange("news", this.news, value);
        this.news = value;

    }
}
class PCLNewsChannel implements PropertyChangeListener {

    private String news;

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        this.setNews((String) evt.getNewValue());
    }

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }
}




// Mediator
class Mediator {
    private Button button;
    private Fan fan;
    private PowerSupplier powerSupplier;

    public void setButton(Button button) {
        this.button = button;
        this.button.setMediator(this);
    }

    public void setFan(Fan fan) {
        this.fan = fan;
        this.fan.setMediator(this);
    }

    public void setPowerSupplier(PowerSupplier powerSupplier) {
        this.powerSupplier = powerSupplier;
    }

    public void press() {
        if (fan.isOn()) {
            fan.turnOff();
        } else {
            fan.turnOn();
        }
    }

    public void start() {
        powerSupplier.turnOn();
    }

    public void stop() {
        powerSupplier.turnOff();
    }
}
class Button {
    private Mediator mediator;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public void press() {
        this.mediator.press();
    }
}
class Fan {
    private Mediator mediator;
    private boolean isOn = false;

    public void setMediator(Mediator mediator) {
        this.mediator = mediator;
    }

    public boolean isOn() {
        return isOn;
    }

    public void turnOn() {
        this.mediator.start();
        isOn = true;
    }

    public void turnOff() {
        isOn = false;
        this.mediator.stop();
    }
}
class PowerSupplier {
    public void turnOn() {
        // implementation
    }

    public void turnOff() {
        // implementation
    }
}



interface AuthenticationProvider {
}

abstract class AuthenticationProcessor {

    // next element in chain or responsibility
    public AuthenticationProcessor nextProcessor;
    
    public AuthenticationProcessor(AuthenticationProcessor nextProcessor) {
        this.nextProcessor = nextProcessor;
    }

    public abstract boolean isAuthorized(AuthenticationProvider authProvider);
}


class OAuthTokenProvider implements AuthenticationProvider {
}
class OAuthAuthenticationProcessor extends AuthenticationProcessor {

    public OAuthAuthenticationProcessor(AuthenticationProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public boolean isAuthorized(AuthenticationProvider authProvider) {

        if (authProvider instanceof OAuthTokenProvider) {
            return Boolean.TRUE;
        } else if (nextProcessor != null) {
            return nextProcessor.isAuthorized(authProvider);
        } else {
            return Boolean.FALSE;
        }
    }

}

class SamlAuthenticationProvider implements AuthenticationProvider {
}

class UsernamePasswordProvider implements AuthenticationProvider {
}

class UsernamePasswordAuthenticationProcessor extends AuthenticationProcessor {

    public UsernamePasswordAuthenticationProcessor(AuthenticationProcessor nextProcessor) {
        super(nextProcessor);
    }

    @Override
    public boolean isAuthorized(AuthenticationProvider authProvider) {
        if (authProvider instanceof UsernamePasswordProvider) {
            return Boolean.TRUE;
        } else if (nextProcessor != null) {
            return nextProcessor.isAuthorized(authProvider);
        } else {
            return Boolean.FALSE;
        }
    }

}




// Interpreter
class Context {

    private static Map<String, List<Row>> tables = new HashMap<>();

    static {
        List<Row> list = new ArrayList<>();
        list.add(new Row("John", "Doe"));
        list.add(new Row("Jan", "Kowalski"));
        list.add(new Row("Dominic", "Doom"));

        tables.put("people", list);
    }

    private String table;
    private String column;

    /**
     * Index of column to be shown in result.
     * Calculated in {@link #setColumnMapper()}
     */
    private int colIndex = -1;

    /**
     * Default setup, used for clearing the context for next queries.
     * See {@link Context#clear()}
     */
    private static final Predicate<String> matchAnyString = s -> s.length() > 0;
    private static final Function<String, Stream<? extends String>> matchAllColumns = Stream::of;
    /**
     * Varies based on setup in subclasses of {@link Expression}
     */
    private Predicate<String> whereFilter = matchAnyString;
    private Function<String, Stream<? extends String>> columnMapper = matchAllColumns;

    void setColumn(String column) {
        this.column = column;
        setColumnMapper();
    }

    void setTable(String table) {
        this.table = table;
    }

    void setFilter(Predicate<String> filter) {
        whereFilter = filter;
    }

    /**
     * Clears the context to defaults.
     * No filters, match all columns.
     */
    void clear() {
        column = "";
        columnMapper = matchAllColumns;
        whereFilter = matchAnyString;
    }

    List<String> search() {

        List<String> result = tables.entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(table))
                .flatMap(entry -> Stream.of(entry.getValue()))
                .flatMap(Collection::stream)
                .map(Row::toString)
                .flatMap(columnMapper)
                .filter(whereFilter)
                .collect(Collectors.toList());

        clear();

        return result;
    }

    /**
     * Sets column mapper based on {@link #column} attribute.
     * Note: If column is unknown, will remain to look for all columns.
     */
    private void setColumnMapper() {
        switch (column) {
            case "*":
                colIndex = -1;
                break;
            case "name":
                colIndex = 0;
                break;
            case "surname":
                colIndex = 1;
                break;
        }
        if (colIndex != -1) {
            columnMapper = s -> Stream.of(s.split(" ")[colIndex]);
        }
    }
}


interface Expression {
    List<String> interpret(Context ctx);
}
class From implements Expression {

    private String table;
    private Where where;

    From(String table) {
        this.table = table;
    }

    From(String table, Where where) {
        this.table = table;
        this.where = where;
    }

    @Override
    public List<String> interpret(Context ctx) {
        ctx.setTable(table);
        if (where == null) {
            return ctx.search();
        }
        return where.interpret(ctx);
    }
}
class Row {

    private String name;
    private String surname;

    Row(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    @Override
    public String toString() {
        return name + " " + surname;
    }
}

class Select implements Expression {

    private String column;
    private From from;

    Select(String column, From from) {
        this.column = column;
        this.from = from;
    }

    @Override
    public List<String> interpret(Context ctx) {
        ctx.setColumn(column);
        return from.interpret(ctx);
    }
}

class Where implements Expression {

    private Predicate<String> filter;

    Where(Predicate<String> filter) {
        this.filter = filter;
    }

    @Override
    public List<String> interpret(Context ctx) {
        ctx.setFilter(filter);
        return ctx.search();
    }
}