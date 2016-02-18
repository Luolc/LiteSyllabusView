# LiteSyllabusView

![Screenshot](https://github.com/Luolc/LiteSyllabusView/blob/master/assets/mySyllabus.png)

This is a simple custom view for Android, which aims to provide an easy way to show syllabus view for users.

## Features

- Lite and brief User Interface.
- Wrapped OnClickListener and OnLongClickListener for CourseView and BlankSectionView.
- Support for ScrollView.

## Usage

Add Gradle dependency:

```gradle
dependencies {
    compile 'com.luolc.dev:litesyllabusview:1.0.0'
}
```

Layout, often wrapped by a ScrollView:

```xml
<ScrollView
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fillViewport="false">

    <com.luolc.litesyllabusview.view.LiteSyllabusView
        android:id="@+id/lite_syllabus"
        android:layout_width="match_parent"
        android:layout_height="wrap_content">
    </com.luolc.litesyllabusview.view.LiteSyllabusView>
</ScrollView>
```

Setup LiteSyllabusView in onCreate method:
```java
mLiteSyllabusView = (LiteSyllabusView) findViewById(R.id.lite_syllabus);
mLiteSyllabusView.setCourses(getCourses()); // Implement your custom courses data get method.
mLiteSyllabusView.hideWeekendColumn(true);
mLiteSyllabusView.setOnBlankViewClickListener(new LiteSyllabusView.OnBlankViewClickListener() {
    @Override
    public void onClick(int weekday, int section) {
        // TO-DO
    }

    @Override
    public void onLongClick(int weekday, int section) {
        // TO-DO
    }
});
mLiteSyllabusView.show();
```

## Compatibility

Android midSdkVersion 16.

## Change Log

### Version 1.0.0

- Initial Build 

## License

    Copyright 2016, Liangchen Luo.
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.