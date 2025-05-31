jlink ^
  --module-path "%JAVA_HOME%\jmods;C:\Users\JoseANG3L\Downloads\openjfx-24.0.1_windows-x64_bin-sdk\javafx-jmods-24.0.1" ^
  --add-modules javafx.controls,javafx.fxml,javafx.web ^
  --output runtime ^
  --compress=2 ^
  --strip-debug ^
  --no-header-files ^
  --no-man-pages
