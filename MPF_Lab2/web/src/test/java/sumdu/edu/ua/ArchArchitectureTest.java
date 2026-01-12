package sumdu.edu.ua;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class ArchArchitectureTest {

    @Test
    void webShouldNotDependOnPersistenceDirectly() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("sumdu.edu.ua");

        ArchRule rule = noClasses()
                .that().resideInAPackage("..web..")
                .should().dependOnClassesThat()
                .resideInAPackage("..persistence..");

        // Цей тест може впасти, якщо ви в сервлетах робите "new JdbcBookRepository()"
        // За правилами треба використовувати інтерфейс CatalogRepositoryPort
        rule.check(importedClasses);
    }

    @Test
    void servletsShouldBeInWebPackage() {
        JavaClasses importedClasses = new ClassFileImporter().importPackages("sumdu.edu.ua");

        classes()
                .that().haveSimpleNameEndingWith("Servlet")
                .should().resideInAPackage("..web..")
                .check(importedClasses);
    }
}