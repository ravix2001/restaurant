Dependency Injection:

@Autowired Annotation:
```
@Autowired
private final MenuService menuService;
```
When Spring encounters this annotation, it automatically injects the appropriate bean at runtime.

Constructor Injection:
```
private final MenuService menuService;
public MenuController(MenuService menuService) {
    this.menuService = menuService;
}
```
It is a preferred approach in Spring for its simplicity, immutability, and compile-time safety.
</br>
In this example, the dependency is injected during object creation, ensuring that the MenuService is always initialized.