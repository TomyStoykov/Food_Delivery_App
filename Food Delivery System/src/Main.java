public class Main {
    public static void main(String[] args){
        ApplicationInitializer initializer = new ApplicationInitializer();
        UserInterface userInterface = initializer.initialize();
        userInterface.startShopping();
    }
}