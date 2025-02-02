package pl.patrykkukula.Menu;

import lombok.NoArgsConstructor;
import pl.patrykkukula.Builders.InstallationBuilder;
import pl.patrykkukula.Builders.PvModuleBuilder;
import pl.patrykkukula.Model.InstallationList;
import pl.patrykkukula.Model.PvModule;
import pl.patrykkukula.Utils.ScannerUtil;

@NoArgsConstructor
public class MenuHandler {
    boolean shouldContinue = true;
    PvModule pvModule;
    InstallationList installationList = new InstallationList();


    public void start() {
        printWelcomeMessage();
        while (shouldContinue) {
            try {
                printMainMenu();
                int action = ScannerUtil.readInt();
                handleAction(action);
            } catch (IllegalArgumentException ex) {
                System.out.println("Nieprawidłowy format danych. Upewnij się, że podałeś właściwą wartość");
            }
        }
        ScannerUtil.closeScanner();
    }

    private void printWelcomeMessage(){
        System.out.println("""
                .:: Witaj w programie do tworzenia list materiałowych instalacji PV ::.
                    Po podaniu wymaganych danych wejściowych program wyliczy podstawowe
                    materiały konstrukcyjne i elektryczne. Postępuj zgodnie z instrukcjami
                                         widocznymi na ekranie
                    _________________________________________________________________________________
                """);
    }
    private void printMainMenu(){
        System.out.println("Wybierz, co chcesz zrobić");
        System.out.println("[1] - Stwórz listę materiałową");
        System.out.println("[2] - Zakończ");
    }
    private void handleAction(int action){
        try {
        switch (MenuOption.fromCode(action)) {

                case CREATE -> {
                    PvModuleBuilder pvModuleBuilder = new PvModuleBuilder();
                    InstallationBuilder installationBuilder = new InstallationBuilder(installationList);
                    System.out.println("Podaj wymagane dane wejściowe, wyświetlane w następnych wierszach");
                    if (pvModule == null) {
                        pvModule = pvModuleBuilder.build();
                        if (pvModule != null) {
                            System.out.println("Moduł PV został poprawnie utworzony");
                        } else System.out.println("Niepowodzenie w dodawaniu danych modułu. Wprowadź dane ponownie");
                    }
                    installationList = installationBuilder.build(pvModule);
                }
                case EXIT -> shouldContinue = false;
                default -> throw new IllegalArgumentException();
            }
        }
        catch (IllegalArgumentException ex){
            System.out.println("Nieprawidłowa opcja menu. Wybierz [1] lub [2]");
        }
    }
}
