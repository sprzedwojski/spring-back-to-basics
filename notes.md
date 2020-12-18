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

> <context:annotation-config/> only looks for annotations on beans in the same application context in which it is defined. This means that, if you put <context:annotation-config/> in a WebApplicationContext for a DispatcherServlet, it only checks for @Autowired beans in your controllers, and not your services.

## 1.9.1. @Required

- The @Required annotation applies to bean property setter methods
- This annotation indicates that the affected bean property must be populated at configuration time, 
through an explicit property value in a bean definition or through autowiring. 
The container throws an exception if the affected bean property has not been populated.
	
The @Required annotation is formally deprecated as of Spring Framework 5.1, in favor of using constructor 
injection for required settings (or a custom implementation of InitializingBean.afterPropertiesSet() along with 
bean property setter methods).

## 1.9.2. Using @Autowired


