Google Translate Library
======================
[![Build Status](https://travis-ci.org/ligboy/google-translate.svg?branch=master)](https://travis-ci.org/ligboy/google-translate)
[![Download](https://api.bintray.com/packages/ligboy/maven/google-translate/images/download.svg)](https://bintray.com/ligboy/maven/google-translate/_latestVersion)

Usage
-------
### Via Maven:
#### dependency:
```xml
<dependency>
  <groupId>org.ligboy.library</groupId>
  <artifactId>google-translate</artifactId>
  <version>1.0.1</version>
</dependency>
```
### or Gradle:
#### dependency:
```groovy
compile 'org.ligboy.library:google-translate:1.0.1'
```
Java Code

```java
    final Translate translate = new Translate.Builder()
            .logLevel(Translate.LogLevel.BODY)
            .build();
    try {
        translate.refreshTokenKey();
        TranslateResult result = translate.translate("面具下是思想，思想永不惧怕子弹。",
                Translate.SOURCE_LANG_AUTO, "en");
        System.out.println(result);
    } catch (Exception e) {
        e.printStackTrace();
    }
```
Author
=======
 * Ligboy Liu

License
=======
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.