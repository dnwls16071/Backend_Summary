# .gitattributes

```
/gradlew text eol=lf
*.bat text eol=crlf
*.jar binary

```

# .gitignore

```
HELP.md
.gradle
build/
!gradle/wrapper/gradle-wrapper.jar
!**/src/main/**/build/
!**/src/test/**/build/

### STS ###
.apt_generated
.classpath
.factorypath
.project
.settings
.springBeans
.sts4-cache
bin/
!**/src/main/**/bin/
!**/src/test/**/bin/

### IntelliJ IDEA ###
.idea
*.iws
*.iml
*.ipr
out/
!**/src/main/**/out/
!**/src/test/**/out/

### NetBeans ###
/nbproject/private/
/nbbuild/
/dist/
/nbdist/
/.nb-gradle/

### VS Code ###
.vscode/

```

# .idea/.gitignore

```
# Default ignored files
/shelf/
/workspace.xml
# Editor-based HTTP Client requests
/httpRequests/
# Datasource local storage ignored files
/dataSources/
/dataSources.local.xml

```

# .idea/compiler.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="CompilerConfiguration">
    <annotationProcessing>
      <profile name="Gradle Imported" enabled="true">
        <outputRelativeToContentRoot value="true" />
        <processorPath useClasspath="false">
          <entry name="$USER_HOME$/.gradle/caches/modules-2/files-2.1/org.projectlombok/lombok/1.18.36/5a30490a6e14977d97d9c73c924c1f1b5311ea95/lombok-1.18.36.jar" />
        </processorPath>
        <module name="mvc2.main" />
      </profile>
    </annotationProcessing>
    <bytecodeTargetLevel target="17" />
  </component>
</project>
```

# .idea/encodings.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="Encoding" native2AsciiForPropertiesFiles="true" defaultCharsetForPropertiesFiles="UTF-8" />
</project>
```

# .idea/gradle.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="GradleMigrationSettings" migrationVersion="1" />
  <component name="GradleSettings">
    <option name="linkedExternalProjectsSettings">
      <GradleProjectSettings>
        <option name="externalProjectPath" value="$PROJECT_DIR$" />
        <option name="modules">
          <set>
            <option value="$PROJECT_DIR$" />
          </set>
        </option>
      </GradleProjectSettings>
    </option>
  </component>
</project>
```

# .idea/jarRepositories.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="RemoteRepositoriesConfiguration">
    <remote-repository>
      <option name="id" value="central" />
      <option name="name" value="Maven Central repository" />
      <option name="url" value="https://repo1.maven.org/maven2" />
    </remote-repository>
    <remote-repository>
      <option name="id" value="jboss.community" />
      <option name="name" value="JBoss Community repository" />
      <option name="url" value="https://repository.jboss.org/nexus/content/repositories/public/" />
    </remote-repository>
    <remote-repository>
      <option name="id" value="MavenRepo" />
      <option name="name" value="MavenRepo" />
      <option name="url" value="https://repo.maven.apache.org/maven2/" />
    </remote-repository>
  </component>
</project>
```

# .idea/misc.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="ExternalStorageConfigurationManager" enabled="true" />
  <component name="FrameworkDetectionExcludesConfiguration">
    <file type="web" url="file://$PROJECT_DIR$" />
  </component>
  <component name="ProjectRootManager" version="2" languageLevel="JDK_17" project-jdk-name="openjdk-23" project-jdk-type="JavaSDK" />
</project>
```

# .idea/modules.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="ProjectModuleManager">
    <modules>
      <module fileurl="file://$PROJECT_DIR$/.idea/modules/mvc2.main.iml" filepath="$PROJECT_DIR$/.idea/modules/mvc2.main.iml" />
    </modules>
  </component>
</project>
```

# .idea/modules/mvc2.main.iml

```iml
<?xml version="1.0" encoding="UTF-8"?>
<module version="4">
  <component name="AdditionalModuleElements">
    <content url="file://$MODULE_DIR$/../../build/generated/sources/annotationProcessor/java/main">
      <sourceFolder url="file://$MODULE_DIR$/../../build/generated/sources/annotationProcessor/java/main" isTestSource="false" generated="true" />
    </content>
  </component>
</module>
```

# .idea/uiDesigner.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="Palette2">
    <group name="Swing">
      <item class="com.intellij.uiDesigner.HSpacer" tooltip-text="Horizontal Spacer" icon="/com/intellij/uiDesigner/icons/hspacer.svg" removable="false" auto-create-binding="false" can-attach-label="false">
        <default-constraints vsize-policy="1" hsize-policy="6" anchor="0" fill="1" />
      </item>
      <item class="com.intellij.uiDesigner.VSpacer" tooltip-text="Vertical Spacer" icon="/com/intellij/uiDesigner/icons/vspacer.svg" removable="false" auto-create-binding="false" can-attach-label="false">
        <default-constraints vsize-policy="6" hsize-policy="1" anchor="0" fill="2" />
      </item>
      <item class="javax.swing.JPanel" icon="/com/intellij/uiDesigner/icons/panel.svg" removable="false" auto-create-binding="false" can-attach-label="false">
        <default-constraints vsize-policy="3" hsize-policy="3" anchor="0" fill="3" />
      </item>
      <item class="javax.swing.JScrollPane" icon="/com/intellij/uiDesigner/icons/scrollPane.svg" removable="false" auto-create-binding="false" can-attach-label="true">
        <default-constraints vsize-policy="7" hsize-policy="7" anchor="0" fill="3" />
      </item>
      <item class="javax.swing.JButton" icon="/com/intellij/uiDesigner/icons/button.svg" removable="false" auto-create-binding="true" can-attach-label="false">
        <default-constraints vsize-policy="0" hsize-policy="3" anchor="0" fill="1" />
        <initial-values>
          <property name="text" value="Button" />
        </initial-values>
      </item>
      <item class="javax.swing.JRadioButton" icon="/com/intellij/uiDesigner/icons/radioButton.svg" removable="false" auto-create-binding="true" can-attach-label="false">
        <default-constraints vsize-policy="0" hsize-policy="3" anchor="8" fill="0" />
        <initial-values>
          <property name="text" value="RadioButton" />
        </initial-values>
      </item>
      <item class="javax.swing.JCheckBox" icon="/com/intellij/uiDesigner/icons/checkBox.svg" removable="false" auto-create-binding="true" can-attach-label="false">
        <default-constraints vsize-policy="0" hsize-policy="3" anchor="8" fill="0" />
        <initial-values>
          <property name="text" value="CheckBox" />
        </initial-values>
      </item>
      <item class="javax.swing.JLabel" icon="/com/intellij/uiDesigner/icons/label.svg" removable="false" auto-create-binding="false" can-attach-label="false">
        <default-constraints vsize-policy="0" hsize-policy="0" anchor="8" fill="0" />
        <initial-values>
          <property name="text" value="Label" />
        </initial-values>
      </item>
      <item class="javax.swing.JTextField" icon="/com/intellij/uiDesigner/icons/textField.svg" removable="false" auto-create-binding="true" can-attach-label="true">
        <default-constraints vsize-policy="0" hsize-policy="6" anchor="8" fill="1">
          <preferred-size width="150" height="-1" />
        </default-constraints>
      </item>
      <item class="javax.swing.JPasswordField" icon="/com/intellij/uiDesigner/icons/passwordField.svg" removable="false" auto-create-binding="true" can-attach-label="true">
        <default-constraints vsize-policy="0" hsize-policy="6" anchor="8" fill="1">
          <preferred-size width="150" height="-1" />
        </default-constraints>
      </item>
      <item class="javax.swing.JFormattedTextField" icon="/com/intellij/uiDesigner/icons/formattedTextField.svg" removable="false" auto-create-binding="true" can-attach-label="true">
        <default-constraints vsize-policy="0" hsize-policy="6" anchor="8" fill="1">
          <preferred-size width="150" height="-1" />
        </default-constraints>
      </item>
      <item class="javax.swing.JTextArea" icon="/com/intellij/uiDesigner/icons/textArea.svg" removable="false" auto-create-binding="true" can-attach-label="true">
        <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3">
          <preferred-size width="150" height="50" />
        </default-constraints>
      </item>
      <item class="javax.swing.JTextPane" icon="/com/intellij/uiDesigner/icons/textPane.svg" removable="false" auto-create-binding="true" can-attach-label="true">
        <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3">
          <preferred-size width="150" height="50" />
        </default-constraints>
      </item>
      <item class="javax.swing.JEditorPane" icon="/com/intellij/uiDesigner/icons/editorPane.svg" removable="false" auto-create-binding="true" can-attach-label="true">
        <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3">
          <preferred-size width="150" height="50" />
        </default-constraints>
      </item>
      <item class="javax.swing.JComboBox" icon="/com/intellij/uiDesigner/icons/comboBox.svg" removable="false" auto-create-binding="true" can-attach-label="true">
        <default-constraints vsize-policy="0" hsize-policy="2" anchor="8" fill="1" />
      </item>
      <item class="javax.swing.JTable" icon="/com/intellij/uiDesigner/icons/table.svg" removable="false" auto-create-binding="true" can-attach-label="false">
        <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3">
          <preferred-size width="150" height="50" />
        </default-constraints>
      </item>
      <item class="javax.swing.JList" icon="/com/intellij/uiDesigner/icons/list.svg" removable="false" auto-create-binding="true" can-attach-label="false">
        <default-constraints vsize-policy="6" hsize-policy="2" anchor="0" fill="3">
          <preferred-size width="150" height="50" />
        </default-constraints>
      </item>
      <item class="javax.swing.JTree" icon="/com/intellij/uiDesigner/icons/tree.svg" removable="false" auto-create-binding="true" can-attach-label="false">
        <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3">
          <preferred-size width="150" height="50" />
        </default-constraints>
      </item>
      <item class="javax.swing.JTabbedPane" icon="/com/intellij/uiDesigner/icons/tabbedPane.svg" removable="false" auto-create-binding="true" can-attach-label="false">
        <default-constraints vsize-policy="3" hsize-policy="3" anchor="0" fill="3">
          <preferred-size width="200" height="200" />
        </default-constraints>
      </item>
      <item class="javax.swing.JSplitPane" icon="/com/intellij/uiDesigner/icons/splitPane.svg" removable="false" auto-create-binding="false" can-attach-label="false">
        <default-constraints vsize-policy="3" hsize-policy="3" anchor="0" fill="3">
          <preferred-size width="200" height="200" />
        </default-constraints>
      </item>
      <item class="javax.swing.JSpinner" icon="/com/intellij/uiDesigner/icons/spinner.svg" removable="false" auto-create-binding="true" can-attach-label="true">
        <default-constraints vsize-policy="0" hsize-policy="6" anchor="8" fill="1" />
      </item>
      <item class="javax.swing.JSlider" icon="/com/intellij/uiDesigner/icons/slider.svg" removable="false" auto-create-binding="true" can-attach-label="false">
        <default-constraints vsize-policy="0" hsize-policy="6" anchor="8" fill="1" />
      </item>
      <item class="javax.swing.JSeparator" icon="/com/intellij/uiDesigner/icons/separator.svg" removable="false" auto-create-binding="false" can-attach-label="false">
        <default-constraints vsize-policy="6" hsize-policy="6" anchor="0" fill="3" />
      </item>
      <item class="javax.swing.JProgressBar" icon="/com/intellij/uiDesigner/icons/progressbar.svg" removable="false" auto-create-binding="true" can-attach-label="false">
        <default-constraints vsize-policy="0" hsize-policy="6" anchor="0" fill="1" />
      </item>
      <item class="javax.swing.JToolBar" icon="/com/intellij/uiDesigner/icons/toolbar.svg" removable="false" auto-create-binding="false" can-attach-label="false">
        <default-constraints vsize-policy="0" hsize-policy="6" anchor="0" fill="1">
          <preferred-size width="-1" height="20" />
        </default-constraints>
      </item>
      <item class="javax.swing.JToolBar$Separator" icon="/com/intellij/uiDesigner/icons/toolbarSeparator.svg" removable="false" auto-create-binding="false" can-attach-label="false">
        <default-constraints vsize-policy="0" hsize-policy="0" anchor="0" fill="1" />
      </item>
      <item class="javax.swing.JScrollBar" icon="/com/intellij/uiDesigner/icons/scrollbar.svg" removable="false" auto-create-binding="true" can-attach-label="false">
        <default-constraints vsize-policy="6" hsize-policy="0" anchor="0" fill="2" />
      </item>
    </group>
  </component>
</project>
```

# .idea/vcs.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="VcsDirectoryMappings">
    <mapping directory="$PROJECT_DIR$/.." vcs="Git" />
  </component>
</project>
```

# .idea/workspace.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project version="4">
  <component name="AutoImportSettings">
    <option name="autoReloadType" value="SELECTIVE" />
  </component>
  <component name="ChangeListManager">
    <list default="true" id="7523d1a9-6fca-4015-9950-14d840a640b1" name="Changes" comment="[init] 스프링 핵심 원리2 최초 커밋">
      <change afterPath="$PROJECT_DIR$/src/main/java/com/jwj/mvc2/MessageSourceConfig.java" afterDir="false" />
      <change afterPath="$PROJECT_DIR$/src/main/resources/messages.properties" afterDir="false" />
      <change afterPath="$PROJECT_DIR$/src/main/resources/messages_en.properties" afterDir="false" />
      <change afterPath="$PROJECT_DIR$/src/test/java/com/jwj/mvc2/message/MessageSourceTest.java" afterDir="false" />
      <change beforePath="$PROJECT_DIR$/../ErrorLog/Jpa.md" beforeDir="false" afterPath="$PROJECT_DIR$/../ErrorLog/Jpa.md" afterDir="false" />
      <change beforePath="$PROJECT_DIR$/../Readable Code/src/minesweeper/legacy/MinesweeperGame.java" beforeDir="false" afterPath="$PROJECT_DIR$/../Readable Code/src/minesweeper/legacy/MinesweeperGame.java" afterDir="false" />
      <change beforePath="$PROJECT_DIR$/../concurrency/infra/docker-compose.yml" beforeDir="false" afterPath="$PROJECT_DIR$/../concurrency/infra/docker-compose.yml" afterDir="false" />
      <change beforePath="$PROJECT_DIR$/../concurrency/src/main/resources/application.yml" beforeDir="false" afterPath="$PROJECT_DIR$/../concurrency/src/main/resources/application.yml" afterDir="false" />
      <change beforePath="$PROJECT_DIR$/../monitoring/build.gradle" beforeDir="false" afterPath="$PROJECT_DIR$/../monitoring/build.gradle" afterDir="false" />
      <change beforePath="$PROJECT_DIR$/src/main/resources/application.properties" beforeDir="false" afterPath="$PROJECT_DIR$/src/main/resources/application.properties" afterDir="false" />
      <change beforePath="$PROJECT_DIR$/../mysql-performance-optimization/README.md" beforeDir="false" afterPath="$PROJECT_DIR$/../mysql-performance-optimization/README.md" afterDir="false" />
      <change beforePath="$PROJECT_DIR$/../mysql-performance-optimization/docker-compose.yml" beforeDir="false" afterPath="$PROJECT_DIR$/../mysql-performance-optimization/docker-compose.yml" afterDir="false" />
      <change beforePath="$PROJECT_DIR$/../springbatch/build.gradle" beforeDir="false" afterPath="$PROJECT_DIR$/../springbatch/build.gradle" afterDir="false" />
    </list>
    <option name="SHOW_DIALOG" value="false" />
    <option name="HIGHLIGHT_CONFLICTS" value="true" />
    <option name="HIGHLIGHT_NON_ACTIVE_CHANGELIST" value="false" />
    <option name="LAST_RESOLUTION" value="IGNORE" />
  </component>
  <component name="ChangesViewManager">
    <option name="groupingKeys">
      <option value="directory" />
    </option>
  </component>
  <component name="ExternalProjectsData">
    <projectState path="$PROJECT_DIR$">
      <ProjectState />
    </projectState>
  </component>
  <component name="FileTemplateManagerImpl">
    <option name="RECENT_TEMPLATES">
      <list>
        <option value="Class" />
      </list>
    </option>
  </component>
  <component name="Git.Settings">
    <option name="RECENT_GIT_ROOT_PATH" value="$PROJECT_DIR$/.." />
  </component>
  <component name="GitHubPullRequestSearchHistory">{
  &quot;lastFilter&quot;: {
    &quot;state&quot;: &quot;OPEN&quot;,
    &quot;assignee&quot;: &quot;dnwls16071&quot;
  }
}</component>
  <component name="GithubPullRequestsUISettings">{
  &quot;selectedUrlAndAccountId&quot;: {
    &quot;url&quot;: &quot;https://github.com/dnwls16071/Backend_Study_TIL.git&quot;,
    &quot;accountId&quot;: &quot;08eea81c-5d3f-40df-8610-1f9937801f65&quot;
  }
}</component>
  <component name="ProjectColorInfo">{
  &quot;associatedIndex&quot;: 6
}</component>
  <component name="ProjectId" id="2sFd8KtFWPbkdwkfW6rFQzm7QwH" />
  <component name="ProjectViewState">
    <option name="autoscrollToSource" value="true" />
    <option name="openDirectoriesWithSingleClick" value="true" />
    <option name="showExcludedFiles" value="false" />
    <option name="showLibraryContents" value="true" />
    <option name="showScratchesAndConsoles" value="false" />
    <option name="sortByType" value="true" />
    <option name="sortKey" value="BY_TYPE" />
  </component>
  <component name="PropertiesComponent">{
  &quot;keyToString&quot;: {
    &quot;Gradle.MessageSourceTest.executor&quot;: &quot;Run&quot;,
    &quot;JUnit.MessageSourceTest.executor&quot;: &quot;Run&quot;,
    &quot;RunOnceActivity.ShowReadmeOnStart&quot;: &quot;true&quot;,
    &quot;git-widget-placeholder&quot;: &quot;main&quot;,
    &quot;kotlin-language-version-configured&quot;: &quot;true&quot;,
    &quot;last_opened_file_path&quot;: &quot;/Users/jwj/Desktop/Backend_Study_TIL/mvc2&quot;,
    &quot;node.js.detected.package.eslint&quot;: &quot;true&quot;,
    &quot;node.js.detected.package.tslint&quot;: &quot;true&quot;,
    &quot;node.js.selected.package.eslint&quot;: &quot;(autodetect)&quot;,
    &quot;node.js.selected.package.tslint&quot;: &quot;(autodetect)&quot;,
    &quot;nodejs_package_manager_path&quot;: &quot;npm&quot;,
    &quot;settings.editor.selected.configurable&quot;: &quot;File.Encoding&quot;,
    &quot;vue.rearranger.settings.migration&quot;: &quot;true&quot;
  }
}</component>
  <component name="RunManager" selected="Gradle.MessageSourceTest">
    <configuration name="MessageSourceTest" type="GradleRunConfiguration" factoryName="Gradle" temporary="true">
      <ExternalSystemSettings>
        <option name="executionName" />
        <option name="externalProjectPath" value="$PROJECT_DIR$" />
        <option name="externalSystemIdString" value="GRADLE" />
        <option name="scriptParameters" value="" />
        <option name="taskDescriptions">
          <list />
        </option>
        <option name="taskNames">
          <list>
            <option value=":test" />
            <option value="--tests" />
            <option value="&quot;com.jwj.mvc2.message.MessageSourceTest&quot;" />
          </list>
        </option>
        <option name="vmOptions" value="-Dfile.encoding=UTF-8" />
      </ExternalSystemSettings>
      <ExternalSystemDebugServerProcess>false</ExternalSystemDebugServerProcess>
      <ExternalSystemReattachDebugProcess>true</ExternalSystemReattachDebugProcess>
      <DebugAllEnabled>false</DebugAllEnabled>
      <RunAsTest>true</RunAsTest>
      <method v="2" />
    </configuration>
    <configuration name="MessageSourceTest" type="JUnit" factoryName="JUnit" temporary="true" nameIsGenerated="true">
      <module name="mvc2.test" />
      <extension name="coverage">
        <pattern>
          <option name="PATTERN" value="com.jwj.mvc2.message.*" />
          <option name="ENABLED" value="true" />
        </pattern>
      </extension>
      <option name="PACKAGE_NAME" value="com.jwj.mvc2.message" />
      <option name="MAIN_CLASS_NAME" value="com.jwj.mvc2.message.MessageSourceTest" />
      <option name="TEST_OBJECT" value="class" />
      <method v="2">
        <option name="Make" enabled="true" />
      </method>
    </configuration>
    <configuration name="Mvc2Application" type="SpringBootApplicationConfigurationType" factoryName="Spring Boot" nameIsGenerated="true">
      <module name="mvc2.main" />
      <option name="SPRING_BOOT_MAIN_CLASS" value="com.jwj.mvc2.Mvc2Application" />
      <method v="2">
        <option name="Make" enabled="true" />
      </method>
    </configuration>
    <list>
      <item itemvalue="Gradle.MessageSourceTest" />
      <item itemvalue="JUnit.MessageSourceTest" />
      <item itemvalue="Spring Boot.Mvc2Application" />
    </list>
    <recent_temporary>
      <list>
        <item itemvalue="Gradle.MessageSourceTest" />
        <item itemvalue="JUnit.MessageSourceTest" />
      </list>
    </recent_temporary>
  </component>
  <component name="SharedIndexes">
    <attachedChunks>
      <set>
        <option value="bundled-jdk-9823dce3aa75-28b599e66164-intellij.indexing.shared.core-IU-242.23339.11" />
        <option value="bundled-js-predefined-d6986cc7102b-5c90d61e3bab-JavaScript-IU-242.23339.11" />
      </set>
    </attachedChunks>
  </component>
  <component name="SpellCheckerSettings" RuntimeDictionaries="0" Folders="0" CustomDictionaries="0" DefaultDictionary="application-level" UseSingleDictionary="true" transferred="true" />
  <component name="TaskManager">
    <task active="true" id="Default" summary="Default task">
      <changelist id="7523d1a9-6fca-4015-9950-14d840a640b1" name="Changes" comment="" />
      <created>1738055096041</created>
      <option name="number" value="Default" />
      <option name="presentableId" value="Default" />
      <updated>1738055096041</updated>
      <workItem from="1738055097038" duration="1308000" />
      <workItem from="1738056414591" duration="76000" />
      <workItem from="1738056497305" duration="114000" />
    </task>
    <task id="LOCAL-00001" summary="[init] 스프링 핵심 원리2 최초 커밋">
      <option name="closed" value="true" />
      <created>1738055190018</created>
      <option name="number" value="00001" />
      <option name="presentableId" value="LOCAL-00001" />
      <option name="project" value="LOCAL" />
      <updated>1738055190018</updated>
    </task>
    <option name="localTasksCounter" value="2" />
    <servers />
  </component>
  <component name="TypeScriptGeneratedFilesManager">
    <option name="version" value="3" />
  </component>
  <component name="VcsManagerConfiguration">
    <MESSAGE value="[init] 스프링 핵심 원리2 최초 커밋" />
    <option name="LAST_COMMIT_MESSAGE" value="[init] 스프링 핵심 원리2 최초 커밋" />
  </component>
  <component name="XSLT-Support.FileAssociations.UIState">
    <expand />
    <select />
  </component>
</project>
```

# build.gradle

```gradle
plugins {
	id 'java'
	id 'org.springframework.boot' version '3.4.2'
	id 'io.spring.dependency-management' version '1.1.7'
}

group = 'com.jwj'
version = '0.0.1-SNAPSHOT'

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(17)
	}
}

configurations {
	compileOnly {
		extendsFrom annotationProcessor
	}
}

repositories {
	mavenCentral()
}

dependencies {
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	testRuntimeOnly 'org.junit.platform:junit-platform-launcher'
}

tasks.named('test') {
	useJUnitPlatform()
}

```

# gradle/wrapper/gradle-wrapper.jar

This is a binary file of the type: Binary

# gradle/wrapper/gradle-wrapper.properties

```properties
distributionBase=GRADLE_USER_HOME
distributionPath=wrapper/dists
distributionUrl=https\://services.gradle.org/distributions/gradle-8.11.1-bin.zip
networkTimeout=10000
validateDistributionUrl=true
zipStoreBase=GRADLE_USER_HOME
zipStorePath=wrapper/dists

```

# gradlew

```
#!/bin/sh

#
# Copyright © 2015-2021 the original authors.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      https://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# SPDX-License-Identifier: Apache-2.0
#

##############################################################################
#
#   Gradle start up script for POSIX generated by Gradle.
#
#   Important for running:
#
#   (1) You need a POSIX-compliant shell to run this script. If your /bin/sh is
#       noncompliant, but you have some other compliant shell such as ksh or
#       bash, then to run this script, type that shell name before the whole
#       command line, like:
#
#           ksh Gradle
#
#       Busybox and similar reduced shells will NOT work, because this script
#       requires all of these POSIX shell features:
#         * functions;
#         * expansions «$var», «${var}», «${var:-default}», «${var+SET}»,
#           «${var#prefix}», «${var%suffix}», and «$( cmd )»;
#         * compound commands having a testable exit status, especially «case»;
#         * various built-in commands including «command», «set», and «ulimit».
#
#   Important for patching:
#
#   (2) This script targets any POSIX shell, so it avoids extensions provided
#       by Bash, Ksh, etc; in particular arrays are avoided.
#
#       The "traditional" practice of packing multiple parameters into a
#       space-separated string is a well documented source of bugs and security
#       problems, so this is (mostly) avoided, by progressively accumulating
#       options in "$@", and eventually passing that to Java.
#
#       Where the inherited environment variables (DEFAULT_JVM_OPTS, JAVA_OPTS,
#       and GRADLE_OPTS) rely on word-splitting, this is performed explicitly;
#       see the in-line comments for details.
#
#       There are tweaks for specific operating systems such as AIX, CygWin,
#       Darwin, MinGW, and NonStop.
#
#   (3) This script is generated from the Groovy template
#       https://github.com/gradle/gradle/blob/HEAD/platforms/jvm/plugins-application/src/main/resources/org/gradle/api/internal/plugins/unixStartScript.txt
#       within the Gradle project.
#
#       You can find Gradle at https://github.com/gradle/gradle/.
#
##############################################################################

# Attempt to set APP_HOME

# Resolve links: $0 may be a link
app_path=$0

# Need this for daisy-chained symlinks.
while
    APP_HOME=${app_path%"${app_path##*/}"}  # leaves a trailing /; empty if no leading path
    [ -h "$app_path" ]
do
    ls=$( ls -ld "$app_path" )
    link=${ls#*' -> '}
    case $link in             #(
      /*)   app_path=$link ;; #(
      *)    app_path=$APP_HOME$link ;;
    esac
done

# This is normally unused
# shellcheck disable=SC2034
APP_BASE_NAME=${0##*/}
# Discard cd standard output in case $CDPATH is set (https://github.com/gradle/gradle/issues/25036)
APP_HOME=$( cd -P "${APP_HOME:-./}" > /dev/null && printf '%s
' "$PWD" ) || exit

# Use the maximum available, or set MAX_FD != -1 to use that value.
MAX_FD=maximum

warn () {
    echo "$*"
} >&2

die () {
    echo
    echo "$*"
    echo
    exit 1
} >&2

# OS specific support (must be 'true' or 'false').
cygwin=false
msys=false
darwin=false
nonstop=false
case "$( uname )" in                #(
  CYGWIN* )         cygwin=true  ;; #(
  Darwin* )         darwin=true  ;; #(
  MSYS* | MINGW* )  msys=true    ;; #(
  NONSTOP* )        nonstop=true ;;
esac

CLASSPATH=$APP_HOME/gradle/wrapper/gradle-wrapper.jar


# Determine the Java command to use to start the JVM.
if [ -n "$JAVA_HOME" ] ; then
    if [ -x "$JAVA_HOME/jre/sh/java" ] ; then
        # IBM's JDK on AIX uses strange locations for the executables
        JAVACMD=$JAVA_HOME/jre/sh/java
    else
        JAVACMD=$JAVA_HOME/bin/java
    fi
    if [ ! -x "$JAVACMD" ] ; then
        die "ERROR: JAVA_HOME is set to an invalid directory: $JAVA_HOME

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
else
    JAVACMD=java
    if ! command -v java >/dev/null 2>&1
    then
        die "ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.

Please set the JAVA_HOME variable in your environment to match the
location of your Java installation."
    fi
fi

# Increase the maximum file descriptors if we can.
if ! "$cygwin" && ! "$darwin" && ! "$nonstop" ; then
    case $MAX_FD in #(
      max*)
        # In POSIX sh, ulimit -H is undefined. That's why the result is checked to see if it worked.
        # shellcheck disable=SC2039,SC3045
        MAX_FD=$( ulimit -H -n ) ||
            warn "Could not query maximum file descriptor limit"
    esac
    case $MAX_FD in  #(
      '' | soft) :;; #(
      *)
        # In POSIX sh, ulimit -n is undefined. That's why the result is checked to see if it worked.
        # shellcheck disable=SC2039,SC3045
        ulimit -n "$MAX_FD" ||
            warn "Could not set maximum file descriptor limit to $MAX_FD"
    esac
fi

# Collect all arguments for the java command, stacking in reverse order:
#   * args from the command line
#   * the main class name
#   * -classpath
#   * -D...appname settings
#   * --module-path (only if needed)
#   * DEFAULT_JVM_OPTS, JAVA_OPTS, and GRADLE_OPTS environment variables.

# For Cygwin or MSYS, switch paths to Windows format before running java
if "$cygwin" || "$msys" ; then
    APP_HOME=$( cygpath --path --mixed "$APP_HOME" )
    CLASSPATH=$( cygpath --path --mixed "$CLASSPATH" )

    JAVACMD=$( cygpath --unix "$JAVACMD" )

    # Now convert the arguments - kludge to limit ourselves to /bin/sh
    for arg do
        if
            case $arg in                                #(
              -*)   false ;;                            # don't mess with options #(
              /?*)  t=${arg#/} t=/${t%%/*}              # looks like a POSIX filepath
                    [ -e "$t" ] ;;                      #(
              *)    false ;;
            esac
        then
            arg=$( cygpath --path --ignore --mixed "$arg" )
        fi
        # Roll the args list around exactly as many times as the number of
        # args, so each arg winds up back in the position where it started, but
        # possibly modified.
        #
        # NB: a `for` loop captures its iteration list before it begins, so
        # changing the positional parameters here affects neither the number of
        # iterations, nor the values presented in `arg`.
        shift                   # remove old arg
        set -- "$@" "$arg"      # push replacement arg
    done
fi


# Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
DEFAULT_JVM_OPTS='"-Xmx64m" "-Xms64m"'

# Collect all arguments for the java command:
#   * DEFAULT_JVM_OPTS, JAVA_OPTS, JAVA_OPTS, and optsEnvironmentVar are not allowed to contain shell fragments,
#     and any embedded shellness will be escaped.
#   * For example: A user cannot expect ${Hostname} to be expanded, as it is an environment variable and will be
#     treated as '${Hostname}' itself on the command line.

set -- \
        "-Dorg.gradle.appname=$APP_BASE_NAME" \
        -classpath "$CLASSPATH" \
        org.gradle.wrapper.GradleWrapperMain \
        "$@"

# Stop when "xargs" is not available.
if ! command -v xargs >/dev/null 2>&1
then
    die "xargs is not available"
fi

# Use "xargs" to parse quoted args.
#
# With -n1 it outputs one arg per line, with the quotes and backslashes removed.
#
# In Bash we could simply go:
#
#   readarray ARGS < <( xargs -n1 <<<"$var" ) &&
#   set -- "${ARGS[@]}" "$@"
#
# but POSIX shell has neither arrays nor command substitution, so instead we
# post-process each arg (as a line of input to sed) to backslash-escape any
# character that might be a shell metacharacter, then use eval to reverse
# that process (while maintaining the separation between arguments), and wrap
# the whole thing up as a single "set" statement.
#
# This will of course break if any of these variables contains a newline or
# an unmatched quote.
#

eval "set -- $(
        printf '%s\n' "$DEFAULT_JVM_OPTS $JAVA_OPTS $GRADLE_OPTS" |
        xargs -n1 |
        sed ' s~[^-[:alnum:]+,./:=@_]~\\&~g; ' |
        tr '\n' ' '
    )" '"$@"'

exec "$JAVACMD" "$@"

```

# gradlew.bat

```bat
@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem
@rem SPDX-License-Identifier: Apache-2.0
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Gradle startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem This is normally unused
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and GRADLE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH. 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo. 1>&2
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME% 1>&2
echo. 1>&2
echo Please set the JAVA_HOME variable in your environment to match the 1>&2
echo location of your Java installation. 1>&2

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar


@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*

:end
@rem End local scope for the variables with windows NT shell
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem Set variable GRADLE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%GRADLE_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

```

# HELP.md

```md
# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.2/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.2/gradle-plugin/packaging-oci-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.2/reference/web/servlet.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)


```

# settings.gradle

```gradle
rootProject.name = 'mvc2'

```

# src/main/java/com/jwj/mvc2/MessageSourceConfig.java

```java
package com.jwj.mvc2;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;

@Configuration
public class MessageSourceConfig {

	@Bean
	public MessageSource messageSource() {
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasenames("messages", "errors");
		resourceBundleMessageSource.setDefaultEncoding("utf-8");
		return resourceBundleMessageSource;
	}
}

```

# src/main/java/com/jwj/mvc2/Mvc2Application.java

```java
package com.jwj.mvc2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Mvc2Application {

	public static void main(String[] args) {
		SpringApplication.run(Mvc2Application.class, args);
	}

}

```

# src/main/java/com/jwj/mvc2/valid-start/domain/item/Item.java

```java
package com.jwj.mvc2.valid1.domain.item;
import lombok.Data;

@Data
public class Item {

    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}

```

# src/main/java/com/jwj/mvc2/valid-start/domain/item/ItemRepository.java

```java
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ItemRepository {

    private static final Map<Long, Item> store = new HashMap<>(); //static
    private static long sequence = 0L; //static

    public Item save(Item item) {
        item.setId(++sequence);
        store.put(item.getId(), item);
        return item;
    }

    public Item findById(Long id) {
        return store.get(id);
    }

    public List<Item> findAll() {
        return new ArrayList<>(store.values());
    }

    public void update(Long itemId, Item updateParam) {
        Item findItem = findById(itemId);
        findItem.setItemName(updateParam.getItemName());
        findItem.setPrice(updateParam.getPrice());
        findItem.setQuantity(updateParam.getQuantity());
    }

    public void clearStore() {
        store.clear();
    }

}

```

# src/main/java/com/jwj/mvc2/valid-start/itemservice/com.jwj.mvc2.TestDataInit.java

```java
import Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class com.jwj.mvc2.TestDataInit {

    private final ItemRepository itemRepository;

    /**
     * 테스트용 데이터 추가
     */
    @PostConstruct
    public void init() {
        itemRepository.save(new Item("itemA", 10000, 10));
        itemRepository.save(new Item("itemB", 20000, 20));
    }

}
```

# src/main/java/com/jwj/mvc2/valid-start/web/validation/ValidationItemControllerV1.java

```java
import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/validation/v1/items")
@RequiredArgsConstructor
public class ValidationItemControllerV1 {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model) {
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);
        return "validation/v1/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("item", new Item());
        return "validation/v1/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, RedirectAttributes redirectAttributes) {
        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);
        return "redirect:/validation/v1/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);
        return "validation/v1/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item) {
        itemRepository.update(itemId, item);
        return "redirect:/validation/v1/items/{itemId}";
    }

}


```

# src/main/resources/application.properties

```properties
spring.application.name=mvc2
spring.messages.basename=messages,config.i18n.messages
```

# src/main/resources/messages_en.properties

```properties
hello=hello
hello.name=hello {0}
```

# src/main/resources/messages.properties

```properties
hello=??
hello.name=?? {0}
```

# src/test/java/com/jwj/mvc2/Mvc2ApplicationTests.java

```java
package com.jwj.mvc2;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Mvc2ApplicationTests {

	@Test
	void contextLoads() {
	}

}

```

