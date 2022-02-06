mkdir build

[ ! -d "/javafx-sdk-12.0.1" ] && echo "Missing Dependencies for JavaFX."
PATH_TO_FX=$(pwd)/javafx-sdk-12.0.1/lib

cd src/
javac --module-path $PATH_TO_FX --add-modules javafx.controls,javafx.fxml -d ../build/ $(find ./ -name "*.java")
cp *.fxml ../build/algat/
cp -r img ../build/algat/
cp -r questions ../build/algat/