# 1.5 Bean scopes

Beans are like rifles. They have scopes.

There are 6 of them. But you can also create your own.

| Scope     | Description   |
|-----------|---------------|
| singleton | (Default) Scopes a single bean definition to a single object instance for each Spring IoC container. |
| prototype | Scopes a single bean definition to any number of object instances. |
| request   | Scopes a single bean definition to the lifecycle of a single HTTP request. That is, each HTTP request has its own instance of a bean created off the back of a single bean definition. Only valid in the context of a web-aware Spring ApplicationContext. |
| session   | Scopes a single bean definition to the lifecycle of an HTTP Session. Only valid in the context of a web-aware Spring ApplicationContext. |
| application | Scopes a single bean definition to the lifecycle of a ServletContext. Only valid in the context of a web-aware Spring ApplicationContext. |
| websocket | Scopes a single bean definition to the lifecycle of a WebSocket. Only valid in the context of a web-aware Spring ApplicationContext. |

Since Spring 3.0 there's also Thread scope (but not registered by default).

As a rule, you should use the prototype scope for all stateful beans and the singleton scope for stateless beans.

In contrast to the other scopes, Spring does not manage the complete lifecycle of a prototype bean. The container instantiates, configures, and otherwise assembles a prototype object and hands it to the client, with no further record of that prototype instance.

---

The request, session, application, and websocket scopes are available only if you use a web-aware 
Spring `ApplicationContext` implementation (such as `XmlWebApplicationContext`).

# 1.6 Customizing the Nature of a Bean 

JSR-250 `@PostConstruct` and `@PreDestroy` annotations - best practice.

Internally Spring uses `BeanPostProcessor` to process any callback interfaces.

Don't use the `InitializingBean` interface - it's the same effect as `init-method` / `@PostConstruct`, but couples
the code to Spring unnecessarily.

The Spring container guarantees that a configured initialization callback is called immediately after a bean is supplied with all dependencies. Thus, the initialization callback is called on the raw bean reference, which means that AOP interceptors and so forth are not yet applied to the bean.

A target bean is fully created first and then an AOP proxy (for example) with its interceptor chain is applied.

The `Lifecycle` interface defines the essential methods for any object that has its own lifecycle requirements (such as starting and stopping some background process).


## 1.6.2 `ApplicationContextAware` and `BeanNameAware`

When an `ApplicationContext` creates an object instance that implements the `org.springframework.context.ApplicationContextAware` 
interface, the instance is provided with a reference to that `ApplicationContext`.

# 1.7. Bean Definition Inheritance

A bean definition can contain a lot of configuration information, including constructor arguments, property values, and container-specific information, such as the initialization method, a static factory method name, and so on. A child bean definition inherits configuration data from a parent definition. The child definition can override some values or add others as needed. Using parent and child bean definitions can save a lot of typing. Effectively, this is a form of templating.

# 1.8. Container Extension Points

The BeanPostProcessor interface defines callback methods that you can implement to provide your own (or override the container’s default) instantiation logic, dependency resolution logic, and so forth.

## 1.8.2 `BeanFactoryPostProcessor`

- operates on bean configuration metadata
- you can change configuration metadata before the container instantiates any other beans
- you can have multiple `BeanFactoryPostProcessor`s and control the order in which they run with the `order` property

## 1.8.3. Customizing Instantiation Logic with a FactoryBean
You can implement the org.springframework.beans.factory.FactoryBean interface for objects that are themselves factories.
The FactoryBean interface is a point of pluggability into the Spring IoC container’s instantiation logic. 

# 1.9. Annotation-based Container Configuration
- bytecode metadata for wiring up components instead of angle-bracket declarations
- annotations on the relevant class, method, or field declaration

Annotation injection is performed before XML injection. 
Thus, the XML configuration overrides the annotations for properties wired through both approaches.

> <context:annotation-config/> only looks for annotations on beans in the same application context in which it is defined. 
>This means that, if you put <context:annotation-config/> in a WebApplicationContext for a DispatcherServlet, it only 
>checks for @Autowired beans in your controllers, and not your services.

## 1.9.1. @Required

- The @Required annotation applies to bean property setter methods
- This annotation indicates that the affected bean property must be populated at configuration time, 
through an explicit property value in a bean definition or through autowiring. 
The container throws an exception if the affected bean property has not been populated.
	
The @Required annotation is formally deprecated as of Spring Framework 5.1, in favor of using constructor 
injection for required settings (or a custom implementation of InitializingBean.afterPropertiesSet() along with 
bean property setter methods).

## 1.9.2. Using @Autowired

> JSR 330’s @Inject annotation can be used in place of Spring’s @Autowired annotation

	
Only one constructor of any given bean class may declare @Autowired with the required attribute set to true, 
indicating the constructor to autowire when used as a Spring bean. 

As a consequence, if the required attribute is left at its default value true, only a single constructor may be annotated with @Autowired. 

If multiple constructors declare the annotation, they will all have to declare required=false in order to be considered 
as candidates for autowiring (analogous to autowire=constructor in XML). The constructor with the greatest number of 
dependencies that can be satisfied by matching beans in the Spring container will be chosen. 

If none of the candidates can be satisfied, then a primary/default constructor (if present) will be used. 

Similarly, if a class declares multiple constructors but none of them is annotated with @Autowired, then a 
primary/default constructor (if present) will be used. 

If a class only declares a single constructor to begin with, it will always be used, even if not annotated. 
Note that an annotated constructor does not have to be public.

The required attribute of @Autowired is recommended over the deprecated @Required annotation on setter methods.

Alternatively, you can express the non-required nature of a particular dependency through Java 8’s java.util.Optional

```
public class SimpleMovieLister {

    @Autowired
    public void setMovieFinder(Optional<MovieFinder> movieFinder) {
        ...
    }
}
```

As of Spring Framework 5.0, you can also use a @Nullable annotation.

You can also use @Autowired for interfaces that are well-known resolvable dependencies: BeanFactory, ApplicationContext, 
Environment, ResourceLoader, ApplicationEventPublisher, and MessageSource.

## 1.9.3. Fine-tuning Annotation-based Autowiring with @Primary

`@Primary` – a particular bean will be given preference when multiple beans are candidates to be 
autowired to a single-valued dependency.

## 1.9.4. Fine-tuning Annotation-based Autowiring with Qualifiers

You can associate qualifier values with specific arguments, narrowing the set of type matches so that a 
specific bean is chosen for each argument.

```
    @Autowired
    @Qualifier("main")
    private MovieCatalog movieCatalog;
```

## 1.9.5. Using Generics as Autowiring Qualifiers

In addition to the @Qualifier annotation, you can use Java generic types as an implicit form of qualification.

```
@Autowired
private Store<String> s1; // <String> qualifier, injects the stringStore bean

@Autowired
private Store<Integer> s2; // <Integer> qualifier, injects the integerStore bean
```

## 1.9.7. Injection with `@Resource` [#](https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#beans-resource-annotation)

Spring also supports injection by using the JSR-250 `@Resource` annotation (javax.annotation.Resource) on fields or 
bean property setter methods.

```
    @Resource(name="myMovieFinder") 
    public void setMovieFinder(MovieFinder movieFinder) {
        this.movieFinder = movieFinder;
    }
```

## 1.9.8. Using `@Value`

`@Value` is typically used to inject externalized properties:

```java
@Component
public class MovieRecommender {

    private final String catalog;

    public MovieRecommender(@Value("${catalog.name:defaultCatalog}") String catalog) {
        this.catalog = catalog;
    }
}
```

(`:defaultCatalog` is the optional default value)

With the following configuration:

```java
@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig { }
```

And the following `application.properties` file:
```
catalog.name=MovieCatalog
```

Spring Boot configures by default a `PropertySourcesPlaceholderConfigurer` bean that will get properties 
from `application.properties` and `application.yml` files.

## 1.9.9. Using @PostConstruct and @PreDestroy

```java
public class CachingMovieLister {

    @PostConstruct
    public void populateMovieCache() {
        // populates the movie cache upon initialization...
    }

    @PreDestroy
    public void clearMovieCache() {
        // clears the movie cache upon destruction...
    }
}
```

## 1.10. Classpath Scanning and Managed Components

This section describes an option for implicitly detecting the candidate components by scanning the classpath. 
Candidate components are classes that match against a filter criteria and have a corresponding bean definition 
registered with the container. This removes the need to use XML to perform bean registration. Instead, you can use 
annotations (for example, @Component), AspectJ type expressions, or your own custom filter criteria to select which 
classes have bean definitions registered with the container.

### 1.10.1. @Component and Further Stereotype Annotations

The `@Repository` annotation is a marker for any class that fulfills the role or stereotype of a repository 
(also known as Data Access Object or DAO).

`@Component` is a generic stereotype for any Spring-managed component.
There are more specialized ones: `@Component`, `@Service`, and `@Controller`. You can use `@Component` everywhere,
but using the dedicated ones makes the beans better targets for pointcuts in AOP, convey the type of the bean better 
to the reader and can provide some unique features, e.g. automatic translation of exceptions in the case of `@Repository`.

### 1.10.2. Using Meta-annotations and Composed Annotations

Meta-annotation: can be applied to another annotation. Example:

```
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component 
public @interface Service {

    // ...
}
```

You can also combine meta-annotations to create “composed annotations”. For example, the `@RestController` 
annotation from Spring MVC is composed of `@Controller` and `@ResponseBody`.

