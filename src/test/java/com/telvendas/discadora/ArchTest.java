package com.telvendas.discadora;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.telvendas.discadora");

        noClasses()
            .that()
            .resideInAnyPackage("com.telvendas.discadora.service..")
            .or()
            .resideInAnyPackage("com.telvendas.discadora.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.telvendas.discadora.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
