PATH_TO_FX=$(pwd)/javafx-sdk-12.0.1/lib

find $PATH_TO_FX/{javafx.base.jar,javafx.graphics.jar,javafx.controls.jar,javafx.fxml.jar} -exec unzip -nq {} -d build \;

cp $PATH_TO_FX/{libprism*.dylib,libjavafx*.dylib,libglass.dylib,libdecora_sse.dylib} build

rm build/META-INF/MANIFEST.MF build/module-info.class
mkdir libs

jar --create --file=libs/algat.jar --main-class=algat.Launcher -C build .
