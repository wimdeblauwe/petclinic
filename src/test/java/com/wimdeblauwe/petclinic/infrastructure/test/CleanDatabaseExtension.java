package com.wimdeblauwe.petclinic.infrastructure.test;

import com.wimdeblauwe.petclinic.infrastructure.repository.DatabaseCleaner;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class CleanDatabaseExtension implements AfterEachCallback {

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    var applicationContext = SpringExtension.getApplicationContext(context);
    DatabaseCleaner cleaner = applicationContext.getBean(DatabaseCleaner.class);
    cleaner.clean();
  }
}