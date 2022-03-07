### Description
Spring Boot starter test extended features.

Features:
- Component test annotations
- Mocks service
- Generic test result messages

### Configuration
Add the following config params with prefix *spring.custom.extended-test* to test configuration file (for example src/test/resources/application.yaml):
- mocksPath: path of mock files
- wordsJoiner: symbol used as word tokenizer
- collectionsLabel: added suffix to any collection name

Example of configuration file (YAML format):
 ``` yaml
spring:
  custom:
    extended-test:
      # mocksPath
      mocksPath: "src/test/resources/mocks"
      # wordsJoiner e.g "classType_test.yaml"
      wordsJoiner: "_"
      # collectionsLabel e.g "classType(s)_test.yaml"
      collectionsLabel: "s"
```

